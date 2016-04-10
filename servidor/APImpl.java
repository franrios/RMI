import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

class APImpl extends UnicastRemoteObject implements AP {
    
    private list<string> ssidlist;
    private int maxSSID;
    private int id;

    APImpl(int id, int maxSSID) throws RemoteException {
        this.id = id;
        this.maxSSID = maxSSID;
        ssid = new LinkedList<string>();
    }

    public void addSSID(string ssid) throws RemoteException {
        ssidlist.add(ssid);
    }

    public void removeSSID(string ssid) throws RemoteException {
        int index = 0;
        for (string i:ssidlist){
            if (i.isEqual(ssid))
                ssidlist.remove(index);
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
