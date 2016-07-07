import java.io.*;
import static java.lang.Integer.parseInt;
import java.net.*;

public class TCPClient {
    public static void main(String argv[]) throws Exception {
        String sentence="";
        String modifiedSentence="";
	Boolean flag = true;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        try{
            String puerto = argv[0];
            if(!puerto.matches("[0-9]+") && puerto.length() < 5){
                System.out.println("ERROR: argumento puerto no valido.");
                return;
            }
        }catch(Exception e){
            System.out.println("ERROR: argumento puerto no valido.");
            return;
        }
		try{
            String ip = argv[0];
            if(!(ip.matches("[0-9.]+") || ip.toLowerCase().equals("localhost"))){
                System.out.println("ERROR: argumento ip no valido.");
                return;
            }
        }catch(Exception e){
            System.out.println("ERROR: argumento ip no valido.");
            return;
        }
        try {
            Socket clientSocket = new Socket(argv[0], parseInt(argv[1]));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
             BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
	while(flag){
            System.out.println("Ingresar comando: ");
            try
            {
                sentence = inFromUser.readLine();
                verificarPalabra(sentence);
                
                if(sentence.trim().compareTo("help") == 0){
                    System.out.println("Comandos Soportados: ");
			System.out.println("Comando: get  parametros: llave descripción: Retorna el valor asociado a dicha clave.");
			System.out.println("Comando: set  parametros: llave, valor descripción: Almacena la clave con el valor asociado.");
			System.out.println("Comando: del  paramaetros: llave descripción: Elimina la clave, con su valor asociado.");
			System.out.println("Comando: list  parametros: No descripción: Retorna la lista de todas las claves almacenadas.");
			System.out.println("Comando: exit  parametros: No descripción: Termina la conexion con el servidor y posteriormente termina la ejecucion del programa cliente.");
                }
                else{
                    flag = false;
                }
                
            }    
            catch (Error e){
               //e.printStackTrace();
                System.out.println(e.getMessage());
            }
        
        }
	if(sentence.compareTo("exit")== 0){
            outToServer.close();
            inFromServer.close();
            clientSocket.close();    
        }else{
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println(modifiedSentence);
            clientSocket.close();
        } 
        } catch (Exception e) {
            System.out.println("ERROR: Problemas al abrir el socket.");
            return;

        }
    }
    public static void verificarPalabra(String word) throws Error {
        
        String error1 = "comando no reconocido :";
        String error2 = "llave con saltos de linea, espacios o tabs";
        String error3 = "valor con salto de linea";
        
        String temp = word;
        
        String[] parts = temp.split(" ");
        String comando = parts[0].toLowerCase();
        int tam= 1;
        if (comando.compareTo("get") == 0 || comando.compareTo("del")== 0){
            tam = 2;
        }
        else if (comando.compareTo("set") == 0){
            tam = 3;
        } 
        String key = "";
        String value = "";
        switch (tam) {
            case 1: 
                    parts = temp.split(" ", 1);
                    comando = parts[0].toLowerCase();
                    if(verificarComando(comando)){
                        throw new Error(error1 + comando);
                    }
                    break;
            case 2: 
                    parts = temp.split(" ", 2);
                    comando = parts[0].toLowerCase(); 
                    key = parts[1];
                    if(verificarComando(comando)){
                        throw new Error(error1 + comando);
                    }
                    else if(verificarKey(key)){
                        throw new Error(error2);
                    }
                    else {
                        return;
                    }
                
            case 3: 
                    comando = parts[0].toLowerCase(); 
                    key = parts[1]; 
                    value = parts[2];
                    if(verificarKey(key)){
                        throw new Error(error2);
                    }
                    else if (verificarValue(value)){
                        throw new Error(error3);
                    }
                    else if (verificarComando(comando)){
                        throw new Error(error1 + comando);
                    }
                    else {
                        return;
                    }
        } 
    }
    
   public static boolean verificarKey(String key){
        for (char c : key.toCharArray()){     
            if ( (c == '\t') || (c == '\n') || (c == '\r') || (c==' ') ){
               return true; 
            } 
        }
        return false;
   }
   public static boolean verificarValue(String value){
        for (char c : value.toCharArray()){         
            if ((c == '\n')){
               return true; 
            } 
        }
        return false;
   }
   public static boolean verificarComando(String comando){
        if  (comando.compareTo("get") == 0){ //||  || comando.compareTo("del") != 0 || comando.compareTo("list") != 0 ){
            return false;
        }
        if (comando.compareTo("set") == 0){
            return false;
        }
        if (comando.compareTo("del") == 0){
            return false;
        }
        if (comando.compareTo("list") == 0){
            return false;
        }
        if(comando.compareTo("help") == 0){
            return false;
        }
        if(comando.compareTo("exit") == 0){
            return false;
        }
        return true;
   }
}
