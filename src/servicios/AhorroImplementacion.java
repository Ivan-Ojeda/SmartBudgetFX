package servicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import controladores.Inicio;
import entidades.Categorias;

public class AhorroImplementacion {

    /**
     * Método principal para crear un objetivo de ahorro
     */
    public void crearAhorro(String frecuenciaPago,
                            String nombreCategoria,
                            int cantidadAhorro,
                            String descripcionAhorro) {

        // Validación básica
        if (nombreCategoria == null || nombreCategoria.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio.");
        }

        if (frecuenciaPago == null || frecuenciaPago.isEmpty()) {
            throw new IllegalArgumentException("Debes seleccionar una frecuencia de pago.");
        }

        if (cantidadAhorro <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0.");
        }

        // Crear la categoría de ahorro
        Categorias nuevaCategoria = new Categorias(
                nombreCategoria,
                true, // Siempre true para ahorro
                Inicio.idUsuarioActual,
                cantidadAhorro,
                descripcionAhorro,
                frecuenciaPago
        );

        // Añadir a la lista global
        Inicio.listaCategorias.add(nuevaCategoria);
        guardarCategoriasEnFichero();
    }
    
    
    
 // MÉTODO PARA GUARDAR (Escritura)
    public void guardarCategoriasEnFichero() {
        // El nombre del archivo debe coincidir con el de tu raíz de proyecto
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("listaCategorias"))) {
            for (Categorias c : Inicio.listaCategorias) {
                // USAMOS TU MÉTODO ESPECÍFICO
                bw.write(c.formatoObjetoFichero());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    
    // MÉTODO PARA CARGAR (Lectura) - Estático para llamarlo desde el Inicio
    public static void cargarCategoriasDesdeFichero() {
        File archivo = new File("listaCategorias");
        if (!archivo.exists()) return;

        Inicio.listaCategorias.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(";");
                if (datos.length >= 7) {
                    // Creamos el objeto pasando los datos del CSV
                    Categorias c = new Categorias(
                        datos[1],                       // nombreCategoria
                        Boolean.parseBoolean(datos[2]),  // tipoMovimiento
                        Integer.parseInt(datos[3]),     // idUsuarioFK
                        Integer.parseInt(datos[4]),     // cantidadAhorro
                        datos[5],                       // descripcionAhorro
                        datos[6]                        // frecuenciaPago
                    );
                    // IMPORTANTE: Mantenemos el ID original del fichero
                    c.setIdCategoria(Integer.parseInt(datos[0]));
                    
                    Inicio.listaCategorias.add(c);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar: " + e.getMessage());
        }
    }
    
    

    public static ArrayList<Categorias> obtenerAhorros() {

        ArrayList<Categorias> listaAhorros =
                new ArrayList<>();

        for (Categorias c : Inicio.listaCategorias) {

            if (!c.getFrecuenciaPago()
                    .equals("categoriaNoAhorro")) {

                listaAhorros.add(c);

            }

        }

        return listaAhorros;

    }
    
    
    
    
}