package org.htw.prog2.ampel;

public class Light {
    private int x;
    private int y;
    private int r;
    private String name;
    public Light(String configline) throws FormatException {
        String[] strings = configline.split(",");
        if (strings.length != 4)
            throw new FormatException(configline,"Wrong number of elements.");

        name = strings[0].strip();
        try {
            x = Integer.parseInt(strings[1].strip());
        } catch (NumberFormatException e){
            throw new FormatException(configline,"Could not parse" + strings[1] + " as coordinate (need an integer).");
        }

        try {
            y = Integer.parseInt(strings[2].strip());
        } catch (NumberFormatException e){
            throw new FormatException(configline,"Could not parse" + strings[2] + " as coordinate (need an integer).");
        }

        try {
            r = Integer.parseInt(strings[3].strip());
        } catch (NumberFormatException e){
            throw new FormatException(configline,"Could not parse" + strings[3] + " as diameter (need an integer).");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public String getName() {
        return name;
    }
}
