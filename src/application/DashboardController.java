package application;

import entidades.Movimientos;
import servicios.movimientosFuncionalidad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    // ========================================================================
    // 🖥️ ELEMENTOS DE NAVEGACIÓN Y DASHBOARD
    // ========================================================================
    @FXML private StackPane authView;
    @FXML private BorderPane dashboardView;
    @FXML private VBox formLogin, formRegister;
    @FXML private CheckBox checkAdmin;
    @FXML private Label displayUser, lblTitulo;
    @FXML private Button btnMiembros; 
    
    @FXML private Label tabLoginLabel;
    @FXML private Label tabRegisterLabel;

    // Los ScrollPanes de cada sección según tu FXML
    @FXML private ScrollPane secResumen;
    @FXML private ScrollPane secMovimientos;
    @FXML private ScrollPane secCategorias;
    @FXML private ScrollPane secAhorro;
    @FXML private ScrollPane secGraficos;
    @FXML private ScrollPane secMiembros;
    @FXML private ScrollPane secConfig;

    // ========================================================================
    // 💸 ELEMENTOS DEL MÓDULO DE MOVIMIENTOS (Integrados)
    // ========================================================================
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

    // ========================================================================
    // 🧠 LÓGICA Y DATOS
    // ========================================================================
    private int idUsuarioActivo = 2; // 1 = Admin, 2 = Miembro
    private ObservableList<Movimientos> listaMovimientos;
    private FilteredList<Movimientos> datosFiltrados;
    private movimientosFuncionalidad servicioMovimientos; 

    // ========================================================================
    // 🚀 INICIALIZACIÓN
    // ========================================================================
    @FXML
    public void initialize() {
        // Inicialización de la vista de autenticación
        authView.setVisible(true);
        dashboardView.setVisible(false);
        mostrarFormLogin();

        // Inicialización de la lógica de movimientos
        servicioMovimientos = new movimientosFuncionalidad();
        listaMovimientos = FXCollections.observableArrayList();
        
        // Configuramos la tabla y combos
        if (tablaMovimientos != null) {
            configurarTabla();
            
            if (cmbFiltroMes != null) {
                cmbFiltroMes.getItems().addAll("Todos", "01 - Enero", "02 - Febrero", "03 - Marzo", "04 - Abril", "05 - Mayo", "06 - Junio", "07 - Julio", "08 - Agosto", "09 - Septiembre", "10 - Octubre", "11 - Noviembre", "12 - Diciembre");
            }
            
            if (dpFechaMovimiento != null) {
                dpFechaMovimiento.setValue(LocalDate.now());
            }

            if (cmbFiltroCategoria != null) {
                cmbFiltroCategoria.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
            }
            if (cmbFiltroMes != null) {
                cmbFiltroMes.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
            }

            cargarDatosDesdeFichero();
        }
    }

    // ========================================================================
    // 🔑 MÉTODOS DE LOGIN Y NAVEGACIÓN
    // ========================================================================
    @FXML
    void entrarDashboard(ActionEvent event) {
        idUsuarioActivo = checkAdmin.isSelected() ? 1 : 2; 
        
        displayUser.setText(idUsuarioActivo == 1 ? "Modo: Administrador" : "Modo: Miembro");
        btnMiembros.setVisible(idUsuarioActivo == 1);
        btnMiembros.setManaged(idUsuarioActivo == 1);

        authView.setVisible(false);
        dashboardView.setVisible(true);
        
        // Al entrar, refrescamos la UI de movimientos con el nuevo usuario activo
        actualizarPrivilegiosUI();
        aplicarFiltros();
        if (tablaMovimientos != null) tablaMovimientos.refresh();
        
        mostrarInicio(null);
    }

    @FXML void mostrarInicio(ActionEvent e) { mostrarSeccion(secResumen, "Resumen General"); }
    @FXML void mostrarMovimientos(ActionEvent e) { mostrarSeccion(secMovimientos, "Gestión de Movimientos"); }
    @FXML void mostrarAhorro(ActionEvent e) { mostrarSeccion(secAhorro, "Cuenta de Ahorro"); }
    @FXML void mostrarCategorias(ActionEvent e) { mostrarSeccion(secCategorias, "Gestión de Categorías"); }
    @FXML void mostrarGraficos(ActionEvent e) { mostrarSeccion(secGraficos, "Visualización Gráfica"); }
    @FXML void mostrarMiembros(ActionEvent e) { mostrarSeccion(secMiembros, "Miembros Casa"); }
    @FXML void mostrarConfig(ActionEvent e) { mostrarSeccion(secConfig, "Configuración"); }

    private void mostrarSeccion(ScrollPane seccion, String titulo) {
        ScrollPane[] modulos = {secResumen, secMovimientos, secAhorro, secCategorias, secGraficos, secMiembros, secConfig};
        for (ScrollPane m : modulos) {
            if (m != null) m.setVisible(false);
        }
        
        if (seccion != null) {
            seccion.setVisible(true);
            lblTitulo.setText(titulo);
        }
    }

    @FXML void mostrarFormLogin() { 
        formLogin.setVisible(true); formLogin.setManaged(true); 
        formRegister.setVisible(false); formRegister.setManaged(false); 
        
        // Efecto visual básico para las pestañas
        if(tabLoginLabel != null) tabLoginLabel.setStyle("-fx-text-fill: #4A4A4A; -fx-font-weight: bold; -fx-padding: 10; -fx-border-color: #C6A664; -fx-border-width: 0 0 2 0; -fx-cursor: hand;");
        if(tabRegisterLabel != null) tabRegisterLabel.setStyle("-fx-text-fill: #888; -fx-font-weight: bold; -fx-padding: 10; -fx-cursor: hand;");
    }
    
    @FXML void mostrarFormRegistro() { 
        formLogin.setVisible(false); formLogin.setManaged(false); 
        formRegister.setVisible(true); formRegister.setManaged(true); 
        
        // Efecto visual básico para las pestañas
        if(tabRegisterLabel != null) tabRegisterLabel.setStyle("-fx-text-fill: #4A4A4A; -fx-font-weight: bold; -fx-padding: 10; -fx-border-color: #C6A664; -fx-border-width: 0 0 2 0; -fx-cursor: hand;");
        if(tabLoginLabel != null) tabLoginLabel.setStyle("-fx-text-fill: #888; -fx-font-weight: bold; -fx-padding: 10; -fx-cursor: hand;");
    }

    // ========================================================================
    // 📊 LÓGICA INTERNA DE MOVIMIENTOS
    // ======================================================== ================
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
        colCategoria.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCategoriaFK()));
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
        if (cmbCategoria != null) cmbCategoria.setItems(categorias);
        
        ObservableList<String> categoriasFiltro = FXCollections.observableArrayList("Todas");
        categoriasFiltro.addAll(categorias); 
        if (cmbFiltroCategoria != null) {
            cmbFiltroCategoria.setItems(categoriasFiltro);
            cmbFiltroCategoria.getSelectionModel().select("Todas");
        }
    }

    private void aplicarFiltros() {
        if (datosFiltrados == null || servicioMovimientos == null) return;
        String cat = (cmbFiltroCategoria != null) ? cmbFiltroCategoria.getValue() : "Todas";
        String mes = (cmbFiltroMes != null) ? cmbFiltroMes.getValue() : "Todos";
        
        datosFiltrados.setPredicate(mov -> servicioMovimientos.cumpleFiltros(mov, idUsuarioActivo, cat, mes));
    }

    @FXML 
    void guardarMovimiento(ActionEvent event) {
        try {
            Movimientos nuevoMov = servicioMovimientos.validarYCrearMovimiento(
                txtImporte.getText(), cmbCategoria.getValue(), dpFechaMovimiento.getValue(), 
                txtDescripcion.getText(), idUsuarioActivo
            );
            
            listaMovimientos.add(nuevoMov);
            if(controladores.Inicio.listaMovimientos != null) controladores.Inicio.listaMovimientos.add(nuevoMov);
            
            servicioMovimientos.guardarMovimientos(listaMovimientos);

            txtDescripcion.clear(); txtImporte.clear(); cmbCategoria.getSelectionModel().clearSelection();

        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @FXML 
    void limpiarFiltros(ActionEvent event) {
        if (cmbFiltroCategoria != null) cmbFiltroCategoria.getSelectionModel().select("Todas");
        if (cmbFiltroMes != null) cmbFiltroMes.getSelectionModel().select("Todos");
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