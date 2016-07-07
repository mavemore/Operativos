
import java.io.*;
import java.net.*;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author colorless
 */
public class ServidorTCP {
    public static void main(String args[]) throws IOException  {
        HashMap storeValue = new HashMap();
        storeValue.put("1","uno");
        storeValue.put("2","dos");
        storeValue.put("3","tres");
        String comando;
        String[] comandoParsed;
        String comandoSplit = null;
        ServerSocket servidor = new ServerSocket(6789);

        while(true) {
            Socket conexion = servidor.accept();
            BufferedReader desdeCliente = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            DataOutputStream HaciaCliente = new DataOutputStream(conexion.getOutputStream());
            comando = desdeCliente.readLine();
            comandoParsed = comando.split(" ");
            if(comandoParsed[0].equals("get".toLowerCase()) && comandoParsed.length == 2){
                if(storeValue.containsKey(comandoParsed[1])){
                    comandoSplit = "key="+comandoParsed[1];
                }else{
					comandoSplit = "key=";
				}
            } else if(comandoParsed[0].equals("set".toLowerCase()) && comandoParsed.length == 3){
                storeValue.put(comandoParsed[1],comandoParsed[2]);
                comandoSplit = "El valor de la llave "+ comandoParsed[1]+" es "+comandoParsed[2];
            }else if(comandoParsed[0].equals("del".toLowerCase()) && comandoParsed.length == 2){
                if(storeValue.containsKey(comandoParsed[1])){
                    storeValue.remove(comandoParsed[1]);
                    comandoSplit = "Ok";
				}
            }else if(comandoParsed[0].equals("list".toLowerCase()) && comandoParsed.length == 1){
                    comandoSplit = "no"; //storeValue.keySet().toString();
            } else {
                comandoSplit = "ERROR: Comando no valido";
            }
            HaciaCliente.writeBytes(comandoSplit);
        }
    }
}
