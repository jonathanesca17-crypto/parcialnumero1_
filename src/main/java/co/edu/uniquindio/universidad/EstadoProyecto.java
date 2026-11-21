package co.edu.uniquindio.universidad;

public enum EstadoProyecto {
    PENDIENTE("Pendiente"),
    CONFIRMADO("Confirmado"),
    EN_CURSO("En curso"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String etiqueta;

    EstadoProyecto(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}