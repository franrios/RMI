import java.io.*;

class Ssid implements Serializable {
    private String ssidName;
    private String password;

    Ssid (String ssidname, String password) {
      this.ssidName = ssidName;
      this.password = password;
    }

    public String getSsidName() {
      return ssidName;
    }

    public String getPassword(){
      return password;
    }

    public void setSsidName(String ssidName) {
      this.ssidName = ssidName;    
    }

    public void setPassword(String password){
      this.password = password;
    }


}
