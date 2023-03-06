// COMPILE: javac JNetTools.java
// RUN: java JNetTools

import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class JNetTools {

  /**
   * Checks wether the given <i>port</i> is open on the specified <i>IP</i>
   * @param IP        The remote IP address to connect to. Value is of type <code>String</code>. Example: <code>"192.168.0.1"</code>.
   * @param port      The port of the remote IP address to connect to. Value is of type <code>int</code>. Example: <code>80</code>.
   * @param timeout   The time in milliseconds allowed for connection. Value is of type <code>int</code>. Example: <code>500</code>.
   * @return          <code>true</code> if the port is open on the remote host;
   *                  <code>false</code> otherwise.
   */
  public static boolean isUp(String IP, int port, int timeout) {
    try {

      // Create a new socket object
      Socket clientSocket = new Socket();

      // Connect to socket using specified IP, port, and a timeout. Close socket directly afterwards.
      clientSocket.connect(new InetSocketAddress(IP, port), timeout);
      clientSocket.close();
      return true;
    } catch (Exception e) {

      // If any exceptions occur, return false.
      return false;
    }
  }

  /**
   * Checks wether the given <i>port</i> is open on the specified <i>IP</i>
   * @param IP        The remote IP address to connect to. Value is of type <code>String</code>. Example: <code>"192.168.0.1"</code>.
   * @param port      The port of the remote IP address to connect to. Value is of type <code>int</code>. Example: <code>80</code>.
   * @return          <code>true</code> if the port is open on the remote host;
   *                  <code>false</code> otherwise.
   *
   */
  public static boolean isUp(String IP, int port) {
    // Run isUp with a default timeout of 500ms
    return isUp(IP, port, 500);
  }

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

  public static String getPrivateIP() {
    try {
      // Create a UDP socket object
      final DatagramSocket UDPSocket = new DatagramSocket();

      // Connect to 8.8.8.8, googles dns. this is only so we can get the cards default interface.
      UDPSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);

      // Return our IP address
      return UDPSocket.getLocalAddress().getHostAddress();
    } catch (Exception e) {

      // Return "127.0.0.1" if there are any errors
      return "127.0.0.1";
    }
  }

  public static String getPublicIP() {
    try {
      // Connect to Ipifys API and return the stripped output
      return new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A").next();
    } catch (Exception e) {
      // Return 0.0.0.0 as a fallback incase of errors
      return "0.0.0.0";
    }
  }

  public static String getHostName() {
    try {
      // Attempt to retrieve the host machines hostname
      return InetAddress.getLocalHost().getHostName();
    } catch(Exception e) {

      // Return nothing if we cant find anything
      return "";
    }
  }

  public static String getMACAddress() {
    try {
      // Get hardware address in hexbyte format. Initialize new string
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

  // debug
  public static void main(String args[]) {
    System.out.println("Host 192.168.0.1:80 is: " + (isUp("192.168.0.1", 80) ? "up" : "down"));
  }
}
