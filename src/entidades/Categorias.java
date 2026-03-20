package entidades;

public class Categorias {
	
	private int idCategoria;
	private String nombreCategoria;
	private String tipoDeMovimientoParaLaCategoria;
	 
	
	public String getTipoDeMovimientoParaLaCategoria() {
		return tipoDeMovimientoParaLaCategoria;
	}
	public void setTipoDeMovimientoParaLaCategoria(String tipoDeMovimientoParaLaCategoria) {
		this.tipoDeMovimientoParaLaCategoria = tipoDeMovimientoParaLaCategoria;
	}
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

	@Override
	public String toString() {
		return "Categorias [idCategoria=" + idCategoria + ", nombreCategoria=" + nombreCategoria
				+ ", tipoDeMovimientoParaLaCategoria=" + tipoDeMovimientoParaLaCategoria + "]";
	}

	public Categorias(int idCategoria, String nombreCategoria, String tipoDeMovimientoParaLaCategoria) {
		super();
		this.idCategoria = idCategoria;
		this.nombreCategoria = nombreCategoria;
		this.tipoDeMovimientoParaLaCategoria = tipoDeMovimientoParaLaCategoria;
	}
}
