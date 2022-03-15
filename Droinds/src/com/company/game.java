package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class game implements KeyListener, MouseListener, Runnable, MouseMotionListener {
    public JFrame frame;
    public boolean mp = false;
    public boolean shop = false;
    public int coins = 0;
    public Thread t = new Thread(this);
    public JPanel panel;
    public Canvas canvas;
    public Boolean mainMenu = true;
    public Random ran = new Random();
    public int mx = 0;
    public int my = 0;
    public int waveCounter = 0;
    public ArrayList<enemy> enemies = new ArrayList<>(100);
    public HashMap<Character, Boolean> k = new HashMap<>(120);
    public HashMap<String, ShopStatButton> b2 = new HashMap<>();
    public ArrayList<button> buttons  = new ArrayList<>();
    public player p;
    public boolean playing = false;
    public ArrayList<entity> entities = new ArrayList<>(500);
    game(){
        for (int i = 0; i < 127; i++) {
            k.put((char) i, false);
        }
        //graphics setup
        frame = new JFrame("Droinds");
        panel = new JPanel();
        frame.add(panel);
        canvas = new Canvas();
        panel.add(canvas);
        frame.pack();
        frame.setBounds(0,0,1000,500);
        panel.setBounds(0,0,1000,500);
        canvas.setBounds(0,0,1000,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        canvas.requestFocus();
        frame.setVisible(true);
        frame.setResizable(false);
        canvas.addMouseListener(this);
        canvas.addKeyListener(this);
        canvas.createBufferStrategy(2);
        canvas.addMouseMotionListener(this);
        t.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (k.containsKey(e.getKeyChar())){
            k.replace(e.getKeyChar(), true);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (k.containsKey(e.getKeyChar())){
            k.replace(e.getKeyChar(), false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mp = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mp = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @SuppressWarnings("DuplicateCondition")
    @Override
    public void run() {
        long stTime;
        long crTime;
        try {
            p = new player(12,25,50,50,0,0,true);
            entities.add(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttons.add(new button(100,150,900,200,"Play"));
        buttons.add(new button(10,10,50,40,"Back"));
        //noinspection InfiniteLoopStatement
        while(true) {
            stTime = System.currentTimeMillis();
            Graphics2D g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
            g.clearRect(0, 0, 1000, 500);
            g.setColor(Color.black);
            g.fillRect(0, 0, 1000, 500);
            if (playing) {
                spawn();
                //noinspection ForLoopReplaceableByForEach
                for (int i = 0; i < entities.size(); i++) {
                    entity e = entities.get(i);
                    if (e.exist) {
                        e.update(g, this);
                    }
                }
                if (!p.exist){
                    playing = false;
                    mainMenu = true;
                }
            }
            else if(mainMenu) {
                if (buttons.get(0).draw(g, mx, my, mp)) {
                    playing = true;
                    p.exist = true;
                    p.health = p.maxhealth;
                    coins = 0;
                    p.resetStats();
                    mp = false;
                    for(enemy e : enemies){
                        e.exist = false;
                    }
                    enemies = new ArrayList<>();
                    waveCounter = 0;
                    mainMenu = false;
                }
                for(String s : p.stats.keySet()){
                    if (!b2.containsKey(s)){
                        if (!Objects.equals(s, "turspd") || !Objects.equals(s, "rof")) {
                            b2.put(s, new ShopStatButton(250,(b2.size()%10)*50, 750, (b2.size()%10)*50+50, s,
                                    1, 2, 5, 100, this));
                        }
                        else if (Objects.equals(s, "rof") || Objects.equals(s, "turnspd")){
                            b2.put(s, new ShopStatButton(250,(b2.size()%10)*50, 750, (b2.size()%10)*50+50, s,
                                    2, 0, -1, 100, this));
                        }
                    }
                }
            }
            else if (shop){
                if(buttons.get(1).draw(g, mx, my, mp)){
                    playing = true;
                    shop = false;
                    mp = false;
                }
                for(String s : p.stats.keySet()){
                    if (b2.containsKey(s)){
                        b2.get(s).draw(mx,my,mp,g,this);
                    }
                }
                String i = String.valueOf(coins);
                g.setColor(Color.WHITE);
                g.drawString(i,900,50);
            }
            crTime = System.currentTimeMillis();
            while (crTime - stTime < 30){
                crTime = System.currentTimeMillis();
            }
            canvas.getBufferStrategy().show();
            canvas.update(g);
            g.dispose();
        }
    }
    /** @return Get an unused(not existing) member of class c that extends entity.
     * Returns null if there is no member of the class who applies. */
    public entity getUnused(@SuppressWarnings("rawtypes") Class c){
        for (entity e : entities) {
            if (!e.exist && e.getClass() == c){
                return e;
            }
        }
        return null;
    }

    /**
     * @return Get a random XY position on a screen boundary.
     */
    public int[] getXYwall(){
        int side = ran.nextInt(4);
        int x = 0;
        int y = 0;
        if (side == 0){
            y = ran.nextInt(500);
        }
        else if(side == 1){
            x = 1000;
            y = ran.nextInt(500);
        }
        else if(side == 2){
            x = ran.nextInt(1000);
        }
        else{
            x = ran.nextInt(1000);
            y = 500;
        }
        return(new int[]{x, y});
    }
    public void spawn(){
        if(enemies.size() == 0) {
            coins += waveCounter*10;
            waveCounter++;
            int power = 0;
            while (power < waveCounter) {
                int x = ran.nextInt(waveCounter-power);
                if (x <= 1) {
                    power++;
                    drone p = (drone) getUnused(drone.class);
                    int[] coords = this.getXYwall();
                    if (p == null) {
                        p = new drone(coords[0], coords[1], 0, 0, true);
                        entities.add(p);
                    } else {
                        p.exist = true;
                        p.x = coords[0];
                        p.y = coords[1];
                        p.dx = 0;
                        p.dy = 0;
                        p.h = 0;
                    }
                    enemies.add(p);
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
    }
}
