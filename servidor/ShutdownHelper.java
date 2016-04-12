public class ShutdownHelper extends Thread {
	ApImpl ap;

   public ShutdownHelper(ApImpl ap) {
   		super();
   		this.ap = ap;
   }

   public void run() {
   	    try {
            System.out.println("The AP is being disconnected from the Controller...");
            ap.unregisterAp();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.err.println(e);
        }
   }
}