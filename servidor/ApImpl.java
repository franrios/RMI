import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ApImpl extends UnicastRemoteObject implements Ap {

    //private List<String> ssidList;
    //private int maxSSID;
    private int id;
    private Controller srv;

    ApImpl(int id, String controllerHost, String controllerPort) throws RemoteException {
        this.id = id;
        //this.maxSSID = maxSSID;
        //ssidList = new LinkedList<String>();

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {

            this.srv = (Controller) Naming.lookup("//" + controllerHost + ":" + controllerPort + "/Controller");
            if (this.srv.registerAp(this) == false)
                System.err.println("Error during AP registration." + e.toString());
        }
        catch (RemoteException e) {
            System.err.println("Communication error: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("AP Exception:");
            e.printStackTrace();
        }
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
    }*/

    public int getMaxSSID() throws RemoteException {
       return maxSSID;
    }

    public int getID() throws RemoteException {
       return id;
    }
}
