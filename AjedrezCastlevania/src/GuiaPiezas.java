/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public final class GuiaPiezas extends JPanel{
    private GUI ventana;
    
    private ImageIcon cargarImagen(String ruta, int ancho, int alto){
        try {
            BufferedImage imagen = ImageIO.read(new File(ruta));
            Image escalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        } catch (IOException e) {
            return null;
        }
    }
    
    private JPanel crearTarjetaPieza(String rutaIcono, String nombre, Piezas pieza, String habilidades){
        JPanel tarjeta = new JPanel(new BorderLayout(15, 10));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),BorderFactory.createEmptyBorder(12, 15, 12, 15)));
        
        JLabel icono = new JLabel(cargarImagen(rutaIcono, 70, 90));
        icono.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(icono, BorderLayout.WEST);
        
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        
        JLabel titulo = new JLabel(nombre);
        titulo.setFont(new Font("Serif", Font.BOLD, 20));
        titulo.setAlignmentX(0f);

        JLabel stats = new JLabel("Vida: " + pieza.getVida() + "     Daño: " + pieza.getDanio() + "     Escudo: " + pieza.getEscudo());
        stats.setFont(new Font("Arial", Font.BOLD, 14));
        stats.setAlignmentX(0f);
        
        JTextArea descripcion = new JTextArea(habilidades);
        descripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        descripcion.setEditable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setOpaque(false);
        descripcion.setFocusable(false);
        descripcion.setAlignmentX(0f);
        
        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(4));
        contenido.add(stats);
        contenido.add(Box.createVerticalStrut(6));
        contenido.add(descripcion);
        
        tarjeta.add(contenido, BorderLayout.CENTER);
        return tarjeta;
    }
    
    private void inicializar(){
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titulo = new JLabel("Guía de piezas", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        add(titulo, BorderLayout.NORTH);
        
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        contenedor.add(crearTarjetaPieza("src/Iconos/VampiroBlanco.png", "Vampiro", new Vampiro(), "Se mueve 1 casilla vacía en cualquier dirección. Al atacar a un enemigo " + "adyacente puede elegir entre el ataque normal o su habilidad especial, " + "Absorción de sangre: resta solo 1 punto de vida (respetando el escudo del " + "objetivo) y recupera ese punto para sí mismo."));
        contenedor.add(Box.createVerticalStrut(15));
        
        contenedor.add(crearTarjetaPieza("src/Iconos/HombreLoboBlanco.png", "Hombre Lobo", new HombreLobo(), "Su habilidad especial es el movimiento: en vez de una sola casilla, puede " + "avanzar hasta 2 casillas vacías en línea recta (horizontal, vertical o " + "diagonal). Ataca de forma normal contra cualquier enemigo adyacente."));
        contenedor.add(Box.createVerticalStrut(15));
        
        contenedor.add(crearTarjetaPieza("src/Iconos/NecromanteBlanco.png", "Necrómante", new Necromante(), "La pieza más versátil, con tres habilidades según la distancia al objetivo: " + "lanza a distancia (ataca a 2 casillas en línea recta con camino libre, " + "ignora el escudo pero hace la mitad de daño), invocar un Zombie propio en " + "cualquier casilla vacía del tablero, y ordenar un ataque a través de un " + "Zombie propio contra un enemigo fuera de su alcance normal."));
        contenedor.add(Box.createVerticalStrut(15));
        
        contenedor.add(crearTarjetaPieza("src/Iconos/ZombieBlanco.png", "Zombie", new Zombie(), "La pieza más débil: solo 1 punto de vida y sin escudo, así que se destruye " + "con un solo golpe. No se mueve por sí mismo ni participa en la ruleta; " + "únicamente entra en combate cuando el Necrómante ordena un ataque a través " + "de él. Sirve como obstáculo táctico, pero el rival igual debe destruirlo " + "para ganar la partida."));
        
        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
        
        JButton regresar = new JButton("Regresar");
        regresar.setFont(new Font("Arial", Font.BOLD, 16));
        regresar.addActionListener(e -> ventana.mostrarMenu());
        add(regresar, BorderLayout.SOUTH);
    }
    
    public GuiaPiezas(GUI ventana){
        this.ventana = ventana;
        inicializar();
    }
}
