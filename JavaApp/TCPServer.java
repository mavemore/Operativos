import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer {
    private static TCPServer server; 
    private ServerSocket serverSocket;

    //maximo 10 hilos
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);        

    public TCPServer(int port){
        server.runServer(port);
    }

    private void runServer(int port) {        
        int serverPort = port;
        try {
            //inicia servidor
            serverSocket = new ServerSocket(serverPort); 

            while(true) {
                //espera hasta que llegue request
                try {
                    Socket s = serverSocket.accept();
                    //procesa request
                    executorService.submit(new ServiceRequest(s));
                } catch(IOException ioe) {
                    System.out.println("ERROR: No se pudo establecer la conexión");
                }
            }
        }catch(IOException e) {
            System.out.println("ERROR: No se pudo inicar el servidor en el puerto "+serverPort);
        }
    }

    //Call the method when you want to stop your server
    private void stopServer() {
        //Stop the executor service.
        executorService.shutdownNow();
        try {
            //Stop accepting requests.
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("ERROR: Ocurrió una falla al cerrar el servidor");
        }
        System.exit(0);
    }

    class ServiceRequest implements Runnable {

        private Socket socket;

        public ServiceRequest(Socket connection) {
            this.socket = connection;
        }

        @Override
        public void run() {
            String clientSentence;
            String capitalizedSentence;
            try {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                clientSentence = inFromClient.readLine();
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
            } catch (IOException ex) {
                    Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            try {
                socket.close();
            }catch(IOException ioe) {
                System.out.println("ERROR: Ocurrió una falla en la conexion con el cliente");
            }
        }        
    }
}

