package application;

import javafx.fxml.FXML;
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

	@FXML
	private void anadirObjetivo() {

		try {

			String nombreCategoria = txtNombreCategoria.getText();
			String descripcionAhorro = txtDescripcionAhorro.getText();
			String frecuenciaPago = comboFrecuenciaPago.getValue();

			int cantidadAhorro = Integer.parseInt(txtCantidadAhorro.getText());

			servicio.crearAhorro(frecuenciaPago, nombreCategoria, cantidadAhorro, descripcionAhorro);

			limpiarCampos();
			cerrarVentana(); // no todos los cambios realizados, solo cerrarVentana

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

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