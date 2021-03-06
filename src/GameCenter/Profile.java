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
    
    private int id;
    private String name;
    private Color color;
    private int x;
    private int y;
    private int score;
    private boolean end;

    public Profile(int id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.x = 0;
        this.y = 0;
        this.score = 0;
        this.end = false;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean finish() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
