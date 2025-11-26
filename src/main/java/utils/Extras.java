package utils;

import application.App;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public static String fechaHoy(){
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Locale locale = new Locale("es", "ES");
        String diaSemana = fechaHoy.getDayOfWeek().getDisplayName(TextStyle.FULL, locale).toUpperCase();

        return fechaHoy.format(formatoFecha) + " - " + diaSemana;
    }

    public static void textoBienvenida(Label label){
        label.setText("Bienvenido, " + App.usuarioLogueado.getClass().getSimpleName().toUpperCase() +
                " " + App.usuarioLogueado.getNombre() + " " + App.usuarioLogueado.getApellido());
    }

    public static String formatoHora(LocalTime hora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        return hora.format(formatter).replace(".", "");
    }

    public static void abrirCarpeta(String ruta) {
        try {
            File carpeta = new File(ruta);
            if (carpeta.exists()) {
                Desktop.getDesktop().open(carpeta);
            } else {
                Alertas.mostrarError("La carpeta no existe.");
            }
        } catch (Exception e) {
            Alertas.mostrarError("Error al abrir la carpeta: " + e.getMessage());
        }
    }

}
