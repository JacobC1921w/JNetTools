package io.github.JacobC1921w.JNetTools;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class JNetToolsGetPrivateIP {
  public static String getPrivateIP() {
    try {
      // Create a UDP socket object
      final DatagramSocket UDPSocket = new DatagramSocket();

      // Connect to 8.8.8.8, googles DNS. this is only so we can get the cards default interface.
      UDPSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);

      // Return our IP address
      String IP = UDPSocket.getLocalAddress().getHostAddress();
      UDPSocket.close();
      return IP;
    } catch (Exception e) {

      // Return "127.0.0.1" if there are any errors
      return "127.0.0.1";
    }
  }
}