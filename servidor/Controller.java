
import java.rmi.*;
import java.util.*;

interface Controller extends Remote {

  Association connect(Station station) throws RemoteException; /*  Check if a user is already associated and reassociate it or
                                                          create a new association */
  Boolean registerAp(Ap ap) throws RemoteException;
}
