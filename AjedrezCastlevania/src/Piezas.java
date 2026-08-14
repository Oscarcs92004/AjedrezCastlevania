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
    private int vidaMaxima;
    private int danio;
    private int escudo;
    private int fila;
    private int columna;
    private int jugador;
    
    public Piezas(int vida, int danio, int escudo){
        this.vida = vida;
        this.vidaMaxima = vida;
        this.danio = danio;
        this.escudo = escudo;
    }
    
    public abstract void ataque(Piezas objetivo); 
    public abstract String movimientoEspecial(Piezas objetivo, int fila, int columna, Piezas[][] tablero);
    
    public boolean tieneAtaqueEspecial(){
        return false;
    }
    
    public String nombreAtaqueEspecial(){
        return null;
    }
    
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
    
    protected int getVidaMaxima(){
        return vidaMaxima;
    }
    
    public int getFila(){
        return fila;
    }
    
    public int getColumna(){
        return columna;
    }
    
    public void setPosicion(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }
    
    public int getVida(){
        return vida;
    }
    
    public int getDanio(){
        return danio;
    }
    
    public int getEscudo(){
        return escudo;
    }
    
    public boolean estaViva(){
        return vida > 0;
    }
    
    public void setVida(int vida){
        this.vida = vida;
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
    
    public boolean esAdyacente(int fil, int col){
        int difFila = Math.abs(fil-fila);
        int difColumna = Math.abs(col-columna);
        return difFila <= 1 && difColumna <= 1 && (difFila + difColumna > 0);
    }
    
    public void recibirDanio(int cantidad){
        if(escudo > 0){
            int absorbido = Math.min(escudo, cantidad);
            escudo -= absorbido;
            cantidad -= absorbido;
        }
        if(cantidad > 0){
            vida -= cantidad;
            if(vida < 0){
                vida = 0;
            }
        }
    }
    
    public void recibirDanioDirecto(int cantidad){
        vida -= cantidad;
        if(vida < 0){
            vida = 0;
        }
    }
}
