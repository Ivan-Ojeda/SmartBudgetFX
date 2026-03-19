package entidades;

import java.util.ArrayList;

public class Categorias {
	
	/**
	 * 
	 * */
	
	private int idCategoria;
	private String nombreCategoria;
	private ArrayList<Movimientos> listaMovimientos;
	 
	 
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	public ArrayList<Movimientos> getListaMovimientos() {
		return listaMovimientos;
	}
	public void setListaMovimientos(ArrayList<Movimientos> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}
	
	
	@Override
	public String toString() {
		return "Categorias [idCategoria=" + idCategoria + ", nombreCategoria=" + nombreCategoria + ", listaMovimientos="
				+ listaMovimientos + "]";
	}
	public Categorias(int idCategoria, String nombreCategoria, ArrayList<Movimientos> listaMovimientos) {
		super();
		this.idCategoria = idCategoria;
		this.nombreCategoria = nombreCategoria;
		this.listaMovimientos = listaMovimientos;
	}
	
	

}
