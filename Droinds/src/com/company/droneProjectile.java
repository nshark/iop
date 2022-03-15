package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class droneProjectile extends projectile{
    public static BufferedImage image;

    static {
        try {
            image = ImageIO.read(new File("/Users/noah/IdeaProjects/Droinds/Images/DroneShot1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    droneProjectile(int x1, int y1, int dx1, int dy1, boolean exis) {
        super(5, 1, x1, y1, dx1, dy1, 5, exis);
    }
    @Override
    public void update(Graphics2D g, game game) {
        super.update(g, game);
        Main.drawImage(g, (int) x, (int) y, (float) 0, image);
    }
    /**Takes Drone r, and computes trajectory.*/
    public void fire(drone r){
        this.x = r.x;
        this.y = r.y;
        this.dx = (float) ((float) 2*Math.cos(r.h));
        this.dy = (float) ((float) 2*Math.sin(r.h));
        this.x += 5*dx;
        this.y += 5*dy;
        this.exist = true;
    }
}
