package org.htw.prog2.ampel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private ArrayList<Light> lights = new ArrayList<>();

    public Configuration(File configfile) throws IOException, FormatException {
        BufferedReader reader = new BufferedReader(new FileReader(configfile));
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null)
            lights.add(new Light(line));
    }

    public List<Light> getLights() {
        return lights;
    }
}
