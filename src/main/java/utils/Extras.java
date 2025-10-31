package utils;

import application.App;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Extras {

    public static void mostrarFechaHoy(Label label) {
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Locale locale = new Locale("es", "ES");
        String diaSemana = fechaHoy.getDayOfWeek().getDisplayName(TextStyle.FULL, locale).toUpperCase();

        label.setText("Fecha actual: " + fechaHoy.format(formatoFecha) + " - " + diaSemana);
    }

    public static void textoBienvenida(Label label){
        label.setText("Bienvenido " + App.usuarioLogueado.getClass().getSimpleName().toUpperCase() +
                " " + App.usuarioLogueado.getNombre() + " " + App.usuarioLogueado.getApellido());
    }
}
