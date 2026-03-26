package servicios;

import controladores.Inicio;

public class SubMenuCategorias implements MenuInterfaz {

	@Override
	public void mostrarMenu() {
		System.out.println("___________________________________________________");
		System.out.println("|          Bienvenido al de categorías:           |");
		System.out.println("|_________________________________________________|");
		System.out.println("|-Elija una opción:                               |");
		System.out.println("|-------------------------------------------------|");
		System.out.println("|  1.Crear categoría.                             |");
		System.out.println("|  2.Mostrar categorías de ingresos y gastos.     |");
		System.out.println("|  3.Cerrar el de categorías.                     |");
		System.out.println("|_________________________________________________|");

	}

	@Override
	public byte elegirOpcion() {
		byte opcionElegida = Inicio.sc.nextByte();
		return opcionElegida;
	}

	@Override
	public void usarMenu() {
		categoriasOpciones co = new categoriasOpciones();
		boolean usarSubMenuCategorias = true;
		do{
			mostrarMenu();
			byte opcionSubMenucategorias = elegirOpcion();
			switch(opcionSubMenucategorias) {
			case 1:
				co.crearCategoria();
					break;
			case 2:
				co.mostrarCategorias();
					break;
			case 3:
				System.out.println("Cerrando el menú de categorías...");
				System.out.println("Menú de categorías cerrado con éxito.");
				System.out.println("Volviendo al menú principal...");	
					usarSubMenuCategorias = false;
					break;
			default:
				System.out.println("No ha introducido una opción correcta.");
					break;
			}
		}while(usarSubMenuCategorias);
	}

}