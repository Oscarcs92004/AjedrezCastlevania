/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author oscar
 */
public class Usuarios {
    private static ArrayList<Usuarios> u = new ArrayList<>();
    private String nombre;
    private String contra;
    private Calendar fechaRegistro;
    private int puntos;
    private boolean activo;
    
    public Usuarios(String nombre, String contra){
        this.nombre = nombre;
        this.contra = contra;
        fechaRegistro = Calendar.getInstance();
        puntos = 0;
        activo = true;
    }

    public static ArrayList<Usuarios> getU() {
        return u;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContra() {
        return contra;
    }
    
    public void setContra(String contra){
        this.contra = contra;
    }

    public Calendar getFechaRegistro() {
        return fechaRegistro;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean isActivo() {
        return activo;
    }
    
    public static void agregarUsuario(Usuarios user){
        u.add(user);
    }
    
    private static boolean existeUsuario(String nombre, int indice){
        if (indice >= u.size()) {
            return false;
        }
        if (u.get(indice).getNombre().equalsIgnoreCase(nombre)) {
            return true;
        }
        return existeUsuario(nombre, indice + 1);
    }
    
    public static boolean crearUsuario(String nombre,String contra) {
        if(existeUsuario(nombre,0)){
            return false;
        }
        u.add(new Usuarios(nombre,contra));
        return true;
    }
     
    public static Usuarios iniciarSesion(String user, String contra){
        for(Usuarios usuario : u){
            if(usuario.getNombre().equals(user) && usuario.getContra().equals(contra) && usuario.isActivo()){
                return usuario;
            }
        }
        return null;
    }
    
    public void aumentarPuntos(){
        this.puntos += 3;
    }
    
    public void desactivar(){
        this.activo = false;
    }
}
