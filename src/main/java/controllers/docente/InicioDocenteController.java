package controllers.docente;

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
    void initialize(){
        Extras.mostrarFechaHoy(txtFecha);
        Extras.textoBienvenida(txtBienvenida);
        Transiciones.cargarDesdeAbajo(contenedor, 1.2, 0, 1, 90, 0);
    }
}
