import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

class Station implements Serializable {
    private String ip;
    private String mac;
    private String hostname;
    private Position position;

    static public void main (String args[]) {
        String registryHost;
        String registryPortNumber;
        String apId;
        String mac;
        String hostname;
        double x;
        double y;
        double z;
        String operation;


        if (args.length!=9) {
            System.err.println("Use: Station registryHost registryPortNumber AP_ID MAC hostname position(x) position(y) position(z) (dis)connect");
            return;
        } else {
            registryHost = args [0];
            registryPortNumber = args[1];
            apId = args[2];
            mac = args[3];
            hostname = args[4];
            x = Double.parseDouble(args[5]);
            y = Double.parseDouble(args[6]);
            z = Double.parseDouble(args[7]);
            operation = args[8];
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {

            Ap srv = (Ap) Naming.lookup("//" + registryHost + ":" + registryPortNumber + "/Ap" + apId);
            
            Station station = new Station(mac, hostname, new Position(x,y,z));

            if (operation.equals("connect")) {
              station.setIp(srv.connect(station).getStation().getIp());
              System.out.println("The station has been successfully connected.");
            } else if(operation.equals("disconnect")) {
              srv.disconnect(station);
              System.out.println("The station has been successfully disconnected.");
            } else {
              System.out.println("Forbidden operation.");
            }
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en Client:");
            e.printStackTrace();
        }
    }
    
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
      return "\tMAC: " + mac + "\n\tIP: " +ip + "\n\tHostname: " +
      hostname + "\n\tPosition: " +position;
    }
}
