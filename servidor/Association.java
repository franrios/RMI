import java.io.*;

class Association implements Serializable {
    private Ap ap;
    private Station station;

    /*
    Constructor de la clase Association. Recibe como parámetros:
      - El AP al que se quiere conectar la estación.
      - La estación que se quiere conectar al AP.
    */
    Association(Ap ap, Station station) {
      this.ap = ap;
      this.station = station;
    }
    /*
       Método para obtener el AP que forma parte de la asociación.
    */
    public Ap getAp() {
      return ap;
    }
    /*
       Método para obtener la estación que forma parte de la asociación.
    */
    public Station getStation() {
      return station;
    }
    /*
       Método para setear el AP que forma parte de la asociación.
    */
    public void setAp(Ap ap) {
      this.ap = ap;
    }
   /*
       Método para setear la estación que forma parte de la asociación.
    */
    public void setStation(Station station) {
      this.station = station;
    }

    public String toString() {
      try {
        return "\nThe station with the following parameters:\n" + "\tMAC: " + station.getMac() 
        + "\n\tIP: " +station.getIp() + "\n\tHostname: " + station.getHostname() 
        + "\n\tPosition: " +station.getPosition() + "\nis associated with the following AP:\n\tID: " 
        + ap.getID() + "\n\tPosition: " + ap.getPosition();
      } catch (Exception e) {
        System.out.println(e);
        return null;
      }
    }
}
