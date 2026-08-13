/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
public class Zombie extends Piezas{
    
    public Zombie(){
        super(1,1,0);
    }
    
    @Override
    public void ataque(){
    
    }

    @Override
    public void movimientoEspecial(){
        // no tiene se queda en blanco
    }
    
    @Override
    public boolean puedeMoverse(int nuevaFila, int nuevaColumna, Piezas[][] tablero){
        return false;
    }
    
}
