package entidades;

import java.util.ArrayList;
import java.util.Comparator;

public class Usuarios {

    private int idUsuario;
    private String nombreUsuario;
    private String contrasenya;
    private String rol;

    public Usuarios(String nombreUsuario, String contrasenya, String rol) {
        super();
        this.idUsuario = crearIdUsuario();
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
        this.rol = rol;
    }

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

    
    public String getRol() {
        return rol;
    }

 
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Método que creará la id en funcion de si ya existe en la lista
     * * @return esperado
     */
    public int crearIdUsuario() {
        // Nota: Asegúrate de tener tu clase controladores.Inicio creada con listaUsuarios
        // ArrayList<Usuarios> lug = controladores.Inicio.listaUsuarios;
        
        // Simulación temporal para evitar errores de compilación si no tienes Inicio.java:
        ArrayList<Usuarios> lug = new ArrayList<>(); 

        if (lug.isEmpty()) {
            return 1;
        }

        lug.sort(Comparator.comparingInt(Usuarios::getIdUsuario));

        int esperado = 1;
        for (Usuarios obj : lug) {
            if (obj.getIdUsuario() != esperado) {
                return esperado;
            }
            esperado++;
        }
        return esperado;
    }

    public String formatoObjetoFichero() {
        return this.idUsuario + ";" + this.nombreUsuario + ";" + this.contrasenya + ";" + this.rol;
    }
}