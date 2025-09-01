import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int listenPort = 49152;
        BackendService backendService1 = new BackendService("service_1");
        BackendService backendService2 = new BackendService("service_2");
        backendService2.setOnline(false);
        BackendService backendService3 = new BackendService("service_3");
        BackendService[] backendServices = {
                backendService1, backendService2, backendService3
        };
        new LoadBalancer(listenPort, Arrays.asList(backendServices)).start();
    }
}
