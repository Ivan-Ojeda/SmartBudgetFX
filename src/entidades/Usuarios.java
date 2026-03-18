package entidades;

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
	
	
	public Usuarios(int idUsuario, String nombreUsuario, String contrasenya) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contrasenya = contrasenya;
	}
	
	

}
