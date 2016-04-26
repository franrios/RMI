import java.rmi.*;
import java.rmi.server.*;

class ApServer  {
    static public void main (String args[]) {
        String registryPortNumber;
        String controllerHost;
        String id;
        double x;
        double y;
        double z;
       //Comprobamos que el número de argumentos es el correcto y si no imprimimos
       //el error
       if (args.length!=6) {
            System.err.println("Use: ApServer controllerHost registryPortNumber id position(x) position(y) position(z)");
            return;
        } else {
            //Asignamos cada argumento de linea de comandos a cada una de las variables
            controllerHost = args [0];
            registryPortNumber = args[1];
            id = args[2];
            x = Double.parseDouble(args[3]);
            y = Double.parseDouble(args[4]);
            z = Double.parseDouble(args[5]);
        }

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        try {
            ApImpl srv = new ApImpl(controllerHost, registryPortNumber, id, new Position(x,y,z));
            Naming.rebind("rmi://localhost:" + registryPortNumber + "/Ap" + id, srv);
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
