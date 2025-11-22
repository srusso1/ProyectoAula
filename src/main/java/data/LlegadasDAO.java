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

    public int totalIngresos() {
        String query = "SELECT COUNT(*) FROM llegadas";
        try (Connection conexion = ConexionSQLite.conectar();
             PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            return rs.getInt(1);
        } catch (Exception e) {
            Alertas.mostrarError("Error SQL: " + e.getMessage());
            return 0;
        }finally {
            ConexionSQLite.cerrarConexion();
        }
    }


    public ArrayList<String[]> infoIngresosMes(){
        ArrayList<String[]> infoIngresosMes = new ArrayList<>();
        String query = "WITH MESES(M, NOMBRE) AS (VALUES ('02','FEBRERO'),('03','MARZO'),('04','ABRIL'),('05','MAYO'),('06','JUNIO'),('07','JULIO'),('08','AGOSTO')," +
                "('09','SEPTIEMBRE'),('10','OCTUBRE'),('11','NOVIEMBRE')) " +
                "SELECT MESES.NOMBRE AS MES, COALESCE(SUM(CASE WHEN l.ESTADO = 0 THEN 1 ELSE 0 END), 0) AS INGRESOS_TIEMPO, " +
                "COALESCE(SUM(CASE WHEN l.ESTADO = 1 THEN 1 ELSE 0 END), 0) AS INGRESOS_TARDE " +
                "FROM MESES LEFT JOIN llegadas l ON SUBSTR(l.FECHA, 4, 2) = MESES.M GROUP BY MESES.M, MESES.NOMBRE ORDER BY MESES.M";
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
        String query = "SELECT e.NOMBRE || ' ' || e.APELLIDO AS 'NOMBRE COMPLETO', " +
                "e.GRADO, COUNT(l.FECHA) AS 'REGISTROS', SUM(CASE WHEN l.ESTADO = 1 THEN 1 ELSE 0 END) AS 'INGRESOS_TARDE', " +
                "CASE WHEN SUM(CASE WHEN l.ESTADO = 1 THEN 1 ELSE 0 END) > 0 THEN 'Presenta ' || SUM(CASE WHEN l.ESTADO = 1 THEN 1 ELSE 0 END) || ' ingresos tarde' " +
                "ELSE 'No tiene ingresos tarde' END AS 'INFO' FROM estudiantes e LEFT JOIN llegadas l ON e.ID = l.ID_ESTUDIANTE WHERE e.GRADO = ? GROUP BY e.ID";

        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, grado);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String nombre = rs.getString("NOMBRE COMPLETO");
                String gradoEstudiante = rs.getString("GRADO");
                String registros = rs.getString("REGISTROS");
                String infoIngresos = rs.getString("INGRESOS_TARDE");
                String info = rs.getString("INFO");

                lista.add(new infoGrado(nombre, gradoEstudiante, registros, infoIngresos, info));
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
