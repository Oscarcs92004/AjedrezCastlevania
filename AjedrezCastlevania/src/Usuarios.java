/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author oscar
 */
public class Usuarios {
    private ArrayList<Usuarios> u = new ArrayList<>();
    private String nombre;
    private String contra;
    private Calendar fechaRegistro;
    private int puntos;
    private boolean activo;
    
    public Usuarios(String nombre, String contra){
        this.nombre = nombre;
        this.contra = contra;
        fechaRegistro = Calendar.getInstance();
        puntos = 0;
        activo = true;
    }
    
    public void aumentarPuntaje(){
    
    }
    
}
