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
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Reportes extends JPanel{
    private GUI ventana;
    private Usuarios usuario;
    
    
    private JPanel crearPanelRanking(){
        ArrayList<Usuarios> activos = new ArrayList<>();
        for (Usuarios u : Usuarios.getU()) {
            if (u.isActivo()) {
                activos.add(u);
            }
        }
        activos.sort(new Comparator<Usuarios>() {
            @Override
            public int compare(Usuarios a, Usuarios b) {
                return b.getPuntos() - a.getPuntos();
            }
        });
        
        String[] columnas = {"Posición", "Usuario", "Puntos"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int fila, int columna){
                return false;
            }
        };
        for (int i = 0; i < activos.size(); i++) {
            Usuarios u = activos.get(i);
            modelo.addRow(new Object[]{i + 1, u.getNombre(), u.getPuntos()});
        }
        
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setRowHeight(26);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setEnabled(false);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }
    
    
    private JPanel crearPanelHistorial(){
        ArrayList<RegistroPartida> registros = Historial.getRegistrosDe(usuario);
        
        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setFont(new Font("Monospaced", Font.PLAIN, 13));
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        
        if (registros.isEmpty()) {
            texto.setText("Todavía no has finalizado ninguna partida.");
        } else {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            StringBuilder sb = new StringBuilder();
            for (RegistroPartida r : registros) {
                sb.append("[").append(formato.format(r.getFecha().getTime())).append("] ");
                sb.append(r.getMensaje()).append("\n\n");
            }
            texto.setText(sb.toString());
        }
        texto.setCaretPosition(0);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JScrollPane(texto), BorderLayout.CENTER);
        return panel;
    }
    
    private void inicializar(){
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Reportes", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        add(titulo, BorderLayout.NORTH);
        
        JTabbedPane pestanias = new JTabbedPane();
        pestanias.setFont(new Font("Arial", Font.BOLD, 14));
        pestanias.addTab("Ranking de jugadores", crearPanelRanking());
        pestanias.addTab("Mi historial", crearPanelHistorial());
        add(pestanias, BorderLayout.CENTER);
        
        JButton regresar = new JButton("Regresar");
        regresar.setFont(new Font("Arial", Font.BOLD, 16));
        regresar.addActionListener(e -> ventana.cambiarPanel(new MenuPrincipal(ventana, usuario)));
        add(regresar, BorderLayout.SOUTH);
    }
    
    public Reportes(GUI ventana, Usuarios usuario){
        this.ventana = ventana;
        this.usuario = usuario;
        inicializar();
    }
}
