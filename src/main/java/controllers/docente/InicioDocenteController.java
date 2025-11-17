package controllers.docente;

import application.App;
import data.LlegadasDAO;
import data.UsuariosDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utils.Extras;
import utils.Transiciones;

public class InicioDocenteController {

    @FXML
    private Label txtBienvenida;

    @FXML
    private Label txtFecha;

    @FXML
    private VBox contenedor;

    @FXML
    private Label totalLlegadas;

    @FXML
    private Label totalInformes;

    @FXML
    private Label totalTardes;

    LlegadasDAO llegadasDAO = new LlegadasDAO();
    UsuariosDAO usuariosDAO = new UsuariosDAO();

    @FXML
    void initialize(){
        Extras.mostrarFechaHoy(txtFecha);
        Extras.textoBienvenida(txtBienvenida);
        asignarConteo();
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
    }

    private void asignarConteo(){
        int idDocente = App.usuarioLogueado.getID();
        totalLlegadas.setText("Total: " + llegadasDAO.conteoLlegadas(idDocente, 0));
        totalTardes.setText("Total: " + llegadasDAO.conteoLlegadas(idDocente, 1));
        totalInformes.setText("Total: " + usuariosDAO.cantidadInformes(idDocente));
    }
}
