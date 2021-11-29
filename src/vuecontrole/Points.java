/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuecontrole;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static javafx.scene.paint.Color.color;
import javax.swing.JPanel;

/**
 *
 * @author Robin Duflot
 */
public class Points extends JPanel{
    private double x;
    private double y;
    private Color c;

    Points(double x, double y,Color c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D)g ;
        g.setColor(c);
        g.fillOval(20*(int)x, 20*(int)y, 10, 10);
    }
    
}
