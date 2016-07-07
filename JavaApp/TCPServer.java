import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class TCPServer extends Thread{
    public static HashMap storeValue = new HashMap();
    protected Socket socket;

    public TCPServer(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        String comm;
        String retornoUsuario="";
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            retornoUsuario = "ERROR: No se pudo leer el socket.";
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                comm = line.split(" ")[0];
                if (line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    if(comm.toLowerCase().equals("get") && line.split(" ").length == 2){
                        if(storeValue.containsKey(line.split(" ")[1])){
                            retornoUsuario = "key="+storeValue.get(line.split(" ")[1]);
                        }else{
                            retornoUsuario = "key=";
                        }
                    }else if(comm.toLowerCase().equals("set") && line.split(" ").length == 3){
                        storeValue.put(line.split(" ")[1],line.split(" ")[2]);
                        retornoUsuario = "Ok";
                    }else if(comm.toLowerCase().equals("del") && line.split(" ").length == 2){
                        if(storeValue.containsKey(line.split(" ")[1])){
                            storeValue.remove(line.split(" ")[1]);
                            retornoUsuario = "Eliminado";
                        }
                    }else if(comm.toLowerCase().equals("list".toLowerCase()) && line.split(" ").length == 1){
                        Collection c = storeValue.values();
                        Iterator itr = c.iterator();
                        String lista = new String();
                        while (itr.hasNext()) {
                            if(lista.isEmpty()){
                                lista = ""+itr.next();
                            }else{
                                lista = lista + ", "+ itr.next();
                            }
                        }
                        retornoUsuario = lista; 
                    } else {
                        retornoUsuario = "ERROR: Comando no valido";
                    }
                    out.writeBytes(retornoUsuario + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                retornoUsuario = "ERROR: No se pudo leer la linea";
                return;
            }
        }
    }
}    



