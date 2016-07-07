import java.io.*;
import java.net.*;

public class KeyValueStore {
    static final int PORT = 6789;

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("ERROR: Problemas al abrir el socket.");

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("ERROR: No pudo establecer la conexion con el cliente.");
            }
            // new threa for a client
            new TCPServer(socket).start();
        }
    }
}
