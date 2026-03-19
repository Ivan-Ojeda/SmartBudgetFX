package entidades;

import java.util.ArrayList;
import java.util.Comparator;

public class Usuarios {
	
	/**
	 * 
	 * */
	
	private int idUsuario; 
	private String nombreUsuario; 
	private String contrasenya;
	
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContrasenya() {
		return contrasenya;
	}
	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}
	
	
	public Usuarios( String nombreUsuario, String contrasenya) {
		super();
		this.idUsuario = crearIdCategoria();
		this.nombreUsuario = nombreUsuario;
		this.contrasenya = contrasenya;
	}
	
	public int crearIdCategoria() {
	    ArrayList<Usuarios> lug = controladores.Inicio.listaGlobalUsuarios;

	    // Si la lista está vacía --> devuelve 1
	    if (lug.isEmpty()) {
	        return 1;
	    }

	    // Ordenar la lista por id (de menor a mayor)
	    lug.sort(Comparator.comparingInt(Usuarios::getIdUsuario));

	    int esperado = 1;

	    for (Usuarios obj : lug) {
	        if (obj.getIdUsuario() != esperado) {
	            // Encontramos un hueco --> devolvemos ese ID
	            return esperado;
	        }
	        esperado++;
	    }

	    // Si no hay huecos --> devolvemos el siguiente al último
	    return esperado;
	}
	
	public String formatoObjetoFichero() {
		String formatoFichero = this.idUsuario + ";" + this.nombreUsuario + ";" + this.contrasenya;
		
		return formatoFichero;
	}

}
