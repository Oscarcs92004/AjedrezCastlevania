/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 *
 * @author oscar
 */
public class Principal extends JFrame{
    
    JButton botonJugar;
    
    public void inicializarComponentes(){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        
        botonJugar = new JButton("Jugar");
        
        p.add(botonJugar);
        add(p);
    }
    
    public Principal(){
        super("Vampire Wargame");
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        inicializarComponentes();
        setVisible(true);
    }
    
    public static void main(String args[]){
        Principal p = new Principal();
    }
}
