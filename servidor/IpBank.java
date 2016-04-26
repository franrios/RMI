import java.io.*;

public class IpBank {

  public static final String IP_BASE = "192.168.0.0";

  private static IpBank instance = null;
  private String ipParts[] = null;
  private Boolean assignedIps[] = null;
  /*
     Constructor de la clase IpBank
  */
  private IpBank() {
    ipParts = IP_BASE.split("\\.");
    //Rango de IPs para asignar
    assignedIps = new Boolean[256];
    //Eliminamos las IPs .0 (red) y .1 (Controller)
    assignedIps[0] = true;
    assignedIps[1] = true;
    //Rellenamos la tabla para indicar que IPs puede asignar
    for (int i = 2; i < 255; i++)
      assignedIps[i] = false;
    //Eliminamos la IP de difusion
    assignedIps[255] = true;
  }

    /*
      Metodo para crear un objeto de la clase IpBank
    */ 
  public static IpBank getInstance() {
    if(instance == null) {
      instance = new IpBank();
    }
    return instance;
  }

    /*
      Metodo para asignar una IP a un AP o a una estacion
    */ 
  public String assignIp () {
    String ip = null;
    for (int i = 0; (i < 256) && (ip == null); i++){
      if (assignedIps[i] == false) {
        ip = ipParts[0] + "." + ipParts[1] + "." + ipParts[2] + "." + i;
        assignedIps[i] = true;
      }
    }
    return ip;
  }

   /*
      Metodo para liberar una IP que ya no esta en uso
    */
  public void freeIp (String ip) {
    String parts[] = ip.split("\\.");
    assignedIps[Integer.parseInt(parts[3])] = false;
  }
}
