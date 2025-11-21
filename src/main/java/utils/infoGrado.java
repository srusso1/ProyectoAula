package utils;

public class infoGrado {
    private String nombre;
    private String grado;
    private String registros;
    private String ingresosTarde;
    private String info;

    public infoGrado(String nombre, String grado, String registros, String ingresosTarde, String info) {
        this.nombre = nombre;
        this.grado = grado;
        this.registros = registros;
        this.ingresosTarde = ingresosTarde;
        this.info = info;
    }

    public String getNombre() { return nombre; }
    public String getGrado() { return grado; }
    public String getRegistros() { return registros; }
    public String getIngresosTarde() { return ingresosTarde; }
    public String getInfo() { return info; }
}

