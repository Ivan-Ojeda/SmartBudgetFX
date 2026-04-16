package servicios;

import entidades.Movimientos;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class movimientosFuncionalidad {

    // 🎯 Apuntamos directamente a tu archivo CSV en la carpeta BBDD
    private final String RUTA_ARCHIVO = "BBDD/movimientos.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // ========================================================================
    // 📂 CARGA Y GUARDADO DE DATOS (Fichero CSV)
    // ========================================================================
    
    public List<Movimientos> cargarMovimientos() {
        List<Movimientos> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return lista; // Si el CSV no existe aún, devolvemos lista vacía
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Asumimos que tu formatoObjetoFichero() separa con ";"
                String[] datos = linea.split(";");
                
                if (datos.length == 6) {
                    int idMovimiento = Integer.parseInt(datos[0]);
                    double importe = Double.parseDouble(datos[1]);
                    int idCategoria = Integer.parseInt(datos[2]);
                    LocalDate fecha = LocalDate.parse(datos[3], formatter);
                    String descripcion = datos[4];
                    int idUsuario = Integer.parseInt(datos[5]);

                    Movimientos mov = new Movimientos(importe, idCategoria, fecha, descripcion, idUsuario);
                    mov.setIdMovimiento(idMovimiento); 
                    
                    lista.add(mov);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el CSV de movimientos: " + e.getMessage());
        }
        return lista;
    }

    public void guardarMovimientos(List<Movimientos> listaMovimientos) {
        // Asegurarnos de que la carpeta BBDD existe por si acaso
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs(); 

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Movimientos mov : listaMovimientos) {
                // Utiliza tu método de la entidad que ya formatea con ";"
                bw.write(mov.formatoObjetoFichero());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar en el CSV: " + e.getMessage());
        }
    }

    // ========================================================================
    // 🛡️ VALIDACIÓN Y CREACIÓN
    // ========================================================================

    public Movimientos validarYCrearMovimiento(String importeStr, String categoriaStr, LocalDate fecha, String descripcion, int idUsuarioActivo) throws IllegalArgumentException {
        if (importeStr == null || importeStr.trim().isEmpty()) throw new IllegalArgumentException("El importe no puede estar vacío.");
        if (categoriaStr == null || categoriaStr.trim().isEmpty()) throw new IllegalArgumentException("Debes seleccionar una categoría.");
        if (fecha == null) throw new IllegalArgumentException("Debes seleccionar una fecha.");
        if (descripcion == null || descripcion.trim().isEmpty()) throw new IllegalArgumentException("La descripción no puede estar vacía.");

        double importe;
        try {
            importe = Double.parseDouble(importeStr.replace(",", "."));
            if (importe <= 0) {
                throw new IllegalArgumentException("El importe debe ser mayor que 0.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El formato del importe no es válido. Usa números.");
        }

        int idCategoria = obtenerIdCategoria(categoriaStr);

        return new Movimientos(importe, idCategoria, fecha, descripcion, idUsuarioActivo);
    }

    // ========================================================================
    // 🔍 FILTROS Y BÚSQUEDAS
    // ========================================================================

    public boolean cumpleFiltros(Movimientos mov, int idUsuarioActivo, String categoriaFiltro, String mesFiltro) {
        boolean cumpleCategoria = true;
        if (categoriaFiltro != null && !categoriaFiltro.equals("Todas")) {
            String nombreCategoriaMov = obtenerNombreCategoria(mov.getCategoriaFK());
            cumpleCategoria = nombreCategoriaMov.equalsIgnoreCase(categoriaFiltro);
        }

        boolean cumpleMes = true;
        if (mesFiltro != null && !mesFiltro.equals("Todos")) {
            int mesSeleccionado = Integer.parseInt(mesFiltro.split(" ")[0]); 
            cumpleMes = (mov.getFechaMovimiento().getMonthValue() == mesSeleccionado);
        }

        return cumpleCategoria && cumpleMes;
    }

    // ========================================================================
    // 🏷️ UTILIDADES (Mapeo de IDs a Nombres)
    // ========================================================================

    public String obtenerNombreUsuario(int idUsuario) {
        switch (idUsuario) {
            case 1: return "Administrador";
            case 2: return "Miembro";
            default: return "Desconocido";
        }
    }

    public String obtenerNombreCategoria(int idCategoria) {
        switch (idCategoria) {
            case 1: return "Alimentación";
            case 2: return "Ocio";
            case 3: return "Transporte";
            case 4: return "Facturas";
            case 5: return "Nómina";
            default: return "Otros";
        }
    }

    private int obtenerIdCategoria(String nombreCategoria) {
        switch (nombreCategoria) {
            case "Alimentación": return 1;
            case "Ocio": return 2;
            case "Transporte": return 3;
            case "Facturas": return 4;
            case "Nómina": return 5;
            default: return 6;
        }
    }
}