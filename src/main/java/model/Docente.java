package model;

public class Docente extends Usuario{
    private String usuario;
    private String password;
    private tipoRol rol;

    public Docente(String nombre, String apellido, long identificacion, String usuario, String password) {
        super(nombre, apellido, identificacion);
        this.usuario = usuario;
        this.password = password;
        this.rol = tipoRol.DOCENTE;
    }

    public Docente(int ID, String nombre, String apellido, long identificacion, String usuario, String password) {
        super(nombre, apellido, identificacion);
        this.usuario = usuario;
        this.password = password;
        this.rol = tipoRol.DOCENTE;
        this.ID = ID;
    }


    public Docente(String nombre, String apellido, long identificacion){
        super(nombre, apellido, identificacion);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public tipoRol getRol() {
        return rol;
    }

    public void setRol(tipoRol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return  "> Nombre: " + nombre + "\n" +
                "> Apellido: " + apellido + "\n" +
                "> Rol: " + rol;
    }
}
