package controllers.admin;

import data.UsuariosDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Docente;
import utils.Alertas;
import utils.Validaciones;

public class RegistrarDocenteAdminController {

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    void clickRegistrarDocente(ActionEvent event) {
        registrarDocente();
    }

    private void registrarDocente(){
        String nombre = null, apellido = null, password = null, user = null;
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

}
