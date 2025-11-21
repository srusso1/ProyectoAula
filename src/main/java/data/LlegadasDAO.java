package data;

import model.Docente;
import model.Estudiante;
import org.sqlite.SQLiteConnection;
import utils.Alertas;
import utils.infoGrado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class LlegadasDAO {
    public boolean registrarLlegada(int IDEstudiante, int IDDocente, String fecha, int caso, String info){
        String query = "INSERT INTO llegadas (ID_ESTUDIANTE, ID_DOCENTE, FECHA, ESTADO, INFO) VALUES (?, ?, ?, ?, ?)";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, IDEstudiante);
            ps.setInt(2, IDDocente);
            ps.setString(3, fecha);
            switch (caso){
                case 0:
                    ps.setInt(4, 0);
                    ps.setString(5, info);
                    break;
                case 1:
                    ps.setString(5, "El estudiante llego " + info + " tarde");
                    ps.setInt(4, 1);
                    break;
            }
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }catch(SQLException e){
            Alertas.mostrarError("Error SQL: "+e.getMessage());
            return false;
        }finally {
            ConexionSQLite.cerrarConexion();
        }
    }

    public int conteoLlegadas(int id_docente, int casoLlegadas){
        String query = "SELECT COUNT(*) FROM llegadas WHERE ID_DOCENTE = ?";
        String query2 = "SELECT COUNT(*) FROM llegadas WHERE ID_DOCENTE = ? AND ESTADO = 1";


        int cantidad;
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = switch (casoLlegadas) {
                case 0 -> conexion.prepareStatement(query);
                case 1 -> conexion.prepareStatement(query2);
                default -> null;
            };

            Objects.requireNonNull(ps).setInt(1, id_docente);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                cantidad = rs.getInt("COUNT(*)");
            }else{
                cantidad = 0;
            }
            return cantidad;
        } catch (SQLException e) {
            Alertas.mostrarError("ERROR SQL: " + e.getMessage());
            return -1;
        }finally {
            ConexionSQLite.cerrarConexion();
        }
    }

    public ArrayList<Integer> infoIngresosGrado(){
        ArrayList<Integer> infoLlegadas = new ArrayList<Integer>();

        String query = "SELECT e.GRADO, SUM(CASE WHEN l.ESTADO = 1 THEN 1 ELSE 0 END) AS 'LLEGADAS_TARDE' " +
                "FROM estudiantes e LEFT JOIN llegadas l ON e.ID = l.ID_ESTUDIANTE GROUP BY e.GRADO";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                infoLlegadas.add(rs.getInt("LLEGADAS_TARDE"));
            }
            return infoLlegadas;
        } catch (SQLException e) {
            Alertas.mostrarError("Error SQL: " + e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return infoLlegadas;
    }

    public ArrayList<String[]> infoIngresosMes(){
        ArrayList<String[]> infoIngresosMes = new ArrayList<>();
        String query = "SELECT CASE SUBSTR(FECHA, 4, 2) WHEN '02' THEN 'FEBRERO' WHEN '03' THEN 'MARZO' WHEN '04' THEN 'ABRIL' " +
                "WHEN '05' THEN 'MAYO' WHEN '06' THEN 'JUNIO' WHEN '07' THEN 'JULIO' WHEN '08' THEN 'AGOSTO' WHEN '09' THEN 'SEPTIEMBRE' " +
                "WHEN '10' THEN 'OCTUBRE' WHEN '11' THEN 'NOVIEMBRE' END AS MES, " +
                "SUM(CASE WHEN ESTADO = 0 THEN 1 ELSE 0 END) AS INGRESOS_TIEMPO, " +
                "SUM(CASE WHEN ESTADO = 1 THEN 1 ELSE 0 END) AS INGRESOS_TARDE " +
                "FROM llegadas GROUP BY MES ORDER BY SUBSTR(FECHA, 4, 2)";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String[] info = new String[3];
                info[0] = rs.getString("MES");
                info[1] = rs.getString("INGRESOS_TIEMPO");
                info[2] = rs.getString("INGRESOS_TARDE");
                infoIngresosMes.add(info);
            }
            return infoIngresosMes;
        } catch (SQLException e) {
            Alertas.mostrarError("Error SQL: " + e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return null;
    }

    public ArrayList<infoGrado> infoConsultaGrados(int grado){
        ArrayList<infoGrado> lista = new ArrayList<>();
        String query = "SELECT e.NOMBRE || ' ' || e.APELLIDO AS 'NOMBRE COMPLETO', e.GRADO, l.FECHA, " +
                "CASE(ESTADO) WHEN 0 THEN 'Ingreso a tiempo' WHEN 1 THEN 'Ingreso tarde' END AS 'ESTADO', l.INFO " +
                "FROM estudiantes e LEFT JOIN llegadas l ON e.ID = l.ID_ESTUDIANTE WHERE e.GRADO = ?";

        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, grado);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                lista.add(new infoGrado(
                        rs.getString("NOMBRE COMPLETO"),
                        rs.getString("GRADO"),
                        rs.getString("FECHA"),
                        rs.getString("ESTADO"),
                        rs.getString("INFO")
                ));
            }
            return lista;

        } catch (SQLException e) {
            Alertas.mostrarError("Error SQL: " + e.getMessage());
        } finally {
            ConexionSQLite.cerrarConexion();
        }
        return null;
    }

}
