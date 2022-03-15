package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerProjectileBase extends projectile{
    /**Image*/
    public static BufferedImage image;
    /**heading in radians*/
    public float h = 0;
    static {
        try {
            image = ImageIO.read(new File("/Users/noah/IdeaProjects/Droinds/Images/PlayerShotBase.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    PlayerProjectileBase(int x1, int y1, int dx1, int dy1, boolean exis) {
        super(7, 1, x1, y1, dx1, dy1, 5, exis);
        plCollide = false;
    }
    /**Draws itself, and then calls Projectile.Update*/
    @Override
    public void update(Graphics2D g, game game) {
        super.update(g, game);
        Main.drawImage(g, (int) x, (int) y, h, image);
    }
    /**Takes player p, and initial x & y and computes trajectory*/
    public void fire(player p, float x, float y){
        this.x = x;
        this.y = y;
        this.dx = (float) ((float) 4*Math.cos(p.h));
        this.dy = (float) ((float) 4*Math.sin(p.h));
        this.x += 5*dx;
        this.y += 5*dy;
        this.h = p.h;
        this.dmg = p.stats.get("dmg");
        this.exist = true;
    }
}
