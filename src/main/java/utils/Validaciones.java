package utils;

import data.EstudiantesDAO;
import data.UsuariosDAO;
import model.Docente;
import model.Estudiante;

public class Validaciones {

    public static final int CARACTERES_PASSWORD = 8;
    public static final int CARACTERES_USUARIO = 5;
    public static final int MAX_CARACTERES = 30;

    public static boolean validarTexto(String texto, String caso){
        caso = caso.toUpperCase();
        if(texto.isEmpty()){
            Alertas.mostrarError("Es obligatorio ingresar el campo " + caso);
            return false;
        }else{
            for(int i = 0; i < texto.length(); i++){
                if(Character.isDigit(texto.charAt(i))){
                    Alertas.mostrarError("No se admiten números en el campo " + caso);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarPassword(String texto){
        if(texto.isEmpty()){
            Alertas.mostrarError("La contraseña es obligatoria");
            return false;
        }else{
            if(texto.length() < CARACTERES_PASSWORD){
                Alertas.mostrarError("La contraseña debe contener mínimo "  + CARACTERES_PASSWORD + " caracteres");
                return false;
            }
        }
        return true;
    }

    public static boolean validarUsuario(String texto){
        if(texto.isEmpty()){
            Alertas.mostrarError("El usuario es obligatorio");
            return false;
        }else{
            if(texto.length() < CARACTERES_USUARIO){
                Alertas.mostrarError("La contraseña debe contener mínimo "  + CARACTERES_PASSWORD + " caracteres");
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
}
