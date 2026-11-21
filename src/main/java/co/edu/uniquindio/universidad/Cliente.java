package co.edu.uniquindio.universidad;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    // Cantidad de proyectos a partir de la cual un cliente se considera frecuente
    public static final int PROYECTOS_PARA_SER_FRECUENTE = 3;

    private String nombreCompleto;
    private String documento;
    private String telefono;
    private String correo;
    private String pais;
    private List<Proyecto> proyectos;

    public Cliente(String nombreCompleto, String documento, String telefono, String correo, String pais) {
        this.nombreCompleto = nombreCompleto;
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
        this.pais = pais;
        this.proyectos = new ArrayList<>();
    }

    public void agregarProyecto(Proyecto proyecto) {
        if (!proyectos.contains(proyecto)) {
            proyectos.add(proyecto);
        }
    }

    /** Un cliente es frecuente si ya tiene contratados al menos 3 proyectos (sin contar cancelados). */
    public boolean esFrecuente() {
        int contratados = 0;
        for (Proyecto p : proyectos) {
            if (p.getEstado() != EstadoProyecto.CANCELADO) {
                contratados++;
            }
        }
        return contratados >= PROYECTOS_PARA_SER_FRECUENTE;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public List<Proyecto> getProyectos() { return proyectos; }

    @Override
    public String toString() {
        return String.format("%s | Doc/NIT: %s | Tel: %s | %s | %s | Proyectos: %d%s",
                nombreCompleto, documento, telefono, correo, pais, proyectos.size(),
                esFrecuente() ? " | CLIENTE FRECUENTE" : "");
    }
}