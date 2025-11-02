package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class VistaManager {

    public static <T> T cambiarVista(AnchorPane principal, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(VistaManager.class.getResource(rutaFXML)));
            Parent nuevaVista = loader.load();

            // Reemplaza el contenido del AnchorPane principal
            principal.getChildren().setAll(nuevaVista);

            // Ajusta la nueva vista para ocupar todo el espacio del contenedor
            AnchorPane.setTopAnchor(nuevaVista, 0.0);
            AnchorPane.setRightAnchor(nuevaVista, 0.0);
            AnchorPane.setBottomAnchor(nuevaVista, 0.0);
            AnchorPane.setLeftAnchor(nuevaVista, 0.0);

            // Devuelve el controlador
            return loader.getController();

        } catch (IOException e) {
            System.out.println("No se pudo cargar la vista: " + e.getMessage());
            return null;
        }
    }

    public static void cargarVista(String rutaFXML, BorderPane principal) {
        try {
            URL recurso = VistaManager.class.getResource(rutaFXML);
            if (recurso == null) {
                System.err.println("ERROR: No se encontró el archivo FXML en la ruta: " + rutaFXML);
                System.err.println("Verifica que el archivo existe en src/main/resources" + rutaFXML);
                return;
            }

            Node vista = FXMLLoader.load(recurso);
            principal.setCenter(vista);
        } catch (IOException e) {
            System.err.println("ERROR: No se pudo cargar la vista desde: " + rutaFXML);
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
