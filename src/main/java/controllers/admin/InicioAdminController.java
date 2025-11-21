package controllers.admin;


import application.App;
import data.EstudiantesDAO;
import data.UsuariosDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utils.Extras;
import utils.Transiciones;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class InicioAdminController {

    @FXML
    private Label lbFecha;

    @FXML
    private Label txtBienvenida;

    @FXML
    private Label txtDocenteRegistrado;

    @FXML
    private Label txtEstuRegistrado;

    @FXML
    private Label txtInformeRegistrado;

    @FXML
    private VBox contenedor;

    EstudiantesDAO estudiantesDAO = new EstudiantesDAO();
    UsuariosDAO usuariosDAO = new UsuariosDAO();

    @FXML
    void initialize(){
        Extras.mostrarFechaHoy(lbFecha);
        Extras.textoBienvenida(txtBienvenida);
        mostrarCantidadEstudiantes();
        mostrarCantidadDocentes();
        mostrarCantidadInformes();
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
    }

    private void mostrarCantidadDocentes(){
        int cantidadDocentes = usuariosDAO.cantidadDocentes();
        txtDocenteRegistrado.setText("Total: " + cantidadDocentes);
    }


    private void mostrarCantidadEstudiantes(){
        int cantidadEstudiantes = estudiantesDAO.cantidadEstudiantes();
        txtEstuRegistrado.setText("Total: " + cantidadEstudiantes);
    }

    private void mostrarCantidadInformes(){
        int cantidadInformes = usuariosDAO.totalInformes();
        txtInformeRegistrado.setText("Total: " + cantidadInformes);
    }

}