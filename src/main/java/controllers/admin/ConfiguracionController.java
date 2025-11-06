package controllers.admin;

import application.App;
import data.ConfigDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import utils.Alertas;
import utils.Extras;
import utils.Paths;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConfiguracionController {

    @FXML
    private Spinner<LocalTime> spinnerHora;

    @FXML
    private Label lblHoraActual;

    @FXML
    private Text infoRuta;

    ConfigDAO configDAO = new ConfigDAO();

    @FXML
    private Button btnSeleccionarCarpeta;


    @FXML
    public void initialize() {
        configurarSpinner();
        mostrarHora();
        mostrarRutaActual();
    }

    @FXML
    void clickEstablecer(ActionEvent event) {
        establecerHora();
    }

    @FXML
    void clickSeleccionarCarpeta(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta para guardar informe");

        // Puedes establecer una carpeta inicial opcional:
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Documents"));
        Stage stage = (Stage) btnSeleccionarCarpeta.getScene().getWindow();
        File carpetaSeleccionada = directoryChooser.showDialog(stage);

        if (carpetaSeleccionada != null) {
            String ruta = carpetaSeleccionada.getAbsolutePath();
            if(configDAO.establecerRutaArchivo(ruta)){
                Alertas.mostrarInfo("Ruta de guardado establecida correctamente");
                mostrarRutaActual();
            }
        } else {
            System.out.println("No se seleccionó ninguna carpeta.");
        }
    }

    @FXML
    void clickEliminarRuta(ActionEvent event) {
        boolean confirmar = Alertas.mostrarConfirmacion("¿Estás seguro de eliminar la ruta actual? No se podran guardar informes si no hay una ruta establecida");
        if (confirmar) {
            if(configDAO.eliminarRutaEstablecida()){
                Alertas.mostrarInfo("La ruta de guardado se eliminó correctamente.");
                mostrarRutaActual();
            }else{
                Alertas.mostrarError("Aún no se ha establecido una ruta para guardar los informes.");
            }
        }else{
            Alertas.mostrarInfo("Acción cancelada por el usuario");
        }


    }

    private void establecerHora(){
        LocalTime horaSeleccionada = spinnerHora.getValue();

        if(configDAO.establecerHora(Extras.formatoHora(horaSeleccionada))){
            Alertas.mostrarInfo("Hora establecida correctamente");
            mostrarHora();
        }
    }

    private void mostrarRutaActual(){
        infoRuta.setText((configDAO.obtenerRutaArchivo() == null ? "No se ha establecido una ruta para guardar informes" : configDAO.obtenerRutaArchivo()));
    }

    private void mostrarHora(){
        lblHoraActual.setText(configDAO.obtenerHora());
    }

    private void configurarSpinner(){
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                // Formato de 12 horas con AM/PM
                setConverter(new javafx.util.StringConverter<LocalTime>() {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

                    @Override
                    public String toString(LocalTime time) {
                        return (time != null) ? time.format(formatter) : "";
                    }

                    @Override
                    public LocalTime fromString(String string) {
                        try {
                            return LocalTime.parse(string.toUpperCase(), formatter);
                        } catch (Exception e) {
                            return getValue(); // Mantiene el valor actual si hay error
                        }
                    }
                });

                setValue(LocalTime.of(8, 0)); // Valor inicial
            }

            private final int STEP_MINUTES = 5;
            private final LocalTime MIN_TIME = LocalTime.of(6, 0);
            private final LocalTime MAX_TIME = LocalTime.of(23, 55);

            public void increment(int steps) {
                LocalTime newTime = getValue().plusMinutes(steps * STEP_MINUTES);
                if (newTime.isAfter(MAX_TIME)) newTime = MIN_TIME;
                setValue(newTime);
            }

            public void decrement(int steps) {
                LocalTime newTime = getValue().minusMinutes(steps * STEP_MINUTES);
                if (newTime.isBefore(MIN_TIME)) newTime = MAX_TIME;
                setValue(newTime);
            }
        };
        spinnerHora.setValueFactory(valueFactory);
    }

}