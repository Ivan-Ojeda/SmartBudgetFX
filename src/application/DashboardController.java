package application;

import entidades.Movimientos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DashboardController {
	@FXML
	private StackPane authView;
	@FXML
	private BorderPane dashboardView;
	@FXML
	private VBox formLogin;
	@FXML
	private VBox formRegister;
	@FXML
	private CheckBox checkAdmin;
	@FXML
	private Label lblTitulo;
	@FXML
	private Label displayUser;
	@FXML
	private Button btnMiembros;
	@FXML
	private ScrollPane secResumen, secMovimientos, secCategorias, secAhorro, secGraficos, secMiembros, secConfig;
	@FXML
	private DatePicker dpFechaMovimiento;
	@FXML
	private ComboBox<String> cmbCategoria;
	@FXML
	private TextField txtDescripcion;
	@FXML
	private TextField txtImporte;

	@FXML
	private HBox cajaFiltros;
	@FXML
	private ComboBox<String> cmbFiltroCategoria;
	@FXML
	private ComboBox<String> cmbFiltroMes;
	@FXML
	private TableView<Movimientos> tablaMovimientos;
	@FXML
	private TableColumn<Movimientos, LocalDate> colFecha;
	@FXML
	private TableColumn<Movimientos, Integer> colCategoria;
	@FXML
	private TableColumn<Movimientos, String> colDescripcion;
	@FXML
	private TableColumn<Movimientos, Integer> colUsuario;
	@FXML
	private TableColumn<Movimientos, Double> colImporte;
	@FXML
	private TableColumn<Movimientos, Void> colAcciones;
	private ObservableList<Movimientos> listaMovimientos;
	private FilteredList<Movimientos> datosFiltrados;

	private final String ARCHIVO_MOVIMIENTOS = "movimientos.csv";

	private int idUsuarioActivo = 2; // 1 = Admin, 2 = Miembro

	@FXML
	public void initialize() {
		authView.setVisible(true);
		dashboardView.setVisible(false);
		mostrarFormLogin();
		listaMovimientos = FXCollections.observableArrayList();

		if (controladores.Inicio.listaMovimientos != null) {
			listaMovimientos.addAll(controladores.Inicio.listaMovimientos);
		}
		configurarModuloMovimientos();
	}
	
	@FXML
	private void abrirVistaAhorro() {

	    try {

	        FXMLLoader loader = new FXMLLoader(
	                getClass().getResource("secAhorroVista.fxml")
	        );

	        Parent root = loader.load();

	        Stage stage = new Stage();

	        stage.setTitle("Nuevo objetivo de ahorro");

	        stage.setScene(new Scene(root));

	        stage.initModality(Modality.APPLICATION_MODAL);

	        stage.setResizable(false);

	        stage.centerOnScreen();

	        stage.showAndWait();

	    } catch (Exception e) {

	        e.printStackTrace();

	    }

	}	
	

	private void configurarModuloMovimientos() {
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaMovimiento"));
		colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoriaFK"));
		colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuarioFK"));
		colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
		colImporte.setCellFactory(column -> new TableCell<Movimientos, Double>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setStyle("");
				} else {
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
			@Override
			protected void updateItem(Integer idUsuario, boolean empty) {
				super.updateItem(idUsuario, empty);
				if (empty || idUsuario == null) {
					setGraphic(null);
					setText(null);
				} else {
					String nombreUsuario = obtenerNombreUsuario(idUsuario);
					HBox box = new HBox(10);
					box.setAlignment(Pos.CENTER_LEFT);
					Circle circulo = new Circle(14, Color.web("#C6A664"));
					String inicial = nombreUsuario.substring(0, 1).toUpperCase();
					Label lblInicial = new Label(inicial);
					lblInicial.setStyle(
							"-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 13px;");
					StackPane avatar = new StackPane(circulo, lblInicial);
					Label lblNombre = new Label(nombreUsuario);
					lblNombre.setStyle("-fx-text-fill: #4A4A4A; -fx-font-size: 14px;");
					box.getChildren().addAll(avatar, lblNombre);
					setGraphic(box);
					setText(null);
				}
			}
		});
		colCategoria.setCellFactory(column -> new TableCell<Movimientos, Integer>() {
			@Override
			protected void updateItem(Integer idCat, boolean empty) {
				super.updateItem(idCat, empty);
				if (empty || idCat == null)
					setText(null);
				else
					setText(obtenerNombreCategoria(idCat));
			}
		});
		colAcciones.setCellFactory(param -> new TableCell<>() {
			private final Button btnBorrar = new Button("Borrar");
			{
				btnBorrar.getStyleClass().add("btn-borrar");
				btnBorrar.setOnAction(event -> {
					Movimientos mov = getTableView().getItems().get(getIndex());
					confirmarYBorrar(mov);
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || idUsuarioActivo != 1) {
					setGraphic(null);
				} else {
					setGraphic(btnBorrar);
				}
			}
		});
		cmbFiltroMes.getItems().addAll("Todos", "01 - Enero", "02 - Febrero", "03 - Marzo", "04 - Abril", "05 - Mayo",
				"06 - Junio", "07 - Julio", "08 - Agosto", "09 - Septiembre", "10 - Octubre", "11 - Noviembre",
				"12 - Diciembre");
		dpFechaMovimiento.setValue(LocalDate.now());
		datosFiltrados = new FilteredList<>(listaMovimientos, p -> true);
		tablaMovimientos.setItems(datosFiltrados);

		cmbFiltroCategoria.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
		cmbFiltroMes.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
	}

	private void actualizarPrivilegiosUI() {
		boolean esAdmin = (idUsuarioActivo == 1);
		cajaFiltros.setVisible(esAdmin);
		cajaFiltros.setManaged(esAdmin);
		ObservableList<String> categorias = FXCollections.observableArrayList("Alimentación", "Ocio", "Transporte",
				"Facturas");
		if (esAdmin)
			categorias.add("Nómina");
		cmbCategoria.setItems(categorias);

		ObservableList<String> categoriasFiltro = FXCollections.observableArrayList("Todas");
		categoriasFiltro.addAll(categorias);
		cmbFiltroCategoria.setItems(categoriasFiltro);

		cmbFiltroCategoria.getSelectionModel().select("Todas");
		tablaMovimientos.refresh();
	}

	private void aplicarFiltros() {
		datosFiltrados.setPredicate(mov -> {
			boolean esAdmin = (idUsuarioActivo == 1);
			if (mov.getCategoriaFK() == 5 && !esAdmin)
				return false;

			String catSeleccionada = cmbFiltroCategoria.getValue();
			if (catSeleccionada != null && !catSeleccionada.equals("Todas")) {
				if (mov.getCategoriaFK() != obtenerIdCategoria(catSeleccionada))
					return false;
			}

			String mesSeleccionado = cmbFiltroMes.getValue();
			if (mesSeleccionado != null && !mesSeleccionado.equals("Todos")) {
				int mesFiltro = Integer.parseInt(mesSeleccionado.substring(0, 2));
				if (mov.getFechaMovimiento().getMonthValue() != mesFiltro)
					return false;
			}
			return true;
		});
	}

	@FXML
	void limpiarFiltros(ActionEvent event) {
		cmbFiltroCategoria.getSelectionModel().select("Todas");
		cmbFiltroMes.getSelectionModel().select("Todos");
	}

	@FXML
	void guardarMovimiento(ActionEvent event) {
		try {
			LocalDate fecha = dpFechaMovimiento.getValue();
			String categoriaStr = cmbCategoria.getValue();
			String descripcion = txtDescripcion.getText();

			if (txtImporte.getText().isEmpty())
				throw new NumberFormatException();
			double importe = Double.parseDouble(txtImporte.getText().replace(",", "."));
			if (fecha == null || categoriaStr == null || descripcion.isEmpty()) {
				mostrarAlerta("Faltan datos", "Por favor, completa todos los campos.", Alert.AlertType.WARNING);
				return;
			}
			int idCategoria = obtenerIdCategoria(categoriaStr);
			Movimientos nuevoMov = new Movimientos(importe, idCategoria, fecha, descripcion, idUsuarioActivo);

			listaMovimientos.add(nuevoMov);
			if (controladores.Inicio.listaMovimientos != null)
				controladores.Inicio.listaMovimientos.add(nuevoMov);
			guardarMovimientosEnFichero();
			txtDescripcion.clear();
			txtImporte.clear();
			cmbCategoria.getSelectionModel().clearSelection();
		} catch (NumberFormatException e) {
			mostrarAlerta("Importe incorrecto", "Revisa el importe introducido.", Alert.AlertType.ERROR);
		}
	}

	private void confirmarYBorrar(Movimientos mov) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmación");
		alert.setHeaderText("Borrar registro");
		alert.setContentText("¿Eliminar movimiento?");

		Optional<ButtonType> resultado = alert.showAndWait();
		if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
			listaMovimientos.remove(mov);
			if (controladores.Inicio.listaMovimientos != null)
				controladores.Inicio.listaMovimientos.remove(mov);
			guardarMovimientosEnFichero();
		}
	}

	private void guardarMovimientosEnFichero() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_MOVIMIENTOS))) {
			for (Movimientos m : listaMovimientos) {
				bw.write(m.formatoObjetoFichero());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarMovimientosDesdeFichero() {
		listaMovimientos.clear();
		if (controladores.Inicio.listaMovimientos != null)
			controladores.Inicio.listaMovimientos.clear();

		File archivo = new File(ARCHIVO_MOVIMIENTOS);
		if (!archivo.exists())
			return;
		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.trim().isEmpty())
					continue;
				String[] datos = linea.split(";");
				if (datos.length >= 6) {
					Movimientos m = new Movimientos(Double.parseDouble(datos[1]), Integer.parseInt(datos[2]),
							LocalDate.parse(datos[3]), datos[4], Integer.parseInt(datos[5]));
					m.setIdMovimiento(Integer.parseInt(datos[0]));
					listaMovimientos.add(m);
					if (controladores.Inicio.listaMovimientos != null)
						controladores.Inicio.listaMovimientos.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int obtenerIdCategoria(String nombre) {
		switch (nombre) {
		case "Alimentación":
			return 1;
		case "Ocio":
			return 2;
		case "Transporte":
			return 3;
		case "Facturas":
			return 4;
		case "Nómina":
			return 5;
		default:
			return 6;
		}
	}

	private String obtenerNombreCategoria(int id) {
		switch (id) {
		case 1:
			return "Alimentación";
		case 2:
			return "Ocio";
		case 3:
			return "Transporte";
		case 4:
			return "Facturas";
		case 5:
			return "Nómina";
		default:
			return "Otros";
		}
	}

	private String obtenerNombreUsuario(int id) {
		return (id == 1) ? "Papá/Mamá" : "Hijo/a";
	}

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle("SmartBudget");
		alert.setHeaderText(titulo);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	@FXML
	void mostrarFormLogin() {
		formLogin.setVisible(true);
		formLogin.setManaged(true);
		formRegister.setVisible(false);
		formRegister.setManaged(false);
	}

	@FXML
	void mostrarFormRegistro() {
		formLogin.setVisible(false);
		formLogin.setManaged(false);
		formRegister.setVisible(true);
		formRegister.setManaged(true);
	}

	@FXML
	void entrarDashboard(ActionEvent event) {
		idUsuarioActivo = checkAdmin.isSelected() ? 1 : 2;
		displayUser.setText(obtenerNombreUsuario(idUsuarioActivo));
		btnMiembros.setVisible(checkAdmin.isSelected());
		btnMiembros.setManaged(checkAdmin.isSelected());
		authView.setVisible(false);
		dashboardView.setVisible(true);
		mostrarSeccion(secResumen, "Resumen General");
		cargarMovimientosDesdeFichero();
		actualizarPrivilegiosUI();
		aplicarFiltros();
	}

	@FXML
	void mostrarInicio(ActionEvent e) {
		mostrarSeccion(secResumen, "Resumen General");
	}

	@FXML
	void mostrarMovimientos(ActionEvent e) {
		mostrarSeccion(secMovimientos, "Gestión de Movimientos");
	}

	@FXML
	void mostrarCategorias(ActionEvent e) {
		mostrarSeccion(secCategorias, "Gestión de Categorías");
	}

	@FXML
	void mostrarAhorro(ActionEvent e) {
		mostrarSeccion(secAhorro, "Cuenta de Ahorro");
	}

	@FXML
	void mostrarGraficos(ActionEvent e) {
		mostrarSeccion(secGraficos, "Visualización Gráfica");
	}

	@FXML
	void mostrarMiembros(ActionEvent e) {
		mostrarSeccion(secMiembros, "Miembros de la Casa");
	}

	@FXML
	void mostrarConfig(ActionEvent e) {
		mostrarSeccion(secConfig, "Configuración");
	}

	private void mostrarSeccion(ScrollPane seccion, String titulo) {
		secResumen.setVisible(false);
		secMovimientos.setVisible(false);
		secCategorias.setVisible(false);
		secAhorro.setVisible(false);
		secGraficos.setVisible(false);
		secMiembros.setVisible(false);
		secConfig.setVisible(false);
		seccion.setVisible(true);
		if (lblTitulo != null)
			lblTitulo.setText(titulo);
	}
}