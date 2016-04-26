import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Random;

class StationImpl extends UnicastRemoteObject implements Station{
    //IP de la estacion
    private String ip;
    //MAC de la estacion
    private String mac;
    //Nombre de la estacion
    private String hostname;
    //Posicion en la que se encuentra la estacion
    private Position position;
    //Variable aleatoria con la que simularemos el movimiento de la sta
    private Random rand;
    //AP
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
        //Comprobamos si los argumentos son los correctos
        if (args.length!=8) {
            System.err.println("Use: Station registryHost registryPortNumber AP_ID MAC hostname position(x) position(y) position(z)");
            return;
        } else {
            //Asignamos cada argumento a su variable correspondiente
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
            //Creamos un objeto de la clase StationImpl llamando al constructor.         
            StationImpl station = new StationImpl(mac, hostname, new Position(x,y,z), 
              registryHost, registryPortNumber, apId);
            //Le asignamos la IP
            station.setIp(station.getSrv().connect(station).getStation().getIp());
            //Creamos un shutdownHelper para que registre la desconexión.
            shutdownHelper = new ShutdownHelper(station);
            Runtime.getRuntime().addShutdownHook(shutdownHelper);

            System.out.println("The station has been successfully connected.");

            /*
             En este bucle simularemos el movimiento de la estación cada 3seg.
             Para ello generamos una variable aleatoria entre -2 y 2 y se la sumamos
             a las coordenadas de la posicion en la que estaba la sta.
            */            
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

    /*
        Constuctor de la clase StationImpl. 
    */     
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
    /*
      Metodo para obtener la IP de la estacion
    */     
    public String getIp() {
      return ip;
    }
    /*
      Metodo para obtener la MAC de la estacion
    */ 
    public String getMac() {
      return mac;
    }
    /*
      Metodo para obtener el nombre de la estacion
    */ 
    public String getHostname() {
      return hostname;
    }
    /*
      Metodo para obtener la posicion de la estacion
    */ 
    public Position getPosition() {
      return position;
    }
    /*
      Metodo para obtener el AP al que se quiere conectar la estacion
    */ 
    public Ap getSrv() {
      return srv;
    }
    /*
      Metodo para setear la IP de la estacion
    */ 
    public void setIp(String ip) {
      this.ip = ip;
    }
    /*
      Metodo para setear la MAC de la estacion
    */
    public void setMac(String mac) {
      this.mac = mac;
    }
    /*
      Metodo para setear el nombre de la estacion
    */
    public void setHostname(String hostname) {
      this.hostname = hostname;
    }
    /*
      Metodo para setear la posicion de la estacion
    */
    public void setPosition(Position position) {
      this.position = position;
    }
    /*
      Metodo para setear el AP al que se quiere conectar la estacion
    */
    public void setSrv(Ap srv) {
      this.srv = srv;
    }
    /*
      Metodo para generar la posicion de manera aleatoria 
    */
    public void randPosition(double min, double max) {

      double dx = rand.nextDouble()*(max - min) + min;
      double dy = rand.nextDouble()*(max - min) + min;
      double dz = rand.nextDouble()*(max - min) + min;

      this.position.setX(this.position.getX() + dx);
      this.position.setY(this.position.getY() + dy);
      this.position.setZ(this.position.getZ() + dz);
    }
    /*
      Metodo para reconectar la estacion a un AP.
    */
    public void reconnect(Ap ap) throws RemoteException {
      try {
        srv = (Ap) Naming.lookup("//" + registryHost + ":" + registryPortNumber + "/Ap" + ap.getID());
      } catch (Exception e) {
        System.err.println("Station Exception:");
        e.printStackTrace();
      }
    }
}
