package co.edu.uniquindio.universidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Desarrollador {
    private String codigo;
    private String equipoTrabajo;
    private NivelDesarrollador nivel;
    private int maxProyectosSimultaneos;
    private double tarifaPorDia;
    private EstadoDesarrollador estado;
    private List<Proyecto> proyectos;

    public Desarrollador(String codigo, String equipoTrabajo, NivelDesarrollador nivel,
                         int maxProyectosSimultaneos, double tarifaPorDia, EstadoDesarrollador estado) {
        this.codigo = codigo;
        this.equipoTrabajo = equipoTrabajo;
        this.nivel = nivel;
        this.maxProyectosSimultaneos = maxProyectosSimultaneos;
        this.tarifaPorDia = tarifaPorDia;
        this.estado = estado;
        this.proyectos = new ArrayList<>();
    }

    /**
     * Valida que el desarrollador pueda trabajar en el rango de fechas: no debe estar en
     * capacitación y la cantidad de proyectos activos (confirmados o en curso) que se cruzan
     * con esas fechas debe ser menor al máximo de proyectos simultáneos.
     */
    public boolean estaDisponible(LocalDate inicio, LocalDate fin) {
        if (estado == EstadoDesarrollador.EN_CAPACITACION) {
            return false;
        }
        int cruzados = 0;
        for (Proyecto p : proyectos) {
            boolean activo = p.getEstado() == EstadoProyecto.CONFIRMADO || p.getEstado() == EstadoProyecto.EN_CURSO;
            boolean seCruza = !p.getFechaInicio().isAfter(fin) && !p.getFechaEntrega().isBefore(inicio);
            if (activo && seCruza) {
                cruzados++;
            }
        }
        return cruzados < maxProyectosSimultaneos;
    }

    public void agregarProyecto(Proyecto proyecto) {
        if (!proyectos.contains(proyecto)) {
            proyectos.add(proyecto);
        }
    }

    /** Recalcula el estado según los proyectos activos (confirmados o en curso). */
    public void actualizarDisponibilidad() {
        if (estado == EstadoDesarrollador.EN_CAPACITACION) {
            return;
        }
        int activos = 0;
        for (Proyecto p : proyectos) {
            if (p.getEstado() == EstadoProyecto.CONFIRMADO || p.getEstado() == EstadoProyecto.EN_CURSO) {
                activos++;
            }
        }
        if (activos == 0) {
            estado = EstadoDesarrollador.DISPONIBLE;
        } else if (activos >= maxProyectosSimultaneos) {
            estado = EstadoDesarrollador.OCUPADO;
        } else {
            estado = EstadoDesarrollador.ASIGNADO;
        }
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getEquipoTrabajo() { return equipoTrabajo; }
    public void setEquipoTrabajo(String equipoTrabajo) { this.equipoTrabajo = equipoTrabajo; }
    public NivelDesarrollador getNivel() { return nivel; }
    public void setNivel(NivelDesarrollador nivel) { this.nivel = nivel; }
    public int getMaxProyectosSimultaneos() { return maxProyectosSimultaneos; }
    public void setMaxProyectosSimultaneos(int max) { this.maxProyectosSimultaneos = max; }
    public double getTarifaPorDia() { return tarifaPorDia; }
    public void setTarifaPorDia(double tarifaPorDia) { this.tarifaPorDia = tarifaPorDia; }
    public EstadoDesarrollador getEstado() { return estado; }
    public void setEstado(EstadoDesarrollador estado) { this.estado = estado; }
    public List<Proyecto> getProyectos() { return proyectos; }

    @Override
    public String toString() {
        return String.format("[%s] Equipo: %s | Nivel: %s | Máx. simultáneos: %d | Tarifa/día: $%,.0f | Estado: %s",
                codigo, equipoTrabajo, nivel, maxProyectosSimultaneos, tarifaPorDia, estado);
    }
}