public class Main {

    public static void main(String[] args) {
        int listenPort = 49152;
        new LoadBalancer(listenPort).start();
    }
}
