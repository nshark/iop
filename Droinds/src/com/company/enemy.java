package com.company;

import java.awt.*;

public class enemy extends entity{
    public float h = 0;
    enemy(int hit, int health1, int x1, int y1, int dx1, int dy1, boolean exis) {
        super(hit, health1, x1, y1, dx1, dy1, exis);
    }

    @Override
    public boolean isInEnemies() {
        return true;
    }

    @Override
    public void update(Graphics2D g, game game) {
        super.update(g, game);
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
}
