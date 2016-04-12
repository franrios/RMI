import java.io.*;

class Association implements Serializable {
    private Ap ap;
    private Station station;

    Association(Ap ap, Station station) {
      this.ap = ap;
      this.station = station;
    }

    public Ap getAp() {
      return ap;
    }

    public Station getStation() {
      return station;
    }

    public void setAp(Ap ap) {
      this.ap = ap;
    }

    public void setStation(Station station) {
      this.station = station;
    }

    public String toString() {
      try {
        return "\nThe station with the following parameters:\n" + this.station.toString() + 
        "\nis associated with the following AP:\n\tID: " + this.ap.getID() + "\n\tPosition: "
        + this.ap.getPosition();
      } catch (Exception e) {
        System.out.println(e);
        return null;
      }
    }
}
