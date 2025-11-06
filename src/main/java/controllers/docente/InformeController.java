package controllers.docente;

import application.App;
import data.EstudiantesDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Estudiante;
import model.Informes;
import utils.Alertas;
import utils.Extras;
import utils.Validaciones;

import java.util.ArrayList;
import java.util.Objects;

public class InformeController {

    @FXML
    private VBox infoEstudiante;

    @FXML
    private Label lbGrado;

    @FXML
    private Label lbNombre;

    @FXML
    private TextField txtID;

    @FXML
    private Button btnInforme;

    Estudiante estudiante;
    EstudiantesDAO estudiantesDAO = new EstudiantesDAO();
    Informes informes;

    @FXML
    void clickBuscar(ActionEvent event) {
        if(!Validaciones.validarIdentificacion(txtID.getText())){
            limpiarCampos();
            return;
        }

        long identificacion = Long.parseLong(txtID.getText());
        estudiante = estudiantesDAO.buscarEstudiante(identificacion);
        if(estudiante == null){
            Alertas.mostrarError("No hay ningún estudiante registrado con esa identificación");
            limpiarCampos();
        }else{
            lbNombre.setText("Nombre completo: " + estudiante.getNombreCompleto());
            lbGrado.setText("Grado: " + estudiante.getGrado());
            mostrarElementos();
        }
    }

    @FXML
    void clickInforme(ActionEvent event) {
        // Parámetros informes: String fecha, Estudiante estudiante, String nombreEncargado, ArrayList<String[]> infoLlegadas

        String nombreEncargado = App.usuarioLogueado.getNombreCompleto();
        String fechaHoy = Extras.fechaHoy();
        ArrayList<String[]> infoLlegadas = estudiantesDAO.infoIngresoEstudiante(estudiante.getID());

        if(infoLlegadas.isEmpty()){
            Alertas.mostrarWarning("El estudiante " + estudiante.getNombreCompleto() + " no tiene ningún ingreso registrado, no se puede generar el informe.");
            return;
        }else{
            if(!Objects.equals(infoLlegadas.getFirst()[2], "Ingreso tarde")){
                Alertas.mostrarWarning("El estudiante " + estudiante.getNombreCompleto() + " no tiene ningún ingreso tarde registrado, no se puede generar el informe.");
                return;
            }
        }

        informes = new Informes(fechaHoy, estudiante, nombreEncargado, infoLlegadas);
        Informes.generarInforme();
    }

    @FXML
    void initialize() {
        ocultarElementos();
    }

    private void limpiarCampos(){
        txtID.clear();
    }

    private void ocultarElementos(){
        infoEstudiante.setVisible(false);
        infoEstudiante.setManaged(false);

        btnInforme.setVisible(false);
        btnInforme.setManaged(false);
    }

    private void mostrarElementos(){
        infoEstudiante.setVisible(true);
        infoEstudiante.setManaged(true);

        btnInforme.setVisible(true);
        btnInforme.setManaged(true);
    }
}

