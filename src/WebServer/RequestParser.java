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
                boolean status = match.join(this.getColorId(color), package_parts[1], color);
                if (status)
                    this.matches.remove(match);
                String response = "001" + this.separator + match.getId() + this.separator + String.valueOf(this.getColorId(color));;
                for(Profile playeri: match.getPlayers()){
                    if (this.getColorId(playeri.getColor()) != this.getColorId(color))
                        response += this.separator + String.valueOf(this.getColorId(playeri.getColor()));
                }
                return response;
                //return "001"+separator+String.valueOf(match.getId())+separator+String.valueOf(this.getColorId(color))+separator+String.valueOf(this.getColorId(color));
            }
            // Unirse a una partida
            else{
                Match match = matches.get(0);
                Color color = match.getDispColor();
                boolean status = match.join(this.getColorId(color), package_parts[1], color);
                if (status)
                    this.matches.remove(match);
                String response = "001" + this.separator + match.getId() + this.separator + String.valueOf(this.getColorId(color));;
                for(Profile playeri: match.getPlayers()){
                    if (this.getColorId(playeri.getColor()) != this.getColorId(color))
                        response += this.separator + String.valueOf(this.getColorId(playeri.getColor()));
                }
                return response;
                //return "001"+separator+String.valueOf(match.getId())+separator+String.valueOf(this.getColorId(color))+separator+String.valueOf(this.getColorId(color));
            }
        }
        // Client dispatch game action
        else if(request.startsWith("010")){
            String[] package_parts = request.split(separator);
            Match match = this.all_matches.get(Integer.valueOf(package_parts[1]));
            Profile player = null;
            for(Profile i: match.getPlayers())
                if (i.getId() == Integer.valueOf(package_parts[2])){
                    player = i;
                    break;
                }
            //Profile player = match.getPlayers().get(Integer.valueOf(package_parts[2]));
            //Prifile player = match.getPlayers().get(this.colors.indexOf);
            player.setX(Integer.valueOf(package_parts[3]));
            player.setY(Integer.valueOf(package_parts[4]));
            player.setScore(Integer.valueOf(package_parts[5].trim()));
            String response = "011";
            for(Profile playeri: match.getPlayers()){
                if (this.getColorId(playeri.getColor()) != this.getColorId(player.getColor())){
                    response += this.separator + String.valueOf(this.getColorId(playeri.getColor()));
                    response += this.separator + String.valueOf(playeri.getX());
                    response += this.separator + String.valueOf(playeri.getY());
                    response += this.separator + String.valueOf(playeri.getScore());
                }
            }
            return response;
        }
        // End game
        else if(request.startsWith("100")){
            String[] package_parts = request.split(separator);            
            Match match = this.all_matches.get(Integer.valueOf(package_parts[1]));
            //Profile player = match.getPlayers().get(this.getColorId(Integer.valueOf(package_parts[2])));
            //System.out.println("S: " + player.getName() + " ends");
            Profile player = null;
            for(Profile i: match.getPlayers())
                if (i.getId() == Integer.valueOf(package_parts[2])){
                    player = i;
                    break;
                }
            player.setScore(Integer.valueOf(package_parts[3].trim()));
            boolean end = match.end(match, player);
            if (end){                
                String response = "101";
                for(Profile playeri: match.getPlayers()){
                    response += this.separator + playeri.getName();
                    response += this.separator + String.valueOf(playeri.getScore());
                }
                return response;
            }
        }
        
        return "111";
    }
}
