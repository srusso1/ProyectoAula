package data;

import utils.Alertas;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {

    static String appData = System.getenv("LOCALAPPDATA");
    static String dbFolder = appData + "\\ProyectoAula";  // cambia el nombre de tu programa
    static String dbPath = dbFolder + "\\database.db";
    private static final String URL = "jdbc:sqlite:" + dbPath;
    private static Connection conexion = null;
    public static Connection conectar(){
        try{
            String metodoLlamador = Thread.currentThread().getStackTrace()[2].getMethodName();
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexion establecida con la base de datos - " + metodoLlamador);
        } catch (SQLException e) {
            Alertas.mostrarError("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
                String metodoLlamador = Thread.currentThread().getStackTrace()[2].getMethodName();

                System.out.println("Se cerro la conexion a la base de datos - " + metodoLlamador);
            }
        } catch (SQLException e) {
            Alertas.mostrarError("Error al cerrar la conexion a la base de datos: " + e.getMessage());
        }
    }

}
