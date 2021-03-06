/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author DanielAlejandro
 */
public class WebServer {
    private int port = 5555;
    private String configFile = "config.txt";
    private int maxThreads = 0;
    private RequestParser requestParser = new RequestParser();
    
    public WebServer(){
        readConfigFile();
    }
    
    private void readConfigFile(){
        String base_path = System.getProperty("user.dir");
        String path = base_path + "/src/WebServer/" + configFile;
        HashMap<String, Integer> configs = new HashMap<String, Integer>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                int separator = line.indexOf("=");
                if (separator != -1){
                    String key = line.substring(0,separator);
                    Integer value = Integer.parseInt(line.substring(separator+1, line.length()));
                    configs.put(key, value);
                }
                line = br.readLine();
            }            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        maxThreads = configs.get("MaxThreads");        
        //System.out.println("Server MaxThreads: " + String.valueOf(maxThreads));
        System.out.println("Listening at port: " + String.valueOf(port));
    }
    
    public void run() throws Exception{
        System.out.println("--- GAMECENTER SERVER CONNECTION OPEN ---");
        WorkingQueue wq = new WorkingQueue(port, maxThreads);
        // Armar partidas
        while (true){
            Socket socket = wq.getSocket();
            GameRequest request = new GameRequest(socket,requestParser);
            Thread thread = new Thread(request);
            thread.start();
        }
    }
}
