package data;

import utils.Alertas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigDAO {
    public boolean establecerHora(String hora){
        String query = "UPDATE config_hora SET HORA_LIMITE = ?";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1,hora);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas.mostrarError("Error SQL: "+e.getMessage());
            return false;
        }finally {
            ConexionSQLite.cerrarConexion();
        }
    }

    public String obtenerHora(){
        String query = "SELECT HORA_LIMITE FROM config_hora";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("HORA_LIMITE");
            }
        } catch (SQLException e) {
            Alertas.mostrarError("Error SQL: "+e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return null;
    }
}
