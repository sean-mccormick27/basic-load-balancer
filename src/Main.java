import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int listenPort = 49152;
        int healthCheckInterval = 5000;
        BackendService backendService1 = new BackendService("localhost", 5000);
        BackendService backendService2 = new BackendService("localhost", 6000);
        BackendService[] backendServices = {
                backendService1, backendService2
        };
        new LoadBalancer(listenPort, healthCheckInterval, Arrays.asList(backendServices)).start();
    }
}
