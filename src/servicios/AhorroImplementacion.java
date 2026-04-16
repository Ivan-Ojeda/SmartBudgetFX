package servicios;

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