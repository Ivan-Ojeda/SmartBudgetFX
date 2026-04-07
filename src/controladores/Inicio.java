 package controladores;

import java.util.ArrayList;

import java.util.Scanner;
import entidades.Categorias;
import entidades.Movimientos;
import entidades.Usuarios;
import servicios.MenuPrincipal;


public class Inicio {
	public static Scanner sc = new Scanner(System.in);
	public static final int idUsuarioActual = 0;// Esta id de dara cuando se inicie la sesion con un usuario. Puede cambiar de const a normal.
	public static int ultimaCategoria = 0;
	public static int ultimoMovimiento = 0;
	public static ArrayList<Categorias> listaCategorias = new ArrayList();
	public static ArrayList<Movimientos> listaMovimientos = new ArrayList();
	public static ArrayList<Usuarios> listaUsuarios = new ArrayList();
	
	public static void main(String[] args) {
		MenuPrincipal mp = new MenuPrincipal();
			mp.usarMenu();
				sc.close();
	}
}
