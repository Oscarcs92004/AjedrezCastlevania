
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
import javax.swing.JLayeredPane;
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
    private ImageIcon imagenHombreLoboBlanco;
    private ImageIcon imagenVampiroBlanco;
    private ImageIcon imagenNecromanteBlanco;
    private ImageIcon imagenZombieBlanco;
    private ImageIcon imagenHombreLoboNegro;
    private ImageIcon imagenVampiroNegro;
    private ImageIcon imagenNecromanteNegro;
    private ImageIcon imagenZombieNegro;
    private int jugadorActual = 1;
    private JLabel[] iconosRuleta;
    private ImageIcon iconoVampiro;
    private ImageIcon iconoNecromante;
    private ImageIcon iconoHombreLobo;
    private ImageIcon imagenRuleta;
    private ImageIcon iconoVampiroNegro;
    private ImageIcon iconoNecromanteNegro;
    private ImageIcon iconoHombreLoboNegro;

    private ImageIcon convertirNegro(ImageIcon original) {
        if (original == null) {
            return null;
        }
        Image imagen = original.getImage();
        BufferedImage nuevaImagen = new BufferedImage(imagen.getWidth(null),imagen.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = nuevaImagen.createGraphics();
        g2.drawImage(imagen, 0, 0, null);
        g2.dispose();
        for (int x = 0; x < nuevaImagen.getWidth(); x++) {
            for (int y = 0; y < nuevaImagen.getHeight(); y++) {
                int pixel = nuevaImagen.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                if (alpha != 0) {
                    nuevaImagen.setRGB(x,y,(alpha << 24));
                }
            }
        }
        return new ImageIcon(nuevaImagen);
    }
    
    private void cargarImagenesRuleta() {
        imagenRuleta = cargarImagen("src/Iconos/Ruleta.png",300,300);
        iconoVampiro = cargarImagen("src/Iconos/IconoVampiro.png",120,120);
        iconoNecromante = cargarImagen("src/Iconos/IconoNecromante.png",120,120);
        iconoHombreLobo = cargarImagen("src/Iconos/IconoHombreLobo.png",120,120);

        iconoVampiroNegro = convertirNegro(iconoVampiro);
        iconoNecromanteNegro = convertirNegro(iconoNecromante);
        iconoHombreLoboNegro = convertirNegro(iconoHombreLobo);
    }
    
    private ImageIcon obtenerImagen(Piezas pieza,boolean jugador1) {
        
        if (pieza instanceof HombreLobo) {
            return jugador1 ? imagenHombreLoboBlanco : imagenHombreLoboNegro;
            } else if (pieza instanceof Vampiro) {
                return jugador1 ? imagenVampiroBlanco : imagenVampiroNegro;
            } else if (pieza instanceof Necromante) {
                return jugador1 ? imagenNecromanteBlanco : imagenNecromanteNegro;
            } else if (pieza instanceof Zombie) {
                return jugador1 ? imagenZombieBlanco : imagenZombieNegro;
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
                    ImageIcon imagen = obtenerImagen(pieza,jugador1);
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
    
    private ImageIcon cargarImagen(String ruta, int ancho, int alto) {
        try {
            BufferedImage imagen = ImageIO.read(new File(ruta));
            Image imagenEscalada = imagen.getScaledInstance(ancho,alto, Image.SCALE_SMOOTH );
            return new ImageIcon(imagenEscalada);
        } catch (IOException e) {
            System.out.println( "No se pudo cargar: " + ruta);
            return null;
        }
    }   
    
    private void cargarImagenes() {

        imagenHombreLoboBlanco = cargarImagen("src/Iconos/HombreLoboBlanco.png",70,90);
        imagenHombreLoboNegro = cargarImagen("src/Iconos/HombreLoboNegro.png",70,90);
        imagenVampiroBlanco = cargarImagen("src/Iconos/VampiroBlanco.png",70,90);
        imagenVampiroNegro = cargarImagen("src/Iconos/VampiroNegro.png",70,90);
        imagenNecromanteBlanco = cargarImagen("src/Iconos/NecromanteBlanco.png",70,90);
        imagenNecromanteNegro = cargarImagen("src/Iconos/NecromanteNegro.png",70,90);
        imagenZombieBlanco = cargarImagen("src/Iconos/ZombieBlanco.png",70,90);
        imagenZombieNegro = cargarImagen("src/Iconos/ZombieNegro.png",70,90);
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
                if((fila+columna)%2 == 0){
                    casilla.setBackground(Color.WHITE);
                } else {
                    casilla.setBackground(Color.BLACK);
                }
                casilla.setOpaque(true);
                casilla.setBorderPainted(false);
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
    
    private void actualizarRuleta() {
        if (iconosRuleta == null || piezasTablero == null) {
            return;
        }
        int vampiros = 0;
        int necromantes = 0;
        int hombresLobo = 0;

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {

                Piezas p = piezasTablero[fila][columna];

                if (p == null) {
                    continue;
                }

                if (p.getJugador() != jugadorActual) {
                    continue;
                }

                if (p instanceof Vampiro) {
                    vampiros++;
                }

                else if (p instanceof Necromante) {
                    necromantes++;
                }

                else if (p instanceof HombreLobo) {
                    hombresLobo++;
                }
            }
        }
        iconosRuleta[0].setIcon(vampiros >= 1? iconoVampiro: iconoVampiroNegro);
        iconosRuleta[1].setIcon(necromantes >= 2? iconoNecromante: iconoNecromanteNegro);
        iconosRuleta[2].setIcon(hombresLobo >= 1? iconoHombreLobo: iconoHombreLoboNegro);
        iconosRuleta[5].setIcon(vampiros >= 2? iconoVampiro: iconoVampiroNegro);
        iconosRuleta[4].setIcon(necromantes >= 1? iconoNecromante: iconoNecromanteNegro);
        iconosRuleta[3].setIcon(hombresLobo >= 2? iconoHombreLobo: iconoHombreLoboNegro);
        panelRuleta.revalidate();
        panelRuleta.repaint();
    }
    
    private void crearPanelRuleta(){
        panelRuleta = new JPanel(new BorderLayout(5,5));

        panelRuleta.setPreferredSize(new Dimension(330, 400));
        JLabel titulo = new JLabel("Ruleta",SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        panelRuleta.add(titulo,BorderLayout.NORTH);
        cargarImagenesRuleta();
        JLayeredPane ruletaVisual = new JLayeredPane();
        ruletaVisual.setPreferredSize(new Dimension(320,320));
        JLabel fondoRuleta = new JLabel(imagenRuleta);
        fondoRuleta.setBounds(10,10,300,300);
        ruletaVisual.add(fondoRuleta, JLayeredPane.DEFAULT_LAYER);
        iconosRuleta = new JLabel[6];
        
        for (int i = 0; i < 6; i++) {

            iconosRuleta[i] = new JLabel();
            iconosRuleta[i].setHorizontalAlignment(SwingConstants.CENTER);
            iconosRuleta[i].setVerticalAlignment(SwingConstants.CENTER);
            ruletaVisual.add(iconosRuleta[i],JLayeredPane.PALETTE_LAYER);
        }
        iconosRuleta[0].setBounds(105, 60, 55, 55);
        iconosRuleta[1].setBounds(175, 60, 55, 55);
        iconosRuleta[2].setBounds(55, 135, 55, 55);
        iconosRuleta[3].setBounds(215, 135, 55, 55);
        iconosRuleta[4].setBounds(105, 200, 55, 55);
        iconosRuleta[5].setBounds(175, 200, 55, 55);
        
        panelRuleta.add(ruletaVisual, BorderLayout.CENTER);
        
        JPanel informacion = new JPanel(new BorderLayout());
        JLabel texto = new JLabel("Pieza seleccionada: ",SwingConstants.CENTER);
        texto.setFont(new Font("Arial", Font.BOLD, 16));
        pieza = new JLabel("???",SwingConstants.CENTER);
        pieza.setFont(new Font("Arial", Font.BOLD, 22));
        informacion.add(texto,BorderLayout.NORTH);
        informacion.add(pieza,BorderLayout.CENTER);
        girar = new JButton("Girar ruleta");
        girar.setFont(new Font("Arial", Font.BOLD, 18));
        
        girar.addActionListener(e -> {
            girarRuleta();
        });
        
        JPanel parteAbajo = new JPanel(new BorderLayout());
        parteAbajo.add(informacion, BorderLayout.CENTER);
        parteAbajo.add(girar, BorderLayout.SOUTH);
        panelRuleta.add(parteAbajo,BorderLayout.SOUTH);
        actualizarRuleta();
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
        crearPanelTablero();
        crearPanelRuleta();
        //crearPanelConsola();
        panelMain.add(panelRuleta,BorderLayout.WEST);
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
