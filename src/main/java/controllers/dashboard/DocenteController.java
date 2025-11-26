package controllers.dashboard;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utils.Alertas;
import utils.Paths;
import utils.Transiciones;
import utils.VistaManager;

public class DocenteController {

    @FXML
    private Button btnSalir;

    @FXML
    private BorderPane rootPane;



    @FXML
    void ClickVerCurso(ActionEvent event) {
        VistaManager.cargarVista(Paths.CONSULTAR_GRADO, rootPane);
    }

    @FXML
    void clickInformes(ActionEvent event) {
        VistaManager.cargarVista(Paths.INFORME, rootPane);
    }

    @FXML
    void clickInicio(ActionEvent event) {
        VistaManager.cargarVista(Paths.DOCENTE_INICIO, rootPane);
    }

    @FXML
    void clickRegistrarLlegada(ActionEvent event) {
        VistaManager.cargarVista(Paths.DOCENTE_REGISTRAR_LLEGADA, rootPane);
    }

    @FXML
    void clickRevisarEstudiante(ActionEvent event) {
        VistaManager.cargarVista(Paths.CONSULTAR_ESTUDIANTE, rootPane);
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