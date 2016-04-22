import java.rmi.*;

public class ShutdownHelper extends Thread {
	Ap ap;
  Station station;

   public ShutdownHelper(Ap ap) {
   		super();
   		this.ap = ap;
      this.station = null;
   }

   public ShutdownHelper(Station station) {
      super();
      try {
        this.ap = station.getSrv();
      } catch (RemoteException e) {
        System.err.println("Communication Error: " + e.toString());
      }
      this.station = station;
   }

   public void run() {
   	    try {
          if (station == null) {
            System.out.println("The AP is being disconnected from the Controller...");
            ap.unregisterAp();
          } else {
            System.out.println("The Station is being disconnected...");
            ap.disconnect(station);
          }
        } catch (Exception e) {
            System.err.println(e);
        }
   }
}