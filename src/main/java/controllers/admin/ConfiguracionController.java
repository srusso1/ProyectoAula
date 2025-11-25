package controllers.admin;

import application.App;
import data.ConfigDAO;
import data.EstudiantesDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Estudiante;
import utils.Alertas;
import utils.Extras;
import utils.Paths;
import utils.Transiciones;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConfiguracionController {

    @FXML
    private Spinner<LocalTime> spinnerHora;

    @FXML
    private Label lblHoraActual;

    @FXML
    private Button btnImportar;

    @FXML
    private Text infoRuta;

    @FXML
    private Button btnSeleccionarCarpeta;

    @FXML
    private TableColumn<Estudiante, String> colApellido;

    @FXML
    private TableColumn<Estudiante, Integer> colGrado;

    @FXML
    private TableColumn<Estudiante, Long> colIdentificacion;

    @FXML
    private TableColumn<Estudiante, String> colNombre;

    @FXML
    private TableView<Estudiante> tabla;

    @FXML
    private Button btnBorrarLista;

    @FXML
    private VBox contenedor;

    private ArrayList<Estudiante> estudiantes = new ArrayList<>();
    ConfigDAO configDAO = new ConfigDAO();
    EstudiantesDAO estudiantesDAO = new  EstudiantesDAO();

    @FXML
    public void initialize() {
        configurarSpinner();
        mostrarHora();
        mostrarRutaActual();
        ocultarElementos();
        Transiciones.cargarDesdeLado(contenedor, 1, 0, 1, -90, 0);
    }

    @FXML
    void clickEstablecer(ActionEvent event) {
        establecerHora();
    }

    @FXML
    void clickSeleccionarCarpeta(ActionEvent event) {
        seleccionarCarpeta();
    }

    @FXML
    void clickEliminarRuta(ActionEvent event) {
        eliminarRuta();
    }

    @FXML
    void elegirCSV(ActionEvent event) {
        elegirCSV();
    }

    @FXML
    void importar(ActionEvent event) {
        importarEstudiantes();
    }

    @FXML
    void clickBorrarLista(ActionEvent event) {
        borrarLista();
        ocultarElementos();
    }

    private void importarEstudiantes(){
        int exitos = 0;
        int duplicados = 0;
        ArrayList<String> listaDuplicados = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            Estudiante estu = estudiantesDAO.buscarEstudiante(estudiante.getIdentificacion());
            if(estu != null){
                duplicados++;
                listaDuplicados.add(estudiante.getNombreCompleto() + " (ID: " + estudiante.getIdentificacion() + ")");
            }else{
                if(estudiantesDAO.registrarEstudiante(estudiante)){
                    exitos++;
                }
            }
        }

        StringBuilder mensaje = new StringBuilder();

        if(exitos > 0){
            mensaje.append("Se importaron correctamente ").append(exitos).append(" estudiante(s).\n\n");
        }

        if(duplicados > 0){
            mensaje.append(duplicados).append(" estudiante(s) fueron omitidos por estar duplicados:\n");
            for (int i = 0; i < Math.min(listaDuplicados.size(), 5); i++) {
                mensaje.append("  • ").append(listaDuplicados.get(i)).append("\n");
            }
            if(listaDuplicados.size() > 5){
                mensaje.append("  ... y ").append(listaDuplicados.size() - 5).append(" más.\n");
            }
        }

        if(exitos == 0 && duplicados == 0){
            Alertas.mostrarError("No se pudo importar ningún estudiante.");
        }else if(exitos > 0 && duplicados == 0){
            Alertas.mostrarExito(mensaje.toString());
        }else if(exitos == 0 && duplicados > 0){
            Alertas.mostrarWarning(mensaje.toString());
        }else{
            Alertas.mostrarInfo(mensaje.toString());
        }
    }

    private void borrarLista(){
        Alertas.mostrarInfo("La lista de estudiantes se vació correctamente.\nElige un nuevo archivo CSV si así lo deseas.");
        tabla.getItems().clear();
        estudiantes.clear();
        ocultarElementos();
    }

    private void elegirCSV(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo CSV");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos CSV", "*.csv")
        );

        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if(archivoSeleccionado != null){
            try(BufferedReader leer = new BufferedReader(new FileReader(archivoSeleccionado))){
                String linea;
                leer.readLine();
                while((linea = leer.readLine()) != null){
                    String[] datos =  linea.split(",");
                    long id = Long.parseLong(datos[0]);
                    String nombre = datos[1];
                    String apellido = datos[2];
                    int grado = Integer.parseInt(datos[3]);
                    estudiantes.add(new Estudiante(nombre,apellido,id,grado));
                }
                if(estudiantes.isEmpty()){
                    Alertas.mostrarError("No hay estudiantes validos en el archivo");
                }else{
                    if(!tabla.getItems().isEmpty()){
                        Alertas.mostrarError("La tabla contiene estudiantes actualmente.\n" +
                                "Use la opción 'Borrar lista' antes de volver a cargar otro archivo.");
                        return;
                    }
                    iniciarTabla();
                    mostrarElementos();
                    Alertas.mostrarInfo("Fueron cargados " + estudiantes.size() + " estudiantes desde el archivo CSV");
                }
            }catch(Exception e){
                Alertas.mostrarError("Error al leer archivo CSV: " + e.getMessage());
            }
        }else{
            Alertas.mostrarError("No se eligió ningún archivo CSV");
        }
    }

    private void ocultarElementos(){
        tabla.setVisible(false);
        tabla.setManaged(false);

        btnImportar.setVisible(false);
        btnImportar.setManaged(false);

        btnBorrarLista.setVisible(false);
        btnBorrarLista.setManaged(false);
    }

    private void mostrarElementos(){
        tabla.setVisible(true);
        tabla.setManaged(true);

        btnImportar.setVisible(true);
        btnImportar.setManaged(true);

        btnBorrarLista.setVisible(true);
        btnBorrarLista.setManaged(true);
    }

    private void iniciarTabla(){
        colIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colGrado.setCellValueFactory(new PropertyValueFactory<>("grado"));
        tabla.getItems().setAll(estudiantes);
    }

    private void seleccionarCarpeta(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta para guardar informe");

        // Establecer ruta inicial
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Documents"));
        Stage stage = (Stage) btnSeleccionarCarpeta.getScene().getWindow();
        File carpetaSeleccionada = directoryChooser.showDialog(stage);

        if (carpetaSeleccionada != null) {
            String ruta = carpetaSeleccionada.getAbsolutePath();
            if(configDAO.establecerRutaArchivo(ruta)){
                Alertas.mostrarInfo("Ruta de guardado establecida correctamente");
                mostrarRutaActual();
            }
        } else {
            System.out.println("No se seleccionó ninguna carpeta.");
        }
    }

    private void eliminarRuta(){
        boolean confirmar = Alertas.mostrarConfirmacion("¿Estás seguro de eliminar la ruta actual? No se podran guardar informes si no hay una ruta establecida");
        if (confirmar) {
            if(configDAO.eliminarRutaEstablecida()){
                Alertas.mostrarInfo("La ruta de guardado se eliminó correctamente.");
                mostrarRutaActual();
            }else{
                Alertas.mostrarError("Aún no se ha establecido una ruta para guardar los informes.");
            }
        }else{
            Alertas.mostrarInfo("Acción cancelada por el usuario");
        }
    }

    private void establecerHora(){
        LocalTime horaSeleccionada = spinnerHora.getValue();

        if(configDAO.establecerHora(Extras.formatoHora(horaSeleccionada))){
            Alertas.mostrarInfo("Hora establecida correctamente");
            mostrarHora();
        }
    }

    private void mostrarRutaActual(){
        infoRuta.setText((configDAO.obtenerRutaArchivo() == null ? "No se ha establecido una ruta para guardar informes" : configDAO.obtenerRutaArchivo()));
    }

    private void mostrarHora(){
        lblHoraActual.setText(configDAO.obtenerHora());
    }

    private void configurarSpinner(){
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            private final int STEP_MINUTES = 5;
            private final LocalTime MIN_TIME = LocalTime.of(6, 0);
            private final LocalTime MAX_TIME = LocalTime.of(14, 0);

            {
                // Formato de 12 horas con AM/PM
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

                setConverter(new javafx.util.StringConverter<LocalTime>() {
                    @Override
                    public String toString(LocalTime time) {
                        return (time != null) ? time.format(formatter) : "";
                    }

                    @Override
                    public LocalTime fromString(String string) {
                        return getValue(); // Mantiene el valor actual (no permite edición manual)
                    }
                });

                setValue(LocalTime.of(8, 0)); // Valor inicial
            }

            @Override
            public void increment(int steps) {
                LocalTime newTime = getValue().plusMinutes(steps * STEP_MINUTES);
                if (newTime.isAfter(MAX_TIME)) {
                    setValue(MAX_TIME); // Se detiene en el máximo (2:00 PM)
                } else {
                    setValue(newTime);
                }
            }

            @Override
            public void decrement(int steps) {
                LocalTime newTime = getValue().minusMinutes(steps * STEP_MINUTES);
                if (newTime.isBefore(MIN_TIME)) {
                    setValue(MIN_TIME); // Se detiene en el mínimo (6:00 AM)
                } else {
                    setValue(newTime);
                }
            }
        };

        spinnerHora.setValueFactory(valueFactory);
        spinnerHora.setEditable(false); // No permitir edición manual
    }
}