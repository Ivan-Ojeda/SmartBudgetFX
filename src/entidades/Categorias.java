package entidades;

import java.util.ArrayList;
import java.util.Comparator;

public class Categorias {

	private int idCategoria;
	private int idUsuarioFK;
	private String nombreCategoria;
	private boolean tipoMovimiento; // gasto/ingreso
	private int cantidadAhorro = 0; //
	private String descripcionAhorro = "";
	private String frecuenciaPago = "categoriaNoAhorro";


	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getIdUsuarioFK() {
		return idUsuarioFK;
	}

	public void setIdUsuarioFK(int idUsuarioFK) {
		this.idUsuarioFK = idUsuarioFK;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public boolean isTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(boolean tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public int getCantidadAhorro() {
		return cantidadAhorro;
	}

	public void setCantidadAhorro(int cantidadAhorro) {
		this.cantidadAhorro = cantidadAhorro;
	}

	public String getDescripcionAhorro() {
		return descripcionAhorro;
	}

	public void setDescripcionAhorro(String descripcionAhorro) {
		this.descripcionAhorro = descripcionAhorro;
	}

	public String getFrecuenciaPago() {
		return frecuenciaPago;
	}

	public void setFrecuenciaPago(String frecuenciaPago) {
		this.frecuenciaPago = frecuenciaPago;
	}

	

	/**
	 * Constructor al que le pasamos valores para crear el nuevo objeto Categorias
	 * 
	 * @param nombreCategoria
	 * @param tipoDeMovimiento
	 * @param idUsuarioFK
	 * @param cantidadAhorro
	 * @param descripcionAhorro
	 * @param frecuenciaPago
	 */
	public Categorias(String nombreCategoria, boolean tipoDeMovimiento, int idUsuarioFK, int cantidadAhorro,
			String descripcionAhorro, String frecuenciaPago) {
		super();
		this.idCategoria = crearIdCategoria();
		this.nombreCategoria = nombreCategoria;
		this.tipoMovimiento = tipoDeMovimiento;
		this.idUsuarioFK = idUsuarioFK;
		this.cantidadAhorro = cantidadAhorro;
		this.descripcionAhorro = descripcionAhorro;
		this.frecuenciaPago = frecuenciaPago;
	}

	@Override
	public String toString() {
		return "Categorias [idCategoria=" + idCategoria + ", nombreCategoria=" + nombreCategoria + "]";
	}

	/**
	 * Método que creará la id en funcion de si ya existe en la lista
	 * 
	 * @return esperado
	 */
	public int crearIdCategoria() {
		ArrayList<Categorias> lcg = controladores.Inicio.listaCategorias;

		// Si la lista está vacía --> devuelve 1
		if (lcg.isEmpty()) {
			return 1;
		}

		// Ordenar la lista por id (de menor a mayor)
		ArrayList<Categorias> copia = new ArrayList<>(lcg);
		copia.sort(Comparator.comparingInt(Categorias::getIdCategoria));

		int esperado = 1;

		for (Categorias obj : copia) {
			if (obj.getIdCategoria() != esperado) {
				// Encontramos un hueco --> devolvemos ese ID
				return esperado;
			}
			esperado++;
		}

		// Si no hay huecos --> devolvemos el siguiente al último
		return esperado;
	}

	/***
	 * Este método devolvera el formato en el que se escribira el fichero de base de
	 * datos
	 * 
	 * @return formatoFichero
	 */
	public String formatoObjetoFichero() {
		String formatoFichero = this.idCategoria + ";" + this.nombreCategoria + ";" + this.tipoMovimiento + ";"
				+ idUsuarioFK + ";" + this.cantidadAhorro + ";" + this.descripcionAhorro + ";" + this.frecuenciaPago;

		return formatoFichero;
	}

}
