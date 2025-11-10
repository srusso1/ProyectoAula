package controllers.admin;

import data.EstudiantesDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Estudiante;

import java.util.ArrayList;

public class VerEstudiantesAdminController {

    @FXML
    private TableColumn<Estudiante, String> apellido;

    @FXML
    private TableColumn<Estudiante, Integer> grado;

    @FXML
    private TableColumn<Estudiante, String> llegadas;

    @FXML
    private TableColumn<Estudiante, String> nombre;

    @FXML
    private TableColumn<Estudiante, Long> identificacion;

    @FXML
    private TableView<Estudiante> tabla;
    static EstudiantesDAO estudiantesDAO = new EstudiantesDAO();

    private final ArrayList<Estudiante> estudiantes = estudiantesDAO.listaEstudiantesIngresos();

    @FXML
    public void initialize() {
        identificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        grado.setCellValueFactory(new PropertyValueFactory<>("grado"));
        llegadas.setCellValueFactory(new PropertyValueFactory<>("llegadas"));
        tabla.getItems().setAll(estudiantes);
    }

}
