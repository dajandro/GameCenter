/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author DanielAlejandro
 */
public class GameRequest implements Runnable {
    
    private RequestParser requestParser;
    private Socket socket;
    private int size = 1000;

    public GameRequest(Socket socket, RequestParser rp) {
        this.socket = socket;
        this.requestParser = rp;
    } 

    @Override
    public void run(){
        try{
            String request = "";
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //while(true){
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                //int size = in.readInt();
                //System.out.println("read int");
                byte[] request_bytes = new byte[size];
                in.read(request_bytes);
                System.out.println("C: " + new String(request_bytes));
                request = new String(request_bytes);                
                String response = requestParser.parse(request);                
                //out.writeInt(response.length());
                //System.out.println("write int");
                out.write(response.getBytes());                
                System.out.println("S: " + response);
            //}
            socket.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            //System.exit(1);
        }
    }
    
}
