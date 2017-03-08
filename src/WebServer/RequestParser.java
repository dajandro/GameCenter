/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import GameCenter.Match;
import GameCenter.Profile;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author DanielAlejandro
 */
public class RequestParser {
    
    private ArrayList<Match> matches;
    private ArrayList<Match> all_matches;
    private ArrayList<Color> colors;
    private final String separator = "~";
    private int id;

    public RequestParser() {
        this.matches = new ArrayList<>();
        this.all_matches = new ArrayList<>();
        this.id = 0;
        this.colors = new ArrayList<>();
        this.colors.add(Color.red);
        this.colors.add(Color.yellow);
        this.colors.add(Color.pink);
        this.colors.add(Color.white);
    }
    
    public int getColorId(Color color){
        int index = -1;
        for(int i=0; i<this.colors.size(); i++)
        {
            Color ci = this.colors.get(i);
            if(ci.getRGB() == color.getRGB()){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public String parse(String request) throws Exception{
        // Inicio Client
        if(request.startsWith("000")){
            String[] package_parts = request.split(separator);
            // Iniciar partida si no hay una creada
            if(matches.isEmpty()){
                Match match = new Match(this.id, this.colors);
                this.matches.add(match); this.all_matches.add(match);
                this.id++;
                Color color = match.getDispColor();
                boolean status = match.join(package_parts[1], color);
                if (status)
                    this.matches.remove(match);
                return "001"+separator+String.valueOf(match.getId())+separator+String.valueOf(this.getColorId(color))+separator+String.valueOf(this.getColorId(color));
            }
            // Unirse a una partida
            else{
                Match match = matches.get(0);
                Color color = match.getDispColor();
                boolean status = match.join(package_parts[1], color);
                if (status)
                    this.matches.remove(match);
                return "001"+separator+String.valueOf(match.getId())+separator+String.valueOf(this.getColorId(color))+separator+String.valueOf(this.getColorId(color));
            }
        }
        // Client dispatch game action
        else if(request.startsWith("010")){
            String[] package_parts = request.split(separator);
            Match match = this.all_matches.get(Integer.valueOf(package_parts[1]));
            Profile player = match.getPlayers().get(Integer.valueOf(package_parts[2]));
            player.setX(Integer.valueOf(package_parts[3]));
            player.setY(Integer.valueOf(package_parts[4]));
            player.setScore(Integer.valueOf(package_parts[5]));
            String response = "011";
            for(Profile playeri: match.getPlayers()){
                response += this.separator + String.valueOf(this.getColorId(playeri.getColor()));
                response += this.separator + String.valueOf(playeri.getX());
                response += this.separator + String.valueOf(playeri.getY());
            }
            return response;
        }
        // End game
        else if(request.startsWith("100")){
            String[] package_parts = request.split(separator);
            Match match = this.all_matches.get(Integer.valueOf(package_parts[1]));
            Profile player = match.getPlayers().get(Integer.valueOf(package_parts[2]));
            player.setScore(Integer.valueOf(package_parts[3]));
            boolean end = match.end(Integer.valueOf(package_parts[1]));
            if (end){
                System.out.println("S: Match " + String.valueOf(match.getId()) + " end");
                String response = "101";
                for(Profile playeri: match.getPlayers()){
                    response += this.separator + playeri.getName();
                    response += this.separator + String.valueOf(playeri.getScore());
                }
                return response;
            }
        }
        
        return "";
    }
}
