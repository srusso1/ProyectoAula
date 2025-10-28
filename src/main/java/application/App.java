package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Usuario;
import utils.Alertas;
import utils.Paths;

import java.io.IOException;

public class App extends Application {

    private static Stage primaryStage;

    public static Usuario usuarioLogueado = null;

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.LOGIN_VIEW));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
            Scene nuevaEscena = new Scene(loader.load());
            primaryStage.setScene(nuevaEscena);
            primaryStage.show();
        } catch (IOException e) {
            Alertas.mostrarError("Error al cargar la vista: " + e.getMessage());
        }
    }

}
