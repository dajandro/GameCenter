/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCenter;

import java.awt.Color;
import java.net.Socket;

/**
 *
 * @author DanielAlejandro
 */
public class Profile {
    
    private String name;
    private Color color;

    public Profile(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }  
    
}
