package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.Math.abs;

public class player extends entity {
    /**heading in radians*/
    public float h = 0;
    /**stats to be advanced*/
    public HashMap<String, Integer> stats = new HashMap<>(Map.of("rof", 20, "dmg", 10, "thr", 25, "turspd", 10));
    /**img, can be changed if model upgrades*/
    public BufferedImage img = ImageIO.read(new File("/Users/noah/IdeaProjects/Droinds/Images/playerBase.png"));
    /**Model, defines what stats, fire types, etc that player has.*/
    public String model = "base";
    /**Ticks until the next shot of base cannon*/
    public int tickForBase = 20;
    player(int hit, int health1, int x1, int y1, int dx1, int dy1, boolean exis) throws IOException {
        super(hit, health1, x1, y1, dx1, dy1, exis);
    }

    @Override
    /**Draws itself and checks for key presses.*/
    public void update(Graphics2D g, game game) {
        if (abs(dx) > 0.01 || abs(dy) > 0.01){
            dx -= 0.1*Math.cos(Math.atan2(dy,dx));
            dy -= 0.1*Math.sin(Math.atan2(dy,dx));
        }
        super.update(g, game);
        Main.drawImage(g,(int)x,(int)y, h,img);
        g.setColor(Color.red);
        g.fillRect(200,430, 600,25);
        g.setColor(Color.GREEN);
        g.fillRect(200, 430, (int) (((float)health)/((float)maxhealth)*600), 25);
        if (game.k.get('w')){
            dx += ((float)stats.get("thr"))/100 * Math.cos(h);
            dy += ((float)stats.get("thr"))/100 * Math.sin(h);
        }
        if (game.k.get('a')){
            h -= 0.1*(stats.get("turspd")/10);
        }
        if (game.k.get('d')){
            h+=0.1*(stats.get("turspd")/10);
        }
        if (game.k.get('f')){
            game.playing = false;
            game.shop = true;
        }
        if (game.k.get('e')){
            if (Objects.equals(model, "base")){
                if (tickForBase == 0) {
                    tickForBase = stats.get("rof");
                    PlayerProjectileBase pShot = (PlayerProjectileBase) game.getUnused(PlayerProjectileBase.class);
                    if (pShot == null) {
                        pShot = new PlayerProjectileBase(0, 0, 0, 0, false);
                        game.entities.add(pShot);
                        pShot.fire(this, x, y);
                    } else {
                        pShot.fire(this, x, y);
                    }
                }
                else{
                    tickForBase -= 1;
                }
            }
        }
        if(x > 1000){
            x = 0;
        }
        if(x<0){
            x = 1000;
        }
        if(y>500){
            y = 0;
        }
        if (y< 0){
            y=500;
        }
    }
    public void resetStats(){
        if (Objects.equals(model, "base")){
            stats.clear();
            stats.putAll(Map.of("rof", 20, "dmg", 10, "thr", 25, "turspd", 10));
        }
    }
}
