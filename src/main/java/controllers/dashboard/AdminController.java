package controllers.dashboard;


import application.App;
import data.LlegadasDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import utils.Alertas;
import utils.Paths;
import utils.VistaManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AdminController {

    @FXML
    private Button btnSalir;

    @FXML
    private BorderPane rootPane;

    LlegadasDAO llegadasDAO = new LlegadasDAO();

    @FXML
    void clickInicio(ActionEvent event) {
        VistaManager.cargarVista(Paths.ADMIN_INICIO, rootPane);

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
    void clickVerEstudiantes(ActionEvent event) {
        VistaManager.cargarVista(Paths.VER_ESTUDIANTES_ADMIN, rootPane);
    }

    @FXML
    void clickRegistrarEstudiante(ActionEvent event) {
        VistaManager.cargarVista(Paths.REGISTRO_ESTUDIANTE_ADMIN, rootPane);
    }

    @FXML
    void clickRegistrarDocente(ActionEvent event) {
        VistaManager.cargarVista(Paths.REGISTRAR_DOCENTE_ADMIN, rootPane);
    }

    @FXML
    void clickInformes(ActionEvent event) {
        ArrayList<String[]> registros = llegadasDAO.infoIngresosMes();
        if(registros.isEmpty()){
            Alertas.mostrarWarning("Aún no se han registrado ingresos, no hay estadísticas disponibles");
        }else{
            VistaManager.cargarVista(Paths.ESTADISTICAS, rootPane);
        }


    }

    @FXML
    void clickConfiguracion(ActionEvent event) {
        VistaManager.cargarVista(Paths.CONFIGURACION_ADMIN, rootPane);
    }

    @FXML
    void initialize(){
        VistaManager.cargarVista(Paths.ADMIN_INICIO, rootPane);
    }


}

