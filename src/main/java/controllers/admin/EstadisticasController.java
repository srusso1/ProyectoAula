package controllers.admin;

import data.LlegadasDAO;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;

public class EstadisticasController {

    @FXML
    private PieChart graficoTorta;
    LlegadasDAO llegadasDAO = new LlegadasDAO();

    private final ArrayList<Integer> infoLlegadas = llegadasDAO.infoIngresosGrado();

    @FXML
    public void initialize() {
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList(
                new PieChart.Data("6°", infoLlegadas.getFirst()),
                new PieChart.Data("7°", infoLlegadas.get(1)),
                new PieChart.Data("8°", infoLlegadas.get(2)),
                new PieChart.Data("9°", infoLlegadas.get(3)),
                new PieChart.Data("10°", infoLlegadas.get(4)),
                new PieChart.Data("11°", infoLlegadas.getLast())
        );

        graficoTorta.setData(datos);
        graficoTorta.setTitle("Ingresos tarde por grado");
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
