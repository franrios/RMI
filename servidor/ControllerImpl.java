import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ControllerImpl extends UnicastRemoteObject implements Controller {

    private List<Ap> apList;
    private List<Association> associationList;
    private IpBank ipBank;



    ControllerImpl() throws RemoteException {
        apList = new LinkedList<Ap>();
        associationList = new LinkedList<Association>();
        ipBank = IpBank.getInstance();
    }

    public synchronized Association connect(Station station) throws RemoteException{
      if (apList.size() > 0) {
        Association aux = isStationConnected(station);
        Ap nearestAp = searchNearestAP(station.getPosition());
        
        if (aux != null) {
          aux.getStation().setPosition(station.getPosition());
          if (!nearestAp.getID().equals(aux.getAp().getID())){
            station.reconnect(nearestAp);
            aux.setAp(nearestAp);
            System.out.println(aux);
          } 
        } else {
          station.setIp(ipBank.assignIp());
          station.reconnect(nearestAp);
          aux = new Association(nearestAp, station);
          associationList.add(aux);
          System.out.println(aux);
        }

        return aux;
      } else {
        System.out.println("AP list is empty.");
        return null;
      }
    }

    public synchronized void disconnect(Station station) throws RemoteException{
        Association aux = isStationConnected(station);
        
        if (aux != null) {
          ipBank.freeIp(aux.getStation().getIp());
          associationList.remove(aux);
          System.out.println("\nThe station with the following parameters:\n" 
            + "\tMAC: " + station.getMac() + "\n\tIP: " +station.getIp() + "\n\tHostname: " +
          station.getHostname() + "\n\tPosition: " +station.getPosition() + "\nhas been disconnected.");
        }
    }

    public synchronized Boolean registerAp(Ap ap) throws RemoteException{
      Boolean result = false;

      if (isAPRegistered(ap))
        System.out.println("\nAP with ID " + ap.getID() + ", already registered.");
      else if ((result = apList.add(ap)) == true)
        System.out.println("\nAP with ID " + ap.getID() + ", successfully registered.");
      

      return result;
    }


    public synchronized void unregisterAp(Ap ap) throws RemoteException{
        Ap nearestAp;
        if (isAPRegistered(ap)) {
          for(Ap i: apList)
            if (i.getID().equals(ap.getID())) {
              apList.remove(i);
              System.out.println("\nAP with ID " + ap.getID() + ", has been unregistered.");
            } 

          for(Association j: associationList)
            if (j.getAp().getID().equals(ap.getID())) {
              if (apList.size() > 0) {
                nearestAp = searchNearestAP(j.getStation().getPosition());
                j.setAp(nearestAp);
                j.getStation().reconnect(nearestAp);
                System.out.println("\nReconnecting stations...\n" + j);
              } else {
                System.out.println("\nThere is not any AP to reconnect the stations...");
              }
            }
        } else
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
