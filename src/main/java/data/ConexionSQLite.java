package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {
    private static final String URL = "jdbc:sqlite:src/main/resources/data/DATABASE.db";
    private static Connection conexion = null;
    public static Connection conectar(){
        try{
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexion establecida con la base de datos");
        } catch (SQLException e) {
            System.err.println("Error al conectar la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion(){
        try{
            if(conexion != null){
                conexion.close();
                System.out.println("Se cerro la conexion a la base de datos");
            }
        }catch(SQLException e){
            System.err.println("Error al cerrar la base de datos: " + e.getMessage());
        }
    }
}
