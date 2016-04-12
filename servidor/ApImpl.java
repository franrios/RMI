import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ApImpl extends UnicastRemoteObject implements Ap {

    //private List<String> ssidList;
    //private int maxSSID;
    private String id;
    private Controller srv;
    private Position position;

    static public void main (String args[]) {
        ApImpl ap = null;
        Station station = null;
        
        try{
            if (args.length != 6)
                System.err.println("Error with input arguments. ApImpl host port id x y z");
            else{
                ap = new ApImpl(args[0],args[1], args[2], new Position(Double.parseDouble(args[3]),
                    Double.parseDouble(args[4]),Double.parseDouble(args[5])));
                station = new Station("192.168.45.72","01:02:03:04:05:06","station_1", new Position(3.0,2.0,13.0));

                ap.connect(station);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    ApImpl(String controllerHost, String controllerPort, String id, Position position) throws RemoteException {
        this.id = id;
        this.position = position;
        //this.maxSSID = maxSSID;
        //ssidList = new LinkedList<String>();

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            this.srv = (Controller) Naming.lookup("//" + controllerHost + ":" + controllerPort + "/Controller");
            if (this.srv.registerAp(this) == false)
                System.err.println("Error during AP registration.");
        }
        catch (RemoteException e) {
            System.err.println("Communication error: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("AP Exception:");
            e.printStackTrace();
        }
    }

    public Association connect (Station station) throws RemoteException {
        Association result = this.srv.connect(station);
        System.out.println(result);
        return result;
    }

    /*public void addSSID(String ssid) throws RemoteException {
        ssidList.add(ssid);
    }

    public void removeSSID(String ssid) throws RemoteException {
        int index = 0;
        for (String i:ssidList){
            if (i.isEqual(ssid))
                ssidList.remove(index);
            index ++;
        }
    }

    public int getMaxSSID() throws RemoteException {
       return maxSSID;
    }*/

    public String getID() throws RemoteException {
       return this.id;
    }

    public Position getPosition() throws RemoteException {
       return this.position;
    }

    public void setID(String id) throws RemoteException {
       this.id = id;
    }

    public void setPosition(Position position) throws RemoteException {
       this.position = position;
    }
}
