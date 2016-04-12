import java.io.*;

class Station implements Serializable {
    private String ip;
    private String mac;
    private String hostname;
    private Position position;
    
    Station(String mac, String hostname, Position position) {
      this.ip = "";
      this.mac = mac;
      this.hostname = hostname;
      this.position = position;
    }
    
    public String getIp() {
      return ip;
    }

    public String getMac() {
      return mac;
    }

    public String getHostname() {
      return hostname;
    }

    public Position getPosition() {
      return position;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }

    public void setMac(String mac) {
      this.mac = mac;
    }

    public void setHostname(String hostname) {
      this.hostname = hostname;
    }

    public void setPosition(Position position) {
      this.position = position;
    }

    public String toString() {
      return "\tMAC: " + this.mac + "\n\tIP: " +this.ip + "\n\tHostname: " +
      this.hostname + "\n\tPosition: " +this.position;
    }
}
