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
    
    public Piezas(int vida, int danio, int escudo){
        this.vida = vida;
        this.danio = danio;
        this.escudo = escudo;
    }
    
    public abstract void ataque(); 
    public abstract void movimientoEspecial();
    public abstract void mover();
}
