/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 *
 * @author oscar
 */
public class MenuPrincipal extends JPanel{
    private GUI ventana;
    private String usuario;
    private JButton jugar;
    private JButton miCuenta;
    private JButton reportes;
    private JButton cerrarSesion;
    
    private void inicializar(){
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Vampire Wargame",SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        add(titulo, BorderLayout.NORTH);
        JLabel bienvenida = new JLabel("Bienvenido, " + usuario,SwingConstants.CENTER);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        add(bienvenida, BorderLayout.CENTER);
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4,1,15,15));
        jugar = new JButton("Jugar Vampire Wargame");
        miCuenta = new JButton("Mi cuenta");
        reportes = new JButton("Reportes");
        cerrarSesion = new JButton("Cerrar sesión");
        jugar.setFont(new Font("Arial", Font.BOLD, 18)); 
        miCuenta.setFont(new Font("Arial", Font.BOLD, 18)); 
        reportes.setFont(new Font("Arial", Font.BOLD, 18));
        cerrarSesion.setFont(new Font("Arial", Font.BOLD, 18));
        panelBotones.add(jugar);
        panelBotones.add(miCuenta);
        panelBotones.add(reportes);
        panelBotones.add(cerrarSesion);
        add(panelBotones, BorderLayout.SOUTH);
        jugar.addActionListener(e->{
            //ventana.cambiarPanel(); falta crear clase nueva partida
        });
        
        miCuenta.addActionListener(e->{
            // falta crear clase
        });
        
        reportes.addActionListener(e->{
            // falta crear clase
        });
        
        cerrarSesion.addActionListener(e->{
            ventana.mostrarMenu();
        });
    }
    
    public MenuPrincipal(GUI ventana, String usuario){
        this.ventana = ventana;
        this.usuario = usuario;
        
        inicializar();
    }
}
