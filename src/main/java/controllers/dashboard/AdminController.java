package controllers.dashboard;


import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import utils.Alertas;
import utils.Paths;

import java.io.IOException;
import java.util.Objects;

public class AdminController {

    @FXML
    private Button btnSalir;

    @FXML
    private BorderPane rootPane;

    @FXML
    void clickInicio(ActionEvent event) {
        cargarVista(Paths.ADMIN_INICIO);

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
        cargarVista(Paths.VER_ESTUDIANTES_ADMIN);
    }

    @FXML
    void clickRegistrarEstudiante(ActionEvent event) {
        cargarVista(Paths.REGISTRO_ESTUDIANTE_ADMIN);
    }

    @FXML
    void clickRegistrarDocente(ActionEvent event) {
        cargarVista(Paths.REGISTRAR_DOCENTE_ADMIN);
    }

    @FXML
    void clickInformes(ActionEvent event) {

    }

    @FXML
    void initialize(){
        cargarVista(Paths.ADMIN_INICIO);
    }

    private void cargarVista(String rutaFXML) {
        try {
            Node vista = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(rutaFXML)));
            rootPane.setCenter(vista);
        } catch (IOException e) {
            System.out.println(("No se pudo cargar la vista: " + e.getMessage()));
        }
    }



}

