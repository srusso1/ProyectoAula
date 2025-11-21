package controllers.admin;

import data.UsuariosDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Docente;
import utils.Alertas;
import utils.Transiciones;
import utils.Validaciones;

public class RegistrarDocenteAdminController {

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    private VBox contenedor;

    @FXML
    void clickRegistrarDocente(ActionEvent event) {
        registrarDocente();
    }

    private void registrarDocente(){
        String nombre, apellido;
        long identificacion;

        if(!Validaciones.validarIdentificacion(txtID.getText(), "docente")){
            limpiarCampos();
            return;
        }

        identificacion = Long.parseLong(txtID.getText());

        if(!Validaciones.validarTexto(txtNombre.getText(), "nombre")){
            limpiarCampos();
            return;
        }
        nombre = txtNombre.getText().toUpperCase();

        if(!Validaciones.validarTexto(txtApellido.getText(), "apellido")){
            limpiarCampos();
            return;
        }
        apellido = txtApellido.getText().toUpperCase();

        Docente docente = new Docente(nombre, apellido, identificacion);
        UsuariosDAO usuariosDAO = new UsuariosDAO();

        if(usuariosDAO.registrarDocente(docente)){
            Alertas.mostrarInfo("Fue registrado correctamente el docente");
        }else{
            Alertas.mostrarError("Error al registrar el docente");
        }

        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtID.clear();
    }

    @FXML
    void initialize(){
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
    }

}
