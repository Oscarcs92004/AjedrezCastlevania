/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
public class Vampiro extends Piezas{
    private static final int danioAbsorcion = 1;
    public Vampiro(){
        super(3,4,5);
    }
    
    @Override
    public void ataque(Piezas objetivo){
        objetivo.recibirDanio(getDanio());
    }

    @Override
    public String movimientoEspecial(Piezas objetivo, int fila, int columna, Piezas[][] tablero){
        if (objetivo == null || !esAdyacente(objetivo.getFila(), objetivo.getColumna())) {
            return null;
        }
        objetivo.recibirDanio(danioAbsorcion);
        curar(danioAbsorcion);
        return "absorbe sangre y recupera " + danioAbsorcion + " punto de vida (vida actual: " + getVida() + ").";
    }
  
    @Override
    public boolean tieneAtaqueEspecial(){
        return true;
    }
    
    @Override
    public String nombreAtaqueEspecial(){
        return "Absorcion de sangre";
    }
    
    public void curar(int cantidad){
        int nuevaVida = Math.min(getVida()+cantidad, getVidaMaxima());
        setVida(nuevaVida);
    }
}
