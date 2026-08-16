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
public final class GestorHistorial implements GestorDatos<RegistroPartida>{
    private static final GestorHistorial INSTANCIA = new GestorHistorial();
    
    private GestorHistorial(){
    }
    
    public static GestorHistorial getInstancia(){
        return INSTANCIA;
    }
    
    @Override
    public void agregar(RegistroPartida elemento){
        Historial.agregarRegistro(elemento);
    }
    
    @Override
    public ArrayList<RegistroPartida> obtenerTodos(){
        return Historial.getRegistros();
    }
    
    public ArrayList<RegistroPartida> obtenerDe(Usuarios jugador){
        return Historial.getRegistrosDe(jugador);
    }
}
