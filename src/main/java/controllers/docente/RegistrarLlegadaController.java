package controllers.docente;

import application.App;
import data.ConfigDAO;
import data.EstudiantesDAO;
import data.LlegadasDAO;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Estudiante;
import utils.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RegistrarLlegadaController {

    @FXML
    private Button btnRegistrar;

    @FXML
    private TableView<InfoLlegada> tablaLlegada;

    @FXML
    private TableColumn<InfoLlegada, String> colDocente;

    @FXML
    private TableColumn<InfoLlegada, String> colFecha;

    @FXML
    private TableColumn<InfoLlegada, String> colEstado;

    @FXML
    private TableColumn<InfoLlegada, String> colInfo;


    @FXML
    private VBox contenedor;

    @FXML
    private HBox infoEstudiante;

    @FXML
    private Label infoID;

    @FXML
    private TextField txtID;

    @FXML
    private Button btnBuscar;

    @FXML
    void clickBuscar(ActionEvent event) {
        buscarEstudiante();
    }

    @FXML
    void clickRegistrar(ActionEvent event) {
        registrarLlegada();
    }

    private Estudiante estudiante;
    EstudiantesDAO estudiantesDAO = new  EstudiantesDAO();

    @FXML
    void initialize() {
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
        ocultarElementos();
        tablaLlegada.setPlaceholder(new Label("Aún no hay registros de ingreso para este estudiante."));
        colDocente.setCellValueFactory(new PropertyValueFactory<>("docenteEncargado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colInfo.setCellValueFactory(new PropertyValueFactory<>("informacion"));

    }

    private void ocultarElementos(){
        btnRegistrar.setManaged(false);
        btnRegistrar.setVisible(false);
        infoEstudiante.setManaged(false);
        infoEstudiante.setVisible(false);
        tablaLlegada.setVisible(false);
        tablaLlegada.setManaged(false);
    }

    private void mostrarElementos(){
        btnRegistrar.setManaged(true);
        btnRegistrar.setVisible(true);
        infoEstudiante.setManaged(true);
        infoEstudiante.setVisible(true);
        tablaLlegada.setVisible(true);
        tablaLlegada.setManaged(true);
        btnRegistrar.setDisable(false);
    }

    private void limpiarID(){
        txtID.clear();
    }

    private void buscarEstudiante(){

        if(!Validaciones.validarIdentificacion(txtID.getText())){
            limpiarID();
            return;
        }

        Long id = Long.parseLong(txtID.getText());
        estudiante = estudiantesDAO.buscarEstudiante(id);
        if(estudiante != null){
            infoID.setText(estudiante.getNombreCompleto() + " — Grado " + estudiante.getGrado() +"°");
            mostrarElementos();
            cargarUltimosRegistros(estudiante.getID());
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
                    Alertas.mostrarInfo("Se registró el ingreso, el estudiante ha llegado a tiempo. Verifique en la tabla los últimos ingresos");
                }
            } else {
                long minutosTarde = java.time.Duration.between(horaLimite, horaActual).toMinutes();
                String tiempoTarde = minutosAString(minutosTarde);
                if(llegadasDAO.registrarLlegada(estudiante.getID(), App.usuarioLogueado.getID(), Extras.fechaHoy(), 1, tiempoTarde)){
                    Alertas.mostrarWarning("Se registró el ingreso, el estudiante ha llegado " + tiempoTarde + " tarde" +
                            ". Hora límite: " + Extras.formatoHora(horaLimite));

                }
            }
            cargarUltimosRegistros(estudiante.getID());
            btnRegistrar.setDisable(true);
            btnBuscar.setDisable(true);
            txtID.setDisable(true);
            PauseTransition pausa = new PauseTransition(Duration.seconds(5));
            pausa.setOnFinished(event -> {
                ocultarElementos();
                limpiarID();
                Platform.runLater(() -> {
                   btnBuscar.setDisable(false);
                   txtID.setDisable(false);
                   Alertas.mostrarInfo("Se ocultó automáticamente la tabla y se limpió el campo de identificación");
                });
            });
            pausa.play();
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

    private void cargarUltimosRegistros(long idEstudiante) {
        ArrayList<String[]> lista = estudiantesDAO.infoIngresoEstudiante((int) idEstudiante);

        // ultimos cinco
        int desde = Math.max(0, lista.size() - 5);
        List<String[]> ultimosCinco = lista.subList(desde, lista.size());

        ObservableList<InfoLlegada> data = FXCollections.observableArrayList();
        for (String[] datos : ultimosCinco) {
            data.add(new InfoLlegada(datos[0], datos[1], datos[2], datos[3]));
        }

        tablaLlegada.setItems(data);
    }


}