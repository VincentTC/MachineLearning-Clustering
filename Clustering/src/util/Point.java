/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Gerry
 */
public class Point {
    private float axis;
    private float ordinate;
   
   public Point(float axis, float ordinate){
       this.axis = axis;
       this.ordinate = ordinate;
   }
   
   public void setAxis(float axis){
       this.axis = axis;
   }
   
   public void setOrdinate(float ordinate){
       this.ordinate = ordinate;
   }
   
   public float getAxis(){
       return this.axis;
   }
   
   public float getOrdinate(){
       return this.ordinate;
   }
   
   @Override
   public String toString(){
       String points = "";
       
       points += this.axis + "#" + this.ordinate;
      
       return points;
   }
}
