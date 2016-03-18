import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ControllerImpl extends UnicastRemoteObject implements Controller {

    private List<Ap> apList;
    private List<Association> associationList;

  //  private List<Ssid> ssidlist;
//    private List<Station> stationlist;

    ControllerImpl() throws RemoteException {
        apList = new LinkedList<Ap>();
      //  ssidlist = new LinkedList<Ssid>();
        associationList = new LinkedList<Association>();
    }


    private Association isStationConnected(Station station) throws RemoteException{
      Association aux = null;

      for (i: this.associationList) {
        if (i.getStation().getMac() == station.getMac())
        aux = i;
      }
      return aux;
    }

    public Ap connect(Station station) throws RemoteException{
      Association aux = isStationConnected(station);
      defaultAp = apList.get(1);
      if (aux != null){
        aux.setAp(defaultAp);
        }
      else
        associationList.add(new Association(defaultAp, station));
      }
    }

    public Boolean registerAp(Ap ap) throws RemoteException{
      return this.apList.add(ap);
    }
}
