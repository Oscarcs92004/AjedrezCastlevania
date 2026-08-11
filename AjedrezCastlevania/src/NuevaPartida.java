
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
    private Usuarios jugador1;
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
    private Piezas[][] piezasTablero;
    private ImageIcon imagenHombreLobo;
    private ImageIcon imagenVampiro;
    private ImageIcon imagenNecromante;
    private ImageIcon imagenZombie;


    private ImageIcon obtenerImagen(Piezas pieza,boolean jugador1) {
        boolean blanco = jugador1;
        if (pieza instanceof HombreLobo) {
            return cambiarColor(imagenHombreLobo,blanco);
        } else if (pieza instanceof Vampiro) {
            return cambiarColor(imagenVampiro,blanco);
        } else if (pieza instanceof Necromante) {
            return cambiarColor(imagenNecromante,blanco);
        } else if (pieza instanceof Zombie) {
            return cambiarColor(imagenZombie,blanco);
        }
        return null;
    }
    
    private void actualizarTablero() {

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                JButton casilla = tablero[fila][columna];
                Piezas pieza = piezasTablero[fila][columna];
                if (pieza == null) {
                    casilla.setIcon(null);
                    casilla.setText("");
                } else {
                    casilla.setText("");
                    boolean jugador1 = pieza.getJugador() == 1;
                    ImageIcon imagen =obtenerImagen(pieza,jugador1);
                    casilla.setIcon(imagen);
                }
            }
        }
    }
    
    private void colocarPiezas() {
        piezasTablero[0][0] = new HombreLobo();
        piezasTablero[0][0].setJugador(1);
        piezasTablero[0][1] = new Vampiro();
        piezasTablero[0][1].setJugador(1);
        piezasTablero[0][2] = new Necromante();
        piezasTablero[0][2].setJugador(1);
        piezasTablero[0][3] = new Necromante();
        piezasTablero[0][3].setJugador(1);
        piezasTablero[0][4] = new Vampiro();
        piezasTablero[0][4].setJugador(1);
        piezasTablero[0][5] = new HombreLobo();
        piezasTablero[0][5].setJugador(1);
        piezasTablero[5][0] = new HombreLobo();
        piezasTablero[5][0].setJugador(2);
        piezasTablero[5][1] = new Vampiro();
        piezasTablero[5][1].setJugador(2);
        piezasTablero[5][2] = new Necromante();
        piezasTablero[5][2].setJugador(2);
        piezasTablero[5][3] = new Necromante();
        piezasTablero[5][3].setJugador(2);
        piezasTablero[5][4] = new Vampiro();
        piezasTablero[5][4].setJugador(2);
        piezasTablero[5][5] = new HombreLobo();
        piezasTablero[5][5].setJugador(2);
        
        
        actualizarTablero();
    }

    private ImageIcon cambiarColor( ImageIcon original, boolean blanco) {
        if (original == null) {
            return null;
        }
        Image imagen = original.getImage();
        int ancho = imagen.getWidth(null);
        int alto = imagen.getHeight(null);
        BufferedImage nuevaImagen = new BufferedImage( ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = nuevaImagen.createGraphics();
        g.drawImage( imagen, 0, 0, null);
        g.dispose();
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                int pixel = nuevaImagen.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                if (alpha == 0) {
                    continue;
                }
                Color color = new Color(pixel, true);
                Color nuevoColor;
                if (blanco) {
                    nuevoColor = new Color( 255, 255, 255, alpha);
                } else {
                    nuevoColor = new Color( 0, 0, 0, alpha);
                }
                nuevaImagen.setRGB( x, y, nuevoColor.getRGB());
            }
        }

        return new ImageIcon(nuevaImagen);
        }
        
    
    private ImageIcon cargarImagen(String ruta) {
        try {
            BufferedImage imagen = ImageIO.read(new File(ruta));
            Image imagenEscalada = imagen.getScaledInstance( 60, 60, Image.SCALE_SMOOTH );
            return new ImageIcon(imagenEscalada);
        } catch (IOException e) {
            System.out.println( "No se pudo cargar: " + ruta);
            return null;
        }
    }   
    
    private void cargarImagenes() {

        imagenHombreLobo = cargarImagen("src/Iconos/hombreLobo.png");

        imagenVampiro = cargarImagen("src/Iconos/vampiro.png");

        imagenNecromante = cargarImagen("src/Iconos/necromante.png");

        imagenZombie = cargarImagen("src/Iconos/zombie.png");
    }

    private void seleccionarPieza(int fila, int columna){
    
    }
    
    private void crearPanelTablero(){
        panelTablero = new JPanel(new GridLayout(6, 6));
        panelTablero.setBorder(BorderFactory.createTitledBorder("Tablero"));
        tablero = new JButton[6][6];
        piezasTablero = new Piezas[6][6];
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                JButton casilla = new JButton();
                casilla.setPreferredSize( new Dimension(80, 80));
                final int fil = fila;
                final int col = columna;
                casilla.addActionListener(e -> {
                    seleccionarPieza(fil,col);
                });
                tablero[fila][columna] = casilla;
                panelTablero.add(casilla);
            }
        }
        cargarImagenes();
        colocarPiezas();
        }
    
    private void girarRuleta(){
        
    }
    
    private void crearPanelRuleta(){
        panelRuleta = new JPanel();

        panelRuleta.setPreferredSize(new Dimension(250, 0));
        panelRuleta.setLayout(new BorderLayout(10, 10));
        JLabel titulo = new JLabel("Ruleta",SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        panelRuleta.add(titulo,BorderLayout.NORTH);
        JPanel informacion = new JPanel(new BorderLayout());
        JLabel texto = new JLabel("Pieza seleccionada:",SwingConstants.CENTER);
        texto.setFont(new Font("Arial", Font.BOLD, 16));
        pieza = new JLabel("???",SwingConstants.CENTER);
        pieza.setFont(new Font("Arial", Font.BOLD, 22));
        informacion.add(texto,BorderLayout.NORTH);
        informacion.add(pieza,BorderLayout.CENTER);
        panelRuleta.add(informacion,BorderLayout.CENTER);
        girar = new JButton("Girar ruleta");
        girar.setFont(new Font("Arial", Font.BOLD, 18));
        
        girar.addActionListener(e -> {
            girarRuleta();
        });
        
        panelRuleta.add(girar,BorderLayout.SOUTH);
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
        //crearPanelRuleta();
        crearPanelTablero();
        //crearPanelConsola();
        //panelMain.add(panelRuleta,BorderLayout.WEST);
        panelMain.add(panelTablero,BorderLayout.CENTER);
        //panelMain.add(panelConsola,BorderLayout.EAST);
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
    
    public NuevaPartida(GUI ventana, Usuarios jugador1){
        this.ventana = ventana;
        this.jugador1 = jugador1;
        
        random = new Random();
        inicializar();
        seleccionar();
    }
    
}
