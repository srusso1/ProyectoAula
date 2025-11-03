package model;

public class Estudiante extends Usuario {
    private int grado;
    private String llegadas;

    public Estudiante(String nombre, String apellido, long identificacion, int grado) {
        super(nombre, apellido, identificacion);
        this.grado = grado;
    }

    public Estudiante(int ID, String nombre, String apellido, long identificacion, int grado) {
        super(nombre, apellido, identificacion);
        this.grado = grado;
        this.ID = ID;
    }

    public Estudiante() {
        super();
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public String getLlegadas() {
        return llegadas;
    }
    public void setLlegadas(String llegadas) {
        this.llegadas = llegadas;
    }

    @Override
    public String toString() {
        return  "> Nombre: " + nombre + "\n" +
                "> Apellido: " + apellido + "\n" +
                "> Grado: " + grado;
    }
}
