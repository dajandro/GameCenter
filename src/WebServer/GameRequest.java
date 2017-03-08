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
    
    private RequestParser requestParser = new RequestParser();
    private Socket socket;

    public GameRequest(Socket socket) {
        this.socket = socket;
    } 

    @Override
    public void run(){
        try{
            String request = "";
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while(true){
                int size = in.readInt();
                byte[] request_bytes = new byte[size];
                in.read(request_bytes);
                System.out.println("C: " + new String(request_bytes));
                request = new String(request_bytes);                
                String response = requestParser.parse(request);                
                out.writeInt(response.length());
                out.write(response.getBytes());
                System.out.println("S: " + response);
            }
            //socket.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
