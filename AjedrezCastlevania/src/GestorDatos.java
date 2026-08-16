/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
/**
 *
 * @author oscar
 */
public interface GestorDatos<T> {
    void agregar(T elemento);
    
    ArrayList<T> obtenerTodos();
}
