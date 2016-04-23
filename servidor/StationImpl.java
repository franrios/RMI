import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Random;

class StationImpl extends UnicastRemoteObject implements Station{
    private String ip;
    private String mac;
    private String hostname;
    private Position position;
    private Random rand;
    private Ap srv;
    private String registryHost;
    private String registryPortNumber;

    static public void main (String args[]) {
        String registryHost;
        String registryPortNumber;
        String apId;
        String mac;
        String hostname;
        double x;
        double y;
        double z;
        ShutdownHelper shutdownHelper;

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
            StationImpl station = new StationImpl(mac, hostname, new Position(x,y,z), 
              registryHost, registryPortNumber, apId);
            station.setIp(station.getSrv().connect(station).getStation().getIp());
            shutdownHelper = new ShutdownHelper(station);
            Runtime.getRuntime().addShutdownHook(shutdownHelper);

            System.out.println("The station has been successfully connected.");
            
            for (; true;) {
              Thread.sleep(3000);
              station.randPosition(-2.0, 2.0);

              System.out.println("New position: " + station.getPosition());
              System.out.println("Connected to AP: " + station.getSrv().getID());

              station.getSrv().connect(station);
              Thread.sleep(500);
              Runtime.getRuntime().removeShutdownHook(shutdownHelper);
              shutdownHelper = new ShutdownHelper(station);
              Runtime.getRuntime().addShutdownHook(shutdownHelper);
            }
        }
        catch (RemoteException e) {
            System.err.println("Communication Error: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Station Exception:");
            e.printStackTrace();
        }
    }
    
    StationImpl(String mac, String hostname, Position position, 
      String registryHost, String registryPortNumber, String apId) throws RemoteException{
      this.ip = "";
      this.mac = mac;
      this.hostname = hostname;
      this.position = position;
      this.rand = new Random();
      this.registryHost = registryHost;
      this.registryPortNumber = registryPortNumber;
      try {
        this.srv = (Ap) Naming.lookup("//" + registryHost + ":" + registryPortNumber + "/Ap" + apId);
      } catch (Exception e) {
        System.err.println("Station Exception:");
        e.printStackTrace();
      }
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

    public Ap getSrv() {
      return srv;
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

    public void setSrv(Ap srv) {
      this.srv = srv;
    }

    public void randPosition(double min, double max) {

      double dx = rand.nextDouble()*(max - min) + min;
      double dy = rand.nextDouble()*(max - min) + min;
      double dz = rand.nextDouble()*(max - min) + min;

      this.position.setX(this.position.getX() + dx);
      this.position.setY(this.position.getY() + dy);
      this.position.setZ(this.position.getZ() + dz);
    }

    public void reconnect(Ap ap) throws RemoteException {
      try {
        srv = (Ap) Naming.lookup("//" + registryHost + ":" + registryPortNumber + "/Ap" + ap.getID());
      } catch (Exception e) {
        System.err.println("Station Exception:");
        e.printStackTrace();
      }
    }
}
