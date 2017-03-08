/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Jacobs
 */
public class UDPRequest implements Runnable {
    private DatagramSocket serverSocket;
    private RequestParser requestParser = new RequestParser();
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];
        
    public UDPRequest() {
        try {
            serverSocket = new DatagramSocket(9876);
        } catch (SocketException ex) {
            Logger.getLogger(UDPRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    @Override
    public void run(){
        try{
            String request = "";
            while(true){
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                
                String sentence = new String(receivePacket.getData());
                System.out.println("C: " + sentence);
                
                request = sentence;                
                String response = requestParser.parse(request);                
                
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
                
                System.out.println("S: " + response);
            }
            //socket.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
