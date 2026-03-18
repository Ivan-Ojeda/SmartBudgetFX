package entidades;

import java.sql.Date;

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
	
	
	public Movimientos(int idMovimiento, double importe, String categoriaFK, String tipoDeMovimiento,
			Date fechaMovimiento, String descripcion, int idUsuarioFK) {
		super();
		this.idMovimiento = idMovimiento;
		this.importe = importe;
		this.categoriaFK = categoriaFK;
		this.tipoDeMovimiento = tipoDeMovimiento;
		this.fechaMovimiento = fechaMovimiento;
		this.descripcion = descripcion;
		this.idUsuarioFK = idUsuarioFK;
	}
	
	

}
