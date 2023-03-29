package io.github.JacobC1921w.JNetTools;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Lots of this couldn't be possible without the help of ChatGPT lol. Massive thanks to that amazing tool.

public class JNetToolsHostScan implements Runnable {
	
	// Global variables
    private final float timeout;
    private final int threads;
    private List<String> hostsToScan = new ArrayList<String>();
    private List<String> onlineHosts = new ArrayList<String>();

    // Constructor, adds all hosts in subnet to an array
    public JNetToolsHostScan(String routerIP, int threads, float timeout) {
        this.timeout = timeout;
        this.threads = threads;

        // Convert our routers ip format, i.e 192.168.0.1=>192.168.0. 10.0.0.138=>10.0.0.
        String IPSuffix = routerIP.substring(0, routerIP.lastIndexOf('.') + 1);
        for (int i = 1; i <= 254; i++) {
        	hostsToScan.add(IPSuffix + i);
        }
    }

    // Run method for multithreading. Sets up timeouts and logic for each thread
    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<String> localOnlineHosts = new ArrayList<String>();
        
        // Run PingWorker for each host in the hosts list
        for (String host: hostsToScan) {
            executor.execute(new PingWorker(host, timeout, localOnlineHosts));
        }

        executor.shutdown();
        
        // Kill each thread after a specified timeout
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            //
        }

        // Merge the local list of online hosts with the global list
        synchronized(onlineHosts) {
            onlineHosts.addAll(localOnlineHosts);
        }
    }

    // This handles the actual ping functionality
    private static class PingWorker implements Runnable {

    	// Global variables throughout this class
        private final String host;
        private final float timeout;
        private final List<String> onlineHosts;

        // Constructor to set variables
        public PingWorker(String host, float timeout, List<String> onlineHosts) {
            this.host = host;
            this.timeout = timeout;
            this.onlineHosts = onlineHosts;
        }

        @Override
        public void run() {
            try {
            	// This is the ping code
                InetAddress address = InetAddress.getByName(host);
                boolean reachable = address.isReachable((int) timeout);
                if (reachable) {
                    synchronized(onlineHosts) {
                    	// Add the host to the online hosts list
                        onlineHosts.add(host);
                    }
                }
            } catch (Exception e) {
                //
            }
        }
    }

    public List<String> getOnlineHosts() {
        return onlineHosts;
    }

    /**
     * Scans the given <i>IP</i> subnet for reachable hosts
     * @param routerIP		Our routers IP address
     * @param threads		The amount of threads to run concurrently (default 10)
     * @param timeout		The timeout in milliseconds before each thread is killed (default 100)
     * @return				List<String> of online hosts
     */
    public static List<String> hostScan(String routerIP, int threads, float timeout) {
    	
    	// Initialize our scanner, start the thread
        JNetToolsHostScan scanner = new JNetToolsHostScan(routerIP, threads, timeout);
        Thread scannerThread = new Thread(scanner);
        scannerThread.start();
        
        // Pause this thread until all other threads are finished
        try {
            scannerThread.join();
        } catch (InterruptedException e) {
            //
        }
        return scanner.getOnlineHosts();
    }
    
    /**
     * Scans the given <i>IP</i> subnet for reachable hosts
     * @param routerIP		Our routers IP address
     * @param timeout		The timeout in milliseconds before each thread is killed (default 100)
     * @return				List<String> of online hosts
     */
    public static List<String> hostScan(String routerIP, float timeout) {
    	return hostScan(routerIP, 10, timeout);
    }
    
    /**
     * Scans the given <i>IP</i> subnet for reachable hosts
     * @param routerIP		Our routers IP address
     * @param threads		The amount of threads to run concurrently (default 10)
     * @return				List<String> of online hosts
     */
    public static List<String> hostScan(String routerIP, int threads) {
    	return hostScan(routerIP, threads, (float) 100);
    }
    /**
     * Scans the given <i>IP</i> subnet for reachable hosts
     * @param routerIP		Our routers IP address
     * @return				List<String> of online hosts
     */
    public static List<String> hostScan(String routerIP) {
    	return hostScan(routerIP, 10, (float) 100);
    }
    
}
