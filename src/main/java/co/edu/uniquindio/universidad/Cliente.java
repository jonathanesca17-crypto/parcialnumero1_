package co.edu.uniquindio.universidad;
import java.util.ArrayList;

public class Cliente {
    private String nombre;
    private String documento;
    private String telefono;
    private String correo;
    private String pais;
    private ArrayList<Proyecto> proyectos;

    public Cliente(String nombre, String documento, String telefono, String correo, String pais) {
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
        this.pais = pais;
        this.proyectos = new ArrayList<Proyecto>();
    }

    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getPais() { return pais; }
    public ArrayList<Proyecto> getProyectos() { return proyectos; }

    public void agregarProyecto(Proyecto p) {
        proyectos.add(p);
    }

    // Cliente frecuente: tiene 3 o mas proyectos
    public boolean esFrecuente() {
        return proyectos.size() >= 3;
    }

    public String toString() {
        return nombre + " | Documento: " + documento + " | Tel: " + telefono + " | Correo: " + correo
                + " | Pais: " + pais + " | Proyectos: " + proyectos.size();
    }
}