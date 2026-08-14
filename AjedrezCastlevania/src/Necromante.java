/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
public class Necromante extends Piezas{
    private static final int danioLanza = 2;
    private static final int danioZombie = 1;
    
    public Necromante(){
        super(4,3,1);
    }
    
    @Override
    public void ataque(Piezas objetivo){
        objetivo.recibirDanio(getDanio());
    }

    private boolean esLineaRectaADistancia2(int fila, int columna){
        int diferenciaFila = fila - getFila();
        int diferenciaColumna = columna - getColumna();
        return (diferenciaFila == 0 && Math.abs(diferenciaColumna) == 2) || (diferenciaColumna == 0 && Math.abs(diferenciaFila) == 2);
    }
    
    private boolean caminoLibre(int fila, int columna, Piezas[][] tablero){
        int filaIntermedia = getFila() + Integer.signum(fila - getFila());
        int columnaIntermedia = getColumna() + Integer.signum(columna - getColumna());
        return tablero[filaIntermedia][columnaIntermedia] == null;
    }
    
    private Piezas buscarZombiePropioAdyacente(int fila, int columna, Piezas[][] tablero){
        for (int fil = 0; fil < 6; fil++) {
            for (int col = 0; col < 6; col++) {
                Piezas p = tablero[fil][col];
                if (p instanceof Zombie && p.getJugador() == getJugador() && p.esAdyacente(fila, columna)) {
                    return p;
                }
            }
        }
        return null;
    }
    
    @Override
    public String movimientoEspecial(Piezas objetivo, int fila, int columna, Piezas[][] tablero){ 
        if(objetivo != null){
            if(esAdyacente(objetivo.getFila(), objetivo.getColumna())){
                return null;
            }
            if (esLineaRectaADistancia2(objetivo.getFila(), objetivo.getColumna())
                    && caminoLibre(objetivo.getFila(), objetivo.getColumna(), tablero)) {
                objetivo.recibirDanioDirecto(danioLanza);
                return "lanza su lanza (ignora el escudo) y le quita " + danioLanza + " puntos de vida.";
            }
            Piezas zombiePropio = buscarZombiePropioAdyacente(objetivo.getFila(), objetivo.getColumna(), tablero);
            if (zombiePropio != null) {
                objetivo.recibirDanio(danioZombie);
                return "ordena a su Zombie atacar y le quita " + danioZombie + " punto de vida.";
            }
            return null;
        }
        if (fila < 0 || fila >= 6 || columna < 0 || columna >= 6 || tablero[fila][columna] != null) {
            return null;
        }
        Zombie zombie = new Zombie();
        zombie.setJugador(getJugador());
        zombie.setPosicion(fila, columna);
        tablero[fila][columna] = zombie;
        return "invoca un Zombie.";
    }
    
    @Override
    public boolean tieneAtaqueEspecial(){
        return true;
    }
    
    @Override 
    public String nombreAtaqueEspecial(){
        return "Ataque a distancia (lanza / Zombie)";
    }
}
