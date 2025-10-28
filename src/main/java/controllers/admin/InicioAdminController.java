package controllers.admin;


import application.App;
import data.EstudiantesDAO;
import data.UsuariosDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


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
        mostrarFechaHoy();
        mostrarDatosUsuario();
        mostrarCantidadEstudiantes();
        mostrarCantidadDocentes();
    }

    private void mostrarFechaHoy(){
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Locale locale = new Locale("es", "ES");
        String diaSemana = fechaHoy.getDayOfWeek().getDisplayName(TextStyle.FULL, locale).toUpperCase();

        lbFecha.setText("Fecha actual: " + fechaHoy.format(formatoFecha) + " - " + diaSemana);
    }

    private void mostrarDatosUsuario(){
        txtBienvenida.setText("Bienvenido ADMINISTRADOR, " + App.usuarioLogueado.getNombre() + " " + App.usuarioLogueado.getApellido() );
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