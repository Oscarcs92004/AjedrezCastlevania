/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
/**
 *
 * @author oscar
 */
public final class GestorUsuarios implements GestorDatos<Usuarios>{
    private static final GestorUsuarios INSTANCIA = new GestorUsuarios();
    
    private GestorUsuarios(){
    }
    
    public static GestorUsuarios getInstancia(){
        return INSTANCIA;
    }
    
    @Override
    public void agregar(Usuarios elemento){
        Usuarios.agregarUsuario(elemento);
    }
    
    @Override
    public ArrayList<Usuarios> obtenerTodos(){
        return Usuarios.getU();
    }
    
    public boolean crearUsuario(String nombre, String contra){
        return Usuarios.crearUsuario(nombre, contra);
    }
    
    public Usuarios iniciarSesion(String nombre, String contra){
        return Usuarios.iniciarSesion(nombre, contra);
    }
}
