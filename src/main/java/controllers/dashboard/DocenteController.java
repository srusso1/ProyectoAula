package controllers.dashboard;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import utils.Alertas;
import utils.Paths;
import utils.VistaManager;

public class DocenteController {

    @FXML
    private Button btnSalir;

    @FXML
    private BorderPane rootPane;

    @FXML
    void ClickVerCurso(ActionEvent event) {

    }

    @FXML
    void clickEstadisticas(ActionEvent event) {

    }

    @FXML
    void clickInicio(ActionEvent event) {

    }

    @FXML
    void clickRegistrarLlegada(ActionEvent event) {

    }

    @FXML
    void clickRevisarEstudiante(ActionEvent event) {

    }

    @FXML
    void clickSalir(ActionEvent event) {
        boolean confirmar = Alertas.mostrarConfirmacion("¿Estás seguro de cerrar sesión?");
        if (confirmar) {
            App.setRoot(Paths.LOGIN_VIEW);
        }else{
            Alertas.mostrarInfo("Acción cancelada por el usuario");
        }
    }

    @FXML
    void initialize(){
        VistaManager.cargarVista(Paths.DOCENTE_INICIO, rootPane);
    }


}