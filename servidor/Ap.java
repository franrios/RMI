
import java.rmi.*;

interface Ap extends Remote {
	//void addSSID(String ssid) throws RemoteException;
	//void removeSSID(String ssid) throws RemoteException;
	//int getMaxSSID() throws RemoteException;
	String getID() throws RemoteException;
	Position getPosition() throws RemoteException;
	void setID(String id) throws RemoteException;
	void setPosition(Position position) throws RemoteException;
	Association connect (Station station) throws RemoteException;
	void disconnect (Station station) throws RemoteException;
	void unregisterAp () throws RemoteException;
}
