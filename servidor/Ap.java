
import java.rmi.*;

interface Ap extends Remote {
	//void addSSID(String ssid) throws RemoteException;
	//void removeSSID(String ssid) throws RemoteException;
	//int getMaxSSID() throws RemoteException;
	int getID() throws RemoteException;

}
