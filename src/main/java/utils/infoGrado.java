package utils;

public class infoGrado {
    private String nombre;
    private String grado;
    private String fecha;
    private String estado;
    private String info;

    public infoGrado(String nombre, String grado, String fecha, String estado, String info) {
        this.nombre = nombre;
        this.grado = grado;
        this.fecha = fecha;
        this.estado = estado;
        this.info = info;
    }

    public String getNombre() { return nombre; }
    public String getGrado() { return grado; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getInfo() { return info; }
}

