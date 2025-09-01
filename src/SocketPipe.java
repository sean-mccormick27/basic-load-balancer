import java.io.*;
import java.net.Socket;

/**
 * A utility class used for piping data from one socket to another, run in its own thread.
 * The class reads data from the input socket and writes it to the output socket
 * until the input stream is closed or an I/O error occurs.
 */
public class SocketPipe implements Runnable {

    /** The input stream from which data will be read. */
    private final InputStream inputStream;

    /** The output stream to which data will be written. */
    private final OutputStream outputStream;

    /** The socket associated with the input stream. */
    private final Socket inputSocket;

    /** The socket associated with the output stream. */
    private final Socket outputSocket;

    /**
     * Constructs a new SocketPipe that will forward data from the input socket
     * to the output socket.
     *
     * @param inputSocket  the socket from which data will be read
     * @param outputSocket the socket to which data will be written
     * @throws IOException if an I/O error occurs while obtaining the socket streams
     */
    public SocketPipe(Socket inputSocket, Socket outputSocket) throws IOException {
        this.inputSocket = inputSocket;
        this.outputSocket = outputSocket;
        this.inputStream = new BufferedInputStream(inputSocket.getInputStream());
        this.outputStream = new BufferedOutputStream(outputSocket.getOutputStream());
    }

    /**
     * Continuously reads data from the input socket and writes it to the output socket.
     * The transfer continues until the input stream is closed or an I/O error occurs.
     * When the transfer is complete, both sockets are closed.
     */
    public void run() {
        byte[] buffer = new byte[4096]; // buffer for reading data
        int read;
        try {
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
                outputStream.flush();
            }
        } catch (IOException e) {
            System.err.println("Socket I/O error: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    /**
     * Cleanup method to close both the input and output sockets. It is called automatically
     * when the data transfer is complete or if an I/O error occurs.
     */
    private void cleanup() {
        try {
            inputSocket.close();
        } catch (IOException e) {
            System.err.println("Failed to close input socket: " + e.getMessage());
        }
        try {
            outputSocket.close();
        } catch (IOException e) {
            System.err.println("Failed to close output socket: " + e.getMessage());
        }
    }
}