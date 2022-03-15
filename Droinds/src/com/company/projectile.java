package com.company;

import java.awt.*;

public class projectile extends entity{
    public int dmg;
    public boolean plCollide = true;
    projectile(int hit, int health1, int x1, int y1, int dx1, int dy1, int dmg1, boolean exis) {
        super(hit, health1, x1, y1, dx1, dy1, exis);
        dmg = dmg1;
    }
    /**If the projectile is out of screen, it no longer exists:
     * checks for collisions with everything, and calls hit if does*/
    @Override
    public void update(Graphics2D g, game game) {
        super.update(g, game);
        if (x > 1000 || y > 500 || x < 0 || y < 0){
            exist = false;
        }
        for (entity e: game.entities){
            if(e.exist&&this.exist){
                if ((e.getClass() != player.class || plCollide)&& this.getClass() != e.getClass()) {
                    if (this.collide(e)) {
                        if (e.health <= this.dmg) {
                            e.exist = false;
                            if (e.isInEnemies()){
                                game.enemies.remove(e);
                            }
                            this.hit();
                        } else {
                            e.health -= dmg;
                            this.hit();
                        }
                    }
                }
            }
        }
    }
    /**to be overridden if tesseracts: default just makes it not exist.*/
    private void hit() {
        this.exist = false;
    }
}
