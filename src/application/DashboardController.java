package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import entidades.Categorias;
import controladores.Inicio;

public class DashboardController {

   // Vistas principales
   @FXML private StackPane authView;
   @FXML private BorderPane dashboardView;

   // Login/Register
   @FXML private VBox formLogin;
   @FXML private VBox formRegister;
   @FXML private CheckBox checkAdmin;
   @FXML private Label tabLoginLabel;
   @FXML private Label tabRegisterLabel;

   // Dashboard
   @FXML private Label lblTitulo;
   @FXML private Label displayUser;

   // Sidebar
   @FXML private Button btnMiembros;

   // Secciones
   @FXML private ScrollPane secResumen, secMovimientos, secCategorias, secAhorro, secGraficos, secMiembros, secConfig;

   // 🔴 CAMBIO: tabla categorías UI (la añadimos al FXML o la conectamos)
   @FXML private TableView<Categorias> tablaCategorias;
   @FXML private TableColumn<Categorias, String> colNombre;
   @FXML private TableColumn<Categorias, String> colTipo;
   @FXML private TableColumn<Categorias, Integer> colPresupuesto;
   @FXML private TableColumn<Categorias, Void> colAcciones;

   // 🔴 CAMBIO: lista observable (IMPORTANTE para que la tabla se actualice)
   private ObservableList<Categorias> listaCategoriasFX = FXCollections.observableArrayList();

   @FXML
   public void initialize() {

       authView.setVisible(true);
       dashboardView.setVisible(false);

       mostrarFormLogin();

       // 🔴 CAMBIO: inicializar tabla categorías
       if (tablaCategorias != null) {
           tablaCategorias.setItems(listaCategoriasFX);

           colNombre.setCellValueFactory(data ->
                   new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreCategoria()));

           colTipo.setCellValueFactory(data ->
                   new javafx.beans.property.SimpleStringProperty(
                           data.getValue().isTipoMovimiento() ? "Ingreso" : "Gasto"
                   ));

           colPresupuesto.setCellValueFactory(data ->
                   new javafx.beans.property.SimpleIntegerProperty(
                           data.getValue().getCantidadAhorro()
                   ).asObject());

           // 🔴 CAMBIO: botón eliminar dentro de la tabla
           colAcciones.setCellFactory(col -> new TableCell<>() {
               private final Button btn = new Button("Eliminar");

               {
                   btn.setStyle(
                       "-fx-background-color: #4A4A4A;" +
                       "-fx-text-fill: white;" +
                       "-fx-background-radius: 8px;" +
                       "-fx-cursor: hand;"
                   );

                   btn.setOnAction(e -> {
                       Categorias cat = getTableView().getItems().get(getIndex());
                       eliminarCategoria(cat);
                   });
               }

               @Override
               protected void updateItem(Void item, boolean empty) {
                   super.updateItem(item, empty);
                   setGraphic(empty ? null : btn);
               }
           });

           cargarCategorias();
       }
   }

   // =========================
   // LOGIN / REGISTRO
   // =========================

   @FXML
   void mostrarFormLogin() {
       formLogin.setVisible(true);
       formRegister.setVisible(false);
   }

   @FXML
   void mostrarFormRegistro() {
       formLogin.setVisible(false);
       formRegister.setVisible(true);
   }

   @FXML
   void entrarDashboard(ActionEvent event) {

       if (checkAdmin.isSelected()) {
           btnMiembros.setVisible(true);
           btnMiembros.setManaged(true);
       } else {
           btnMiembros.setVisible(false);
           btnMiembros.setManaged(false);
       }

       authView.setVisible(false);
       dashboardView.setVisible(true);

       mostrarSeccion(secResumen, "Resumen General");
   }

   // =========================
   // NAVEGACIÓN
   // =========================

   @FXML void mostrarInicio(ActionEvent e) { mostrarSeccion(secResumen, "Resumen General"); }
   @FXML void mostrarMovimientos(ActionEvent e) { mostrarSeccion(secMovimientos, "Movimientos"); }

   // 🔴 CAMBIO: ahora categorías recarga tabla
   @FXML
   void mostrarCategorias(ActionEvent e) {
       mostrarSeccion(secCategorias, "Categorías");
       cargarCategorias();
   }

   @FXML void mostrarAhorro(ActionEvent e) { mostrarSeccion(secAhorro, "Ahorro"); }
   @FXML void mostrarGraficos(ActionEvent e) { mostrarSeccion(secGraficos, "Gráficos"); }
   @FXML void mostrarMiembros(ActionEvent e) { mostrarSeccion(secMiembros, "Miembros"); }
   @FXML void mostrarConfig(ActionEvent e) { mostrarSeccion(secConfig, "Configuración"); }

   private void mostrarSeccion(ScrollPane seccion, String titulo) {

       secResumen.setVisible(false);
       secMovimientos.setVisible(false);
       secCategorias.setVisible(false);
       secAhorro.setVisible(false);
       secGraficos.setVisible(false);
       secMiembros.setVisible(false);
       secConfig.setVisible(false);

       seccion.setVisible(true);

       if (lblTitulo != null) {
           lblTitulo.setText(titulo);
       }
   }

   // =========================
   // CATEGORÍAS
   // =========================

   // 🔴 CAMBIO: abrir ventana para crear categoría
   @FXML
   void crearCategoriaUI(ActionEvent event) {

       Dialog<ButtonType> dialog = new Dialog<>();
       dialog.setTitle("Nueva Categoría");

       // 🔴 CAMBIO: estilo de ventana (colores de tu app)
       dialog.getDialogPane().setStyle(
               "-fx-background-color: #EDE8D0;" +
               "-fx-font-family: 'Roboto';"
       );

       VBox contenedor = new VBox(10);

       contenedor.setStyle(
               "-fx-background-color: #EDE8D0;"
       );

       TextField txtNombre = new TextField();
       txtNombre.setPromptText("Nombre de categoría");

       ComboBox<String> cmbTipo = new ComboBox<>();
       cmbTipo.getItems().addAll("Ingreso", "Gasto");
       cmbTipo.setPromptText("Tipo");

       TextField txtPresupuesto = new TextField();
       txtPresupuesto.setPromptText("Presupuesto (€)");

       // 🔴 CAMBIO: inputs con estilo igual que tu app
       String estiloInputs =
               "-fx-background-color: #ffffff;" +
               "-fx-border-color: #C6A664;" +
               "-fx-border-radius: 10px;" +
               "-fx-background-radius: 10px;";

       txtNombre.setStyle(estiloInputs);
       cmbTipo.setStyle(estiloInputs);
       txtPresupuesto.setStyle(estiloInputs);

       contenedor.getChildren().addAll(
               new Label("Nombre"), txtNombre,
               new Label("Tipo"), cmbTipo,
               new Label("Presupuesto"), txtPresupuesto
       );

       dialog.getDialogPane().setContent(contenedor);

       dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // 🔴 CAMBIO: botón OK con el mismo estilo que "Nueva Categoría"
       Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
       okButton.setText("Guardar");
       okButton.setStyle(
               "-fx-background-color: #4A4A4A;" +
               "-fx-text-fill: #F9F8F3;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 10px;" +
               "-fx-padding: 10 20 10 20;"
       );

       // 🔴 CAMBIO: botón cancelar coherente con UI
       Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
       cancelButton.setStyle(
               "-fx-background-color: #C6A664;" +
               "-fx-text-fill: white;" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 10px;" +
               "-fx-padding: 10 20 10 20;"
       );
       
       // 🔴 CAMBIO: botones con estilo del proyecto
       dialog.getDialogPane().lookupButton(ButtonType.OK)
               .setStyle("-fx-background-color:#4A4A4A; -fx-text-fill:white;");

       dialog.getDialogPane().lookupButton(ButtonType.CANCEL)
               .setStyle("-fx-background-color:#C6A664; -fx-text-fill:white;");

       dialog.showAndWait().ifPresent(response -> {
           if (response == ButtonType.OK) {

               boolean tipo = cmbTipo.getValue().equals("Ingreso");

               Categorias c = new Categorias(
                       txtNombre.getText(),
                       tipo,
                       Inicio.idUsuarioActual,
                       Integer.parseInt(txtPresupuesto.getText()),
                       "",
                       ""
               );

               Inicio.listaCategorias.add(c);
               listaCategoriasFX.add(c);
           }
       });
   }

   // 🔴 CAMBIO: cargar datos en tabla
   private void cargarCategorias() {
       listaCategoriasFX.clear();
       listaCategoriasFX.addAll(Inicio.listaCategorias);
   }

   // 🔴 CAMBIO: eliminar categoría
   private void eliminarCategoria(Categorias c) {
       Inicio.listaCategorias.remove(c);
       listaCategoriasFX.remove(c);
   }
}