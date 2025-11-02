package controllers.admin;

import data.ConfigDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import utils.Alertas;
import utils.Extras;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConfiguracionController {

    @FXML
    private Spinner<LocalTime> spinnerHora;

    @FXML
    private Label lblHoraActual;

    ConfigDAO configDAO = new ConfigDAO();

    @FXML
    public void initialize() {
        configurarSpinner();
        mostrarHora();
    }

    @FXML
    void clickEstablecer(ActionEvent event) {
        establecerHora();

        /*
        System.out.println(spinnerHora.getValue());
        LocalTime horaLimite = spinnerHora.getValue();
        LocalTime horaActual = LocalTime.now().withSecond(0).withNano(0);
        // Comparar
        if (horaActual.isBefore(horaLimite)) {
            System.out.println("Aún estás dentro del horario permitido");
        } else if (horaActual.equals(horaLimite)) {
            System.out.println("Justo en la hora límite, hora actual: " + horaActual);
        } else {
            System.out.println("Se pasó la hora límite, hora actual: " + horaActual);
        }
        */

    }

    private void establecerHora(){
        LocalTime horaSeleccionada = spinnerHora.getValue();

        if(configDAO.establecerHora(Extras.formatoHora(horaSeleccionada))){
            Alertas.mostrarInfo("Hora establecida correctamente");
            mostrarHora();
        }
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