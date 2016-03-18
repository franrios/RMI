import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ControllerImpl extends UnicastRemoteObject implements Controller {

    private List<Ap> aplist;
    private List<Ssid> ssidlist;
    private List<Station> stationlist;

    ControllerImpl() throws RemoteException {
        aplist = new LinkedList<Ap>();
        ssidlist = new LinkedList<Ssid>();
        stationlist = new LinkedList<Station>();
    }

    // ??????????????
    public Ap registerStation(Station station) throws RemoteException {
        Ap ap = new ApImpl(station);
        l.add(ap);
        return ap;
    }

    public List<Ap> getStations() throws RemoteException {
        return l;
    }
}
