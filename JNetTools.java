// COMPILE: javac JNetTools.java
// RUN: java JNetTools

// TODO
// portScan(localIP, port, threads = 10, timeout = 0.5)
// getOpenPorts(IP, portRangeStop = 65535, portRangeStart = 1, threads = 10, timeout = 1.5)
// getOpenPortsFromList(IP, portList, threads = 10, timeout = 1.5)
// hostScan(localIP, threads = 10, timeout = 0.5)

import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class JNetTools {

  public static boolean isUp(String IP, int port, int timeout) {
    try {
      Socket clientSocket = new Socket();
      clientSocket.connect(new InetSocketAddress(IP, port), timeout);
      clientSocket.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean isUp(String IP, int port) {
    return isUp(IP, port, 500);
  }

  public static boolean ping(String IP, int timeout) {
    try {
      return InetAddress.getByName(IP).isReachable(timeout);
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean ping(String IP) {
    return ping(IP, 100);
  }

  public static String getPrivateIP() {
    try {
      final DatagramSocket UDPSocket = new DatagramSocket();
      UDPSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
      return UDPSocket.getLocalAddress().getHostAddress();
    } catch (Exception e) {
      return "127.0.0.1";
    }
  }

  public static String getPublicIP() {
    try {
      return new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A").next();
    } catch (Exception e) {
      return "0.0.0.0";
    }
  }

  public static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch(Exception e) {
      return "";
    }
  }

  public static String getMACAddress() {
    try {
      byte[] MACAddressBytes = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
      String[] MACHex = new String[MACAddressBytes.length];
      for(int i = 0; i < MACAddressBytes.length; i++) {
        MACHex[i] = String.format("%02X", MACAddressBytes[i]);
      }
      return String.join(":", MACHex);
    } catch(Exception e) {
      return "00:00:00:00:00:00";
    }
  }

  public static void main(String args[]) {
    System.out.println("Host 192.168.0.1:80 is: " + (isUp("192.168.0.1", 80) ? "up" : "down"));
  }
}
