
import java.io.*;
import java.net.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author colorless
 */
public class ClienteTCP {
    public static void main(String argv[]) throws IOException  {
        String comando;
        String respuesta;
        BufferedReader desdeUsuario = new BufferedReader(new InputStreamReader(System.in));
        Socket clienteSocket = new Socket("localhost", 6789);
        DataOutputStream haciaServidor = new DataOutputStream(clienteSocket.getOutputStream());
        BufferedReader desdeServidor = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
        comando = desdeUsuario.readLine();
        haciaServidor.writeBytes(comando + '\n');
        respuesta = desdeServidor.readLine();
        System.out.println(respuesta);
        clienteSocket.close();
    }
}
