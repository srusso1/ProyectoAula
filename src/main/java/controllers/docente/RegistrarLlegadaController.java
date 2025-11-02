package controllers.docente;

import data.EstudiantesDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Estudiante;
import utils.Alertas;
import utils.Transiciones;
import utils.Validaciones;

public class RegistrarLlegadaController {

    @FXML
    private Button btnRegistrar;

    @FXML
    private VBox infoEstudiante;

    @FXML
    private VBox contenedor;

    @FXML
    private Label grado;

    @FXML
    private Label infoID;

    @FXML
    private Label nombre;

    @FXML
    private TextField txtID;

    @FXML
    void clickBuscar(ActionEvent event) {
        buscarEstudiante();
    }

    @FXML
    void clickRegistrar(ActionEvent event) {

    }

    @FXML
    void initialize() {
        Transiciones.cargarDesdeAbajo(contenedor, 1.2, 0, 1, 90, 0);
        ocultarElementos();
    }

    private void ocultarElementos(){
        btnRegistrar.setManaged(false);
        btnRegistrar.setVisible(false);
        infoEstudiante.setManaged(false);
        infoEstudiante.setVisible(false);
    }

    private void mostrarElementos(){
        btnRegistrar.setManaged(true);
        btnRegistrar.setVisible(true);
        infoEstudiante.setManaged(true);
        infoEstudiante.setVisible(true);
    }

    private void limpiarID(){
        txtID.clear();
    }

    private void buscarEstudiante(){
        EstudiantesDAO estudiantesDAO = new  EstudiantesDAO();
        if(!Validaciones.validarIdentificacion(txtID.getText())){
            limpiarID();
            return;
        }

        Long id = Long.parseLong(txtID.getText());
        Estudiante estudiante = estudiantesDAO.buscarEstudiante(id);
        if(estudiante != null){
            infoID.setText("> La identificacion " + estudiante.getIdentificacion() + " corresponde al siguiente estudiante:");
            nombre.setText("Nombre completo: " + estudiante.getNombre() + " " +  estudiante.getApellido());
            grado.setText("Grado: " + estudiante.getGrado());
            mostrarElementos();
        }else{
            Alertas.mostrarError("No hay ningún estudiante registrado con esa identificación");
            limpiarID();
        }
    }

}