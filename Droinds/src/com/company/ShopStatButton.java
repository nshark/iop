package com.company;

import java.awt.*;

public class ShopStatButton extends button{
    /**Exponent of upgrade for cost*/
    private final int coe1;
    /**Increment of stat*/
    private final int inc;
    /**coefficient of upgrade for cost*/
    private final int coe2;
    /**Key to player "stats"*/
    private final String text3;
    /**Maximum upgrade*/
    private final int max;
    private int upgrades;
    ShopStatButton(int x1, int y1, int x2, int y2, String text1, int coeA, int coeB,  int inc, int max1, game game){
        super(x1, y1, x2, y2, text1);
        coe1 = coeA;
        this.inc = inc;
        coe2 = coeB;
        text3 = text1;
        max = max1;
        upgrades = game.p.stats.get(text3);
    }
    /**Draws the button, and if it is clicked, advances the relevant stat.*/
    public void draw(int mx, int my, boolean mp, Graphics2D g, game game){
        text = text3 + " level: " + game.p.stats.get(text3) + " Cost: " + getCost();
        if (super.draw(g, mx, my, mp) && game.coins >= this.getCost() && game.p.stats.get(text3) <= max){
            game.p.stats.replace(text3, game.p.stats.get(text3) + inc);
            upgrades += Math.abs(inc);
        }
    }
    /**@return Returns the cost of the next upgrade as a integer*/
    public int getCost(){
        return (int) (Math.pow(upgrades, coe1) + coe2 * upgrades);
    }
}
