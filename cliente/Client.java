import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.Date;


class Client {
    static public void main (String args[]) {
        if (args.length!=4) {
            System.err.println("Uso: Client hostregistro numPuertoRegistro IP MAC hostname");
            return;
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {

            Controller srv = (Controller) Naming.lookup("//" + args[0] + ":" + args[1] + "/Controller");
            // Station = Log
            Station station = new Station(args[2], args[3]); //, args[4]);
            // Ap = ServicioLog
            AP ap = srv.registerStation();
            // registerStation = crearLog

            List <AP> list;
            list = srv.getStations();

            for (Ap i:list) {
              int a = i.getID();
              System.out.println("Id es " + a);

            }
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en Client:");
            e.printStackTrace();
        }
    }
}
