package controllers.docente;

import application.App;
import data.ConfigDAO;
import data.EstudiantesDAO;
import data.UsuariosDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Estudiante;
import model.Informes;
import model.Usuario;
import utils.Alertas;
import utils.Extras;
import utils.Transiciones;
import utils.Validaciones;

import java.util.ArrayList;
import java.util.Objects;

public class InformeController {

    @FXML
    private HBox contenedorEstudiante;

    @FXML
    private VBox contenedor;

    @FXML
    private Label infoEstu;

    @FXML
    private Label lbInstrucciones;

    @FXML
    private TextField txtID;

    @FXML
    private Button btnInforme;

    Estudiante estudiante;
    EstudiantesDAO estudiantesDAO = new EstudiantesDAO();
    UsuariosDAO usuariosDAO = new UsuariosDAO();
    Informes informes;
    ConfigDAO configDAO = new ConfigDAO();

    @FXML
    void clickBuscar(ActionEvent event) {
        buscarEstudiante();
    }

    @FXML
    void clickInforme(ActionEvent event) {
        generarInforme();
    }

    @FXML
    void initialize() {
        ocultarElementos();
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
    }

    private void buscarEstudiante(){
        if(!Validaciones.validarIdentificacion(txtID.getText())){
            limpiarCampos();
            return;
        }

        long identificacion = Long.parseLong(txtID.getText());
        estudiante = estudiantesDAO.buscarEstudiante(identificacion);
        if(estudiante == null){
            Alertas.mostrarError("No hay ningún estudiante registrado con esa identificación");
            limpiarCampos();
            ocultarElementos();
        }else{
            infoEstu.setText(estudiante.getNombreCompleto() + " — Grado " + estudiante.getGrado() + "°");
            mostrarElementos();
        }
    }

    private void generarInforme(){
        // Parámetros informes: String fecha, Estudiante estudiante, String nombreEncargado, ArrayList<String[]> infoLlegadas

        String nombreEncargado = App.usuarioLogueado.getNombreCompleto();
        String fechaHoy = Extras.fechaHoy();
        ArrayList<String[]> infoLlegadas = estudiantesDAO.infoIngresoEstudiante(estudiante.getID());

        if(infoLlegadas.isEmpty()){
            Alertas.mostrarWarning("El estudiante " + estudiante.getNombreCompleto() + " no tiene ningún ingreso registrado, no se puede generar el informe.");
            ocultarElementos();
            limpiarCampos();
            return;
        }else{
            int totalIngresosTarde = 0;
            for(String[] fila : infoLlegadas){
                if(fila[2].equals("Ingreso tarde")){
                    totalIngresosTarde++;
                }
            }
            if(totalIngresosTarde == 0){
                Alertas.mostrarWarning("El estudiante " + estudiante.getNombreCompleto() + " no tiene ningún ingreso tarde registrado, no se puede generar el informe.");
                ocultarElementos();
                limpiarCampos();
                return;
            }
        }

        informes = new Informes(fechaHoy, estudiante, nombreEncargado, infoLlegadas);

        if(informes.generarInforme()){
            int idDocente = App.usuarioLogueado.getID();
            usuariosDAO.registrarInforme(idDocente);
            Alertas.mostrarExito("Informe generado correctamente. Revise la carpeta de informes.");
            Extras.abrirCarpeta(configDAO.obtenerRutaArchivo());
        }

        ocultarElementos();
        limpiarCampos();
    }

    private void limpiarCampos(){
        txtID.clear();
    }

    private void ocultarElementos(){
        contenedorEstudiante.setVisible(false);
        contenedorEstudiante.setManaged(false);

        btnInforme.setVisible(false);
        btnInforme.setManaged(false);
    }

    private void mostrarElementos(){
        contenedorEstudiante.setVisible(true);
        contenedorEstudiante.setManaged(true);

        btnInforme.setVisible(true);
        btnInforme.setManaged(true);

        lbInstrucciones.setVisible(false);
        lbInstrucciones.setManaged(false);
    }
}

