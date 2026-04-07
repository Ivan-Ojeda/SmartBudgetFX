package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardController {

    // Vistas principales
    @FXML private StackPane authView;
    @FXML private BorderPane dashboardView;

    // Elementos del Login/Registro
    @FXML private VBox formLogin;
    @FXML private VBox formRegister;
    @FXML private CheckBox checkAdmin;
    @FXML private Label tabLoginLabel;
    @FXML private Label tabRegisterLabel;

    // Elementos del Dashboard
    @FXML private Label lblTitulo;
    @FXML private Label displayUser;
    
    // Botones del menú lateral
    @FXML private Button btnMiembros;
    
    // Secciones (Las 7 pantallas)
    @FXML private ScrollPane secResumen, secMovimientos, secCategorias, secAhorro, secGraficos, secMiembros, secConfig;

    @FXML
    public void initialize() {
        // Al arrancar, mostramos Login y ocultamos todo lo demás
        authView.setVisible(true);
        dashboardView.setVisible(false);
        mostrarFormLogin(); // Por defecto pestaña Login activa
    }

    // --- LÓGICA DE AUTENTICACIÓN (TABS) ---
    @FXML
    void mostrarFormLogin() {
        formLogin.setVisible(true);
        formLogin.setManaged(true);
        formRegister.setVisible(false);
        formRegister.setManaged(false);
        tabLoginLabel.setStyle("-fx-text-fill: #4A4A4A; -fx-border-color: #C6A664; -fx-border-width: 0 0 2 0; -fx-font-weight: bold;");
        tabRegisterLabel.setStyle("-fx-text-fill: #888888; -fx-border-color: transparent; -fx-border-width: 0 0 2 0; -fx-font-weight: bold;");
    }

    @FXML
    void mostrarFormRegistro() {
        formLogin.setVisible(false);
        formLogin.setManaged(false);
        formRegister.setVisible(true);
        formRegister.setManaged(true);
        tabRegisterLabel.setStyle("-fx-text-fill: #4A4A4A; -fx-border-color: #C6A664; -fx-border-width: 0 0 2 0; -fx-font-weight: bold;");
        tabLoginLabel.setStyle("-fx-text-fill: #888888; -fx-border-color: transparent; -fx-border-width: 0 0 2 0; -fx-font-weight: bold;");
    }

    @FXML
    void entrarDashboard(ActionEvent event) {
        // Si es admin, mostramos el botón de Miembros de la Casa
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

    // --- LÓGICA DE NAVEGACIÓN ---
    @FXML void mostrarInicio(ActionEvent e) { mostrarSeccion(secResumen, "Resumen General"); }
    @FXML void mostrarMovimientos(ActionEvent e) { mostrarSeccion(secMovimientos, "Gestión de Movimientos"); }
    @FXML void mostrarCategorias(ActionEvent e) { mostrarSeccion(secCategorias, "Gestión de Categorías"); }
    @FXML void mostrarAhorro(ActionEvent e) { mostrarSeccion(secAhorro, "Cuenta de Ahorro"); }
    @FXML void mostrarGraficos(ActionEvent e) { mostrarSeccion(secGraficos, "Visualización Gráfica"); }
    @FXML void mostrarMiembros(ActionEvent e) { mostrarSeccion(secMiembros, "Miembros de la Casa"); }
    @FXML void mostrarConfig(ActionEvent e) { mostrarSeccion(secConfig, "Configuración"); }

    private void mostrarSeccion(ScrollPane seccion, String titulo) {
        // Ocultar todas
        secResumen.setVisible(false);
        secMovimientos.setVisible(false);
        secCategorias.setVisible(false);
        secAhorro.setVisible(false);
        secGraficos.setVisible(false);
        secMiembros.setVisible(false);
        secConfig.setVisible(false);
        
        // Mostrar la seleccionada
        seccion.setVisible(true);
        if(lblTitulo != null) lblTitulo.setText(titulo);
    }
}