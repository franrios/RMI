
import java.rmi.*;
import java.util.*;

interface Controller extends Remote {

  Ap connect(Station station) throws RemoteException; /*  Check if a user is already associated and reassociate it or
                                                          create a new association */
  Boolean registerAp(Ap ap) throws RemoteException;
  Association isStationConnected(Station station) throws RemoteException;
}
