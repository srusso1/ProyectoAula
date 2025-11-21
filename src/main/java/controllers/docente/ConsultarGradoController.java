package controllers.docente;

import data.LlegadasDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import utils.Alertas;
import utils.Transiciones;
import utils.infoGrado;

import java.util.ArrayList;

public class ConsultarGradoController {

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private TableView<infoGrado> tabla;

    @FXML
    private VBox contenedor;

    @FXML
    private TableColumn<infoGrado, String> colNombre;

    @FXML
    private TableColumn<infoGrado, String> colGrado;

    @FXML
    private TableColumn<infoGrado, String> colRegistros;

    @FXML
    private TableColumn<infoGrado, String> colIngresosTarde;

    @FXML
    private TableColumn<infoGrado, String> colInfo;

    LlegadasDAO llegadasDAO = new LlegadasDAO();

    @FXML
    void elegirGrado(ActionEvent event) {
        Integer grado = comboBox.getSelectionModel().getSelectedItem();
        if(grado == null){
            Alertas.mostrarError("Grado no seleccionado");
            return;
        }

        ArrayList<infoGrado> resultados = llegadasDAO.infoConsultaGrados(grado);

        if(resultados != null){
            tabla.setItems(FXCollections.observableArrayList(resultados));
            mostrarElementos();

        }
    }


    @FXML
    void initialize() {

        comboBox.setItems(FXCollections.observableArrayList(6,7,8,9,10,11));
        tabla.setPlaceholder(new Label("No hay estudiantes en este grado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGrado.setCellValueFactory(new PropertyValueFactory<>("grado"));
        colRegistros.setCellValueFactory(new PropertyValueFactory<>("registros"));
        colIngresosTarde.setCellValueFactory(new PropertyValueFactory<>("ingresosTarde"));
        colInfo.setCellValueFactory(new PropertyValueFactory<>("info"));


        ocultarElementos();
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
    }

    private void ocultarElementos(){
        tabla.setVisible(false);
        tabla.setManaged(false);
    }

    private void mostrarElementos(){
        tabla.setVisible(true);
        tabla.setManaged(true);
    }


}

