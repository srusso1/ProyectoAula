package utils;

public class InfoLlegada {
    private String docenteEncargado;
    private String fecha;
    private String estado;
    private String informacion;

    public InfoLlegada(String docenteEncargado, String fecha, String estado, String informacion) {
        this.docenteEncargado = docenteEncargado;
        this.fecha = fecha;
        this.estado = estado;
        this.informacion = informacion;
    }

    public String getDocenteEncargado() { return docenteEncargado; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getInformacion() { return informacion; }
}
