package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Movimientos {

	/**
	 * 
	 * */

	private int idMovimiento;
	private double importe;
	private int idCategoriaFK;
	private LocalDate fechaMovimiento = LocalDate.now();
	private String descripcion;
	private int idUsuarioFK; // esta id se cogera de la sesion que este iniciada en el momento de la creacion del movimiento

	public int getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(int idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public int getCategoriaFK() {
		return idCategoriaFK;
	}

	public void setCategoriaFK(int categoriaFK) {
		this.idCategoriaFK = categoriaFK;
	}

	public LocalDate getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(LocalDate fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdUsuarioFK() {
		return idUsuarioFK;
	}

	public void setIdUsuarioFK(int idUsuarioFK) {
		this.idUsuarioFK = idUsuarioFK;
	}

	public Movimientos(double importe, int categoriaFK, LocalDate fechaMovimiento, String descripcion,
			int idUsuarioFK) {
		super();
		this.idMovimiento = crearIdMovimiento();
		this.importe = importe;
		this.idCategoriaFK = categoriaFK;
		this.fechaMovimiento = fechaMovimiento;
		this.descripcion = descripcion;
		this.idUsuarioFK = idUsuarioFK;
	}

	/**
	 * Método que creará la id en funcion de si ya existe en la lista
	 * 
	 * @return esperado
	 */
	public int crearIdMovimiento() {
		ArrayList<Movimientos> lmg = controladores.Inicio.listaMovimientos;

		// Si la lista está vacía --> devuelve 1
		if (lmg.isEmpty()) {
			return 1;
		}

		// Ordenar la lista por id (de menor a mayor)
		lmg.sort(Comparator.comparingInt(Movimientos::getIdMovimiento));

		int esperado = 1;

		for (Movimientos obj : lmg) {
			if (obj.getIdMovimiento() != esperado) {
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
		String formatoFichero = this.idMovimiento + ";" + this.importe + ";" + this.idCategoriaFK + ";"
				+ fechaMovimiento + ";" + this.descripcion + ";" + this.idUsuarioFK;

		return formatoFichero;
	}
		
	

}
