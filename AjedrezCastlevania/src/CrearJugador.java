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
    
    private void crearJugador(){
        String usuario = txtUser.getText();
        String contra = new String(txtContra.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());
        
        if(usuario.isEmpty() || contra.isEmpty() || confirmar.isEmpty()){
            javax.swing.JOptionPane.showMessageDialog(this,"Todos los campos son obligatorios.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(!contra.equals(confirmar)){
            javax.swing.JOptionPane.showMessageDialog(this,"Las contraseñas no coinciden.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        javax.swing.JOptionPane.showMessageDialog(this,"Jugador creado correctamente.");
        
        txtUser.setText("");
        txtContra.setText("");
        txtConfirmar.setText("");
    }
    
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
        
        JLabel usuario = new JLabel("Nombre Usuario");
        txtUser = new JTextField();
        txtUser.setPreferredSize(new Dimension(300,40));
        
        limites.gridx = 0;
        limites.gridy = 0;
        formulario.add(usuario, limites);
        limites.gridx = 1;
        formulario.add(txtUser, limites);
        
        JLabel contra = new JLabel("Contraseña: ");
        txtContra = new JPasswordField();
        txtContra.setPreferredSize(new Dimension(300,40));
        limites.gridx = 0;
        limites.gridy = 1;
        formulario.add(contra, limites);
        limites.gridx = 1;
        formulario.add(txtContra, limites);
        
        JLabel confirmar = new JLabel("Confirmar contraseña: ");
        txtConfirmar = new JPasswordField();
        txtConfirmar.setPreferredSize(new Dimension(300,40));
        limites.gridx = 0;
        limites.gridy = 2;
        formulario.add(confirmar, limites);
        limites.gridx = 1;
        formulario.add(txtConfirmar, limites);
     
        crear = new JButton("Crear Jugador");
        regresar = new JButton("Regresar");
        
        crear.setPreferredSize(new Dimension(180,45));
        regresar.setPreferredSize(new Dimension(180,45));
        crear.setFont(new Font("Arial", Font.BOLD, 16));
        regresar.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(crear);
        panelBotones.add(regresar);
        limites.gridx = 0;
        limites.gridy = 3;
        limites.gridwidth = 2;
        formulario.add(panelBotones,limites);
        add(formulario, BorderLayout.CENTER);
        
        regresar.addActionListener(e-> {
            ventana.mostrarMenu();
        });
        
        crear.addActionListener(e-> {
            crearJugador();
        });
    }
    
    public CrearJugador(GUI ventana){
        this.ventana = ventana;
        inicializar();
    }
}
