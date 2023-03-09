package io.github.JacobC1921w.JNetTools;

import java.util.Scanner;

public class JNetToolsGetPublicIP {
    public static String getPublicIP() {
        try {
          try (
            // Connect to Ipifys API and return the stripped output
            Scanner APIScanner = new Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            String IP = APIScanner.next();
            APIScanner.close();
            return IP;
          }
        } catch (Exception e) {
          // Return 0.0.0.0 as a fallback in case of errors
          return "0.0.0.0";
        }
    }
}