
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
public class NuevaPartida extends JPanel{
    private GUI ventana;
    private String jugador1;
    private String jugador2;
    private JPale panelRuleta;
    private JPanel panelTablero;
    private JPanel panelConsola;
    private JButton[][] tablero;
    private JLabel pieza;
    private JButton girar;
    private JTextArea consola;
    private String[] piezas = {"Vampiro", "Necromante", "Hombre Lobo", "Zombie"};
    private Random random;
    
    private void seleccionarPieza(){
    
    }
    
    private void crearPanelTablero(){
    
    }
    
    private void girarRuleta(){
    
    }
    
    private void crearPanelRuleta(){
    
    }
    
    private void escribir(String mensaje){
        
    }
    
    private void seleccionOponente(){
        
    }
    
    private void crearPanelConsola(){
        
    }
    
    private void inicializar(){
    
    }
    
    private void seleccionar(){
    
    }
    
    public NuevaPartida(GUI ventana, String jugador1){
        this.ventana = ventana;
        this.jugador1 = jugador1;
        
        random = new Random();
        inicializar();
        seleccionar();
    }
    
}
