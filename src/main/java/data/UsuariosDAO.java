package data;

import model.*;
import utils.Alertas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO {

    public UsuariosDAO(){};

    public Usuario validarUsuario(String usuario, String password){
        String query = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
        Usuario u = null;
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, usuario.trim());
            ps.setString(2, password.trim());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                long identificacion = rs.getLong("IDENTIFICACION");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                int rol = rs.getInt("ROL");
                String user = rs.getString("USUARIO");
                String pw = rs.getString("PASSWORD");

                switch (rol){
                    case 0:
                        u = new Docente(nombre, apellido, identificacion, user, pw);
                        break;
                    case 1:
                        u = new Administrador(nombre, apellido, identificacion, user, pw);
                        break;
                }
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            Alertas.mostrarError("ERROR SQL: " + e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return u;
    }

    public int cantidadDocentes(){
        String query = "SELECT COUNT(*) FROM usuarios WHERE ROL = 0";
        int c = 0;
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                c = rs.getInt("COUNT(*)");
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            Alertas.mostrarError("ERROR SQL: " + e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return c;
    }

    public Docente buscarDocente(long identificacion){
        String query = "SELECT * FROM usuarios WHERE IDENTIFICACION = ? AND ROL = 0";
        Docente docente = null;
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setLong(1, identificacion);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                long identi = rs.getLong("IDENTIFICACION");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                String usuario = rs.getString("USUARIO");
                String password = rs.getString("PASSWORD");
                docente = new Docente(nombre, apellido, identi, usuario, password);
            }

        }catch(SQLException e){
            Alertas.mostrarError("Error SQL: "+e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return docente;
    }

    public boolean registrarDocente(Docente docente){
        String query = "INSERT INTO usuarios (IDENTIFICACION, NOMBRE, APELLIDO) VALUES (?, ?, ?)";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setLong(1, docente.getIdentificacion());
            ps.setString(2, docente.getNombre());
            ps.setString(3, docente.getApellido());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }catch(SQLException e){
            Alertas.mostrarError("Error SQL: "+e.getMessage());
            return false;
        }finally {
            ConexionSQLite.cerrarConexion();
        }
    }

    public boolean agregarUsuarioPassword(long id, Docente docente){
        String query = "UPDATE usuarios  SET USUARIO = ?, PASSWORD = ?  WHERE IDENTIFICACION = ?";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, docente.getUsuario());
            ps.setString(2, docente.getPassword());
            ps.setLong(3, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }catch(SQLException e){
            Alertas.mostrarError("Error SQL: "+e.getMessage());
            return false;
        }finally {
            ConexionSQLite.cerrarConexion();
        }
    }

    public boolean buscarUsuario(String usuario){
        String query = "SELECT * FROM usuarios WHERE USUARIO = ?";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            Alertas.mostrarError("Error SQL: "+e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return false;
    }

}
