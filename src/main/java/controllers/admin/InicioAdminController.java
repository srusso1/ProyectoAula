package controllers.admin;


import application.App;
import data.EstudiantesDAO;
import data.UsuariosDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.Extras;


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

    EstudiantesDAO estudiantesDAO = new EstudiantesDAO();
    UsuariosDAO usuariosDAO = new UsuariosDAO();

    @FXML
    void initialize(){
        Extras.mostrarFechaHoy(lbFecha);
        Extras.textoBienvenida(txtBienvenida);
        mostrarCantidadEstudiantes();
        mostrarCantidadDocentes();
    }

    private void mostrarCantidadDocentes(){
        int cantidadDocentes = usuariosDAO.cantidadDocentes();
        txtDocenteRegistrado.setText("Total: " + cantidadDocentes);
    }


    private void mostrarCantidadEstudiantes(){
        int cantidadEstudiantes = estudiantesDAO.cantidadEstudiantes();
        txtEstuRegistrado.setText("Total: " + cantidadEstudiantes);
    }

}