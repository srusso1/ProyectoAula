package data;

import model.Docente;
import model.Estudiante;
import utils.Alertas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
