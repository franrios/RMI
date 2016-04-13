import java.io.*;

public class IpBank {

  public static final String IP_BASE = "192.168.0.0";

  private static IpBank instance = null;
  private String ipParts[] = null;
  private Boolean assignedIps[] = null;

  private IpBank() {
    ipParts = IP_BASE.split("\\.");
    assignedIps = new Boolean[256];
    assignedIps[0] = true;
    assignedIps[1] = true;
    for (int i = 2; i < 255; i++)
      assignedIps[i] = false;
    assignedIps[255] = true;
  }

  public static IpBank getInstance() {
    if(instance == null) {
      instance = new IpBank();
    }
    return instance;
  }

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

  public void freeIp (String ip) {
    String parts[] = ip.split("\\.");
    assignedIps[Integer.parseInt(parts[3])] = false;
  }
}