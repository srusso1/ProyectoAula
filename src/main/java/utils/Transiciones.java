package utils;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Transiciones {

    public static void cargarDesdeAbajo(Node elemento, double duracion, int fromValue, int toValue, int fromY, int toY){
        FadeTransition fade = new FadeTransition(Duration.seconds(duracion), elemento);
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);

        TranslateTransition translate = new TranslateTransition(Duration.seconds(duracion), elemento);
        translate.setFromY(fromY);
        translate.setToY(toY);

        fade.play();
        translate.play();

    }

    public static void cargarDesdeLado(Node elemento, double duracion, int fromValue, int toValue, int fromX, int toX){
        FadeTransition fade = new FadeTransition(Duration.seconds(duracion), elemento);
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        TranslateTransition translate = new TranslateTransition(Duration.seconds(duracion), elemento);
        translate.setFromX(fromX);
        translate.setToX(toX);
        fade.play();
        translate.play();
    }

}
