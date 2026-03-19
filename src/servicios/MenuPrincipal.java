package servicios;

import controladores.Inicio;

public class MenuPrincipal implements MenuInterfaz {

	@Override
	public void mostrarMenu() {
		System.out.println("_____________________________________");
		System.out.println("|   Bienvenido al menú principal:   |");
		System.out.println("|___________________________________|");
		System.out.println("|-Elija una opción:                 |");
		System.out.println("|-----------------------------------|");
		System.out.println("|  1.Movimientos.                   |");
		System.out.println("|  2.Categorías.                    |");
		System.out.println("|  3.Resumen.                       |");
		System.out.println("|  4.Objetivos de ahorro y gráficas.|");
		System.out.println("|  5.Cerrar el menú principal.      |");
		System.out.println("|___________________________________|");

	}

	@Override
	public byte elegirOpcion() {
		byte opcionElegida = Inicio.sc.nextByte();
		return opcionElegida;
	}

	@Override
	public void usarMenu() {
		SubMenuCategorias smc = new SubMenuCategorias();
		boolean usarMenuPrincipal = true;
		do{
			mostrarMenu();
			byte opcionMenuPrincipal = elegirOpcion();
			switch(opcionMenuPrincipal) {
			case 1:
				break;
			case 2:
				smc.usarMenu();
					break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				System.out.println("Cerrando el menú principal...");
				System.out.println("Menú principal cerrado con éxito.");
					usarMenuPrincipal = false;
					break;
			default:
				System.out.println("No ha introducido una opción correcta.");
					break;
			}
		}while(usarMenuPrincipal);
	}

}
