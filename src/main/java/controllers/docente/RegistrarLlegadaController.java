package controllers.docente;

import application.App;
import data.ConfigDAO;
import data.EstudiantesDAO;
import data.LlegadasDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Estudiante;
import utils.Alertas;
import utils.Extras;
import utils.Transiciones;
import utils.Validaciones;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RegistrarLlegadaController {

    @FXML
    private Button btnRegistrar;

    @FXML
    private VBox infoEstudiante;

    @FXML
    private VBox contenedor;

    @FXML
    private Label grado;

    @FXML
    private Label infoID;

    @FXML
    private Label nombre;

    @FXML
    private TextField txtID;

    Estudiante estudiante;

    @FXML
    void clickBuscar(ActionEvent event) {
        buscarEstudiante();
    }

    @FXML
    void clickRegistrar(ActionEvent event) {
        registrarLlegada();
    }

    @FXML
    void initialize() {
        Transiciones.cargarDesdeAbajo(contenedor, 1.2, 0, 1, 90, 0);
        ocultarElementos();
    }

    private void ocultarElementos(){
        btnRegistrar.setManaged(false);
        btnRegistrar.setVisible(false);
        infoEstudiante.setManaged(false);
        infoEstudiante.setVisible(false);
    }

    private void mostrarElementos(){
        btnRegistrar.setManaged(true);
        btnRegistrar.setVisible(true);
        infoEstudiante.setManaged(true);
        infoEstudiante.setVisible(true);
    }

    private void limpiarID(){
        txtID.clear();
    }

    private void buscarEstudiante(){
        EstudiantesDAO estudiantesDAO = new  EstudiantesDAO();
        if(!Validaciones.validarIdentificacion(txtID.getText())){
            limpiarID();
            return;
        }

        Long id = Long.parseLong(txtID.getText());
        estudiante = estudiantesDAO.buscarEstudiante(id);
        if(estudiante != null){
            infoID.setText("> La identificacion " + estudiante.getIdentificacion() + " corresponde al siguiente estudiante:");
            nombre.setText("Nombre completo: " + estudiante.getNombre() + " " +  estudiante.getApellido());
            grado.setText("Grado: " + estudiante.getGrado());
            mostrarElementos();
        }else{
            Alertas.mostrarError("No hay ningún estudiante registrado con esa identificación");
            limpiarID();
        }
    }

    private void registrarLlegada() {
        ConfigDAO configDAO = new ConfigDAO();
        LlegadasDAO llegadasDAO = new LlegadasDAO();
        LocalTime horaActual = LocalTime.now().withSecond(0).withNano(0);

        String horaTexto = configDAO.obtenerHora();
        if (horaTexto == null || horaTexto.isEmpty()) {
            Alertas.mostrarWarning("No se encontró una hora límite configurada.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
            LocalTime horaLimite = LocalTime.parse(horaTexto, formatter);

            if (horaActual.isBefore(horaLimite)) {
                if(llegadasDAO.registrarLlegada(estudiante.getID(), App.usuarioLogueado.getID(), Extras.fechaHoy(), 0, "El estudiante no llego tarde")){
                    Alertas.mostrarInfo("Se registró el ingreso correctamente. El estudiante llegó a tiempo.");
                }
            } else {
                long minutosTarde = java.time.Duration.between(horaLimite, horaActual).toMinutes();
                String tiempoTarde = minutosAString(minutosTarde);
                if(llegadasDAO.registrarLlegada(estudiante.getID(), App.usuarioLogueado.getID(), Extras.fechaHoy(), 1, tiempoTarde)){
                    Alertas.mostrarWarning("Se registró el ingreso, el estudiante ha llegado " + tiempoTarde + " tarde" +
                            ". Hora límite: " + Extras.formatoHora(horaLimite));

                }
            }
            limpiarID();
            ocultarElementos();
        } catch (Exception e) {
            Alertas.mostrarError("Error al interpretar la hora almacenada: " + horaTexto);
        }
    }

    private static String minutosAString(long minutosTarde) {
        String tiempoTarde;
        if (minutosTarde >= 60) {
            long horas = minutosTarde / 60;
            long minutos = minutosTarde % 60;
            tiempoTarde = String.format("%d hora%s y %d minuto%s",
                    horas, (horas == 1 ? "" : "s"),
                    minutos, (minutos == 1 ? "" : "s"));
        } else {
            tiempoTarde = String.format("%d minuto%s", minutosTarde, (minutosTarde == 1 ? "" : "s"));
        }
        return tiempoTarde;
    }



    /*
        System.out.println(spinnerHora.getValue());
        LocalTime horaLimite = spinnerHora.getValue();
        LocalTime horaActual = LocalTime.now().withSecond(0).withNano(0);
        // Comparar
        if (horaActual.isBefore(horaLimite)) {
            System.out.println("Aún estás dentro del horario permitido");
        } else if (horaActual.equals(horaLimite)) {
            System.out.println("Justo en la hora límite, hora actual: " + horaActual);
        } else {
            System.out.println("Se pasó la hora límite, hora actual: " + horaActual);
        }
        */

}