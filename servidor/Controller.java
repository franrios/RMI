
import java.rmi.*;
import java.util.*;

interface Controller extends Remote {

  Association connect(Station station) throws RemoteException; /*  Check if a user is already associated and reassociate it or
                                                          create a new association */
  void disconnect(Station station) throws RemoteException;
  Boolean registerAp(Ap ap) throws RemoteException;
  void unregisterAp(Ap ap) throws RemoteException;
}
