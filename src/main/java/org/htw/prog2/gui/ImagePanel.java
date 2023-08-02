package org.htw.prog2.gui;
/*
 * Name: Weijie Zhang
 * Matrikelnummer: s0582504
 */

import org.htw.prog2.ampel.Configuration;
import org.htw.prog2.ampel.Light;
import org.htw.prog2.ampel.LightImage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Weijie Zhang, weijiezhangpku@gmail.com
 * @version 1.0
 */
public class ImagePanel extends JPanel {
    private LightImage curImage;
    private Configuration curConfig;
    private boolean showBorder;

    public ImagePanel(){
        setSize(560, 315);
    }

    public void setImage(LightImage image){
        curImage = image;
        repaint();
    }

    public void setConfig(Configuration config){
        curConfig = config;
        repaint();
    }

    public LightImage getImage(){
        return curImage;
    }

    public Configuration getConfiguration(){
        return curConfig;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    public void setShowBorder(boolean show){
        showBorder = show;
        repaint();
    }

    public void paintComponent(Graphics g){
        if  (!(curImage != null && curConfig != null)){
            String message = "";
            if (curImage == null && curConfig == null)
                message = "Please load image and config";
            else if (curImage == null)
                message = "Please load image";
            else
                message = "Please load config";
            g.setColor(Color.BLACK);
            g.drawString(message, getWidth()/2 - 70, getHeight()/2);
        } else {
            g.drawImage(curImage.getImage(), 0,0,null);
            if (showBorder){
                g.setColor(Color.BLUE);
                List<Light> lights = curConfig.getLights();
                for (Light light:
                     lights) {
                    int x = light.getX();
                    int y = light.getY();
                    int r = light.getR();
                    g.drawOval(x-r,y-r,2*r,2*r);
                }
            }
        }
    }
}
