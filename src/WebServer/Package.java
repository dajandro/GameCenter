/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

/**
 *
 * @author DanielAlejandro
 */
public class Package {
    
    private final String separator = "*";
    
    public String buildPackage(int type){
        String pack = "";
        switch(type){
            // Inicio
            case 1:
                pack = "001" + separator;
                
        }
        return pack;
    }
    
}
