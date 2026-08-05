/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 *
 * @author oscar
 */
public class GUI extends JFrame{
    
    private JButton iniciar;
    private JButton crearJugador;
    private JButton salir;
    private JLabel titulo;
    private JPanel panel;
    
    public void mostrarMenu(){
        dispose();
        new GUI();
    }
    
    public void cambiarPanel(JPanel nuevoPanel){
        getContentPane().removeAll();
        panel = nuevoPanel;
        add(nuevoPanel);
        revalidate();
        repaint();
    }
    
    private void inicializarComponentes(){
        setTitle("Vampire Wargame");
        setSize(1200,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        titulo = new JLabel("Vampire Wargame", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        panel.add(titulo, BorderLayout.NORTH);
        
        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(3,1,20,20));
        iniciar = new JButton("Iniciar sesion");
        salir = new JButton("Salir");
        crearJugador = new JButton("Crear Jugador");
        
        iniciar.setFont(new Font("Arial",Font.BOLD, 18));
        salir.setFont(new Font("Arial",Font.BOLD, 18));
        crearJugador.setFont(new Font("Arial",Font.BOLD, 18));
        
        botones.add(iniciar);
        botones.add(crearJugador);
        botones.add(salir);
        
        panel.add(botones);
        add(panel);
        salir.addActionListener(e -> System.exit(0));
        
        iniciar.addActionListener(e-> {
            cambiarPanel(new Login(this));
        });
        
        crearJugador.addActionListener(e-> {
            cambiarPanel(new CrearJugador(this));
        });
        
        setVisible(true);        
    }
    
    public GUI(){
        inicializarComponentes();
    }
}
