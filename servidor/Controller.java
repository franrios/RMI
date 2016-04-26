
import java.rmi.*;
import java.util.*;
/*
  Interfaz de la clase Controller
*/
interface Controller extends Remote {

  Association connect(Station station) throws RemoteException; 
  void disconnect(Station station) throws RemoteException;
  Boolean registerAp(Ap ap) throws RemoteException;
  void unregisterAp(Ap ap) throws RemoteException;
}
