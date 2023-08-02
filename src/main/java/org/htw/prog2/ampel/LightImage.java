package org.htw.prog2.ampel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LightImage {
    private BufferedImage image;
    public LightImage(File imagefile) throws IOException {
        image = ImageIO.read(imagefile);
    }

    public double getLightIntensity(Light light) {
        int x = light.getX();
        int y = light.getY();
        int r = light.getR();
        int startX = x - r;
        int endX = x + r;
        int startY = y - r;
        int endY = y + r;

        int intensitySum = 0;
        int count = 0;

        for (int i = startY; i < endY + 1; i++) {
            for (int j = startX; j < endX + 1; j++) {
                if (isInLight(j,i,light)){
                    count++;
                    Color c = new Color(image.getRGB(j, i));
                    intensitySum += (c.getRed() + c.getGreen() + c.getBlue());
                }
            }
        }

        return (double) intensitySum / count;
    }

    public boolean isInLight(int x, int y, Light light) {
        int lightX = light.getX();
        int lightY = light.getY();
        int lightR = light.getR();
        double distance = Math.sqrt((x-lightX)*(x-lightX) + (y-lightY)*(y-lightY));
        return distance <= lightR;
    }

    public String getActiveLight(List<Light> lights) {
        double maxIntensity = Double.MIN_VALUE;
        int maxIndex = 0;
        for (int i = 0; i < lights.size(); i++) {
            double lightIntensity = getLightIntensity(lights.get(i));
            if (lightIntensity > maxIntensity){
                maxIndex = i;
                maxIntensity = lightIntensity;
            }
        }
        return lights.get(maxIndex).getName();
    }

    public BufferedImage getImage() {
        return image;
    }
}
