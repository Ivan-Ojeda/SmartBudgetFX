package controladores;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.Categorias;
import entidades.Movimientos;

public class Inicio {
	public static Scanner sc = new Scanner(System.in);
	public static ArrayList<Movimientos> listaMovimientos = new ArrayList();
	public static ArrayList<Categorias> listaCategorias = new ArrayList();
	public static void main(String[] args) {
		for(Categorias ct : listaCategorias) {
			for(Movimientos mv : ct.getListaMovimientos()) {
				if(mv.getTipoDeMovimiento().equals("Ingresos") || mv.getTipoDeMovimiento().equals("Gastos")){
					System.out.println(ct.toString());
						break;
				}
			}
		}
	}
}
