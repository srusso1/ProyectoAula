package controllers.admin;

import data.EstudiantesDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Estudiante;
import utils.Alertas;
import utils.Validaciones;

import java.io.IOException;

public class RegistrarEstudianteAdminController {

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    void clickRegistrarEstudiante(ActionEvent event) throws IOException {
        registrarEstudiante();
    }

    EstudiantesDAO estudiantesDAO = new EstudiantesDAO();

    private void registrarEstudiante() throws IOException {
        String nombre = null;
        String apellido = null;
        long identificacion = 0;
        int grado = -1;

        if(Validaciones.validarIdentificacion(txtID.getText(), "estudiante")){
            identificacion = Long.parseLong(txtID.getText());
        }else{
            limpiarCampos();
            return;
        }

        if(Validaciones.validarTexto(txtNombre.getText(), "nombre")){
            nombre = txtNombre.getText().toUpperCase();
        }else{
            limpiarCampos();
            return;
        }

        if(Validaciones.validarTexto(txtApellido.getText(), "apellido")){
            apellido = txtApellido.getText().toUpperCase();
        }else{
            limpiarCampos();
            return;
        }

        if (comboBox.getSelectionModel().getSelectedItem() == null) {
            Alertas.mostrarError("Es obligatorio seleccionar un grado");
            return;
        } else {
            grado = Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString());
        }

        Estudiante estudiante = new Estudiante(nombre, apellido, identificacion, grado);

        if(estudiantesDAO.registrarEstudiante(estudiante)){
            Alertas.mostrarExito("Estudiante registrado correctamente");
        }else{
            Alertas.mostrarError("No se pudo registrar el estudiante");
        }

        limpiarCampos();

    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtID.clear();
        comboBox.getSelectionModel().clearSelection();
    }

    @FXML
    void initialize(){
        comboBox.setItems(FXCollections.observableArrayList(6,7,8,9,10,11));
    }

}

