import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ApImpl extends UnicastRemoteObject implements Ap {

    private String id;
    private Controller srv;
    private Position position;

    static public void main (String args[]) {
        ApImpl ap = null;
        StationImpl station = null;
        Association association =null;
        
        try{
            if (args.length != 6)
                System.err.println("Error with input arguments. ApImpl host port id x y z");
            else{
                ap = new ApImpl(args[0],args[1], args[2], new Position(Double.parseDouble(args[3]),
                    Double.parseDouble(args[4]),Double.parseDouble(args[5])));
                station = new StationImpl("01:02:03:04:05:06","station_1", new Position(3.0,2.0,13.0), "localhost", "54321", "1");

                association = ap.connect(station);
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /* Constructor de la clase ApImpl. Recibe como parametros:
       - El identificador del Controller de la red WiFi
       - El puerto del Controller de la red WiFi
       - El identificador del AP.
       - La posición en la que se encuentra el AP en la red WiFi.
     */
    ApImpl(String controllerHost, String controllerPort, String id, Position position) throws RemoteException {
        this.id = id;
        this.position = position;


        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            srv = (Controller) Naming.lookup("//" + controllerHost + ":" + controllerPort + "/Controller");
            if (srv.registerAp(this) == false)
                System.err.println("Error during AP registration.");
            else
                Runtime.getRuntime().addShutdownHook(new ShutdownHelper(this));
        }
        catch (RemoteException e) {
            System.err.println("Communication error: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("AP Exception:");
            e.printStackTrace();
        }
    }
    /* 
    Método para obtener el ID del AP
     */
    public String getID() throws RemoteException {
       return id;
    }
    /* 
    Método para obtener la posición del AP
     */
    public Position getPosition() throws RemoteException {
       return position;
    }
    /* 
    Método para insertar el ID del AP
     */
    public void setID(String id) throws RemoteException {
       this.id = id;
    }
    /* 
    Método para insertar la posición en la que se encuentra el  AP
     */
    public void setPosition(Position position) throws RemoteException {
       this.position = position;
    }
    /* 
    Método connect. 
    Recibe como parámetro:
      - La estación que se quiere asociar a este AP.
    Devuelve:
      - Un objeto de la clase Association que representa la conexión del cliente al AP.
     */
    public Association connect (Station station) throws RemoteException {
        Association result = srv.connect(station);
        System.out.println("Station " + station.getHostname() + " info updated.");
        return result;
    }
    /* 
    Método disconnect. 
    Recibe como parámetro:
      - La estación que se quiere desasociar a este AP.
     */
    public void disconnect (Station station) throws RemoteException {
        srv.disconnect(station);
    }
    /* 
    Método unregisterAp. 
    Este metodo da la orden al Controller de que elimine al AP de su lista de APs.
     */
    public void unregisterAp () throws RemoteException{
        try {
            srv.unregisterAp(this);
        } catch (RemoteException e) {
            System.err.println("Communication error: " + e.toString());
        }
    }
}
