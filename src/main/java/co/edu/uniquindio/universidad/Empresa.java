package co.edu.uniquindio.universidad;
import java.util.ArrayList;

public class Empresa {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String paginaWeb;
    private ArrayList<Cliente> clientes;
    private ArrayList<Desarrollador> desarrolladores;
    private ArrayList<Proyecto> proyectos;
    private ArrayList<ServicioAdicional> servicios;

    public Empresa(String nombreComercial, String nit, String direccion, String telefono, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.clientes = new ArrayList<Cliente>();
        this.desarrolladores = new ArrayList<Desarrollador>();
        this.proyectos = new ArrayList<Proyecto>();
        this.servicios = new ArrayList<ServicioAdicional>();
    }

    public String getNombreComercial() { return nombreComercial; }
    public String getNit() { return nit; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getPaginaWeb() { return paginaWeb; }
    public ArrayList<Cliente> getClientes() { return clientes; }
    public ArrayList<Desarrollador> getDesarrolladores() { return desarrolladores; }
    public ArrayList<Proyecto> getProyectos() { return proyectos; }
    public ArrayList<ServicioAdicional> getServicios() { return servicios; }

    // ---------- Registrar ----------
    public void registrarCliente(Cliente c) { clientes.add(c); }
    public void registrarDesarrollador(Desarrollador d) { desarrolladores.add(d); }
    public void registrarServicio(ServicioAdicional s) { servicios.add(s); }

    public void registrarProyecto(Proyecto p) {
        proyectos.add(p);
        p.getCliente().agregarProyecto(p);
    }

    // ---------- Buscar ----------
    public Cliente buscarClientePorDocumento(String documento) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDocumento().equals(documento)) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public Cliente buscarClientePorTelefono(String telefono) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getTelefono().equals(telefono)) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public Desarrollador buscarDesarrollador(String codigo) {
        for (int i = 0; i < desarrolladores.size(); i++) {
            if (desarrolladores.get(i).getCodigo().equals(codigo)) {
                return desarrolladores.get(i);
            }
        }
        return null;
    }

    public Proyecto buscarProyecto(String codigo) {
        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getCodigo().equals(codigo)) {
                return proyectos.get(i);
            }
        }
        return null;
    }

    public ServicioAdicional buscarServicio(String codigo) {
        for (int i = 0; i < servicios.size(); i++) {
            if (servicios.get(i).getCodigo().equals(codigo)) {
                return servicios.get(i);
            }
        }
        return null;
    }

    // ---------- Funcionalidades del parcial ----------

    // Numero perfecto: es igual a la suma de sus divisores propios (sin contarse el mismo)
    public boolean esNumeroPerfecto(long numero) {
        if (numero < 2) {
            return false;
        }
        long suma = 1;
        // solo recorremos hasta la raiz para que no se demore con telefonos largos
        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                suma = suma + i;
                if (i != numero / i) {
                    suma = suma + numero / i;
                }
            }
        }
        return suma == numero;
    }

    // Busca el cliente por telefono (el main revisa si el numero es perfecto)
    public Cliente consultarClientePorTelefono(String telefono) {
        return buscarClientePorTelefono(telefono);
    }

    // Recorre los proyectos y acumula el valor de los solicitados en esa fecha
    public double calcularIngresosPorFecha(String fecha) {
        double acumulado = 0;
        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getFechaSolicitud().equals(fecha)) {
                acumulado = acumulado + proyectos.get(i).getValorTotal();
            }
        }
        return acumulado;
    }
}