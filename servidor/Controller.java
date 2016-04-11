
import java.rmi.*;
import java.util.*;

interface Controller extends Remote {
  Association isStationConnected(Station station) throws RemoteException;
  /*  Check if a user is already associated and reassociate it or create a new association */
  Ap connect(Station station) throws RemoteException;
  Boolean registerAp(Ap ap) throws RemoteException;
}
