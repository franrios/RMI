import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class APImpl extends UnicastRemoteObject implements AP {
    
    private List<String> ssidList;
    private int maxSSID;
    private int id;

    APImpl(int id, int maxSSID) throws RemoteException {
        this.id = id;
        this.maxSSID = maxSSID;
        ssidList = new LinkedList<String>();
    }

    public void addSSID(String ssid) throws RemoteException {
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
    }
    
    public int getID() throws RemoteException {
       return id;
    }
}
