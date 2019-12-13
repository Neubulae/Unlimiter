package marioandweegee3.unlimiter.config;

import java.util.function.Consumer;

public class ConfigEntry {
    public String attributeName;
    public double min;
    public double max;

    public ConfigEntry(){
        attributeName = "";
        min = -Double.MAX_VALUE;
        max = Double.MAX_VALUE;
    }

    public void log(Consumer<String> log){
        log.accept("Attribute: "+attributeName);
        log.accept("Maximum: "+max);
        log.accept("Minumum: "+min);
    }
}