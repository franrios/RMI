import java.lang.*;
import java.io.*;

class Position implements Serializable {
    private double x;
    private double y;
    private double z;
    /*
       Constructor de la clase Position. Recibe:
         -Las coordenadas x,y,z que describen la posicion
    */    
    Position(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
    /*
      Metodo para obtener la coordenada x
    */      
    public double getX() {
      return x;
    }
    /*
      Metodo para obtener la coordenada y
    */  
    public double getY() {
      return y;
    }
    /*
      Metodo para obtener la coordenada z
    */  
    public double getZ() {
      return z;
    }
    /*
      Metodo para setear la coordenada x
    */ 
    public void setX(double x) {
      this.x = x;
    }
    /*
      Metodo para setear la coordenada y
    */ 
    public void setY(double y) {
      this.y = y;
    }
    /*
      Metodo para setear la coordenada z
    */ 
    public void setZ(double z) {
      this.z = z;
    }
    /*
      Metodo para calcular la distancia entre esta posicion y una
      que se le pase como parametro
    */ 
    public double calculateDistance (Position position) {
      double difx = x - position.getX();
      double dify = y - position.getY();
      double difz = z - position.getZ();

      return Math.sqrt(difx*difx + dify*dify + difz*difz);
    }

    public String toString(){
      return "X: " + x + " | Y: " + y + " | Z: " + z;
    }
}
