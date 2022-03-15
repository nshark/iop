package com.company;

import java.awt.*;

public class button {
    public int x;
    public int y;
    public int hx;
    public int hy;
    public int width;
    public int height;
    public String text;
    button(int x1, int y1, int x2, int y2, String text1){
        x = x1;
        y = y1;
        hx = x2;
        hy = y2;
        text = text1;
        width = Math.abs(x-hx);
        height = Math.abs(y-hy);
    }


    public boolean draw(Graphics2D g, int mouseX, int mouseY, boolean mp){
        g.setColor(Color.BLUE);
        boolean t = false;
        g.fillRect(x-10,y-10, width+20, height+20);
        if (mouseX<hx && mouseX>x && mouseY<hy && mouseY>y){
            g.setColor(Color.CYAN);
            if (mp){
                t = true;
            }
        }
        g.fillRect(x, y, width, height);
        g.setFont(Main.ButtonFont);
        g.setColor(Color.black);
        g.drawString(text, x+width/2-(text.toCharArray().length)*10,y+height/2);
        return t;
    }
}
