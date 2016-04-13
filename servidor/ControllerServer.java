import java.rmi.*;
import java.rmi.server.*;

class ControllerServer  {
    static public void main (String args[]) {
       if (args.length!=1) {
            System.err.println("Use: ControllerServer registryPortNumber");
            return;
        }
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            ControllerImpl srv = new ControllerImpl();
            Naming.rebind("rmi://localhost:" + args[0] + "/Controller", srv);
        }
        catch (RemoteException e) {
            System.err.println("Communication error: " + e.toString());
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("ControllerServer Exception:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
