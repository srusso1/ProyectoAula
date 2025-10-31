package controllers.docente;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.Extras;

public class InicioDocenteController {

    @FXML
    private Label txtBienvenida;

    @FXML
    private Label txtFecha;

    @FXML
    void initialize(){
        Extras.mostrarFechaHoy(txtFecha);
        Extras.textoBienvenida(txtBienvenida);
    }
}
