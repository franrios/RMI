import java.lang.*;
import java.io.*;

class Position implements Serializable {
    private double x;
    private double y;
    private double z;
    
    Position(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
    
    public double getX() {
      return x;
    }

    public double getY() {
      return y;
    }

    public double getZ() {
      return z;
    }

    public void setX(double x) {
      this.x = x;
    }

    public void setY(double y) {
      this.y = y;
    }

    public void setZ(double z) {
      this.z = z;
    }

    public double calculateDistance (Position position) {
      double difx = this.x - position.getX();
      double dify = this.y - position.getY();
      double difz = this.z - position.getZ();

      return Math.sqrt(difx*difx + dify*dify + difz*difz);
    }

    public String toString(){
      return "X: " + this.x + " | Y: " + this.y + " | Z: " + this.z;
    }
}