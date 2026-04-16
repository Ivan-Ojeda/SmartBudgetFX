package application;

import entidades.Categorias;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import servicios.AhorroImplementacion;

public class AhorroController {

	@FXML
	private TextField txtNombreCategoria;

	@FXML
	private TextField txtCantidadAhorro;

	@FXML
	private TextField txtDescripcionAhorro;

	@FXML
	private ComboBox<String> comboFrecuenciaPago;
	
	

	private AhorroImplementacion servicio = new AhorroImplementacion();

	@FXML
	public void initialize() {

		comboFrecuenciaPago.getItems().addAll("Semanal", "Quincenal", "Mensual", "Anual");

	}
	
	
	private AhorroImplementacion ahorroService = new AhorroImplementacion();

	@FXML
    private void anadirObjetivo() {
        // 1. Recogida de datos
        String nombre = txtNombreCategoria.getText().trim();
        String cantidadStr = txtCantidadAhorro.getText().trim();
        String frecuencia = comboFrecuenciaPago.getValue();
        String descripcion = txtDescripcionAhorro.getText().trim();

        // 2. VALIDACIONES ESPECÍFICAS (Voz y Tono Humilde) 

        // Validación: Nombre vacío
        if (nombre.isEmpty()) {
            mostrarAlerta("Nombre del objetivo", 
                "Parece que hay un detalle por revisar: el objetivo necesita un nombre para que podamos identificarlo.");
            txtNombreCategoria.requestFocus();
            return;
        }

        // Validación: Importe vacío
        if (cantidadStr.isEmpty()) {
            mostrarAlerta("Cantidad a ahorrar", 
                "Hola, nos falta saber qué cantidad tienes pensado ahorrar para este objetivo.");
            return;
        }

        // Validación: Importe no numérico o negativo
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                mostrarAlerta("Importe no válido", 
                    "Para que el ahorro crezca, la cantidad debería ser un número mayor que cero.");
                txtCantidadAhorro.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato de número", 
                "Parece que hay un pequeño detalle: en el importe solo podemos anotar números enteros.");
            txtCantidadAhorro.requestFocus();
            return;
        }

        // Validación: Frecuencia no seleccionada
        if (frecuencia == null || frecuencia.isEmpty()) {
            mostrarAlerta("Frecuencia de aportación", 
                "Por favor, selecciona cada cuánto tiempo te gustaría realizar las aportaciones al ahorro.");
            comboFrecuenciaPago.requestFocus();
            return;
        }

        // 3. Proceso de guardado si todo está correcto
        ahorroService.crearAhorro(frecuencia, nombre, cantidad, descripcion);
        
        // Limpiamos los campos para un nuevo registro
        limpiarCampos();
    }
	
	
	private void mostrarAlerta(String encabezado, String mensaje) {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle("SmartBudgetFX - Aviso");
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);

        // Aplicamos el estilo de la marca a la ventana de diálogo
        try {
            alerta.getDialogPane().getStylesheets().add(getClass().getResource("styleAhorroVista.css").toExternalForm());
            alerta.getDialogPane().getStyleClass().add("main-container");
        } catch (Exception e) {
            // Si hay error cargando el CSS, se muestra la alerta estándar
        }

        alerta.showAndWait();
    }
	
	

	private void limpiarCampos() {

		comboFrecuenciaPago.getSelectionModel().clearSelection();

		txtNombreCategoria.clear();

		txtCantidadAhorro.clear();

		txtDescripcionAhorro.clear();

	}

	private void cerrarVentana() {

		Stage stage = (Stage)

		txtNombreCategoria.getScene().getWindow();

		stage.close();

	}

}