/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author oscar
 */
public class CrearJugador extends JPanel{
    private GUI ventana;
    private JTextField txtUser;
    private JPasswordField txtContra;
    private JPasswordField txtConfirmar;
    private JButton crear;
    private JButton regresar;
    
    private void inicializar(){
        setLayout(new BorderLayout());
    
        JLabel titulo = new JLabel("Crear Jugador", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 35));
        add(titulo, BorderLayout.NORTH);
        
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createEmptyBorder(50,300,50,300));
        GridBagConstraints  limites = new GridBagConstraints();
        
        limites.insets = new Insets(10,10,10,10);
        limites.fill = GridBagConstraints.HORIZONTAL;
        
        
    }
    
    public CrearJugador(GUI ventana){
        this.ventana = ventana;
        inicializar();
    }
}
