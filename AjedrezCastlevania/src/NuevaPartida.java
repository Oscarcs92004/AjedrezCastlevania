
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

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
    private JPanel panelRuleta;
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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Vampire Wargame",SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        add(titulo, BorderLayout.NORTH);
        
        JPanel panelMain = new JPanel(new BorderLayout(10, 10));
        crearPanelRuleta();
        crearPanelTablero();
        crearPanelConsola();
        panelMain.add(panelRuleta,BorderLayout.WEST);
        panelMain.add(panelTablero,BorderLayout.CENTER);
        panelMain.add(panelConsola,BorderLayout.EAST);
        add(panelMain,BorderLayout.CENTER);

        JButton regresar = new JButton("Regresar");
        regresar.setFont(new Font("Arial", Font.BOLD, 16));
        
        regresar.addActionListener(e -> {
            ventana.cambiarPanel(new MenuPrincipal(ventana,jugador1));
        });
        
        add(regresar,BorderLayout.SOUTH);
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
