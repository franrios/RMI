import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class ControllerImpl extends UnicastRemoteObject implements Controller {

    //Lista de APs que gestiona el Controller.
    private List<Ap> apList;
    //Lista de asociaciones estación-AP que gestiona el Controller.    
    private List<Association> associationList;
    //Generador de IPs dinámicas.
    private IpBank ipBank;



    /*
      Constructor de la clase ControllerImpl.
    */
    ControllerImpl() throws RemoteException {
        apList = new LinkedList<Ap>();
        associationList = new LinkedList<Association>();
        ipBank = IpBank.getInstance();
    }

    /*
    Metodo Association. Comprueba si un usuario ya esta asociado y si no lo esta 
    crea un nuevo objeto de la clase Association con el AP más cercano.
    */
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

    /*
    Metodo disconnect. Metodo que desconecta una estacion de un AP.
    */
    public synchronized void disconnect(Station station) throws RemoteException{
        Association aux = isStationConnected(station);
        //Comprobamos si la estacion esta conectada a algun AP  
        //Si lo esta, la desconectamos.      
        if (aux != null) {
          ipBank.freeIp(aux.getStation().getIp());
          associationList.remove(aux);
          System.out.println("\nThe station with the following parameters:\n" 
            + "\tMAC: " + station.getMac() + "\n\tIP: " +station.getIp() + "\n\tHostname: " +
          station.getHostname() + "\n\tPosition: " +station.getPosition() + "\nhas been disconnected.");
        }
    }
    /*
    Metodo registerAp. Comprueba si un AP ya esta en la lista de APs.
    */
    public synchronized Boolean registerAp(Ap ap) throws RemoteException{
      Boolean result = false;

      if (isAPRegistered(ap))
        System.out.println("\nAP with ID " + ap.getID() + ", already registered.");
      else if ((result = apList.add(ap)) == true)
        System.out.println("\nAP with ID " + ap.getID() + ", successfully registered.");
      

      return result;
    }

    /*
    Metodo unregisterAp. Metodo que elimina un AP de su lista de APs.
    */
    public synchronized void unregisterAp(Ap ap) throws RemoteException{
        Ap nearestAp;
        if (isAPRegistered(ap)) {
          //Buscamos el AP en la lista
          for(Ap i: apList)
          //Si lo encontramos lo eliminamos
            if (i.getID().equals(ap.getID())) {
              apList.remove(i);
              System.out.println("\nAP with ID " + ap.getID() + ", has been unregistered.");
            } 
          //Si el AP tubiera alguna estacion conectada, la reconectamos al AP
          //mas cercano.
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
    /*
    Metodo isStationConnected. Comprueba si una estacion ya esta conectada a un AP
    */
    private Association isStationConnected(Station station) throws RemoteException{
      Association aux = null;

      for (Association i: associationList) {
        if (i.getStation().getMac().equals(station.getMac()))
          aux = i;
      }
      return aux;
    }
    /*
    Metodo isAPRegistered. Comprueba si ya tiene un AP registrado en su lista en 
    función del ID. Evita repeticiones de ID.
    */
    private Boolean isAPRegistered(Ap ap) throws RemoteException{
      Boolean result = false;

      for(Ap i: apList)
        if (i.getID().equals(ap.getID()))
          result = true;

      return result;
    }
    /*
    Metodo searchNearestAP. Busca en su lista de APs el más cercano a la estacion,
    en función de la posicion de la misma.
    */
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
