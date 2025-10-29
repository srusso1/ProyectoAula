package utils;

import data.EstudiantesDAO;
import data.UsuariosDAO;
import model.Docente;
import model.Estudiante;

public class Validaciones {

    public static final int CARACTERES_PASSWORD = 8;
    public static final int CARACTERES_USUARIO = 5;
    public static final int MAX_CARACTERES = 30;
    public static final int MIN_CARACTERES = 3;

    public static boolean validarTexto(String texto, String caso) {
        caso = caso.toUpperCase();

        // Validar si está vacío
        if (texto == null || texto.trim().isEmpty()) {
            Alertas.mostrarError("Es obligatorio ingresar el campo " + caso);
            return false;
        }

        // Eliminar espacios al inicio y final para una validación más precisa
        texto = texto.trim();

        // Validar longitud máxima
        if (texto.length() > MAX_CARACTERES) {
            Alertas.mostrarError("El campo " + caso + " supera los " + MAX_CARACTERES + " caracteres permitidos");
            return false;
        }

        // Validar longitud mínima
        if (texto.length() < MIN_CARACTERES) {
            Alertas.mostrarError("El campo " + caso + " debe tener al menos " + MIN_CARACTERES + " caracteres");
            return false;
        }

        // Validar que no contenga números
        for (char c : texto.toCharArray()) {
            if (Character.isDigit(c)) {
                Alertas.mostrarError("No se admiten números en el campo " + caso);
                return false;
            }
        }

        return true;
    }


    public static boolean validarPassword(String texto){

        // Validar si está vacío
        if (texto == null || texto.trim().isEmpty()) {
            Alertas.mostrarError("Es obligatorio ingresar la contraseña");
            return false;
        }

        // Eliminar espacios al inicio y final para una validación más precisa
        texto = texto.trim();

        // Validar longitud máxima
        if (texto.length() > MAX_CARACTERES) {
            Alertas.mostrarError("El campo contraseña supera los " + MAX_CARACTERES + " caracteres permitidos");
            return false;
        }

        // Validar longitud mínima
        if (texto.length() < CARACTERES_PASSWORD) {
            Alertas.mostrarError("El campo contraseña debe tener al menos " + CARACTERES_PASSWORD + " caracteres");
            return false;
        }
        return true;
    }

    public static boolean validarUsuario(String texto){
        if(texto.isEmpty()){
            Alertas.mostrarError("El usuario es obligatorio");
            return false;
        }else{
            if(texto.length() < CARACTERES_USUARIO){
                Alertas.mostrarError("El usuario debe contener mínimo "  + CARACTERES_USUARIO + " caracteres");
                return false;
            }
        }
        return true;
    }

    public static boolean validarIdentificacion(String identificacion, String caso){
        caso = caso.toUpperCase();
        long id;
        if(identificacion.isEmpty()){
            Alertas.mostrarError("Es obligatorio la identificacion del " + caso);
            return false;
        }else{
            try {
                id = Long.parseLong(identificacion);
            } catch (NumberFormatException e) {
                Alertas.mostrarError("Solo se admiten valores numericos");
                return false;
            }

            switch (caso) {
                case "ESTUDIANTE":
                    EstudiantesDAO estudiantesDAO = new EstudiantesDAO();
                    Estudiante estudiante = estudiantesDAO.buscarEstudiante(id);

                    if(estudiante != null){
                        Alertas.mostrarError("Ya hay un "+ caso +" registrado con esa identificacion:\n\n" +
                                estudiante.toString());
                        return false;
                    }
                    break;
                case "DOCENTE":
                    UsuariosDAO usuariosDAO = new UsuariosDAO();
                    Docente docente = usuariosDAO.buscarDocente(id);
                    if(docente != null){
                        Alertas.mostrarError("Ya hay un "+ caso +" registrado con esa identificacion:\n\n" +
                                docente.toString());
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public static boolean validarIdentificacion(String identificacion){
        long id;
        if(identificacion.isEmpty()){
            Alertas.mostrarError("Es obligatorio digitar la identificacion");
            return false;
        }else{
            try {
                id = Long.parseLong(identificacion);
            } catch (NumberFormatException e) {
                Alertas.mostrarError("Solo se admiten valores numericos: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static String validarNombre(String nombre){
        boolean valido = false;
        while(!valido){
            if(Validaciones.validarTexto(nombre, "nombre")){
                valido = true;
            }
        }
        return nombre;
    }
}
