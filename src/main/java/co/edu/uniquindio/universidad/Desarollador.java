package co.edu.uniquindio.universidad;
public class Desarrollador {
    private String codigo;
    private String equipoTrabajo;
    private String nivel;            // Junior, Semisenior, Senior
    private int maxProyectos;        // maximo de proyectos simultaneos
    private double tarifaPorDia;
    private String estado;           // Disponible, Asignado, Ocupado, En capacitacion
    private int proyectosActivos;

    public Desarrollador(String codigo, String equipoTrabajo, String nivel, int maxProyectos, double tarifaPorDia, String estado) {
        this.codigo = codigo;
        this.equipoTrabajo = equipoTrabajo;
        this.nivel = nivel;
        this.maxProyectos = maxProyectos;
        this.tarifaPorDia = tarifaPorDia;
        this.estado = estado;
        this.proyectosActivos = 0;
    }

    public String getCodigo() { return codigo; }
    public String getEquipoTrabajo() { return equipoTrabajo; }
    public String getNivel() { return nivel; }
    public int getMaxProyectos() { return maxProyectos; }
    public double getTarifaPorDia() { return tarifaPorDia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getProyectosActivos() { return proyectosActivos; }

    // Revisa si el desarrollador puede tomar un proyecto mas
    public boolean estaDisponible() {
        if (estado.equals("Ocupado") || estado.equals("En capacitacion")) {
            return false;
        }
        if (proyectosActivos >= maxProyectos) {
            return false;
        }
        return true;
    }

    // Se llama cuando el proyecto se confirma
    public void actualizarDisponibilidad() {
        proyectosActivos++;
        if (proyectosActivos >= maxProyectos) {
            estado = "Ocupado";
        } else {
            estado = "Asignado";
        }
    }

    public String toString() {
        return codigo + " | Equipo: " + equipoTrabajo + " | Nivel: " + nivel + " | Max proyectos: " + maxProyectos
                + " | Tarifa/dia: $" + tarifaPorDia + " | Estado: " + estado;
    }
}