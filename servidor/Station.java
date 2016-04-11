import java.io.*;

class Station implements Serializable {
    private String ip;
    private String mac;
    private String hostname;
    
    Station(String ip, String mac, String hostname) {
      this.ip = ip;
      this.mac = mac;
      this.hostname = hostname;
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

    public void setIp(String ip) {
      this.ip = ip;
    }

    public void setMac(String mac) {
      this.mac = mac;
    }

    public void setHostname(String hostname) {
      this.hostname = hostname;
    }
}
