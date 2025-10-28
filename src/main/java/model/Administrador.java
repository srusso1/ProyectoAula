package model;

public class Administrador extends Usuario {
    private String usuario;
    private String password;
    private tipoRol rol;
    public Administrador(String nombre, String apellido, long identificacion, String usuario, String password) {
        super(nombre,apellido, identificacion);
        this.usuario = usuario;
        this.password = password;
        this.rol = tipoRol.ADMINISTRADOR;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public tipoRol getRol() {
        return rol;
    }

    public void setRol(tipoRol rol) {
        this.rol = rol;
    }
}
