import java.io.*;

class SSID implements Serializable {
    private String ssidname;
    private String password;
    
    SSID(String ssidname, String password) {
      this.ssidname = ssidname;
      this.password = password;
    }
    
    public String getSSIDname() {
      return ssidname;
    }

    public int getPassword(){
      return password;
    }


}