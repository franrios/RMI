
import java.rmi.*;

interface AP extends Remote {
	void addSSID(string ssid) throws RemoteException;
	void removeSSID(string ssid) throws RemoteException;
	int getMaxSSID() throws RemoteException;
	int getID() throws RemoteException;

}
