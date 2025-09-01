import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int listenPort = 49152;
        BackendService[] backendServices = {
                new BackendService("service_1"),
                new BackendService("service_2"),
                new BackendService("service_3")
        };
        new LoadBalancer(listenPort, Arrays.asList(backendServices)).start();
    }
}
