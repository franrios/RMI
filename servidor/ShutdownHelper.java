import java.rmi.*;
/*
  Clase ShutdownHelper.
  Es un gestor del apagado,captura el evento Ctrl +C 
  y se encarga de llamar al método correspondiente.
*/
public class ShutdownHelper extends Thread {
	Ap ap;
  Station station;

    /*
      Constructor de la clase en el caso de capturar el evento para un AP.
    */
   public ShutdownHelper(Ap ap) {
   		super();
   		this.ap = ap;
      this.station = null;
   }
    /*
      Constructor de la clase en el caso de capturar el evento para una estacion.
    */
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
