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

    public Association connect(Station station) throws RemoteException{
      if (apList.size() > 0) {
        Association aux = this.isStationConnected(station);
        Ap nearestAp = this.searchNearestAP(station.getPosition());
        
        if (aux != null) {
          aux.setAp(nearestAp);
        } else {
          aux = new Association(nearestAp, station);
          associationList.add(aux);
        }

        System.out.println(aux);
        /*System.out.println(station.getPosition().calculateDistance(nearestAp.getPosition()));
        System.out.println(station.getPosition().toString());
        System.out.println(nearestAp.getPosition().toString());*/
        return aux;
      } else {
        System.out.println("AP list is empty.");
        return null;
      }
    }

    public Boolean registerAp(Ap ap) throws RemoteException{
      Boolean result = false;

      if (this.isAPRegistered(ap))
        System.out.println("AP with ID " + ap.getID() + ", already registered.");
      else if ((result = this.apList.add(ap)) == true)
        System.out.println("AP with ID " + ap.getID() + ", successfully registered.");
      

      return result;
    }

    private Association isStationConnected(Station station) throws RemoteException{
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

    private Ap searchNearestAP(Position position) throws RemoteException {
      Ap nearestAp = null;
      double distance = -1.0;

      if (this.apList.size() > 0) {
        for (Ap i: this.apList) {
          if ((distance < 0.0) || (i.getPosition().calculateDistance(position) < distance)) {
            nearestAp = i;
            distance = i.getPosition().calculateDistance(position);
          }
        }
      }

      return nearestAp;
    }
}
