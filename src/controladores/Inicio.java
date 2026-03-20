package controladores;

import java.util.ArrayList;
import java.util.Scanner;
import entidades.Categorias;
import entidades.Movimientos;
import servicios.MenuPrincipal;

public class Inicio {
	public static Scanner sc = new Scanner(System.in);
	public static int ultimaCategoria = 0;
	public static int ultimoMovimiento = 0;
	public static ArrayList<Categorias> listaCategorias = new ArrayList();
	public static ArrayList<Movimientos> listaMovimientos = new ArrayList();
	public static void main(String[] args) {
		MenuPrincipal mp = new MenuPrincipal();
			mp.usarMenu();
				sc.close();
	}
}
