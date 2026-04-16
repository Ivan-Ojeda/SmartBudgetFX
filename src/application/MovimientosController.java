package application;

import entidades.Movimientos;
import servicios.movimientosFuncionalidad;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MovimientosController {

    @FXML private DatePicker dpFechaMovimiento;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtImporte;
    
    @FXML private HBox cajaFiltros; 
    @FXML private ComboBox<String> cmbFiltroCategoria;
    @FXML private ComboBox<String> cmbFiltroMes;

    @FXML private TableView<Movimientos> tablaMovimientos;
    @FXML private TableColumn<Movimientos, LocalDate> colFecha;
    @FXML private TableColumn<Movimientos, Integer> colCategoria;
    @FXML private TableColumn<Movimientos, String> colDescripcion;
    @FXML private TableColumn<Movimientos, Integer> colUsuario;
    @FXML private TableColumn<Movimientos, Double> colImporte;
    @FXML private TableColumn<Movimientos, Void> colAcciones;

    private ObservableList<Movimientos> listaMovimientos;
    private FilteredList<Movimientos> datosFiltrados;
    private movimientosFuncionalidad servicioMovimientos; 
    private int idUsuarioActivo = 2; // Por defecto empezamos como Miembro (2)

    @FXML
    public void initialize() {
        servicioMovimientos = new movimientosFuncionalidad();
        listaMovimientos = FXCollections.observableArrayList();
        
        configurarTabla();
        cmbFiltroMes.getItems().addAll("Todos", "01 - Enero", "02 - Febrero", "03 - Marzo", "04 - Abril", "05 - Mayo", "06 - Junio", "07 - Julio", "08 - Agosto", "09 - Septiembre", "10 - Octubre", "11 - Noviembre", "12 - Diciembre");
        dpFechaMovimiento.setValue(LocalDate.now());

        cmbFiltroCategoria.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        cmbFiltroMes.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        // Cargamos los datos nada más abrir la vista
        cargarDatosDesdeFichero();
        actualizarPrivilegiosUI();
        aplicarFiltros();
    }

    private void cargarDatosDesdeFichero() {
        listaMovimientos.clear();
        if(controladores.Inicio.listaMovimientos != null) controladores.Inicio.listaMovimientos.clear();
        
        List<Movimientos> datosCargados = servicioMovimientos.cargarMovimientos();
        if (datosCargados != null) {
            listaMovimientos.addAll(datosCargados);
            if(controladores.Inicio.listaMovimientos != null) controladores.Inicio.listaMovimientos.addAll(datosCargados);
        }

        datosFiltrados = new FilteredList<>(listaMovimientos, p -> true);
        tablaMovimientos.setItems(datosFiltrados);
    }

    private void configurarTabla() {
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaMovimiento"));
        
        // 🔥 SOLUCIÓN APLICADA AQUÍ: Usamos Lambda en lugar de PropertyValueFactory
        // Así no importa si la entidad tiene "getIdCategoriaFK" o "getCategoriaFK"
        colCategoria.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCategoriaFK()));
        
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuarioFK"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));

        colImporte.setCellFactory(column -> new TableCell<Movimientos, Double>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); } 
                else {
                    Movimientos mov = getTableView().getItems().get(getIndex());
                    setText(String.format("%.2f €", item));
                    if (mov.getCategoriaFK() == 5) { 
                        setStyle("-fx-text-fill: #35BE45; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                    } else {
                        setStyle("-fx-text-fill: #CE1212; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                    }
                }
            }
        });

        colUsuario.setCellFactory(column -> new TableCell<Movimientos, Integer>() {
            @Override protected void updateItem(Integer idUsuario, boolean empty) {
                super.updateItem(idUsuario, empty);
                if (empty || idUsuario == null || servicioMovimientos == null) { setGraphic(null); setText(null); } 
                else {
                    String nombreUsuario = servicioMovimientos.obtenerNombreUsuario(idUsuario);
                    if (nombreUsuario == null || nombreUsuario.isEmpty()) nombreUsuario = "?";
                    HBox box = new HBox(10); box.setAlignment(Pos.CENTER_LEFT);
                    Circle circulo = new Circle(14, Color.web("#C6A664")); 
                    Label lblInicial = new Label(nombreUsuario.substring(0, 1).toUpperCase());
                    lblInicial.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 13px;");
                    StackPane avatar = new StackPane(circulo, lblInicial);
                    Label lblNombre = new Label(nombreUsuario);
                    lblNombre.setStyle("-fx-text-fill: #4A4A4A; -fx-font-size: 14px;");
                    box.getChildren().addAll(avatar, lblNombre);
                    setGraphic(box); setText(null);   
                }
            }
        });

        colCategoria.setCellFactory(column -> new TableCell<Movimientos, Integer>() {
            @Override protected void updateItem(Integer idCat, boolean empty) {
                super.updateItem(idCat, empty);
                if (empty || idCat == null || servicioMovimientos == null) setText(null); 
                else setText(servicioMovimientos.obtenerNombreCategoria(idCat));
            }
        });

        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnBorrar = new Button("Borrar");
            {
                btnBorrar.getStyleClass().add("btn-borrar");
                btnBorrar.setOnAction(event -> confirmarYBorrar(getTableView().getItems().get(getIndex())));
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || idUsuarioActivo != 1) setGraphic(null);
                else setGraphic(btnBorrar);
            }
        });
    }

    private void actualizarPrivilegiosUI() {
        boolean esAdmin = (idUsuarioActivo == 1);
        if(cajaFiltros != null) { cajaFiltros.setVisible(esAdmin); cajaFiltros.setManaged(esAdmin); }

        ObservableList<String> categorias = FXCollections.observableArrayList("Alimentación", "Ocio", "Transporte", "Facturas");
        if (esAdmin) categorias.add("Nómina");
        cmbCategoria.setItems(categorias);
        
        ObservableList<String> categoriasFiltro = FXCollections.observableArrayList("Todas");
        categoriasFiltro.addAll(categorias); cmbFiltroCategoria.setItems(categoriasFiltro);
        
        cmbFiltroCategoria.getSelectionModel().select("Todas");
    }

    private void aplicarFiltros() {
        if (datosFiltrados == null || servicioMovimientos == null) return;
        datosFiltrados.setPredicate(mov -> servicioMovimientos.cumpleFiltros(
            mov, idUsuarioActivo, cmbFiltroCategoria.getValue(), cmbFiltroMes.getValue()
        ));
    }

    @FXML 
    void guardarMovimiento(ActionEvent event) {
        try {
            System.out.println("1. Intentando validar y crear movimiento...");
            Movimientos nuevoMov = servicioMovimientos.validarYCrearMovimiento(
                txtImporte.getText(), cmbCategoria.getValue(), dpFechaMovimiento.getValue(), 
                txtDescripcion.getText(), idUsuarioActivo
            );
            
            System.out.println("2. Movimiento creado. Añadiendo a la lista...");
            listaMovimientos.add(nuevoMov);
            if(controladores.Inicio.listaMovimientos != null) {
                controladores.Inicio.listaMovimientos.add(nuevoMov);
            }
            
            System.out.println("3. Guardando en fichero CSV...");
            servicioMovimientos.guardarMovimientos(listaMovimientos);

            System.out.println("4. ¡Éxito! Limpiando formulario...");
            txtDescripcion.clear(); txtImporte.clear(); cmbCategoria.getSelectionModel().clearSelection();

        } catch (IllegalArgumentException e) {
            // Este catch atrapa los errores controlados (ej: "Falta poner el importe")
            mostrarAlerta("Atención", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            // 🔥 SOLUCIÓN APLICADA AQUÍ: Este catch atrapa cualquier fallo inesperado de Java
            e.printStackTrace(); 
            mostrarAlerta("Error Interno", "Algo falló al guardar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML 
    void limpiarFiltros(ActionEvent event) {
        cmbFiltroCategoria.getSelectionModel().select("Todas");
        cmbFiltroMes.getSelectionModel().select("Todos");
    }

    private void confirmarYBorrar(Movimientos mov) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación"); alert.setHeaderText("Borrar registro");
        alert.setContentText("¿Deseas eliminar este movimiento de forma permanente?");
        
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            listaMovimientos.remove(mov);
            if(controladores.Inicio.listaMovimientos != null) controladores.Inicio.listaMovimientos.remove(mov);
            servicioMovimientos.guardarMovimientos(listaMovimientos); 
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo); 
        alert.setTitle("SmartBudget"); alert.setHeaderText(titulo); alert.setContentText(mensaje); 
        alert.showAndWait();
    }
}