
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
import javax.swing.Timer;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JComboBox;

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
    private Usuarios jugador2;
    private JPanel panelRuleta;
    private JPanel panelTablero;
    private JPanel panelConsola;
    private JButton[][] tablero;
    private JLabel pieza;
    private JButton girar;
    private JTextArea consola;
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
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    private String tipoPermitido = null;
    private boolean juegoTerminado = false;
    private int girosUsados = 0;
    private int[] piezasPerdidas = new int[3];
    private JLayeredPane ruletaVisual;
    private JLabel fondoRuleta;
    private Timer timerRuleta;
    private double anguloRuleta = 0;
    private boolean girandoRuleta = false;
    private int posicionResultado = -1;
    private Piezas[] piezasRuletaJugador1 = new Piezas[6];
    private Piezas[] piezasRuletaJugador2 = new Piezas[6];
    private Piezas piezaPermitida;
    
    private Piezas[] obtenerRuletaActual() {
        return jugadorActual == 1 ? piezasRuletaJugador1 : piezasRuletaJugador2;
    }
    
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
    
    private void asignarPiezasRuleta() {
        piezasRuletaJugador1 = new Piezas[6];
        piezasRuletaJugador2 = new Piezas[6];
        int[] contadorJ1 = new int[3];
        int[] contadorJ2 = new int[3];
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                Piezas p = piezasTablero[fila][columna];
                if (p == null) {
                    continue;
                }
                int jugador = p.getJugador();
                int[] contador;
                if (jugador == 1) {
                    contador = contadorJ1;
                } else {
                    contador = contadorJ2;
                }
                int tipo;
                if (p instanceof Vampiro) {
                    tipo = 0;
                } else if (p instanceof Necromante) {
                    tipo = 1;
                } else if (p instanceof HombreLobo) {
                    tipo = 2;
                } else {
                    continue;
                }
                int posicion;
                if (contador[tipo] == 0) {
                    if (tipo == 0) {
                        posicion = 0;
                    } else if (tipo == 1) {
                        posicion = 1;
                    } else {
                        posicion = 2;
                    }
                } else {
                    if (tipo == 0) {
                        posicion = 5;
                    } else if (tipo == 1) {
                        posicion = 4;
                    } else {
                        posicion = 3;
                    }
                }
                if (jugador == 1) {
                    piezasRuletaJugador1[posicion] = p;
                } else {
                    piezasRuletaJugador2[posicion] = p;
                }
                contador[tipo]++;
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
        
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if (piezasTablero[fila][columna] != null) {
                    piezasTablero[fila][columna].setPosicion(fila, columna);
                }
            }
        }
        
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

    private String nombreTipo(Piezas pieza){
        if (pieza instanceof HombreLobo) {
            return "Hombre Lobo";
        } else if (pieza instanceof Vampiro) {
            return "Vampiro";
        } else if (pieza instanceof Necromante) {
            return "Necromante";
        } else if (pieza instanceof Zombie) {
            return "Zombie";
        }
        return "";
    }
    
    private String nombreJugador(int numeroJugador){
        if (numeroJugador == 1) {
            return jugador1.getNombre();
        }
        return jugador2.getNombre();
    }
    
    
    private void limpiarResaltados(){
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                tablero[fila][columna].setBorder(null);
                tablero[fila][columna].setBorderPainted(false);
            }
        }
    }
    
    private void resaltarDestinos(Piezas pieza){
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if (pieza.puedeMoverse(fila, columna, piezasTablero)) {
                    tablero[fila][columna].setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    tablero[fila][columna].setBorderPainted(true);
                }
            }
        }
    }
    
    private void seleccionarCasilla(int fila, int columna, Piezas pieza){
        limpiarResaltados();
        filaSeleccionada = fila;
        columnaSeleccionada = columna;
        tablero[fila][columna].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        tablero[fila][columna].setBorderPainted(true);
        resaltarDestinos(pieza);
    }
    
    private void limpiarSeleccion(){
        limpiarResaltados();
        filaSeleccionada = -1;
        columnaSeleccionada = -1;
    }
    
    private void terminarTurno(){
        limpiarSeleccion();
        jugadorActual = jugadorActual == 1 ? 2 : 1;
        tipoPermitido = null;
        girosUsados = 0;
        pieza.setText("???");
        girar.setEnabled(true);
        actualizarRuleta();
        escribir("Turno de " + nombreJugador(jugadorActual) + ". Gira la ruleta.");
    }
    
    private void verificarVictoria(){
        int piezasJugador1 = 0;
        int piezasJugador2 = 0;
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                Piezas p = piezasTablero[fila][columna];
                if (p != null) {
                    if (p.getJugador() == 1) {
                        piezasJugador1++;
                    } else {
                        piezasJugador2++;
                    }
                }
            }
        }
        if (piezasJugador1 == 0 || piezasJugador2 == 0) {
            juegoTerminado = true;
            Usuarios ganador = piezasJugador1 == 0 ? jugador2 : jugador1;
            Usuarios perdedor = piezasJugador1 == 0 ? jugador1 : jugador2;
            registrarPartida(ganador, perdedor, "ELIMINACION");
            girar.setEnabled(false);
            String mensaje = ganador.getNombre() + " venció a " + perdedor.getNombre() + ". ¡Felicidades, has ganado 3 puntos!";
            escribir(mensaje);
            JOptionPane.showMessageDialog(this, mensaje, "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
            ventana.cambiarPanel(new MenuPrincipal(ventana, jugador1));
        }
    }
    
    private void concluirAccion(){
        verificarVictoria();
        if (!juegoTerminado) {
            terminarTurno();
        } else {
            limpiarSeleccion();
        }
    }
    
    private void reportarResultado(Piezas defensor, int fila, int columna){
        if (defensor.estaViva()) {
            escribir("A " + nombreTipo(defensor) + " le quedan " + defensor.getEscudo() + " puntos de escudo y " + defensor.getVida() + " de vida.");
        } else {
            piezasTablero[fila][columna] = null;
            piezasPerdidas[defensor.getJugador()]++;
            escribir("Se destruyó la pieza " + nombreTipo(defensor) + " del jugador " + nombreJugador(defensor.getJugador()) + ".");
        }
        actualizarTablero();
    }
    
    private void finalizarAtaque(Piezas defensor, int fila, int columna){
        reportarResultado(defensor, fila, columna);
        concluirAccion();
    }
    
    private void ejecutarAtaqueNormal(Piezas atacante, Piezas defensor, int fila, int columna){
        escribir(nombreJugador(jugadorActual) + " ataca con su " + nombreTipo(atacante) + " a " + nombreTipo(defensor) + ".");
        atacante.ataque(defensor);
        finalizarAtaque(defensor, fila, columna);
    }
    
    private void ejecutarAtaqueEspecial(Piezas atacante, Piezas defensor, int fila, int columna){
        String resultado = atacante.movimientoEspecial(defensor, fila, columna, piezasTablero);
        if (resultado == null) {
            escribir("Esa pieza enemiga está fuera del alcance de las habilidades de tu " + nombreTipo(atacante) + ".");
            return;
        }
        escribir(nombreJugador(jugadorActual) + " " + resultado);
        finalizarAtaque(defensor, fila, columna);
    }
    
    private void manejarAtaque(Piezas atacante, Piezas defensor, int fila, int columna){
        boolean adyacente = atacante.esAdyacente(fila, columna);
        if (atacante instanceof Necromante) {
            if (adyacente) {
                ejecutarAtaqueNormal(atacante, defensor, fila, columna);
            } else {
                ejecutarAtaqueEspecial(atacante, defensor, fila, columna);
            }
            return;
        }
        if (!adyacente) {
            escribir("Esa pieza enemiga no está al alcance.");
            return;
        }
        if (atacante.tieneAtaqueEspecial()) {
            Object[] opciones = {"Ataque normal", atacante.nombreAtaqueEspecial()};
            int eleccion = JOptionPane.showOptionDialog(this, "¿Qué ataque deseas usar?", nombreTipo(atacante), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (eleccion == 1) {
                ejecutarAtaqueEspecial(atacante, defensor, fila, columna);
            } else if (eleccion == 0) {
                ejecutarAtaqueNormal(atacante, defensor, fila, columna);
            }
        } else {
            ejecutarAtaqueNormal(atacante, defensor, fila, columna);
        }
    }
    
    private void manejarDestinoVacio(Piezas piezaOrigen, int fila, int columna){
        if (piezaOrigen instanceof Necromante) {
            boolean adyacente = piezaOrigen.esAdyacente(fila, columna);
            Object[] opciones = adyacente ? new Object[]{"Mover Necrómante", "Invocar Zombie", "Cancelar"} : new Object[]{"Invocar Zombie", "Cancelar"};
            int eleccion = JOptionPane.showOptionDialog(this, "¿Qué deseas hacer en esa casilla?", "Necrómante", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (adyacente && eleccion == 0) {
                if (piezaOrigen.mover(fila, columna, piezasTablero)) {
                    actualizarTablero();
                    escribir(nombreJugador(jugadorActual) + " mueve su Necrómante.");
                    concluirAccion();
                } else {
                    escribir("Movimiento inválido.");
                }
            } else if ((adyacente && eleccion == 1) || (!adyacente && eleccion == 0)) {
                String resultado = piezaOrigen.movimientoEspecial(null, fila, columna, piezasTablero);
                if (resultado != null) {
                    actualizarTablero();
                    escribir(nombreJugador(jugadorActual) + " " + resultado);
                    concluirAccion();
                } else {
                    escribir("No se pudo invocar el Zombie en esa casilla.");
                }
            }
            return;
        }
        boolean movida = piezaOrigen.mover(fila, columna, piezasTablero);
        if (movida) {
            actualizarTablero();
            escribir(nombreJugador(jugadorActual) + " mueve su " + nombreTipo(piezaOrigen) + ".");
            concluirAccion();
        } else {
            escribir("Movimiento inválido, elige una casilla vecina vacía" + (piezaOrigen instanceof HombreLobo ? " (el Hombre Lobo llega hasta 2 casillas)." : "."));
        }
    }
    
    private void seleccionarPieza(int fila, int columna){
        if (juegoTerminado) {
            return;
        }
        if (tipoPermitido == null) {
            escribir("Primero gira la ruleta para saber qué pieza puedes mover.");
            return;
        }
        Piezas piezaClic = piezasTablero[fila][columna];
        if (filaSeleccionada == -1) {
            if (piezaClic == null) {
                return;
            }
            if (piezaClic.getJugador() != jugadorActual) {
                escribir("Esa pieza no es tuya.");
                return;
            }
            if (!nombreTipo(piezaClic).equals(tipoPermitido)) {
                escribir("La ruleta indica que debes mover: " + tipoPermitido);
                return;
            }
            seleccionarCasilla(fila,columna,piezaClic);
            return;
        }
        if (fila == filaSeleccionada && columna == columnaSeleccionada) {
            limpiarSeleccion();
            return;
        }
        Piezas piezaOrigen = piezasTablero[filaSeleccionada][columnaSeleccionada];
        
        if (piezaClic != null && piezaClic.getJugador() == jugadorActual) {
            if (!nombreTipo(piezaClic).equals(tipoPermitido)) {
                escribir("La ruleta indica que debes mover: " + tipoPermitido);
                return;
            }
            seleccionarCasilla(fila,columna, piezaClic);
            return;
        }
        if (piezaClic != null && piezaClic.getJugador() != jugadorActual) {
            manejarAtaque(piezaOrigen, piezaClic, fila, columna);
            return;
        }
        manejarDestinoVacio(piezaOrigen, fila, columna);
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
        asignarPiezasRuleta();
    }
    
    private int girosPermitidos(int jugador){
        return Math.min(3, 1 + piezasPerdidas[jugador] / 2);
    }
    
    private boolean existeTipo(int jugador, String tipo){
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                Piezas p = piezasTablero[fila][columna];
                if (p != null && p.getJugador() == jugador && nombreTipo(p).equals(tipo)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void moverIconosRuleta(double grados) {
        double centroX = 160;
        double centroY = 160;
        double radianes = Math.toRadians(grados);
        double[] posicionesX = {132.5,202.5,82.5,242.5,132.5,202.5};
        double[] posicionesY = {87.5,87.5,162.5,162.5,227.5,227.5};
        for (int i = 0; i < 6; i++) {
            double x = posicionesX[i] - centroX;
            double y = posicionesY[i] - centroY;
            double nuevoX =x * Math.cos(radianes)- y * Math.sin(radianes);
            double nuevoY =x * Math.sin(radianes)+ y * Math.cos(radianes);
            nuevoX += centroX;
            nuevoY += centroY;
            iconosRuleta[i].setBounds((int)(nuevoX - 27.5),(int)(nuevoY - 27.5),55,55);
        }
    }
    
    private String tipoPorPosicion(int posicion) {
        switch (posicion) {
            case 0:
            case 5:
                return "Vampiro";
            case 1:
            case 4:
                return "Necromante";
            case 2:
            case 3:
                return "Hombre Lobo";
            default:
                return "";
        }
    }
    
    private ImageIcon rotarImagen(ImageIcon original, double grados) {
        if (original == null) {
            return null;
        }
        Image imagenOriginal = original.getImage();
        BufferedImage imagen = new BufferedImage(imagenOriginal.getWidth(null),imagenOriginal.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagen.createGraphics();
        g2.drawImage(imagenOriginal, 0, 0, null);
        g2.dispose();
        BufferedImage rotada = new BufferedImage(imagen.getWidth(),imagen.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = rotada.createGraphics();
        g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.rotate(Math.toRadians(grados),imagen.getWidth() / 2.0,imagen.getHeight() / 2.0);
        g.drawImage(imagen, 0, 0, null);
        g.dispose();
        return new ImageIcon(rotada);
    }
    
    private void actualizarAnimacionRuleta() {
        fondoRuleta.setIcon(rotarImagen(imagenRuleta,anguloRuleta));
        moverIconosRuleta(anguloRuleta);
        ruletaVisual.repaint();
    }
    
    private void finalizarGiroRuleta() {
        girandoRuleta = false;
        if(!posicionDisponible(posicionResultado)){
            for (int i = 0; i < 6; i++) {
                iconosRuleta[i].setBorder(null);
            }
            int permitidos = girosPermitidos(jugadorActual);
            String tipoFallido = tipoPorPosicion(posicionResultado);
            if (girosUsados < permitidos) {
                escribir("La ruleta indicó " + tipoFallido + ", pero ya no tienes ninguno. Giro " + girosUsados + "/" + permitidos + ". Gira de nuevo.");
                pieza.setText("???");
                girar.setEnabled(true);
            } else {
                escribir("La ruleta indicó " + tipoFallido + " y agotaste tus " + permitidos + " giro(s). Pierdes el turno.");
                terminarTurno();
            }
            return;
        }     
        String elegido = tipoPorPosicion(posicionResultado);
        tipoPermitido = elegido;
        pieza.setText(elegido);
        escribir(nombreJugador(jugadorActual)+ " obtuvo: "+ elegido);
        for (int i = 0; i < 6; i++) {
            iconosRuleta[i].setBorder(null);
        }
        iconosRuleta[posicionResultado].setBorder(BorderFactory.createLineBorder(Color.YELLOW,3));
        actualizarRuleta();
        girar.setEnabled(false);
    }
    
    private void iniciarAnimacionRuleta() {
        girandoRuleta = true;
        girar.setEnabled(false);
        pieza.setText("Girando...");
        int vueltas = 5 + random.nextInt(4);
        double rotacionObjetivo = vueltas * 360 + random.nextInt(360);
        double inicio = anguloRuleta;
        double distancia = rotacionObjetivo;
        final long tiempoInicio = System.currentTimeMillis();
        final int duracion = 2500;
        timerRuleta = new Timer(16, e -> {
            long tiempoActual = System.currentTimeMillis();
            double progreso = (double)(tiempoActual - tiempoInicio) / duracion;
            if (progreso >= 1.0) {
                progreso = 1.0;
                timerRuleta.stop();
                anguloRuleta = inicio + distancia;
                actualizarAnimacionRuleta();
                finalizarGiroRuleta();
                return;
            }
            double suavizado = 1 - Math.pow(1 - progreso, 3);
            anguloRuleta = inicio + distancia * suavizado;
            actualizarAnimacionRuleta();
        });
        timerRuleta.start();
    }
    
    private boolean posicionDisponible(int posicion) {
        Piezas[] ruletaActual = obtenerRuletaActual();
        Piezas pieza = ruletaActual[posicion];
        return pieza != null && pieza.estaViva();
    }
    
    private void girarRuleta(){
        if (juegoTerminado || tipoPermitido != null || girandoRuleta) {
        return;
        }
        girosUsados++;
        posicionResultado = random.nextInt(6);
        iniciarAnimacionRuleta();
    }
    
    private void actualizarRuleta() {
        if (iconosRuleta == null || piezasTablero == null) {
            return;
        }
        Piezas[] ruletaActual;
        if (jugadorActual == 1) {
            ruletaActual = piezasRuletaJugador1;
        } else {
            ruletaActual = piezasRuletaJugador2;
        }
        for (int i = 0; i < 6; i++) {
            Piezas pieza = ruletaActual[i];
            if (pieza == null || !pieza.estaViva()) {
                switch (i) {
                    case 0:
                    case 5:
                        iconosRuleta[i].setIcon(iconoVampiroNegro);
                        break;
                    case 1:
                    case 4:
                        iconosRuleta[i].setIcon(iconoNecromanteNegro);
                        break;
                    case 2:
                    case 3:
                        iconosRuleta[i].setIcon(iconoHombreLoboNegro);
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                    case 5:
                        iconosRuleta[i].setIcon(iconoVampiro);
                        break;
                    case 1:
                    case 4:
                        iconosRuleta[i].setIcon(iconoNecromante);
                        break;
                    case 2:
                    case 3:
                        iconosRuleta[i].setIcon(iconoHombreLobo);
                        break;
                }
            }
        }

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
        ruletaVisual = new JLayeredPane();
        ruletaVisual.setPreferredSize(new Dimension(320,320));
        fondoRuleta = new JLabel(imagenRuleta);
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
        if (consola == null) {
            return;
        }
        consola.append(mensaje + "\n");
        consola.setCaretPosition(consola.getDocument().getLength());
    }
    
    private void crearPanelConsola(){
        panelConsola = new JPanel(new BorderLayout());
        panelConsola.setBorder(BorderFactory.createTitledBorder("Consola"));
        panelConsola.setPreferredSize(new Dimension(260, 400));
        
        consola = new JTextArea();
        consola.setEditable(false);
        consola.setLineWrap(true);
        consola.setWrapStyleWord(true);
        consola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(consola);
        panelConsola.add(scroll, BorderLayout.CENTER);
    }
    
    private void registrarPartida(Usuarios ganador, Usuarios perdedor, String tipoFinalizacion){
        ganador.aumentarPuntos();
        Historial.agregarRegistro(new RegistroPartida(jugador1, jugador2, ganador, perdedor, tipoFinalizacion));
    }
    
    private void finalizarPartidaPorRetiro() {
        int jugadorPerdedor = jugadorActual;
        int jugadorGanador = obtenerJugadorOponente();
        Usuarios perdedor;
        Usuarios ganador;
        if (jugadorPerdedor == 1) {
            perdedor = jugador1;
        } else {
            perdedor = jugador2;
        }
        if (jugadorGanador == 1) {
            ganador = jugador1;
        } else {
            ganador = jugador2;
        }
        registrarPartida(ganador,perdedor,"RETIRO");
        JOptionPane.showMessageDialog(this,"La partida ha finalizado.\n\n Ganador: " + ganador.getNombre() + "\n Perdedor: " + perdedor.getNombre() + "\n\n"+ ganador.getNombre()+ " recibe 3 puntos de victoria.","Partida finalizada",JOptionPane.INFORMATION_MESSAGE);
        ventana.cambiarPanel(new MenuPrincipal(ventana,jugador1));
    }
    
    private int obtenerJugadorOponente() {
        if (jugadorActual == 1) {
            return 2;
        }
        return 1;
    }
    
    private void confirmarFinalizarPartida() {
        int respuesta = JOptionPane.showConfirmDialog(this,"¿Confirmar terminar partida?\n\n"+ "La partida quedará registrada como perdida para ti.\n","Confirmar terminar partida",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        finalizarPartidaPorRetiro();
    }
    
    private void inicializar(){
        if(jugador1 == null || jugador2 == null || jugador1 == jugador2){
            ventana.cambiarPanel(new MenuPrincipal(ventana, jugador1));
            return;
        }
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Vampire Wargame",SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        add(titulo, BorderLayout.NORTH);
        
        JPanel panelMain = new JPanel(new BorderLayout(10, 10));
        crearPanelTablero();
        crearPanelRuleta();
        crearPanelConsola();
        panelMain.add(panelRuleta,BorderLayout.WEST);
        panelMain.add(panelTablero,BorderLayout.CENTER);
        panelMain.add(panelConsola,BorderLayout.EAST);
        add(panelMain,BorderLayout.CENTER);

        JButton finalizar = new JButton("Finalizar");
        finalizar.setFont(new Font("Arial", Font.BOLD, 16));
        
        finalizar.addActionListener(e -> {
            confirmarFinalizarPartida();
        });
        
        add(finalizar,BorderLayout.SOUTH);
    }

    private boolean partidaValida(){
        return jugador1 != null && jugador2 != null && jugador1 != jugador2;
    }
    
    public NuevaPartida(GUI ventana, Usuarios jugador1, Usuarios jugador2){
        this.ventana = ventana;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        
        random = new Random();
        inicializar();
        if (partidaValida()) {
            escribir("¡Comienza la partida entre " + jugador1.getNombre() + " y " + jugador2.getNombre() + "!");
            escribir("Turno de " + nombreJugador(jugadorActual) + ". Gira la ruleta.");
        }
    }
    
}
