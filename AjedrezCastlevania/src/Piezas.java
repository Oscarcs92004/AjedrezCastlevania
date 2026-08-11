/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
public abstract class Piezas {
    private int vida;
    private int danio;
    private int escudo;
    private int fila;
    private int columna;
    private int jugador;
    
    public Piezas(int vida, int danio, int escudo){
        this.vida = vida;
        this.danio = danio;
        this.escudo = escudo;
    }
    
    public abstract void ataque(); 
    public abstract void movimientoEspecial();
    
    public boolean puedeMoverse(int nuevaFila, int nuevaColumna, Piezas[][] tablero){
        if (nuevaFila < 0 || nuevaFila >= 6 || nuevaColumna < 0 || nuevaColumna >= 6) {
            return false;
        }
        if (tablero[nuevaFila][nuevaColumna] != null) {
            return false;
        }
        int diferenciaFila = Math.abs(nuevaFila - fila);
        int diferenciaColumna = Math.abs(nuevaColumna - columna);
        return diferenciaFila <= 1 && diferenciaColumna <= 1 && (diferenciaFila + diferenciaColumna > 0);
    }
    
    public int getJugador(){
        return jugador;
    }
    
    public void setJugador(int jugador){
        this.jugador = jugador;
    }
    
    public boolean mover(int nuevaFila, int nuevaColumna, Piezas[][] tablero){
         if (!puedeMoverse(nuevaFila,nuevaColumna,tablero)) {
            return false;
        }
        tablero[fila][columna] = null;
        fila = nuevaFila;
        columna = nuevaColumna;
        tablero[fila][columna] = this;

        return true;
    }
}
