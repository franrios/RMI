import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ControllerImpl extends UnicastRemoteObject implements Controller {
    
    private List<AP> aplist;
    private List<SSID> ssidlist;
    private List<Station> stationlist;

    ControllerImpl() throws RemoteException {
        aplist = new LinkedList<AP>();
        ssidlist = new LinkedList<SSID>();
        stationlist = new LinkedList<Station>();
    }
    
    // ??????????????
    public AP registerStation(Station station) throws RemoteException {
        AP ap = new APImpl(station);
        l.add(ap);
        return ap;
    }

    public List<AP> getStations() throws RemoteException {
        return l;
    }
}
