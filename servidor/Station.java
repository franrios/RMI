import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Random;

class Station implements Serializable {
    private String ip;
    private String mac;
    private String hostname;
    private Position position;
    private Random rand;

    static public void main (String args[]) {
        String registryHost;
        String registryPortNumber;
        String apId;
        String mac;
        String hostname;
        double x;
        double y;
        double z;

        if (args.length!=8) {
            System.err.println("Use: Station registryHost registryPortNumber AP_ID MAC hostname position(x) position(y) position(z)");
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
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {

            Ap srv = (Ap) Naming.lookup("//" + registryHost + ":" + registryPortNumber + "/Ap" + apId);
            
            Station station = new Station(mac, hostname, new Position(x,y,z));

            station.setIp(srv.connect(station).getStation().getIp());

            Runtime.getRuntime().addShutdownHook(new ShutdownHelper(srv,station));

            System.out.println("The station has been successfully connected.");
            
            for (; true;) {
              Thread.sleep(3000);
              station.randPosition(-2.0, 2.0);
              srv.connect(station);
              System.out.println("New position: " + station.getPosition());
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
      this.rand = new Random();
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

    public void randPosition(double min, double max) {

      double dx = rand.nextDouble()*(max - min) + min;
      double dy = rand.nextDouble()*(max - min) + min;
      double dz = rand.nextDouble()*(max - min) + min;

      this.position.setX(this.position.getX() + dx);
      this.position.setY(this.position.getY() + dy);
      this.position.setZ(this.position.getZ() + dz);
    }
}
