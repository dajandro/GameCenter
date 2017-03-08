/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCenter;

import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author DanielAlejandro
 */
public class Match {
    
    private int max_players = 3;
    private ArrayList<Profile> players;
    private ArrayList<Color> colors;
    private int id;

    public Match(int id, ArrayList<Color> colors) {
        this.id = id;
        this.players = new ArrayList<Profile>();
        this.colors = new ArrayList<>();
        this.colors.addAll(colors);
    }

    public int getMaxPlayers() {
        return max_players;
    }

    public void setMaxPlayers(int n_players) {
        this.max_players = n_players;
    }

    public ArrayList<Profile> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Profile> players) {
        this.players = players;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }
    
    public Color getDispColor(){
        synchronized(this){
            if (!this.colors.isEmpty()){
                Color tp = this.colors.get(0);
                this.colors.remove(0);
                return tp;
            }
        }
        return null;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public boolean join(String name, Color color) throws Exception{
        System.out.println("S: " + name + " trying to join match");
        synchronized(this){
            if(this.players.size() < max_players){
                this.players.add(new Profile(name, color));
                // Despertar a los que estaban esperando match
                if (this.players.size() == max_players){
                    notifyAll();
                    return true;
                    //return false;
                }
                //while(this.players.size() < max_players)
                wait();
                return true;
            }
            return false;
        }
    }
    
    public boolean end(int id) throws Exception{
        Profile player = this.players.get(id);
        System.out.println("S: " + player.getName() + " end game!");
        synchronized(this){
            player.setEnd(true);
            int end = 0;
            for(Profile i: this.players)
                if(i.finish())
                    end++;
            if(end == this.players.size()){
                notifyAll();
                return true;
            }
            wait();
            return true;
        }
    }
    
}
