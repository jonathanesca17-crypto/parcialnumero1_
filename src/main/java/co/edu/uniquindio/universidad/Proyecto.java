package co.edu.uniquindio.universidad;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    public static final double DESCUENTO_CLIENTE_FRECUENTE = 0.10;

    private String codigo;
    private LocalDate fechaSolicitud;
    private LocalDate fechaInicio;
    private LocalDate fechaEntrega;
    private EstadoProyecto estado;
    private MetodoPago metodoPago;
    private double valorTotal;
    private Cliente cliente;
    private List<Desarrollador> desarrolladores;
    private List<ServicioAdicional> servicios;

    public Proyecto(String codigo, LocalDate fechaSolicitud, LocalDate fechaInicio, LocalDate fechaEntrega,
                    MetodoPago metodoPago, Cliente cliente) {
        if (fechaEntrega.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser anterior a la fecha de inicio.");
        }
        this.codigo = codigo;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaInicio = fechaInicio;
        this.fechaEntrega = fechaEntrega;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
        this.estado = EstadoProyecto.PENDIENTE;
        this.valorTotal = 0;
        this.desarrolladores = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    /** Agrega un desarrollador validando que esté disponible en las fechas del proyecto. */
    public boolean agregarDesarrollador(Desarrollador desarrollador) {
        if (estado == EstadoProyecto.FINALIZADO || estado == EstadoProyecto.CANCELADO) {
            return false;
        }
        if (desarrolladores.contains(desarrollador)) {
            return false;
        }
        if (!desarrollador.estaDisponible(fechaInicio, fechaEntrega)) {
            return false;
        }
        desarrolladores.add(desarrollador);
        desarrollador.agregarProyecto(this);
        calcularValorTotal();
        return true;
    }

    /** Asocia un servicio adicional al proyecto y suma su costo al valor total. */
    public boolean agregarServicio(ServicioAdicional servicio) {
        if (!servicio.isDisponible() || servicios.contains(servicio)) {
            return false;
        }
        if (estado == EstadoProyecto.FINALIZADO || estado == EstadoProyecto.CANCELADO) {
            return false;
        }
        servicios.add(servicio);
        calcularValorTotal();
        return true;
    }

    /** Cantidad de días de desarrollo (inclusive). */
    public long calcularDias() {
        return ChronoUnit.DAYS.between(fechaInicio, fechaEntrega) + 1;
    }

    /** valor = (suma de tarifas de los desarrolladores x días) + servicios - descuento cliente frecuente. */
    public double calcularValorTotal() {
        double sumaTarifas = 0;
        for (Desarrollador d : desarrolladores) {
            sumaTarifas += d.getTarifaPorDia();
        }
        double subtotal = sumaTarifas * calcularDias();
        for (ServicioAdicional s : servicios) {
            subtotal += s.getPrecio();
        }
        if (cliente.esFrecuente()) {
            subtotal -= subtotal * DESCUENTO_CLIENTE_FRECUENTE;
        }
        valorTotal = subtotal;
        return valorTotal;
    }

    private boolean transicionValida(EstadoProyecto nuevo) {
        switch (estado) {
            case PENDIENTE:
                return nuevo == EstadoProyecto.CONFIRMADO || nuevo == EstadoProyecto.CANCELADO;
            case CONFIRMADO:
                return nuevo == EstadoProyecto.EN_CURSO || nuevo == EstadoProyecto.CANCELADO;
            case EN_CURSO:
                return nuevo == EstadoProyecto.FINALIZADO || nuevo == EstadoProyecto.CANCELADO;
            default:
                return false;
        }
    }

    /** Cambia el estado respetando el flujo y actualiza la disponibilidad de los desarrolladores. */
    public boolean cambiarEstado(EstadoProyecto nuevo) {
        if (!transicionValida(nuevo)) {
            return false;
        }
        if (nuevo == EstadoProyecto.CONFIRMADO && !puedeConfirmarse()) {
            return false;
        }
        estado = nuevo;
        for (Desarrollador d : desarrolladores) {
            d.actualizarDisponibilidad();
        }
        calcularValorTotal();
        return true;
    }

    /** Confirma el proyecto: requiere al menos un desarrollador, todos disponibles en las fechas. */
    public boolean confirmar() {
        return cambiarEstado(EstadoProyecto.CONFIRMADO);
    }

    private boolean puedeConfirmarse() {
        if (desarrolladores.isEmpty()) {
            return false;
        }
        for (Desarrollador d : desarrolladores) {
            if (!d.estaDisponible(fechaInicio, fechaEntrega)) {
                return false;
            }
        }
        return true;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public EstadoProyecto getEstado() { return estado; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    public double getValorTotal() { return valorTotal; }
    public Cliente getCliente() { return cliente; }
    public List<Desarrollador> getDesarrolladores() { return desarrolladores; }
    public List<ServicioAdicional> getServicios() { return servicios; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s] Cliente: %s | Solicitud: %s | Inicio: %s | Entrega: %s (%d días)%n",
                codigo, cliente.getNombreCompleto(), fechaSolicitud, fechaInicio, fechaEntrega, calcularDias()));
        sb.append(String.format("    Estado: %s | Pago: %s | Valor total: $%,.0f%n", estado, metodoPago, valorTotal));
        sb.append("    Desarrolladores: ");
        if (desarrolladores.isEmpty()) {
            sb.append("(ninguno)");
        }
        for (Desarrollador d : desarrolladores) {
            sb.append(d.getCodigo()).append(" ");
        }
        sb.append("\n    Servicios: ");
        if (servicios.isEmpty()) {
            sb.append("(ninguno)");
        }
        for (ServicioAdicional s : servicios) {
            sb.append(s.getNombre()).append("; ");
        }
        return sb.toString();
    }
}