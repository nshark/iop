package com.company;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Main {
    public static Font ButtonFont = new Font("x", Font.PLAIN,18);
    /**Draws an image at location drawLocationX, drawLocationY*/
    public static void drawImage(Graphics2D g2d, int drawLocationX, int drawLocationY, float RotationRequired, BufferedImage image){
        double locationX = image.getWidth() / 2F;
        double locationY = image.getHeight() / 2F;
        AffineTransform tx = AffineTransform.getRotateInstance(RotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2d.drawImage(op.filter(image, null), (int)(drawLocationX-locationX), (int)(drawLocationY-locationY), null);
    }
    /**Gets the game rolling, called on start.*/
    public static void main(String[] args) {
	    game g = new game();
    }
}
