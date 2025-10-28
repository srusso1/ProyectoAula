package model;

public class Estudiante extends Usuario {
    private int grado;

    public Estudiante(String nombre, String apellido, long identificacion, int grado) {
        super(nombre, apellido, identificacion);
        this.grado = grado;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    @Override
    public String toString() {
        return  "> Nombre: " + nombre + "\n" +
                "> Apellido: " + apellido + "\n" +
                "> Grado: " + grado;
    }
}
