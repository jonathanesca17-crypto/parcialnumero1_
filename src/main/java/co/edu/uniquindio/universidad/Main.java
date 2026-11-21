package co.edu.uniquindio.universidad;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Empresa empresa = new Empresa("DevPlus", "901234567-8", "Calle 15 # 14-20", "6067350000", "www.devplus.com");

        // ---------- Datos de prueba ----------
        // los telefonos 496 y 8128 son numeros perfectos
        Cliente c1 = new Cliente("Ana Torres", "1094567890", "8128", "ana@mail.com", "Colombia");
        Cliente c2 = new Cliente("Tech Solutions", "900123456", "496", "info@tech.com", "Estados Unidos");
        empresa.registrarCliente(c1);
        empresa.registrarCliente(c2);

        empresa.registrarDesarrollador(new Desarrollador("D001", "Backend", "Senior", 2, 400000, "Disponible"));
        empresa.registrarDesarrollador(new Desarrollador("D002", "Frontend", "Semisenior", 3, 250000, "Disponible"));
        empresa.registrarDesarrollador(new Desarrollador("D003", "Datos", "Junior", 1, 120000, "En capacitacion"));

        empresa.registrarServicio(new ServicioAdicional("S001", "Soporte tecnico", "Soporte durante el desarrollo", 500000, true));
        empresa.registrarServicio(new ServicioAdicional("S002", "Capacitacion", "Capacitacion a usuarios", 800000, true));
        empresa.registrarServicio(new ServicioAdicional("S003", "Despliegue en la nube", "Subir el sistema a la nube", 1200000, true));
        empresa.registrarServicio(new ServicioAdicional("S004", "Migracion de datos", "Pasar los datos viejos", 1500000, true));

        Proyecto p1 = new Proyecto("P001", "2026-09-01", "2026-10-01", "2026-10-10", "Efectivo", 10, c1);
        empresa.registrarProyecto(p1);
        p1.agregarDesarrollador(empresa.buscarDesarrollador("D001"));
        p1.agregarServicio(empresa.buscarServicio("S001"));

        // ---------- Menu ----------
        int opcion = -1;
        while (opcion != 0) {
            System.out.println();
            System.out.println("===== DEVPLUS =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar desarrollador");
            System.out.println("3. Registrar servicio adicional");
            System.out.println("4. Registrar proyecto");
            System.out.println("5. Agregar desarrollador a proyecto");
            System.out.println("6. Agregar servicio a proyecto");
            System.out.println("7. Confirmar proyecto");
            System.out.println("8. Listar clientes");
            System.out.println("9. Listar desarrolladores");
            System.out.println("10. Listar proyectos");
            System.out.println("11. Consultar cliente por telefono (numero perfecto)");
            System.out.println("12. Ingresos por fecha de solicitud");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.print("Nombre completo o razon social: ");
                String nombre = sc.nextLine();
                System.out.print("Documento o NIT: ");
                String doc = sc.nextLine();
                System.out.print("Telefono: ");
                String tel = sc.nextLine();
                System.out.print("Correo: ");
                String correo = sc.nextLine();
                System.out.print("Pais: ");
                String pais = sc.nextLine();
                empresa.registrarCliente(new Cliente(nombre, doc, tel, correo, pais));
                System.out.println("Cliente registrado");

            } else if (opcion == 2) {
                System.out.print("Codigo: ");
                String codigo = sc.nextLine();
                System.out.print("Equipo de trabajo: ");
                String equipo = sc.nextLine();
                System.out.print("Nivel (Junior, Semisenior, Senior): ");
                String nivel = sc.nextLine();
                System.out.print("Maximo de proyectos simultaneos: ");
                int max = Integer.parseInt(sc.nextLine());
                System.out.print("Tarifa por dia: ");
                double tarifa = Double.parseDouble(sc.nextLine());
                empresa.registrarDesarrollador(new Desarrollador(codigo, equipo, nivel, max, tarifa, "Disponible"));
                System.out.println("Desarrollador registrado");

            } else if (opcion == 3) {
                System.out.print("Codigo: ");
                String codigo = sc.nextLine();
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Descripcion: ");
                String desc = sc.nextLine();
                System.out.print("Precio: ");
                double precio = Double.parseDouble(sc.nextLine());
                empresa.registrarServicio(new ServicioAdicional(codigo, nombre, desc, precio, true));
                System.out.println("Servicio registrado");

            } else if (opcion == 4) {
                System.out.print("Documento del cliente: ");
                Cliente cliente = empresa.buscarClientePorDocumento(sc.nextLine());
                if (cliente == null) {
                    System.out.println("Cliente no encontrado");
                } else {
                    System.out.print("Codigo del proyecto: ");
                    String codigo = sc.nextLine();
                    System.out.print("Fecha de solicitud (aaaa-mm-dd): ");
                    String solicitud = sc.nextLine();
                    System.out.print("Fecha de inicio (aaaa-mm-dd): ");
                    String inicio = sc.nextLine();
                    System.out.print("Fecha de entrega (aaaa-mm-dd): ");
                    String entrega = sc.nextLine();
                    System.out.print("Metodo de pago (Tarjeta de credito, Transferencia bancaria, Efectivo): ");
                    String pago = sc.nextLine();
                    System.out.print("Dias de desarrollo: ");
                    int dias = Integer.parseInt(sc.nextLine());
                    empresa.registrarProyecto(new Proyecto(codigo, solicitud, inicio, entrega, pago, dias, cliente));
                    System.out.println("Proyecto registrado (Pendiente)");
                }

            } else if (opcion == 5) {
                System.out.print("Codigo del proyecto: ");
                Proyecto p = empresa.buscarProyecto(sc.nextLine());
                System.out.print("Codigo del desarrollador: ");
                Desarrollador d = empresa.buscarDesarrollador(sc.nextLine());
                if (p == null || d == null) {
                    System.out.println("Proyecto o desarrollador no encontrado");
                } else if (p.agregarDesarrollador(d)) {
                    System.out.println("Desarrollador agregado. Valor total: $" + p.getValorTotal());
                } else {
                    System.out.println("El desarrollador no esta disponible");
                }

            } else if (opcion == 6) {
                System.out.print("Codigo del proyecto: ");
                Proyecto p = empresa.buscarProyecto(sc.nextLine());
                System.out.print("Codigo del servicio: ");
                ServicioAdicional s = empresa.buscarServicio(sc.nextLine());
                if (p == null || s == null) {
                    System.out.println("Proyecto o servicio no encontrado");
                } else if (p.agregarServicio(s)) {
                    System.out.println("Servicio agregado. Valor total: $" + p.getValorTotal());
                } else {
                    System.out.println("No se pudo agregar el servicio");
                }

            } else if (opcion == 7) {
                System.out.print("Codigo del proyecto: ");
                Proyecto p = empresa.buscarProyecto(sc.nextLine());
                if (p == null) {
                    System.out.println("Proyecto no encontrado");
                } else if (p.confirmar()) {
                    System.out.println("Proyecto confirmado, desarrolladores actualizados");
                } else {
                    System.out.println("No se pudo confirmar (debe estar Pendiente y tener desarrolladores)");
                }

            } else if (opcion == 8) {
                for (int i = 0; i < empresa.getClientes().size(); i++) {
                    System.out.println(empresa.getClientes().get(i));
                }

            } else if (opcion == 9) {
                for (int i = 0; i < empresa.getDesarrolladores().size(); i++) {
                    System.out.println(empresa.getDesarrolladores().get(i));
                }

            } else if (opcion == 10) {
                for (int i = 0; i < empresa.getProyectos().size(); i++) {
                    System.out.println(empresa.getProyectos().get(i));
                }

            } else if (opcion == 11) {
                System.out.print("Telefono del cliente: ");
                String tel = sc.nextLine();
                Cliente c = empresa.consultarClientePorTelefono(tel);
                if (c == null) {
                    System.out.println("No hay cliente con ese telefono");
                } else {
                    System.out.println(c);
                    if (empresa.esNumeroPerfecto(Long.parseLong(tel))) {
                        System.out.println("El telefono " + tel + " SI es un numero perfecto");
                    } else {
                        System.out.println("El telefono " + tel + " NO es un numero perfecto");
                    }
                }

            } else if (opcion == 12) {
                System.out.print("Fecha de solicitud (aaaa-mm-dd): ");
                String fecha = sc.nextLine();
                System.out.println("Ingresos de esa fecha: $" + empresa.calcularIngresosPorFecha(fecha));

            } else if (opcion == 0) {
                System.out.println("Chao!");
            } else {
                System.out.println("Opcion invalida");
            }
        }
        sc.close();
    }
}