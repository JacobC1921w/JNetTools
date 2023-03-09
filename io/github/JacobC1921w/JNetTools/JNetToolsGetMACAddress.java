package io.github.JacobC1921w.JNetTools;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class JNetToolsGetMACAddress {
    public static String getMACAddress() {
        try {
          // Get hardware address in hex byte format. Initialize new string
          byte[] MACAddressBytes = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
          String[] MACHex = new String[MACAddressBytes.length];
    
          // Iterate through hardware address to convert bytes into strings
          for(int i = 0; i < MACAddressBytes.length; i++) {
            MACHex[i] = String.format("%02X", MACAddressBytes[i]);
          }
          return String.join(":", MACHex);
        } catch(Exception e) {
          return "00:00:00:00:00:00";
        }
      }
}
