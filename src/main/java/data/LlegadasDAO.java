package data;

import model.Docente;
import model.Estudiante;
import org.sqlite.SQLiteConnection;
import utils.Alertas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
