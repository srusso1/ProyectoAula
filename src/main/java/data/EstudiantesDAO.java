package data;

import model.Estudiante;
import utils.Alertas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EstudiantesDAO {

    public EstudiantesDAO() {};

    public boolean registrarEstudiante(Estudiante estudiante){
        String query = "INSERT INTO estudiantes (IDENTIFICACION, NOMBRE, APELLIDO, GRADO) VALUES (?, ?, ?, ?)";

        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setLong(1, estudiante.getIdentificacion());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getApellido());
            ps.setInt(4, estudiante.getGrado());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        }catch(SQLException e){
            Alertas.mostrarError("Error SQL: "+e.getMessage());
            return false;
        }finally {
            ConexionSQLite.cerrarConexion();
        }
    }

    public Estudiante buscarEstudiante(long identificacion){
        String query = "SELECT * FROM estudiantes WHERE IDENTIFICACION = ?";
        Estudiante estudiante = null;
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setLong(1, identificacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                long identi = rs.getLong("IDENTIFICACION");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                int grado = rs.getInt("GRADO");
                estudiante = new Estudiante(nombre,apellido,identi,grado);
            }
        }catch(SQLException e){
            Alertas.mostrarError("Error SQL: "+e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return estudiante;
    }

    public int cantidadEstudiantes(){
        String query = "SELECT COUNT(*) FROM estudiantes";
        int c = 0;
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
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

    public ArrayList<Estudiante> mostrarEstudiantes(){
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT * FROM estudiantes";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                long identificacion = rs.getLong("IDENTIFICACION");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                int grado = rs.getInt("GRADO");
                Estudiante estudiante = new Estudiante(nombre,apellido,identificacion,grado);
                estudiantes.add(estudiante);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            Alertas.mostrarError("ERROR SQL: " + e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }

        return estudiantes;
    }
}
