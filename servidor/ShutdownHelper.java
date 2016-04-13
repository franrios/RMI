public class ShutdownHelper extends Thread {
	Ap ap;
  Station station;

   public ShutdownHelper(Ap ap) {
   		super();
   		this.ap = ap;
      this.station = null;
   }

   public ShutdownHelper(Ap ap, Station station) {
      super();
      this.ap = ap;
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