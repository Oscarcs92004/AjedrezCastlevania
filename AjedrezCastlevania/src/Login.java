
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
public class Login extends JPanel{
    private GUI ventana;
    private JLabel titulo;
    private JLabel usuario;
    private JLabel contra;
    private JTextField txtUser;
    private JPasswordField txtContra;
    private JButton iniciar;
    private JButton regresar;

    private void iniciarSesion(){
        String user = txtUser.getText().trim();

        String contraseña = new String(txtContra.getPassword());
        if (user.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog( this,"Ingrese usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Usuarios usuarioEncontrado = Usuarios.iniciarSesion(user, contraseña);
        if (usuarioEncontrado != null) {
            JOptionPane.showMessageDialog( this, "Inicio de sesión exitoso.");
            ventana.cambiarPanel(new MenuPrincipal(ventana,usuarioEncontrado));
        } else {
            JOptionPane.showMessageDialog( this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    private void inicializarComponentes(){
        setLayout(new BorderLayout());
        
        titulo = new JLabel("Iniciar Sesion", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        add(titulo,BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(4,2,15,15));
        JPanel panelContra = new JPanel(new BorderLayout());
        JButton mostrar = new JButton("👁");
        usuario = new JLabel("Usuario: ");
        contra = new JLabel("Contraseña: ");
                
        txtUser = new JTextField(15);
        txtContra = new JPasswordField(15);
        
        iniciar = new JButton("Iniciar sesion");
        
        regresar = new JButton("Regresar");
        
        panelContra.add(txtContra, BorderLayout.CENTER);
        panelContra.add(mostrar, BorderLayout.EAST);
        centro.add(usuario);
        centro.add(txtUser);
        centro.add(contra);
        centro.add(panelContra);
        centro.add(iniciar);
        centro.add(regresar);
        
        add(centro, BorderLayout.CENTER);
        
        regresar.addActionListener(e -> {
            ventana.mostrarMenu();
        });
        
        char caracterOculto = txtContra.getEchoChar();
        
        mostrar.addActionListener(e -> {
            if(txtContra.getEchoChar() == (char)0){
                txtContra.setEchoChar(caracterOculto);
            } else {
                txtContra.setEchoChar((char)0);
            }
        });
        
        iniciar.addActionListener(e -> {
            iniciarSesion();
        });
            
    }
    
    public Login(GUI ventana){
        this.ventana = ventana;
        inicializarComponentes();
    }
}
