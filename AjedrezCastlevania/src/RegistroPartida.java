/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */

import java.util.Calendar;

public class RegistroPartida {
    private Usuarios jugador1;
    private Usuarios jugador2;
    private Usuarios ganador;
    private Usuarios perdedor;
    private String tipoFinalizacion;
    private Calendar fecha;
    
    public RegistroPartida(Usuarios jugador1, Usuarios jugador2, Usuarios ganador, Usuarios perdedor, String tipoFinalizacion){
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.ganador = ganador;
        this.perdedor = perdedor;
        this.tipoFinalizacion = tipoFinalizacion;
        this.fecha = Calendar.getInstance();
    }
    
    public boolean participo(Usuarios jugador){
        return jugador1 == jugador || jugador2 == jugador;
    }
    
    public Usuarios getGanador(){
        return ganador;
    }
    
    public Usuarios getPerdedor(){
        return perdedor;
    }
    
    public String getTipoFinalizacion(){
        return tipoFinalizacion;
    }
    
    public Calendar getFecha(){
        return fecha;
    }
    

    public String getMensaje(){
        if ("RETIRO".equals(tipoFinalizacion)) {
            return perdedor.getNombre() + " se ha retirado. ¡Felicidades, " + ganador.getNombre() + ", has ganado 3 puntos!";
        }
        return ganador.getNombre() + " venció a " + perdedor.getNombre() + ". ¡Felicidades, has ganado 3 puntos!";
    }
}
