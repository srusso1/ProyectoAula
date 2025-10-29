package controllers.login;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import utils.Paths;
import utils.Transiciones;
import utils.VistaManager;

public class LoginController {

    @FXML
    private AnchorPane loginPanel;

    @FXML
    private AnchorPane contenedorLogin;

    @FXML
    public void initialize() {
        // Cargar la vista inicial (LoginForm)
        LoginFormController formController = VistaManager.cambiarVista(
                contenedorLogin,
                Paths.LOGIN_FORM
        );

        if (formController != null) {
            formController.setLoginController(this);
        }

        Transiciones.cargarDesdeAbajo(loginPanel, 1.2, 0, 1, 90, 0);
    }

    public AnchorPane getContenedorLogin() {
        return contenedorLogin;
    }
}
