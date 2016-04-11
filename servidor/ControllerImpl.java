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

    public Ap connect(Station station) throws RemoteException{
      Association aux = isStationConnected(station);
      Ap defaultAp = new ApImpl("localhost", "54321","9999",new Position(5.0,5.0,14.0));
      if (aux != null)
        aux.setAp(defaultAp);
      else
        associationList.add(new Association(defaultAp, station));

      System.out.println(station.getPosition().calculateDistance(defaultAp.getPosition()));
      System.out.println(station.getPosition().toString());
      System.out.println(defaultAp.getPosition().toString());

      return defaultAp;
    }

    public Boolean registerAp(Ap ap) throws RemoteException{
      Boolean result = false;

      if (this.isAPRegistered(ap))
        System.out.println("AP with ID " + ap.getID() + ", already registered.");
      else if ((result = this.apList.add(ap)) == true)
        System.out.println("AP with ID " + ap.getID() + ", successfully registered.");
      

      return result;
    }

    public Association isStationConnected(Station station) throws RemoteException{
      Association aux = null;

      for (Association i: this.associationList) {
        if (i.getStation().getMac().equals(station.getMac()))
          aux = i;
      }
      return aux;
    }

    private Boolean isAPRegistered(Ap ap) throws RemoteException{
      Boolean result = false;

      for(Ap i: this.apList)
        if (i.getID().equals(ap.getID()))
          result = true;

      return result;
    }
}
