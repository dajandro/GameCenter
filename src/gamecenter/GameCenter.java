/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCenter;

import WebServer.WebServer;

/**
 *
 * @author DanielAlejandro
 */
public class GameCenter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        WebServer gamecenter_server = new WebServer();
        gamecenter_server.run();
    }
    
}
