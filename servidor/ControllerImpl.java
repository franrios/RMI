import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ControllerImpl extends UnicastRemoteObject implements Controller {

    private List<Ap> apList;
    private List<Association> associationList;
    private IpBank ipBank;

  //  private List<Ssid> ssidlist;
//    private List<Station> stationlist;

    ControllerImpl() throws RemoteException {
        apList = new LinkedList<Ap>();
      //  ssidlist = new LinkedList<Ssid>();
        associationList = new LinkedList<Association>();
        ipBank = IpBank.getInstance();
    }

    public Association connect(Station station) throws RemoteException{
      if (apList.size() > 0) {
        Association aux = isStationConnected(station);
        Ap nearestAp = searchNearestAP(station.getPosition());
        
        if (aux != null) {
          aux.setAp(nearestAp);
        } else {
          station.setIp(ipBank.assignIp());
          aux = new Association(nearestAp, station);
          associationList.add(aux);
          System.out.println("Bien");
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

    public void disconnect(Station station) throws RemoteException{
        Association aux = isStationConnected(station);
        
        if (aux != null) {
          ipBank.freeIp(aux.getStation().getIp());
          associationList.remove(aux);
          System.out.println("The station with the following parameters:\n" 
            + station.toString() + "\nhas been disconnected.");
        }
    }

    public Boolean registerAp(Ap ap) throws RemoteException{
      Boolean result = false;

      if (isAPRegistered(ap))
        System.out.println("\nAP with ID " + ap.getID() + ", already registered.");
      else if ((result = apList.add(ap)) == true)
        System.out.println("\nAP with ID " + ap.getID() + ", successfully registered.");
      

      return result;
    }


    public void unregisterAp(Ap ap) throws RemoteException{

      if (isAPRegistered(ap)) {
        for(Ap i: apList)
          if (i.getID().equals(ap.getID())) {
            apList.remove(i);
            System.out.println("\nAP with ID " + ap.getID() + ", has been unregistered.");
          } 
      }
      else
        System.out.println("\nAP with ID " + ap.getID() + ", is not registered.");
    }

    private Association isStationConnected(Station station) throws RemoteException{
      Association aux = null;

      for (Association i: associationList) {
        if (i.getStation().getMac().equals(station.getMac()))
          aux = i;
      }
      return aux;
    }

    private Boolean isAPRegistered(Ap ap) throws RemoteException{
      Boolean result = false;

      for(Ap i: apList)
        if (i.getID().equals(ap.getID()))
          result = true;

      return result;
    }

    private Ap searchNearestAP(Position position) throws RemoteException {
      Ap nearestAp = null;
      double distance = -1.0;

      if (apList.size() > 0) {
        for (Ap i: apList) {
          if ((distance < 0.0) || (i.getPosition().calculateDistance(position) < distance)) {
            nearestAp = i;
            distance = i.getPosition().calculateDistance(position);
          }
        }
      }

      return nearestAp;
    }
}
