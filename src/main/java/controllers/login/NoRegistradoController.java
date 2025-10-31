package controllers.login;

import data.UsuariosDAO;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import model.Docente;
import utils.*;

public class NoRegistradoController {

    private LoginController loginController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @FXML
    private Button btnVerificar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private VBox formNoRegistrado;

    @FXML
    private TextFlow textoInfo;

    @FXML
    private TextField txtID;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsuario;

    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private Docente docente;

    @FXML
    public void initialize() {
        ocultarElementos();
        Transiciones.cargarDesdeLado(formNoRegistrado, 1.2, 0, 1, -90, 0);
    }

    @FXML
    void clickVerificar(ActionEvent event) {
        validarDocente();
    }

    @FXML
    void clickRegistrar(ActionEvent event) {
        registrar();
    }

    @FXML
    void clickVolver(ActionEvent event) {
        volver();
    }

    private void volver(){
        if (loginController != null) {
            LoginFormController loginForm = VistaManager.cambiarVista(
                    loginController.getContenedorLogin(),
                    Paths.LOGIN_FORM
            );

            if (loginForm != null) {
                loginForm.setLoginController(loginController);
            }
        }
    }

    private void validarDocente() {
        if (Validaciones.validarIdentificacion(txtID.getText())) {
            long identi = Long.parseLong(txtID.getText());
            docente = usuariosDAO.buscarDocente(identi);

            if (docente != null) {
                if (docente.getUsuario() == null) {
                    Alertas.mostrarInfo("Docente " + docente.getNombre() + " "+ docente.getApellido() +
                            " complete el registro ingresando los campos requeridos a continuación");
                    visiblidadElementos();
                } else {
                    Alertas.mostrarError("El docente " + docente.getNombre() + " "+ docente.getApellido() +
                            " ya tiene registrado su usuario y contraseña");
                    limpiarID();
                }
            } else {
                Alertas.mostrarError("No hay ningún registro de docente con esa identificación");
                limpiarID();
            }
        } else {
            limpiarID();
        }
    }

    private void registrar() {
        if (!Validaciones.validarUsuario(txtUsuario.getText())) {
            limpiarCampos();
            return;
        }

        docente.setUsuario(txtUsuario.getText());

        if(usuariosDAO.buscarUsuario(docente.getUsuario())){
            Alertas.mostrarError("Este usuario ya esta en uso, intente nuevamente usando otro");
            limpiarCampos();
            return;
        }

        if (!Validaciones.validarPassword(txtPassword.getText())) {
            limpiarCampos();
            return;
        }

        docente.setPassword(txtPassword.getText());

        if (usuariosDAO.agregarUsuarioPassword(docente.getIdentificacion(), docente)) {
            Alertas.mostrarInfo("Se completo el registo con éxito, puede ingresar al software utilizando las credenciales registradas");
            limpiarTodo();
            volver();
        } else {
            Alertas.mostrarError("No se pudo completar el registro del docente");
        }
    }

    private void limpiarTodo(){
        limpiarID();
        limpiarCampos();
    }
    private void limpiarCampos() {
        txtPassword.clear();
        txtUsuario.clear();
    }

    private void limpiarID() {
        txtID.clear();
    }

    private void visiblidadElementos(){
        txtUsuario.setVisible(true);
        txtPassword.setVisible(true);
        txtUsuario.setManaged(true);
        txtPassword.setManaged(true);

        btnVerificar.setVisible(false);
        btnVerificar.setManaged(false);

        btnRegistrar.setVisible(true);
        btnRegistrar.setManaged(true);

        txtID.setDisable(true);
    }

    private void ocultarElementos(){
        txtUsuario.setVisible(false);
        txtPassword.setVisible(false);
        btnRegistrar.setVisible(false);

        txtUsuario.setManaged(false);
        txtPassword.setManaged(false);
        btnRegistrar.setManaged(false);
    }
}
