 package controladores;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.Categorias;
import entidades.Movimientos;
import entidades.Usuarios;
import servicios.MenuPrincipal;
import servicios.usuariosOpciones;


public class Inicio {
	public static Scanner sc = new Scanner(System.in);
    public static int idUsuarioActual = -1;
    public static Usuarios usuarioActual = null;
	public static int ultimaCategoria = 0;
	public static int ultimoMovimiento = 0;
	public static ArrayList<Categorias> listaCategorias = new ArrayList();
	public static ArrayList<Movimientos> listaMovimientos = new ArrayList();
	public static ArrayList<Usuarios> listaUsuarios = new ArrayList();
	
    public static void main(String[] args) {

        // 🟢 CAMBIO: usuarios de prueba
        listaUsuarios.add(new Usuarios("admin", "1234", "ADMIN"));
        listaUsuarios.add(new Usuarios("user1", "1234", "USER"));

        // 🟢 CAMBIO: login usando la nueva clase
        usuariosOpciones uo = new usuariosOpciones();
        uo.login();

        // 🟢 CAMBIO: solo entrar si login correcto
        if (usuarioActual != null) {
            MenuPrincipal mp = new MenuPrincipal();
            mp.usarMenu();
        } else {
            System.out.println("No se pudo iniciar sesión.");
        }

        sc.close();
    }
}