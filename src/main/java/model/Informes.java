package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Informes {
    private static String fecha, nombreEncargado;
    private static Estudiante estudiante;
    private static ArrayList<String[]> infoLlegadas;
    private static int llegadasTarde = 0;

    public Informes(){

    }

    public Informes(String fecha, Estudiante estudiante, String nombreEncargado, ArrayList<String[]> infoLlegadas) {
        this.fecha = fecha;
        this.estudiante = estudiante;
        this.nombreEncargado = nombreEncargado;
        this.infoLlegadas = infoLlegadas;

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

    public static void generarInforme(){
        String NOMBRE_ARCHIVO = "INFORME_"+estudiante.getNombre()+"_"+estudiante.getApellido()+".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))){
            writer.write(plantilla());
        }catch(Exception e){
            System.out.println(e.getMessage());
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

    /*
    public static void plantilla(String fecha, String nombreEncargado, Estudiante estudiante, ArrayList<String[]> infoLlegadas){
        System.out.println("-".repeat(100));
        System.out.println("\t\t\t INFORME REGISTRO DE INGRESOS");
        System.out.println("-".repeat(100));

        System.out.println("Fecha: " + fecha);
        System.out.println("Encargado: " + nombreEncargado);
        System.out.println("\nReciba un cordial saludo.\n" +
                            "Nos dirigimos a usted para informar que el estudiante: \n");
        System.out.println("\t\tNombre: " + estudiante.getNombre() + " " + estudiante.getApellido()
                            + "\n\t\tGrado: " + estudiante.getGrado());
        System.out.println("Presenta un total de " + infoLlegadas.size() + " INGRESO(S) TARDE registrados hasta la fecha.");
        System.out.println("A continuación, se relacionan las fechas: ");
        for (String[] infoLlegada : infoLlegadas) {
            if (infoLlegada[2].equals("Ingreso tarde")) {
                System.out.println("\t\t" + infoLlegada[1] + " " + infoLlegada[3]);
            }
        }

        System.out.println("Agradecemos su atención a este informe y quedamos atentos a cualquier inquietud.\n" +
                "Cordialmente,");

        System.out.println("_".repeat(50)+"\t\t\t\t" + "_".repeat(50));
        System.out.println("Docente" + "\t\t\t\t" + "C.C");
        System.out.println(nombreEncargado + "\t\t\t\t" + "Firma de recibido\n");
        System.out.println("¡La participación y el apoyo en casa son fundamentales para el éxito académico tu hijo!");

    }

    public static void generarInforme(Estudiante estudiante){
        String NOMBRE_ARCHIVO = "../resources/Informes/INFORME_"+estudiante.getNombre()+"_"+estudiante.getApellido()+".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))){
            writer.write("prueba");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     */
}
