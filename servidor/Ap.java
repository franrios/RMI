
import java.rmi.*;
/*
   Interfaz de la clase AP que representa a un punto de acceso
*/
interface Ap extends Remote {
	String getID() throws RemoteException;
	Position getPosition() throws RemoteException;
	void setID(String id) throws RemoteException;
	void setPosition(Position position) throws RemoteException;
	Association connect (Station station) throws RemoteException;
	void disconnect (Station station) throws RemoteException;
	void unregisterAp () throws RemoteException;
}
