package controllers.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class DocenteController {

    @FXML
    private StackPane contenidoCentral;

    @FXML
    void cerrarSesion(ActionEvent event) {

    }

    @FXML
    void mostrarEstudiantes(ActionEvent event) {
        contenidoCentral.getChildren().clear();
    }

    @FXML
    void mostrarPerfil(ActionEvent event) {

    }

    @FXML
    void subirNotas(ActionEvent event) {

    }

}
