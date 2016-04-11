import java.io.*;

class Association {
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
}
