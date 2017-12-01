/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

/**
 *
 * @author jorge
 */
public class sorteos {
   private final String sorteo;
   private final int id;
  
  public sorteos(String sorteo, int id ) {
    this.sorteo = sorteo;
    this.id = id;
  }
 
  public int getId(){
    return id;
  }
  
  public String getSorteo(){
    return sorteo;
  }
 
  public String toString() {
    return sorteo;
  }
}