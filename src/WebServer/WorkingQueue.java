/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author DanielAlejandro
 */
public class WorkingQueue {
    private ServerSocket server;
    private ArrayList<Socket> sockets;
    private int workingSockets;
    private int maxSockets;

    public WorkingQueue(int port, int maxSockets) throws Exception{
        server = new ServerSocket(port);
        sockets = new ArrayList<Socket>();
        workingSockets = 0;
        this.maxSockets = maxSockets;
        // Llenar SocketPool
        /*for(int i=0; i<maxSockets; i++)
            sockets.add(server.accept());*/
    }

    public Socket getSocket() throws Exception{
        return server.accept();
        /*synchronized(this){
                while(workingSockets == maxSockets)
                        wait();
                workingSockets++;
                return sockets.remove(0);
        }*/
    }    

    public void setFree() throws Exception{
        synchronized(this){
                workingSockets--;
                sockets.add(server.accept());
                notify();
        }
    }    
}
