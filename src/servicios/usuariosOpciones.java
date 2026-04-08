package servicios;

import controladores.Inicio;
import entidades.Usuarios;

public class usuariosOpciones {

    public void login() {

        System.out.println("Usuario:");
        String nombre = Inicio.sc.nextLine();

        System.out.println("Contraseña:");
        String pass = Inicio.sc.nextLine();

        for (Usuarios u : Inicio.listaUsuarios) {
            if (u.getNombreUsuario().equals(nombre) && u.getContrasenya().equals(pass)) {
                
                Inicio.usuarioActual = u;
                Inicio.idUsuarioActual = u.getIdUsuario();

                System.out.println("✅ Login correcto. Bienvenido " + u.getNombreUsuario());
                return;
            }
        }

        System.out.println("❌ Usuario o contraseña incorrectos");
    }

    // 🔴 CAMBIO: ya no usamos getRol()
    public boolean esAdmin() {
        return Inicio.usuarioActual.esAdmin();
    }

    public void listarUsuarios() {
        for (Usuarios u : Inicio.listaUsuarios) {
            System.out.println(u.getIdUsuario() + " - " + u.getNombreUsuario());
        }
    }
}