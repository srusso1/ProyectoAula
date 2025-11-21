package controllers.admin;

import data.LlegadasDAO;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utils.Transiciones;

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

    @FXML
    private VBox contenedor;

    LlegadasDAO llegadasDAO = new LlegadasDAO();

    private final ArrayList<Integer> infoLlegadas = llegadasDAO.infoIngresosGrado();

    @FXML
    public void initialize() {
        configurarGraficoTorta();
        configurarGraficoBarras();
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
    }

    private void configurarGraficoBarras(){
        // el eje X con los meses
        ejeX.setCategories(FXCollections.observableArrayList(
                Arrays.asList("Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre")));
        ArrayList<String[]> registros = llegadasDAO.infoIngresosMes();
        // una serie por categoría
        XYChart.Series<String, Number> serieATiempo = new XYChart.Series<>();
        serieATiempo.setName("A tiempo");
        serieATiempo.getData().add(new XYChart.Data<>("Febrero", Integer.parseInt(registros.getFirst()[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Marzo", Integer.parseInt(registros.get(1)[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Abril", Integer.parseInt(registros.get(2)[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Mayo", Integer.parseInt(registros.get(3)[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Junio", Integer.parseInt(registros.get(4)[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Julio", Integer.parseInt(registros.get(5)[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Agosto", Integer.parseInt(registros.get(6)[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Septiembre", Integer.parseInt(registros.get(7)[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Octubre", Integer.parseInt(registros.get(8)[1])));
        serieATiempo.getData().add(new XYChart.Data<>("Noviembre", Integer.parseInt(registros.get(9)[1])));

        XYChart.Series<String, Number> serieTarde = new XYChart.Series<>();
        serieTarde.setName("Tarde");
        serieTarde.getData().add(new XYChart.Data<>("Febrero", Integer.parseInt(registros.getFirst()[2])));
        serieTarde.getData().add(new XYChart.Data<>("Marzo", Integer.parseInt(registros.get(1)[2])));
        serieTarde.getData().add(new XYChart.Data<>("Abril", Integer.parseInt(registros.get(2)[2])));
        serieTarde.getData().add(new XYChart.Data<>("Mayo", Integer.parseInt(registros.get(3)[2])));
        serieTarde.getData().add(new XYChart.Data<>("Junio", Integer.parseInt(registros.get(4)[2])));
        serieTarde.getData().add(new XYChart.Data<>("Julio", Integer.parseInt(registros.get(5)[2])));
        serieTarde.getData().add(new XYChart.Data<>("Agosto", Integer.parseInt(registros.get(6)[2])));
        serieTarde.getData().add(new XYChart.Data<>("Septiembre", Integer.parseInt(registros.get(7)[2])));
        serieTarde.getData().add(new XYChart.Data<>("Octubre", Integer.parseInt(registros.get(8)[2])));
        serieTarde.getData().add(new XYChart.Data<>("Noviembre", Integer.parseInt(registros.get(9)[2])));

        // agrego los datos al gráfico
        graficoBarraMes.getData().addAll(serieATiempo, serieTarde);

        for (XYChart.Series<String, Number> serie : graficoBarraMes.getData()) {
            for (XYChart.Data<String, Number> data : serie.getData()) {
                Tooltip tooltip = new Tooltip(
                        serie.getName() + ": " + data.getYValue().intValue()
                );
                Tooltip.install(data.getNode(), tooltip);

                // Opcional: mejora visual (para que el tooltip aparezca más rápido)
                tooltip.setShowDelay(Duration.millis(100));
            }
        }


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
