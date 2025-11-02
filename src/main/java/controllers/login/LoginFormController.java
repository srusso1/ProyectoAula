package controllers.login;

import application.App;
import data.UsuariosDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Usuario;
import utils.*;

public class LoginFormController {

    private LoginController loginController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @FXML
    private VBox formularioIngreso;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsuario;

    @FXML
    private Button btnIngresar;

    @FXML
    void clickIngresar(ActionEvent event) {
        ingresar();
    }

    @FXML
    void clickNoRegistrado(ActionEvent event) {
        irNoRegistrado();
    }
    @FXML

    private void irNoRegistrado(){
        if (loginController != null) {
            // Cargar la vista de No Registrado
            NoRegistradoController noReg = VistaManager.cambiarVista(
                    loginController.getContenedorLogin(),
                    Paths.NO_REGISTRADO_FORM
            );

            if (noReg != null) {
                noReg.setLoginController(loginController);
            }
        }
    }

    private void ingresar() {
        String usuario;
        String password;

        if(!Validaciones.validarUsuario(txtUsuario.getText())){
            limpiarCampos();
            return;
        }

        usuario = txtUsuario.getText();

        if(!Validaciones.validarPassword(txtPassword.getText())){
            limpiarCampos();
            return;
        }
        password = txtPassword.getText();

        UsuariosDAO usuarioDAO = new UsuariosDAO();
        Usuario user = usuarioDAO.validarUsuario(usuario, password);

        App.usuarioLogueado = user;

        if (user != null) {
            Alertas.mostrarInfo("Bienvenido " + user.getNombre() + " " + user.getApellido() +
                    " [" + user.getClass().getSimpleName().toUpperCase() + "]");

            if (user.getClass().getSimpleName().equals("Administrador")) {
                App.setRoot(Paths.ADMIN_VIEW);
            } else {
                App.setRoot(Paths.DOCENTE_VIEW);
            }
            App.usuarioLogueado.setID(user.getID());
        } else {
            Alertas.mostrarError("Usuario o contraseña incorrectos");
            limpiarCampos();
        }
    }

    private void limpiarCampos(){
        txtUsuario.clear();
        txtPassword.clear();
    }
}
