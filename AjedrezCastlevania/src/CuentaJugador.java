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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class CuentaJugador extends JPanel{
    private GUI ventana;
    private Usuarios usuario;
    
    private void agregarFila(JPanel panel, GridBagConstraints limites, int fila, String etiqueta, String valor){
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 16));
    
        limites.gridx = 0;
        limites.gridy = fila;
        panel.add(lblEtiqueta, limites);
        limites.gridx = 1;
        panel.add(lblValor, limites);
    }
    
    private void cambiarContrasena(){
        JPasswordField campoNueva = new JPasswordField();
        JPasswordField campoConfirmar = new JPasswordField();
        Object[] mensaje = {"Nueva contraseña (5 caracteres):", campoNueva,"Confirmar contraseña:", campoConfirmar};
        int resultado = JOptionPane.showConfirmDialog(this, mensaje, "Cambiar contraseña",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }
        
        String nueva = new String(campoNueva.getPassword());
        String confirmar = new String(campoConfirmar.getPassword());
        
        if (nueva.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!nueva.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nueva.length() != 5) {
            JOptionPane.showMessageDialog(this, "La contraseña debe ser de exactamente 5 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        usuario.setContra(nueva);
        JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.");
    }
    
    private void cerrarCuenta(){
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas cerrar tu cuenta?\nNo podrás volver a iniciar sesión con este usuario.",
                "Cerrar cuenta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        usuario.desactivar();
        JOptionPane.showMessageDialog(this, "Tu cuenta ha sido cerrada.");
        ventana.mostrarMenu();
    }
    
    private void inicializar(){
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("Mi cuenta", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        add(titulo, BorderLayout.NORTH);
        
        JPanel info = new JPanel(new GridBagLayout());
        GridBagConstraints limites = new GridBagConstraints();
        limites.insets = new Insets(8, 8, 8, 8);
        limites.anchor = GridBagConstraints.WEST;
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        agregarFila(info, limites, 0, "Usuario:", usuario.getNombre());
        agregarFila(info, limites, 1, "Puntos:", String.valueOf(usuario.getPuntos()));
        agregarFila(info, limites, 2, "Fecha de ingreso:", formato.format(usuario.getFechaRegistro().getTime()));
        agregarFila(info, limites, 3, "Estado:", usuario.isActivo() ? "Activo" : "Inactivo");
        
        JPanel contenedorInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        contenedorInfo.add(info);
        add(contenedorInfo, BorderLayout.CENTER);
        
        JButton cambiarContra = new JButton("Cambiar contraseña");
        JButton botonCerrarCuenta = new JButton("Cerrar mi cuenta");
        JButton regresar = new JButton("Regresar");
        
        cambiarContra.setFont(new Font("Arial", Font.BOLD, 16));
        botonCerrarCuenta.setFont(new Font("Arial", Font.BOLD, 16));
        regresar.setFont(new Font("Arial", Font.BOLD, 16));
        
        cambiarContra.addActionListener(e -> cambiarContrasena());
        botonCerrarCuenta.addActionListener(e -> cerrarCuenta());
        regresar.addActionListener(e -> ventana.cambiarPanel(new MenuPrincipal(ventana, usuario)));
        
        JPanel botones = new JPanel();
        botones.add(cambiarContra);
        botones.add(botonCerrarCuenta);
        botones.add(regresar);
        add(botones, BorderLayout.SOUTH);
    }
    
    public CuentaJugador(GUI ventana, Usuarios usuario){
        this.ventana = ventana;
        this.usuario = usuario;
        inicializar();
    }
}
