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
import utils.Alertas;
import utils.infoGrado;

import java.util.ArrayList;

public class ConsultarGradoController {

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private TableView<infoGrado> tabla;

    @FXML
    private TableColumn<infoGrado, String> colNombre;

    @FXML
    private TableColumn<infoGrado, String> colGrado;

    @FXML
    private TableColumn<infoGrado, String> colFecha;

    @FXML
    private TableColumn<infoGrado, String> colEstado;

    @FXML
    private TableColumn<infoGrado, String> colInfo;

    @FXML
    private Label infoIngresos;

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
            infoIngresos.setText("> Total de ingresos registrados: " + resultados.size());
            mostrarElementos();

        }
    }


    @FXML
    void initialize() {

        comboBox.setItems(FXCollections.observableArrayList(6,7,8,9,10,11));

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGrado.setCellValueFactory(new PropertyValueFactory<>("grado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colInfo.setCellValueFactory(new PropertyValueFactory<>("info"));

        ocultarElementos();
    }

    private void ocultarElementos(){
        tabla.setVisible(false);
        tabla.setManaged(false);

        infoIngresos.setVisible(false);
        infoIngresos.setManaged(false);
    }

    private void mostrarElementos(){
        tabla.setVisible(true);
        tabla.setManaged(true);

        infoIngresos.setVisible(true);
        infoIngresos.setManaged(true);

    }


}

