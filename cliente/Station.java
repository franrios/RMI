import java.rmi.*;

interface Station extends Remote {
    String getIp() throws RemoteException;
    String getMac() throws RemoteException;
    String getHostname() throws RemoteException;
    Position getPosition() throws RemoteException;
    Ap getSrv() throws RemoteException;
    void setIp(String ip) throws RemoteException;
    void setMac(String mac) throws RemoteException;
    void setHostname(String hostname) throws RemoteException;
    void setPosition(Position position) throws RemoteException;
    void setSrv(Ap srv) throws RemoteException;
    void randPosition(double min, double max) throws RemoteException;
    void reconnect(Ap srv) throws RemoteException;
}
