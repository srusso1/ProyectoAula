package model;

import data.ConfigDAO;
import utils.Alertas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Informes {
    private static String fecha, nombreEncargado;
    private static Estudiante estudiante;
    private static ArrayList<String[]> infoLlegadas;
    private static int llegadasTarde;

    public Informes(){

    }

    public Informes(String fecha, Estudiante estudiante, String nombreEncargado, ArrayList<String[]> infoLlegadas) {
        this.fecha = fecha;
        this.estudiante = estudiante;
        this.nombreEncargado = nombreEncargado;
        this.infoLlegadas = infoLlegadas;
        llegadasTarde = 0;
        for(int i=0; i<infoLlegadas.size(); i++){
            if (infoLlegadas.get(i)[2].equals("Ingreso tarde")){
                setLlegadasTarde(getLlegadasTarde() + 1);
            }
        }
    }

    public static String plantilla(){
        return ("-".repeat(100) + "\n" +
                "\t\t\t\t INFORME REGISTRO DE INGRESOS" + "\n" +
                "-".repeat(100) + "\n\n" +
                "Fecha: " + fecha + "\n" +
                "Encargado: " + nombreEncargado + "\n\n" +
                "Reciba un cordial saludo.\nNos dirigimos a usted para informar que el estudiante: \n\n" +
                "\t\tNombre completo: " + estudiante.getNombreCompleto() + "\n" +
                "\t\tGrado: " + estudiante.getGrado() + "\n\n" +
                "Presenta un total de " + llegadasTarde + " INGRESO(S) TARDE registrados hasta la fecha.\n" +
                "A continuación, se relacionan las fechas: \n\n"+
                infoLlegadas() +
                "\n\nAgradecemos su atención a este informe y quedamos atentos a cualquier inquietud.\n" +
                "Cordialmente,\n\n"+
                "_".repeat(25) + "\t\t\t\t" + "_".repeat(25) + "\n\n" +
                "Docente\t\t\t\t\t\t\tC.C"+"\n" +
                nombreEncargado+"\t\t\t\t\t\t\tFirma de recibido\n\n" +
                "¡La participación y el apoyo en casa son fundamentales para el éxito académico tu hijo!");
    }

    public static StringBuilder infoLlegadas(){
        StringBuilder info = new StringBuilder();
        for (String[] infoLlegada : infoLlegadas) {
            if (infoLlegada[2].equals("Ingreso tarde")) {
                assert false;
                info.append("\t\t").append(infoLlegada[1]).append(" - ").append(infoLlegada[3]).append("\n");
            }
        }
        return info;
    }

    public static boolean generarInforme(){
        ConfigDAO configDAO = new ConfigDAO();
        String ruta = configDAO.obtenerRutaArchivo();
        System.out.println(ruta);
        if(ruta == null){
            Alertas.mostrarWarning("Aún no se ha configurado una carpeta para guardar los informes. Por favor, contacta un administrador.");
            return false;
        }


        Path archivo = Paths.get(ruta, String.format("INFORME_%s_%s.txt",
                estudiante.getNombre(),
                estudiante.getApellido()));
        String NOMBRE_ARCHIVO = archivo.toString();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))){
            writer.write(plantilla());
            return true;
        }catch(Exception e){
            Alertas.mostrarError("Error al generar el informe: " + e.getMessage());
            return false;
        }
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public ArrayList<String[]> getInfoLlegadas() {
        return infoLlegadas;
    }

    public void setInfoLlegadas(ArrayList<String[]> infoLlegadas) {
        this.infoLlegadas = infoLlegadas;
    }

    public int getLlegadasTarde() {
        return llegadasTarde;
    }

    public void setLlegadasTarde(int llegadasTarde) {
        this.llegadasTarde = llegadasTarde;
    }
}
