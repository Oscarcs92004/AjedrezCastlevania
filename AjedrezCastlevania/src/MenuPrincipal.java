/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 *
 * @author oscar
 */
public class MenuPrincipal extends JPanel{
    private GUI ventana;
    private Usuarios usuario;
    private JButton jugar;
    private JButton miCuenta;
    private JButton reportes;
    private JButton cerrarSesion;
    
    private Usuarios seleccionarOponente(){
        ArrayList<Usuarios> todos = GestorUsuarios.getInstancia().obtenerTodos();
        ArrayList<Usuarios> disponibles = new ArrayList<>();
        for (Usuarios u : todos) {
            if (u != usuario && u.isActivo()) {
                disponibles.add(u);
            }
        }
        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay otro jugador registrado con quien jugar.","Sin oponentes disponibles", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        String[] nombres = new String[disponibles.size()];
        for (int i = 0; i < disponibles.size(); i++) {
            nombres[i] = disponibles.get(i).getNombre();
        }
        JComboBox<String> comboOponentes = new JComboBox<>(nombres);
        int resultado = JOptionPane.showConfirmDialog(this, comboOponentes, "Selecciona tu oponente (Jugador 2)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) {
            return null;
        }
        return disponibles.get(comboOponentes.getSelectedIndex());
    }
    
    private void inicializar(){
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Vampire Wargame",SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        add(titulo, BorderLayout.NORTH);
        JLabel bienvenida = new JLabel("Bienvenido, " + usuario.getNombre(),SwingConstants.CENTER);
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
            Usuarios oponente  = seleccionarOponente();
            if(oponente == null){
                return;
            }
            if(oponente == usuario){
                JOptionPane.showMessageDialog(this, "No puedes seleccionarte a ti mismo como oponente.","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ventana.cambiarPanel(new NuevaPartida(ventana,usuario,oponente));
        });
        
        miCuenta.addActionListener(e->{
            ventana.cambiarPanel(new CuentaJugador(ventana, usuario));
        });
        
        reportes.addActionListener(e->{
            ventana.cambiarPanel(new Reportes(ventana, usuario));
        });
        
        cerrarSesion.addActionListener(e->{
            ventana.mostrarMenu();
        });
    }
    
    public MenuPrincipal(GUI ventana, Usuarios usuario){
        this.ventana = ventana;
        this.usuario = usuario;
        
        inicializar();
    }
}
