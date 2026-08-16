/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
import java.util.ArrayList;

public final class Historial {
    private static ArrayList<RegistroPartida> registros = new ArrayList<>();
    
    public static void agregarRegistro(RegistroPartida registro){
        registros.add(registro);
    }
    
    public static ArrayList<RegistroPartida> getRegistros(){
        return registros;
    }
    
    public static ArrayList<RegistroPartida> getRegistrosDe(Usuarios jugador){
        ArrayList<RegistroPartida> resultado = new ArrayList<>();
        for (int i = registros.size() - 1; i >= 0; i--) {
            RegistroPartida r = registros.get(i);
            if (r.participo(jugador)) {
                resultado.add(r);
            }
        }
        return resultado;
    }
}
