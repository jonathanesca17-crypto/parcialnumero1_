package co.edu.uniquindio.universidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String paginaWeb;
    private List<Cliente> clientes;
    private List<Desarrollador> desarrolladores;
    private List<Proyecto> proyectos;
    private List<ServicioAdicional> servicios;

    public Empresa(String nombreComercial, String nit, String direccion, String telefono, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.clientes = new ArrayList<>();
        this.desarrolladores = new ArrayList<>();
        this.proyectos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    // ---------------------------- Registros ----------------------------

    public boolean registrarCliente(Cliente cliente) {
        if (buscarClientePorDocumento(cliente.getDocumento()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public boolean registrarDesarrollador(Desarrollador desarrollador) {
        if (buscarDesarrollador(desarrollador.getCodigo()) != null) {
            return false;
        }
        desarrolladores.add(desarrollador);
        return true;
    }

    public boolean registrarServicio(ServicioAdicional servicio) {
        if (buscarServicio(servicio.getCodigo()) != null) {
            return false;
        }
        servicios.add(servicio);
        return true;
    }

    public boolean registrarProyecto(Proyecto proyecto) {
        if (buscarProyecto(proyecto.getCodigo()) != null) {
            return false;
        }
        proyectos.add(proyecto);
        proyecto.getCliente().agregarProyecto(proyecto);
        return true;
    }

    // ----------------------------- Búsquedas ----------------------------

    public Cliente buscarClientePorDocumento(String documento) {
        for (Cliente c : clientes) {
            if (c.getDocumento().equalsIgnoreCase(documento)) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarClientePorTelefono(String telefono) {
        for (Cliente c : clientes) {
            if (c.getTelefono().equals(telefono)) {
                return c;
            }
        }
        return null;
    }

    public Desarrollador buscarDesarrollador(String codigo) {
        for (Desarrollador d : desarrolladores) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) {
                return d;
            }
        }
        return null;
    }

    public Proyecto buscarProyecto(String codigo) {
        for (Proyecto p : proyectos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public ServicioAdicional buscarServicio(String codigo) {
        for (ServicioAdicional s : servicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                return s;
            }
        }
        return null;
    }

    // ---------------------------- Operaciones ---------------------------

    public String asignarDesarrollador(String codigoProyecto, String codigoDesarrollador) {
        Proyecto p = buscarProyecto(codigoProyecto);
        if (p == null) {
            return "No existe un proyecto con código " + codigoProyecto;
        }
        Desarrollador d = buscarDesarrollador(codigoDesarrollador);
        if (d == null) {
            return "No existe un desarrollador con código " + codigoDesarrollador;
        }
        if (p.agregarDesarrollador(d)) {
            return String.format("Desarrollador %s agregado al proyecto %s. Nuevo valor total: $%,.0f",
                    d.getCodigo(), p.getCodigo(), p.getValorTotal());
        }
        return "No se pudo agregar: el desarrollador no está disponible en las fechas del proyecto, "
                + "ya estaba asignado o el proyecto está finalizado/cancelado.";
    }

    public String agregarServicioAProyecto(String codigoProyecto, String codigoServicio) {
        Proyecto p = buscarProyecto(codigoProyecto);
        if (p == null) {
            return "No existe un proyecto con código " + codigoProyecto;
        }
        ServicioAdicional s = buscarServicio(codigoServicio);
        if (s == null) {
            return "No existe un servicio con código " + codigoServicio;
        }
        if (p.agregarServicio(s)) {
            return String.format("Servicio %s agregado al proyecto %s. Nuevo valor total: $%,.0f",
                    s.getNombre(), p.getCodigo(), p.getValorTotal());
        }
        return "No se pudo agregar: el servicio no está disponible, ya estaba asociado o el proyecto está cerrado.";
    }

    public String confirmarProyecto(String codigoProyecto) {
        Proyecto p = buscarProyecto(codigoProyecto);
        if (p == null) {
            return "No existe un proyecto con código " + codigoProyecto;
        }
        if (p.confirmar()) {
            return "Proyecto " + p.getCodigo() + " confirmado. Se actualizó la disponibilidad de sus desarrolladores.";
        }
        return "No se pudo confirmar: debe estar Pendiente, tener al menos un desarrollador y todos disponibles.";
    }

    public String cambiarEstadoProyecto(String codigoProyecto, EstadoProyecto nuevoEstado) {
        Proyecto p = buscarProyecto(codigoProyecto);
        if (p == null) {
            return "No existe un proyecto con código " + codigoProyecto;
        }
        if (p.cambiarEstado(nuevoEstado)) {
            return "Estado del proyecto " + p.getCodigo() + " cambiado a " + nuevoEstado;
        }
        return "Cambio de estado no permitido desde " + p.getEstado() + " hacia " + nuevoEstado;
    }

    // ----------------- Funcionalidades solicitadas en el parcial ----------------

    /** Un número perfecto es igual a la suma de sus divisores propios (sin incluirse). */
    public boolean esNumeroPerfecto(long numero) {
        if (numero < 2) {
            return false;
        }
        long suma = 1; // el 1 siempre es divisor propio
        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                suma += i;
                long pareja = numero / i;
                if (pareja != i) {
                    suma += pareja;
                }
            }
        }
        return suma == numero;
    }

    /** Busca al cliente por teléfono e indica si ese número telefónico es perfecto. */
    public String consultarClientePorTelefono(String telefono) {
        Cliente cliente = buscarClientePorTelefono(telefono);
        if (cliente == null) {
            return "No se encontró ningún cliente con el teléfono " + telefono;
        }
        String soloDigitos = telefono.replaceAll("[^0-9]", "");
        boolean perfecto = false;
        try {
            perfecto = esNumeroPerfecto(Long.parseLong(soloDigitos));
        } catch (NumberFormatException e) {
            perfecto = false;
        }
        return "Cliente encontrado: " + cliente + "\n"
                + "El número " + soloDigitos + (perfecto ? " SÍ" : " NO") + " es un número perfecto.";
    }

    /** Recorre los proyectos y acumula el valor de los solicitados en la fecha consultada. */
    public double calcularIngresosPorFecha(LocalDate fecha) {
        double acumulado = 0;
        for (Proyecto p : proyectos) {
            if (p.getFechaSolicitud().equals(fecha)) {
                acumulado += p.getValorTotal();
            }
        }
        return acumulado;
    }

    // ------------------------- Getters y setters -------------------------

    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getPaginaWeb() { return paginaWeb; }
    public void setPaginaWeb(String paginaWeb) { this.paginaWeb = paginaWeb; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Desarrollador> getDesarrolladores() { return desarrolladores; }
    public List<Proyecto> getProyectos() { return proyectos; }
    public List<ServicioAdicional> getServicios() { return servicios; }

    @Override
    public String toString() {
        return String.format("%s | NIT: %s | %s | Tel: %s | %s",
                nombreComercial, nit, direccion, telefono, paginaWeb);
    }
}