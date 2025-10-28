package controllers.login;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import utils.Paths;
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

        // Efectos visuales del panel principal
        FadeTransition fade = new FadeTransition(Duration.seconds(1.2), loginPanel);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition translate = new TranslateTransition(Duration.seconds(1.2), loginPanel);
        translate.setFromY(90);
        translate.setToY(0);

        fade.play();
        translate.play();
    }

    public AnchorPane getContenedorLogin() {
        return contenedorLogin;
    }
}
