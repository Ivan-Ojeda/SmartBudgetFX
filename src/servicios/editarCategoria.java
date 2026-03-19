package servicios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class editarCategoria {

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
}