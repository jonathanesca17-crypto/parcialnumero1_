package co.edu.uniquindio.universidad;
import java.util.ArrayList;

public class Proyecto {
    private String codigo;
    private String fechaSolicitud;   // formato aaaa-mm-dd
    private String fechaInicio;
    private String fechaEntrega;
    private String estado;           // Pendiente, Confirmado, En curso, Finalizado, Cancelado
    private String metodoPago;       // Tarjeta de credito, Transferencia bancaria, Efectivo
    private int diasDesarrollo;
    private double valorTotal;
    private Cliente cliente;
    private ArrayList<Desarrollador> desarrolladores;
    private ArrayList<ServicioAdicional> servicios;

    public Proyecto(String codigo, String fechaSolicitud, String fechaInicio, String fechaEntrega,
                    String metodoPago, int diasDesarrollo, Cliente cliente) {
        this.codigo = codigo;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaInicio = fechaInicio;
        this.fechaEntrega = fechaEntrega;
        this.metodoPago = metodoPago;
        this.diasDesarrollo = diasDesarrollo;
        this.cliente = cliente;
        this.estado = "Pendiente";
        this.valorTotal = 0;
        this.desarrolladores = new ArrayList<Desarrollador>();
        this.servicios = new ArrayList<ServicioAdicional>();
    }

    public String getCodigo() { return codigo; }
    public String getFechaSolicitud() { return fechaSolicitud; }
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaEntrega() { return fechaEntrega; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMetodoPago() { return metodoPago; }
    public int getDiasDesarrollo() { return diasDesarrollo; }
    public double getValorTotal() { return valorTotal; }
    public Cliente getCliente() { return cliente; }
    public ArrayList<Desarrollador> getDesarrolladores() { return desarrolladores; }
    public ArrayList<ServicioAdicional> getServicios() { return servicios; }

    // Agrega el desarrollador solo si esta disponible
    public boolean agregarDesarrollador(Desarrollador d) {
        if (d.estaDisponible() && !desarrolladores.contains(d)) {
            desarrolladores.add(d);
            calcularValorTotal();
            return true;
        }
        return false;
    }

    public boolean agregarServicio(ServicioAdicional s) {
        if (s.isDisponible() && !servicios.contains(s)) {
            servicios.add(s);
            calcularValorTotal();
            return true;
        }
        return false;
    }

    // tarifas x dias + servicios - descuento (10% si el cliente es frecuente)
    public double calcularValorTotal() {
        double total = 0;
        for (int i = 0; i < desarrolladores.size(); i++) {
            total = total + desarrolladores.get(i).getTarifaPorDia() * diasDesarrollo;
        }
        for (int i = 0; i < servicios.size(); i++) {
            total = total + servicios.get(i).getPrecio();
        }
        if (cliente.esFrecuente()) {
            total = total - total * 0.10;
        }
        valorTotal = total;
        return valorTotal;
    }

    // Confirma el proyecto y actualiza a los desarrolladores
    public boolean confirmar() {
        if (!estado.equals("Pendiente") || desarrolladores.size() == 0) {
            return false;
        }
        estado = "Confirmado";
        for (int i = 0; i < desarrolladores.size(); i++) {
            desarrolladores.get(i).actualizarDisponibilidad();
        }
        return true;
    }

    public String toString() {
        String texto = codigo + " | Cliente: " + cliente.getNombre() + " | Solicitud: " + fechaSolicitud
                + " | Inicio: " + fechaInicio + " | Entrega: " + fechaEntrega + " | Estado: " + estado
                + " | Pago: " + metodoPago + " | Dias: " + diasDesarrollo + " | Valor total: $" + valorTotal;
        texto = texto + "\n   Desarrolladores: ";
        for (int i = 0; i < desarrolladores.size(); i++) {
            texto = texto + desarrolladores.get(i).getCodigo() + " ";
        }
        texto = texto + "\n   Servicios: ";
        for (int i = 0; i < servicios.size(); i++) {
            texto = texto + servicios.get(i).getNombre() + "; ";
        }
        return texto;
    }
}