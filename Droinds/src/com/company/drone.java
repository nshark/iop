package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.*;

public class drone extends enemy {
    /**ticks to fire*/
    private int t = 50;
    /**Target X to move to.*/
    private int tarx;
    /**Target Y to move to*/
    private int tary;
    /**Image of drone*/
    public static BufferedImage image;

    static {
        try {
            image = ImageIO.read(new File("/Users/noah/IdeaProjects/Droinds/Images/Drone1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    drone(int x1, int y1, int dx1, int dy1, boolean exis)  {
        super(5, 10, x1, y1, dx1, dy1, exis);
        tarx = x1 + 10;
        tary = y1 + 10;
        float r = (float) atan2(tary, tarx);
        dx = (float) cos(r);
        dy = (float) sin(r);
    }
    /**Called to update: draws itself, fires if it can, and moves.*/
    @Override
    public void update(Graphics2D g, game game) {
        super.update(g, game);
        Main.drawImage(g, (int) x, (int) y, (float) (h+Math.PI/2), image);
        if(t==0){
            t = 200;
            droneProjectile e = (droneProjectile) game.getUnused(droneProjectile.class);
            if (e == null) {
                droneProjectile dp = new droneProjectile(0,0,0,0,true);
                dp.fire(this);
                game.entities.add(dp);
            }
            else{
                e.fire(this);
            }
        }
        else{
            t -= 1;
        }
        h = (float) Math.atan2(game.p.y - y, game.p.x - x);
        if (abs(tarx - (int) x) <= 1 && abs(tary - (int) y) <= 1){
            tarx = game.ran.nextInt(1000);
            tary = game.ran.nextInt(500);
            float r = (float) Math.atan2(tary - y, tarx - x);
            dx = (float) Math.cos(r);
            dy = (float) Math.sin(r);
        }
    }
}
