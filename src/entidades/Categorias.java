package entidades;

import java.util.ArrayList;
import java.util.Comparator;

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
	
	
	public Categorias(int idCategoria, String nombreCategoria, ArrayList<Movimientos> listaMovimientos) {
		super();
		this.idCategoria = idCategoria;
		this.nombreCategoria = nombreCategoria;
		this.listaMovimientos = listaMovimientos;
	}
	
	
	public int crearIdCategoria() {
	    ArrayList<Categorias> lcg = controladores.Inicio.listaGlobalCategorias;

	    // Si la lista está vacía --> devuelve 1
	    if (lcg.isEmpty()) {
	        return 1;
	    }

	    // Ordenar la lista por id (de menor a mayor)
	    lcg.sort(Comparator.comparingInt(Categorias::getIdCategoria));

	    int esperado = 1;

	    for (Categorias obj : lcg) {
	        if (obj.getIdCategoria() != esperado) {
	            // Encontramos un hueco --> devolvemos ese ID
	            return esperado;
	        }
	        esperado++;
	    }

	    // Si no hay huecos --> devolvemos el siguiente al último
	    return esperado;
	}


}
