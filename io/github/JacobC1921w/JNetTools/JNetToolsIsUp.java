package io.github.JacobC1921w.JNetTools;

import java.net.InetSocketAddress;
import java.net.Socket;

public class JNetToolsIsUp {
    
  /**
   * Checks whether the given <i>port</i> is open on the specified <i>IP</i>
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
   * Checks whether the given <i>port</i> is open on the specified <i>IP</i>
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
}
