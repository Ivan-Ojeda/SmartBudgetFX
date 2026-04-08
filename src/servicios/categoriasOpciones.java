package servicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controladores.Inicio;
import entidades.Categorias;
import entidades.Movimientos;
import entidades.Usuarios;

/**
 * Clase que gestiona las operaciones relacionadas con las categorías. Permite
 * crear nuevas categorías y mostrar las existentes.
 */
public class categoriasOpciones {

	/**
	 * Crea una nueva categoría. Solicita al usuario el nombre de la categoría y el
	 * tipo de movimiento El tipo de movimiento(Ingreso, Gasto o Ahorro) Genera un
	 * id único Añade la categoría a la lista de categorías de Inicio.
	 * 
	 * private boolean tipoMovimiento; // gasto/ingreso private int cantidadAhorro =
	 * 0; // private String descripcionAhorro = "categoriaNoAhorro"; private String
	 * frecuenciaPago = "";
	 * 
	 */
	public void crearCategoria() {

		Inicio.sc.nextLine();
		System.out.println("Introduzca el nombre de la categoría que desea crear:");// Pide el nombre.
		String nombreCategoria = Inicio.sc.nextLine();// Guarda el nombre.

		boolean pedirMovimientoDeseado = true;// Se pide el tipo de movimiento hasta que sea alguna de las opciones correctas.
		boolean tipoMovimiento = true;
		final int cantidadAhorro = 0; String cadaCuantoPago = "";
		String descripcionAhorro = "";

		do {
			// Aqui pasaremos posiblemente a un desplegable o botones para selecciona gasto
			// o ingreso
			System.out.println("Introduzca el tipo de movimiento que va a tener esta categoría (Ingreso/Gasto): ");
			String tipoDeMovimiento = Inicio.sc.nextLine(); // transformamos en lowerCase el valor dado
			
			if (tipoDeMovimiento.toLowerCase().equals("gasto")) {
				tipoMovimiento = false;
				pedirMovimientoDeseado = false;
			} else if (tipoDeMovimiento.toLowerCase().equals("ingreso")) {
				tipoMovimiento = true;
				pedirMovimientoDeseado = false;
			} else {
				System.out.println("No es una opcion valida");
			}
			
		} while (pedirMovimientoDeseado);

		Categorias c = new Categorias(nombreCategoria, tipoMovimiento, Inicio.idUsuarioActual, cantidadAhorro, descripcionAhorro, cadaCuantoPago);// Se crea la nueva categoría.
//	public Categorias(String nombreCategoria, boolean tipoDeMovimiento, int idUsuarioFK, int cantidadAhorro, String descripcionAhorro, String cadaCuantoPago) {

		Inicio.listaCategorias.add(c);// se guarda la nueva categoría en la lista de categorías.
	}

	/**
	 * Muestra todas las categorías que tienen movimientos asociados. Recorre la
	 * lista de categorías y la lista de movimientos, y muestra por pantalla solo
	 * las categorías que coinciden con el tipo de movimiento de algún movimiento
	 * registrado.
	 */
	// Este metodo y el toString de Categorias hay que modificarlo cuando se sepa la
	// estructura de listarCategorias
	public void mostrarCategorias() {
	    usuariosOpciones uo = new usuariosOpciones();
	    boolean esAdmin = uo.esAdmin();
	    int idFiltrar = Inicio.idUsuarioActual; // por defecto, usuario normal ve solo sus categorías

	    // Si es admin, permite filtrar por usuario o todos
	    if (esAdmin) {
	        System.out.println("Usuarios disponibles:");
	        uo.listarUsuarios();

	        boolean idValido = false;
	        do {
	            System.out.println("Introduce el ID del usuario (-1 para todos): ");
	            String input = Inicio.sc.nextLine();
	            try {
	                idFiltrar = Integer.parseInt(input);
	                if (idFiltrar == -1) {
	                    idValido = true; // Todos los usuarios
	                } else {
	                    // Verificar que el ID existe
	                    for (Usuarios u : Inicio.listaUsuarios) {
	                        if (u.getIdUsuario() == idFiltrar) {
	                            idValido = true;
	                            break;
	                        }
	                    }
	                    if (!idValido) {
	                        System.out.println("❌ ID no válido. Intenta de nuevo.");
	                    }
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("❌ Introduce un número válido.");
	            }
	        } while (!idValido);
	    }

	    // Mostrar categorías según el filtro
	    for (Categorias ct : Inicio.listaCategorias) {
	        if (idFiltrar != -1 && ct.getIdUsuarioFK() != idFiltrar) {
	            continue; // Saltar categorías de otros usuarios
	        }

	        boolean tieneMovimiento = false;
	        for (Movimientos mv : Inicio.listaMovimientos) {
	            if (ct.getIdCategoria() == mv.getCategoriaFK()) {
	                tieneMovimiento = true;
	                break;
	            }
	        }

	        String tipo = ct.isTipoMovimiento() ? "[INGRESO]" : "[GASTO]";
	        System.out.println(tipo + " " + ct + (tieneMovimiento ? "" : " (sin movimientos)"));
	    }
	}
	
	/***
	 * 
	 * 
	 * LO DE ABAJO NO HE MIRADO SI ESTA BIEN !!!!
	 * 
	 * 
	 * 
	 */
	
	
	// [DEPENDE DE COMPAÑEROS]: Nombre del archivo y separador que usará el grupo
    private static final String FILE_NAME = "categorias.csv";
    private static final String SEPARADOR = ";";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        // Inicializamos el archivo con datos para poder probar
        prepararDatosEjemplo();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== GESTIÓN DE CATEGORÍAS (MODO EDICIÓN) ===");
            mostrarCategoriasActuales();
            
            System.out.println("\nOpciones: [1] Editar Nombre | [2] Salir");
            System.out.print("Selecciona una opción: ");
            String opcion = teclado.nextLine();

            if (opcion.equals("1")) {
                System.out.print("Introduce el NOMBRE ACTUAL de la categoría: ");
                String viejo = teclado.nextLine();
                
                System.out.print("Introduce el NUEVO NOMBRE: ");
                String nuevo = teclado.nextLine();

                // Llamada a tu funcionalidad
                boolean exito = editarCategoriaPorNombre(viejo, nuevo);

                if (exito) {
                    System.out.println("✅ Cambio guardado en el archivo.");
                } else {
                    System.out.println("❌ No se encontró esa categoría.");
                }
            } else if (opcion.equals("2")) {
                salir = true;
            }
        }
        System.out.println("Programa finalizado.");
        teclado.close();
    }

    /**
     * TU FUNCIONALIDAD PRINCIPAL
     */
    public static boolean editarCategoriaPorNombre(String nombreSeleccionado, String nombreNuevo) {
        List<String> todasLasLineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // [DEPENDE DE COMPAÑEROS]: Aquí se usa el split que definieron
                String[] partes = linea.split(SEPARADOR);

                // [DEPENDE DE COMPAÑEROS]: partes[1] debe ser el nombre
                if (partes.length >= 3 && partes[1].equalsIgnoreCase(nombreSeleccionado)) {
                    
                    // Reconstrucción manteniendo ID (0) y TIPO (2)
                    StringBuilder nuevaLinea = new StringBuilder();
                    nuevaLinea.append(partes[0]).append(SEPARADOR);
                    nuevaLinea.append(nombreNuevo).append(SEPARADOR);
                    nuevaLinea.append(partes[2]);

                    // Mantener columnas extra si existieran
                    for (int i = 3; i < partes.length; i++) {
                        nuevaLinea.append(SEPARADOR).append(partes[i]);
                    }

                    todasLasLineas.add(nuevaLinea.toString());
                    encontrado = true;
                } else {
                    todasLasLineas.add(linea);
                }
            }
        } catch (IOException e) {
            return false;
        }

        if (encontrado) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
                for (String l : todasLasLineas) {
                    writer.write(l);
                    writer.newLine();
                }
            } catch (IOException e) {
                return false;
            }
        }
        return encontrado;
    }

    /**
     * MÉTODOS DE APOYO (Para que el ejemplo funcione)
     */
    private static void prepararDatosEjemplo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            // [DEPENDE DE COMPAÑEROS]: El orden id;nombre;tipo
            pw.println("C1;Alimentacion;GASTO");
            pw.println("C2;Sueldo;INGRESO");
            pw.println("C3;Videojuegos;GASTO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mostrarCategoriasActuales() {
        System.out.println("Contenido del archivo (" + FILE_NAME + "):");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String l;
            while ((l = br.readLine()) != null) {
                System.out.println("  > " + l);
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado.");
        }
    }
	
    public void eliminarCategoria(int idCategoria) {
        boolean tieneMovimiento = false;

        for (Movimientos mv : Inicio.listaMovimientos) {
            if (mv.getCategoriaFK() == idCategoria) {
                tieneMovimiento = true;
                break;
            }
        }

        if (tieneMovimiento) {
            System.out.println("❌ No se puede eliminar, tiene movimientos.");
        } else {
            Inicio.listaCategorias.removeIf(c -> c.getIdCategoria() == idCategoria);
            System.out.println("✅ Categoría eliminada.");
        }
    }
}