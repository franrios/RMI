
import java.rmi.*;
import java.util.*;

interface Controller extends Remote {
  AP registerStation(Station station) throws RemoteException;
  List<AP> getStations() throws RemoteException;
}
