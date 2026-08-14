/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
public class HombreLobo extends Piezas{
    
    public HombreLobo(){
        super(5,5,2);
    }
    
    @Override
    public void ataque(Piezas objetivo){
        objetivo.recibirDanio(getDanio());
    }

    @Override
    public String movimientoEspecial(Piezas objetivo, int fila, int columna, Piezas[][] tablero){
        return null;
    }
    
    @Override
    public boolean puedeMoverse(int nuevaFila, int nuevaColumna, Piezas[][] tablero){
        if(nuevaFila < 0 || nuevaFila >=6 || nuevaColumna < 0 || nuevaColumna >= 6){
            return false;
        }
        if(tablero[nuevaFila][nuevaColumna] != null){
            return false;
        }
        int diferenciaFila = nuevaFila - getFila();
        int diferenciaColumna = nuevaColumna - getColumna();
        boolean lineaRecta = diferenciaFila == 0 || diferenciaColumna == 0 || Math.abs(diferenciaFila) == Math.abs(diferenciaColumna);
        int distancia = Math.max(Math.abs(diferenciaFila), Math.abs(diferenciaColumna));
        return lineaRecta && distancia >= 1 && distancia <= 2;
    }
}
