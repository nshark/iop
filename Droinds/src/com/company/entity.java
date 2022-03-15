package com.company;

import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class entity {
    public int hitbox;
    public int maxhealth;
    public int health;
    public float x;
    public float y;
    public float dx;
    public float dy;
    public boolean exist;
    entity(int hit, int health1, int x1, int y1, int dx1, int dy1, boolean exis){
        hitbox = hit;
        health = health1;
        maxhealth = health1;
        x = x1;
        y = y1;
        dx = dx1;
        dy = dy1;
        exist = exis;
    }
    public boolean collide(entity e2){
        if (e2.exist && this.exist) {
            return pow(x - e2.x, 2) + pow(y - e2.y, 2) <= pow((hitbox + e2.hitbox), 2);
        }
        else{
            return false;
        }
    }
    /**Called every tick for all existing entities.*/
    public void update(Graphics2D g, game game){
        x += dx;
        y += dy;
    }
    public boolean isInEnemies(){
        return false;
    }
}
