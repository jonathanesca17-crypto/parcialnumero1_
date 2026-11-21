package co.edu.uniquindio.universidad;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static Empresa empresa;

    public static void main(String[] args) {
        empresa = new Empresa("DevPlus", "901.234.567-8", "Calle 15 # 14-20, Armenia, Quindío",
                "6067350000", "www.devplus.com");
        cargarDatosDePrueba();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            System.out.println();
            switch (opcion) {
                case 1: registrarCliente(); break;
                case 2: registrarDesarrollador(); break;
                case 3: registrarServicio(); break;
                case 4: registrarProyecto(); break;
                case 5: asignarDesarrollador(); break;
                case 6: agregarServicio(); break;
                case 7: confirmarProyecto(); break;
                case 8: cambiarEstado(); break;
                case 9: listarClientes(); break;
                case 10: listarDesarrolladores(); break;
                case 11: listarProyectos(); break;
                case 12: listarServicios(); break;
                case 13: consultarPorTelefono(); break;
                case 14: consultarIngresos(); break;
                case 0: System.out.println("¡Hasta pronto!"); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // ------------------------------- Menú -------------------------------

    private static void mostrarMenu() {
        System.out.println("\n==================== " + empresa.getNombreComercial() + " ====================");
        System.out.println(" 1. Registrar cliente");
        System.out.println(" 2. Registrar desarrollador");
        System.out.println(" 3. Registrar servicio adicional");
        System.out.println(" 4. Registrar proyecto");
        System.out.println(" 5. Agregar desarrollador a un proyecto");
        System.out.println(" 6. Agregar servicio adicional a un proyecto");
        System.out.println(" 7. Confirmar proyecto");
        System.out.println(" 8. Cambiar estado de un proyecto");
        System.out.println(" 9. Listar clientes");
        System.out.println("10. Listar desarrolladores");
        System.out.println("11. Listar proyectos");
        System.out.println("12. Listar servicios adicionales");
        System.out.println("13. Consultar cliente por teléfono (¿número perfecto?)");
        System.out.println("14. Ingresos por fecha de solicitud");
        System.out.println(" 0. Salir");
    }

    // --------------------------- Opciones del menú ---------------------------

    private static void registrarCliente() {
        String nombre = leerTexto("Nombre completo o razón social: ");
        String doc = leerTexto("Documento de identidad o NIT: ");
        String tel = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo electrónico: ");
        String pais = leerTexto("País de procedencia: ");
        boolean ok = empresa.registrarCliente(new Cliente(nombre, doc, tel, correo, pais));
        System.out.println(ok ? "Cliente registrado." : "Ya existe un cliente con ese documento.");
    }

    private static void registrarDesarrollador() {
        String codigo = leerTexto("Código del desarrollador: ");
        String equipo = leerTexto("Equipo de trabajo: ");
        System.out.println("Nivel: 1. Junior  2. Semisenior  3. Senior");
        int n = leerEnteroEnRango("Opción: ", 1, 3);
        NivelDesarrollador nivel = NivelDesarrollador.values()[n - 1];
        int max = leerEnteroEnRango("Máximo de proyectos simultáneos: ", 1, 50);
        double tarifa = leerDouble("Tarifa por día: ");
        boolean ok = empresa.registrarDesarrollador(
                new Desarrollador(codigo, equipo, nivel, max, tarifa, EstadoDesarrollador.DISPONIBLE));
        System.out.println(ok ? "Desarrollador registrado." : "Ya existe un desarrollador con ese código.");
    }

    private static void registrarServicio() {
        String codigo = leerTexto("Código del servicio: ");
        String nombre = leerTexto("Nombre: ");
        String desc = leerTexto("Descripción: ");
        double precio = leerDouble("Precio: ");
        boolean ok = empresa.registrarServicio(new ServicioAdicional(codigo, nombre, desc, precio, true));
        System.out.println(ok ? "Servicio registrado." : "Ya existe un servicio con ese código.");
    }

    private static void registrarProyecto() {
        String docCliente = leerTexto("Documento/NIT del cliente: ");
        Cliente cliente = empresa.buscarClientePorDocumento(docCliente);
        if (cliente == null) {
            System.out.println("El cliente no está registrado.");
            return;
        }
        String codigo = leerTexto("Código del proyecto: ");
        LocalDate solicitud = leerFecha("Fecha de solicitud (aaaa-mm-dd): ");
        LocalDate inicio = leerFecha("Fecha de inicio (aaaa-mm-dd): ");
        LocalDate entrega = leerFecha("Fecha de entrega (aaaa-mm-dd): ");
        System.out.println("Método de pago: 1. Tarjeta de crédito  2. Transferencia bancaria  3. Efectivo");
        int m = leerEnteroEnRango("Opción: ", 1, 3);
        try {
            Proyecto p = new Proyecto(codigo, solicitud, inicio, entrega, MetodoPago.values()[m - 1], cliente);
            System.out.println(empresa.registrarProyecto(p)
                    ? "Proyecto registrado en estado Pendiente."
                    : "Ya existe un proyecto con ese código.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void asignarDesarrollador() {
        String proyecto = leerTexto("Código del proyecto: ");
        String dev = leerTexto("Código del desarrollador: ");
        System.out.println(empresa.asignarDesarrollador(proyecto, dev));
    }

    private static void agregarServicio() {
        String proyecto = leerTexto("Código del proyecto: ");
        String servicio = leerTexto("Código del servicio: ");
        System.out.println(empresa.agregarServicioAProyecto(proyecto, servicio));
    }

    private static void confirmarProyecto() {
        String proyecto = leerTexto("Código del proyecto a confirmar: ");
        System.out.println(empresa.confirmarProyecto(proyecto));
    }

    private static void cambiarEstado() {
        String proyecto = leerTexto("Código del proyecto: ");
        System.out.println("Nuevo estado: 1. Pendiente  2. Confirmado  3. En curso  4. Finalizado  5. Cancelado");
        int e = leerEnteroEnRango("Opción: ", 1, 5);
        System.out.println(empresa.cambiarEstadoProyecto(proyecto, EstadoProyecto.values()[e - 1]));
    }

    private static void listarClientes() {
        if (empresa.getClientes().isEmpty()) {
            System.out.println("No hay clientes registrados.");
        }
        for (Cliente c : empresa.getClientes()) {
            System.out.println(c);
        }
    }

    private static void listarDesarrolladores() {
        if (empresa.getDesarrolladores().isEmpty()) {
            System.out.println("No hay desarrolladores registrados.");
        }
        for (Desarrollador d : empresa.getDesarrolladores()) {
            System.out.println(d);
        }
    }

    private static void listarProyectos() {
        if (empresa.getProyectos().isEmpty()) {
            System.out.println("No hay proyectos registrados.");
        }
        for (Proyecto p : empresa.getProyectos()) {
            System.out.println(p);
            System.out.println();
        }
    }

    private static void listarServicios() {
        if (empresa.getServicios().isEmpty()) {
            System.out.println("No hay servicios registrados.");
        }
        for (ServicioAdicional s : empresa.getServicios()) {
            System.out.println(s);
        }
    }

    private static void consultarPorTelefono() {
        String tel = leerTexto("Teléfono del cliente: ");
        System.out.println(empresa.consultarClientePorTelefono(tel));
    }

    private static void consultarIngresos() {
        LocalDate fecha = leerFecha("Fecha de solicitud a consultar (aaaa-mm-dd): ");
        double total = empresa.calcularIngresosPorFecha(fecha);
        System.out.printf("Ingresos de los proyectos solicitados el %s: $%,.0f%n", fecha, total);
    }

    // ---------------------------- Datos de prueba ----------------------------

    private static void cargarDatosDePrueba() {
        // Teléfonos 8128 y 496 son números perfectos (para probar la consulta)
        Cliente ana = new Cliente("Ana Torres", "1094567890", "8128", "ana@mail.com", "Colombia");
        Cliente tech = new Cliente("Tech Solutions Inc.", "900123456", "496", "info@techsol.com", "Estados Unidos");
        Cliente carlos = new Cliente("Carlos Gómez", "79876543", "3001234567", "carlos@mail.com", "Colombia");
        empresa.registrarCliente(ana);
        empresa.registrarCliente(tech);
        empresa.registrarCliente(carlos);

        empresa.registrarDesarrollador(new Desarrollador("D001", "Backend", NivelDesarrollador.SENIOR, 2, 400000, EstadoDesarrollador.DISPONIBLE));
        empresa.registrarDesarrollador(new Desarrollador("D002", "Frontend", NivelDesarrollador.SEMISENIOR, 3, 250000, EstadoDesarrollador.DISPONIBLE));
        empresa.registrarDesarrollador(new Desarrollador("D003", "Datos", NivelDesarrollador.JUNIOR, 1, 120000, EstadoDesarrollador.DISPONIBLE));
        empresa.registrarDesarrollador(new Desarrollador("D004", "Móvil", NivelDesarrollador.JUNIOR, 1, 110000, EstadoDesarrollador.EN_CAPACITACION));

        empresa.registrarServicio(new ServicioAdicional("S001", "Soporte técnico", "Soporte durante el desarrollo", 500000, true));
        empresa.registrarServicio(new ServicioAdicional("S002", "Capacitación de usuarios", "Formación al personal del cliente", 800000, true));
        empresa.registrarServicio(new ServicioAdicional("S003", "Despliegue en la nube", "Publicación en servidores cloud", 1200000, true));
        empresa.registrarServicio(new ServicioAdicional("S004", "Migración de datos", "Traslado de datos legados", 1500000, true));

        Proyecto p1 = new Proyecto("P001", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 10), MetodoPago.TRANSFERENCIA_BANCARIA, ana);
        Proyecto p2 = new Proyecto("P002", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 10, 5),
                LocalDate.of(2026, 10, 20), MetodoPago.TARJETA_CREDITO, tech);
        empresa.registrarProyecto(p1);
        empresa.registrarProyecto(p2);
        empresa.asignarDesarrollador("P001", "D001");
        empresa.asignarDesarrollador("P001", "D002");
        empresa.agregarServicioAProyecto("P001", "S001");
        empresa.asignarDesarrollador("P002", "D002");
        empresa.agregarServicioAProyecto("P002", "S003");
        empresa.confirmarProyecto("P001");
    }

    // -------------------------- Utilidades de lectura --------------------------

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static int leerEnteroEnRango(String mensaje, int min, int max) {
        while (true) {
            int n = leerEntero(mensaje);
            if (n >= min && n <= max) {
                return n;
            }
            System.out.println("Debe estar entre " + min + " y " + max + ".");
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                return LocalDate.parse(leerTexto(mensaje));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use aaaa-mm-dd.");
            }
        }
    }
}