package controllers.docente;

import data.EstudiantesDAO; // o el nombre correcto donde tienes el método infoIngresoEstudiante
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Estudiante;
import utils.InfoLlegada;
import utils.Alertas;
import utils.Transiciones;
import utils.Validaciones;

import java.util.ArrayList;

public class ConsultaEstudianteController {

    @FXML
    private TableColumn<InfoLlegada, String> colDocente;

    @FXML
    private TableColumn<InfoLlegada, String> colEstado;

    @FXML
    private TableColumn<InfoLlegada, String> colFecha;

    @FXML
    private TableColumn<InfoLlegada, String> colInfo;

    @FXML
    private TableView<InfoLlegada> infoLlegada;

    @FXML
    private Label lbIngresos;

    @FXML
    private Label lbIngresosTarde;

    @FXML
    private Label lbPuntuliadad;

    @FXML
    private VBox contenedor;

    @FXML
    private TextField txtID;

    @FXML
    private VBox contenedorInfoPuntualidad;

    @FXML
    private VBox contenedorInfoRegistradas;

    @FXML
    private VBox contenedorInfoTarde;

    @FXML
    private Label infoEstudiante;

    private EstudiantesDAO estudiantesDAO = new EstudiantesDAO();

    @FXML
    void initialize() {
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
        configurarColumnas();
        ocultarElementos();
    }

    private void configurarColumnas() {
        colDocente.setCellValueFactory(new PropertyValueFactory<>("docenteEncargado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colInfo.setCellValueFactory(new PropertyValueFactory<>("informacion"));
    }

    @FXML
    void clickConsultar(ActionEvent event) {
        consultarEstudiante();
    }

    private void consultarEstudiante(){
        if(!Validaciones.validarIdentificacion(txtID.getText())){
            limpiarCampos();
            return;
        }

        long identificacion = Long.parseLong(txtID.getText());

        Estudiante estudiante = estudiantesDAO.buscarEstudiante(identificacion);
        if(estudiante == null){
            limpiarCampos();
            Alertas.mostrarError("No hay ningún estudiante registrado con esa identificación");
            ocultarElementos();
            return;
        }
        infoEstudiante.setText(estudiante.getNombreCompleto() + " con la identificación " + estudiante.getIdentificacion() +
                " - Grado: " + estudiante.getGrado());
        int idEstudiante = estudiante.getID();
        cargarInfoEstudiante(idEstudiante);
    }

    private void limpiarCampos(){
        txtID.clear();
    }

    private void cargarInfoEstudiante(int idEstudiante) {
        ArrayList<String[]> registros = estudiantesDAO.infoIngresoEstudiante(idEstudiante);

        if(registros.isEmpty()){
            Alertas.mostrarWarning("El estudiante no tiene ingresos registrados, no hay información para mostrar");
            ocultarElementos();
            limpiarCampos();
            return;
        }

        ObservableList<InfoLlegada> lista = FXCollections.observableArrayList();

        int llegadasTarde = 0;

        for (String[] fila : registros) {
            String docente = fila[0];
            String fecha = fila[1];
            String estado = fila[2];
            String info = fila[3];

            if (estado.equalsIgnoreCase("Ingreso tarde")) {
                llegadasTarde++;
            }

            lista.add(new InfoLlegada(docente, fecha, estado, info));
        }

        infoLlegada.setItems(lista);

        // actualizar etiquetas
        lbIngresos.setText(String.valueOf(lista.size()));
        lbIngresosTarde.setText(String.valueOf(llegadasTarde));

        double puntualidad = lista.isEmpty() ? 0 : ((lista.size() - llegadasTarde) * 100.0 / lista.size());
        lbPuntuliadad.setText(String.format("%.1f%%", puntualidad));
        if(puntualidad < 75){
            lbPuntuliadad.setStyle("-fx-text-fill: red");
        }else{
            lbPuntuliadad.setStyle("-fx-text-fill: green");
        }

        mostrarElementos();
    }

    private void ocultarElementos(){
        infoLlegada.setManaged(false);
        infoLlegada.setVisible(false);
        contenedorInfoTarde.setVisible(false);
        contenedorInfoTarde.setManaged(false);
        contenedorInfoPuntualidad.setVisible(false);
        contenedorInfoPuntualidad.setManaged(false);
        contenedorInfoRegistradas.setVisible(false);
        contenedorInfoRegistradas.setManaged(false);
        infoEstudiante.setManaged(false);
        infoEstudiante.setVisible(false);
    }

    private void mostrarElementos(){
        infoLlegada.setManaged(true);
        infoLlegada.setVisible(true);
        contenedorInfoTarde.setVisible(true);
        contenedorInfoTarde.setManaged(true);
        contenedorInfoPuntualidad.setVisible(true);
        contenedorInfoPuntualidad.setManaged(true);
        contenedorInfoRegistradas.setVisible(true);
        contenedorInfoRegistradas.setManaged(true);
        infoEstudiante.setManaged(true);
        infoEstudiante.setVisible(true);
    }
}
