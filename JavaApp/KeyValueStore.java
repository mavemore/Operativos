import java.io.*;
import static java.lang.Integer.parseInt;
import java.net.*;

public class KeyValueStore {
    static int port;

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try{
            String puerto = args[0];
            if(!puerto.matches("[0-9]+") && puerto.length() < 5){
                System.out.println("ERROR: argumento no valido.");
                return;
            }
        }catch(Exception e){
            System.out.println("ERROR: argumento no valido.");
            return;
        }
        try {
            serverSocket = new ServerSocket(parseInt(args[0]));
        } catch (Exception e) {
            System.out.println("ERROR: Problemas al abrir el socket.");
            return;

        }
        while (true) {
            try {
                socket = serverSocket.accept();
                // new threa for a client
                new TCPServer(socket).start();
            } catch (IOException e) {
                System.out.println("ERROR: No pudo establecer la conexion con el cliente.");
                return;
            }
            
        }
    }
}
