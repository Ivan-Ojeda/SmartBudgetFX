package entidades;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;

public class Movimientos {
	
	/**
	 * 
	 * */
	
	private int idMovimiento; 
	private double importe; 
	private String categoriaFK; 
	private String tipoDeMovimiento; //(Ingreso/gasto/ahorro) 
	private Date fechaMovimiento; 
	private String descripcion; 
	private int idUsuarioFK; //esta id se cogera de la sesion que este iniciada en el momento de la creacion del movimiento
	
	
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
	public String getCategoriaFK() {
		return categoriaFK;
	}
	public void setCategoriaFK(String categoriaFK) {
		this.categoriaFK = categoriaFK;
	}
	public String getTipoDeMovimiento() {
		return tipoDeMovimiento;
	}
	public void setTipoDeMovimiento(String tipoDeMovimiento) {
		this.tipoDeMovimiento = tipoDeMovimiento;
	}
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	private int getIdUsuarioFK() {
		return idUsuarioFK;
	}
	public void setIdUsuarioFK(int idUsuarioFK) {
		this.idUsuarioFK = idUsuarioFK;
	}
	
	
	public Movimientos(double importe, String categoriaFK, String tipoDeMovimiento,
			Date fechaMovimiento, String descripcion, int idUsuarioFK) {
		super();
		this.idMovimiento = crearIdMovimiento();
		this.importe = importe;
		this.categoriaFK = categoriaFK;
		this.tipoDeMovimiento = tipoDeMovimiento;
		this.fechaMovimiento = fechaMovimiento;
		this.descripcion = descripcion;
		this.idUsuarioFK = idUsuarioFK;
	}
	
	public int crearIdMovimiento() {
	    ArrayList<Movimientos> lmg = controladores.Inicio.listaGlobalMovimientos;

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
	
	
	public String formatoObjetoFichero() {
		String formatoFichero = this.idMovimiento + ";" + this.importe
				+ ";" + this.categoriaFK + ";" + this.tipoDeMovimiento
				+ ";" + fechaMovimiento + ";" + this.descripcion
				+ ";" + this.idUsuarioFK;
		
		return formatoFichero;
	}

}
