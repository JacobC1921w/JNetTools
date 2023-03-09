package io.github.JacobC1921w.JNetTools;
import java.net.InetAddress;

public class JNetToolsGetHostname {
  
  public static String getHostName() {
    try {
      // Attempt to retrieve the host machines host name
      return InetAddress.getLocalHost().getHostName();
    } catch(Exception e) {

      // Return nothing if we can't find anything
      return "";
    }
  }
}