package controllers.docente;

import application.App;
import data.LlegadasDAO;
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
    private Label totalTardes;

    LlegadasDAO llegadasDAO = new LlegadasDAO();

    @FXML
    void initialize(){
        Extras.mostrarFechaHoy(txtFecha);
        Extras.textoBienvenida(txtBienvenida);
        asignarConteo();
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
    }

    private void asignarConteo(){
        totalLlegadas.setText("Total: " + llegadasDAO.conteoLlegadas(App.usuarioLogueado.getID(), 0));
        totalTardes.setText("Total: " + llegadasDAO.conteoLlegadas(App.usuarioLogueado.getID(), 1));
    }
}
