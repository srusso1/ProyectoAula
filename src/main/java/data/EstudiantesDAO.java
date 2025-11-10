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
            ps.setString(2, estudiante.getNombre().toUpperCase());
            ps.setString(3, estudiante.getApellido().toUpperCase());
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
                int id = rs.getInt("ID");
                long identi = rs.getLong("IDENTIFICACION");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                int grado = rs.getInt("GRADO");
                estudiante = new Estudiante(id, nombre,apellido,identi,grado);
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

    public ArrayList<Estudiante> listaEstudiantesIngresos() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT e.IDENTIFICACION, e.NOMBRE, e.APELLIDO, e.GRADO, " +
                "SUM(CASE WHEN l.ESTADO = 1 THEN 1 ELSE 0 END) AS LLEGADAS_TARDE " +
                "FROM estudiantes e LEFT JOIN llegadas l ON e.ID = l.ID_ESTUDIANTE " +
                "GROUP BY e.IDENTIFICACION, e.NOMBRE, e.APELLIDO, e.GRADO";
        try {
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Estudiante est = new Estudiante();
                est.setIdentificacion(rs.getLong("IDENTIFICACION"));
                est.setNombre(rs.getString("NOMBRE"));
                est.setApellido(rs.getString("APELLIDO"));
                est.setGrado(rs.getInt("GRADO"));
                est.setLlegadas(rs.getString("LLEGADAS_TARDE"));
                estudiantes.add(est);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            Alertas.mostrarError("ERROR SQL: " + e.getMessage());
        } finally {
            ConexionSQLite.cerrarConexion();
        }
        return estudiantes;
    }


    public ArrayList<String[]> infoIngresoEstudiante(int ID_ESTUDIANTE){
        ArrayList<String[]> info = new ArrayList<>();
        String query = "SELECT u.NOMBRE || ' ' || u.APELLIDO AS DOCENTE_ENCARGADO, l.FECHA AS FECHA, l.ESTADO AS ESTADO, l.INFO AS INFORMACION " +
                "FROM llegadas l " +
                "JOIN usuarios u ON u.ID = l.ID_DOCENTE " +
                "WHERE l.ID_ESTUDIANTE = ?;";
        try{
            Connection conexion = ConexionSQLite.conectar();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, ID_ESTUDIANTE);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String nombreDocente = rs.getString("DOCENTE_ENCARGADO");
                String fecha = rs.getString("FECHA");
                String estado = (rs.getInt("ESTADO") == 0) ? "Ingreso a tiempo" : "Ingreso tarde";
                String infoLlegada = rs.getString("INFORMACION");
                String[] infoCompleta = {nombreDocente, fecha, estado, infoLlegada};
                info.add(infoCompleta);
            }
            ps.close();
            rs.close();
            return info;
        } catch (SQLException e) {
            Alertas.mostrarError("ERROR SQL: " + e.getMessage());
        }finally {
            ConexionSQLite.cerrarConexion();
        }
        return info;
    }
}
