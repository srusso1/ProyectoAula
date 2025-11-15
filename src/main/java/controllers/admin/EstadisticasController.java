package controllers.admin;

import data.LlegadasDAO;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.util.ArrayList;
import java.util.Arrays;

public class EstadisticasController {

    @FXML
    private PieChart graficoTorta;

    @FXML
    private BarChart<String, Number> graficoBarraMes;

    @FXML
    private CategoryAxis ejeX;

    @FXML
    private NumberAxis ejeY;

    LlegadasDAO llegadasDAO = new LlegadasDAO();

    private final ArrayList<Integer> infoLlegadas = llegadasDAO.infoIngresosGrado();

    @FXML
    public void initialize() {
        configurarGraficoTorta();
        configurarGraficoBarras();
    }

    private void configurarGraficoBarras(){
        // el eje X con los meses
        ejeX.setCategories(FXCollections.observableArrayList(
                Arrays.asList("Enero", "Febrero", "Marzo", "Abril", "Mayo")));

        // una serie por categoría
        XYChart.Series<String, Number> serieATiempo = new XYChart.Series<>();
        serieATiempo.setName("A tiempo");
        serieATiempo.getData().add(new XYChart.Data<>("Enero", 1050));
        serieATiempo.getData().add(new XYChart.Data<>("Febrero", 820));
        serieATiempo.getData().add(new XYChart.Data<>("Marzo", 940));
        serieATiempo.getData().add(new XYChart.Data<>("Abril", 980));
        serieATiempo.getData().add(new XYChart.Data<>("Mayo", 974));

        XYChart.Series<String, Number> serieTarde = new XYChart.Series<>();
        serieTarde.setName("Tarde");
        serieTarde.getData().add(new XYChart.Data<>("Enero", 520));
        serieTarde.getData().add(new XYChart.Data<>("Febrero", 480));
        serieTarde.getData().add(new XYChart.Data<>("Marzo", 500));
        serieTarde.getData().add(new XYChart.Data<>("Abril", 863));
        serieTarde.getData().add(new XYChart.Data<>("Mayo", 793));

        // agrego los datos al gráfico
        graficoBarraMes.getData().addAll(serieATiempo, serieTarde);

    }

    private void configurarGraficoTorta(){
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList(
                new PieChart.Data("6°", infoLlegadas.getFirst()),
                new PieChart.Data("7°", infoLlegadas.get(1)),
                new PieChart.Data("8°", infoLlegadas.get(2)),
                new PieChart.Data("9°", infoLlegadas.get(3)),
                new PieChart.Data("10°", infoLlegadas.get(4)),
                new PieChart.Data("11°", infoLlegadas.getLast())
        );

        graficoTorta.setData(datos);
        graficoTorta.setClockwise(true);
        graficoTorta.setLabelsVisible(true);
        graficoTorta.setStartAngle(180);

        double total = datos.stream().mapToDouble(PieChart.Data::getPieValue).sum();

        for (PieChart.Data data : graficoTorta.getData()) {
            double porcentaje = (data.getPieValue() / total) * 100;
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " (", (int) data.getPieValue(), " - ",
                            String.format("%.1f", porcentaje), "%)"
                    )
            );
        }
    }

}
