package io.github.JacobC1921w.JNetTools;

import java.net.InetAddress;

public class JNetToolsPing {
    public static boolean ping(String IP, int timeout) {
        try {
          // If privileged, send an ICMP ping, otherwise send a packet to port 7 (echo)
          return InetAddress.getByName(IP).isReachable(timeout);
        } catch (Exception e) {
          return false;
        }
      }
    
      public static boolean ping(String IP) {
        // Call ping with a default timeout of 100ms
        return ping(IP, 100);
      }
}