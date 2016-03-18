import java.io.*;

class Ssid implements Serializable {
    private String ssidname;
    private String password;

    SSID(String ssidname, String password) {
      this.ssidname = ssidname;
      this.password = password;
    }

    public String getSsidname() {
      return ssidname;
    }

    public int getPassword(){
      return password;
    }


}
