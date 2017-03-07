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
    private ArrayList<Color> colors;
    private final String separator = "~";
    private int id;

    public RequestParser() {
        this.matches = new ArrayList<>();
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
                this.id++;
                Color color = match.getDispColor();
                boolean status = match.join(package_parts[1], color);
                if (!status){
                    this.matches.remove(match);
                    return "110"+separator+"No hay partidas disponibles, intente de nuevo por favor";
                }
                return "001"+separator+String.valueOf(this.id)+separator+String.valueOf(this.getColorId(color))+separator+String.valueOf(this.getColorId(color));
            }
            // Unirse a una partida
            else{
                if (!matches.isEmpty()){
                    Match match = matches.get(0);
                    Color color = match.getDispColor();
                    boolean status = match.join(package_parts[1], color);
                    if (!status){
                        this.matches.remove(match);
                        return "110"+separator+"No hay partidas disponibles, intente de nuevo por favor";
                    }
                    return "001"+separator+String.valueOf(this.id)+separator+String.valueOf(this.getColorId(color))+separator+String.valueOf(this.getColorId(color));
                }
                return "110"+separator+"No hay partidas disponibles, intente de nuevo por favor";
            }
        }
        return "";
    }
}
