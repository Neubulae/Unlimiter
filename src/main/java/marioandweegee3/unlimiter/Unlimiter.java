package marioandweegee3.unlimiter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import marioandweegee3.unlimiter.config.ConfigEntry;
import marioandweegee3.unlimiter.util.ConfigClampedAttribute;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.attribute.ClampedEntityAttribute;

public class Unlimiter implements ModInitializer {
    private static Logger logger = LogManager.getFormatterLogger();

    public static Set<Object> attributes = new HashSet<>();

    public static Map<String, ConfigEntry> configuredAttributes = new HashMap<>();

    public Unlimiter(){
        log("Breaking Past all Limits...");
        log("AAAAAAAAAAAAAAAAAAAAAAAAAAA");

        Gson gson = new GsonBuilder().setPrettyPrinting().setVersion(1.0).create();
        String configPath = FabricLoader.getInstance().getConfigDirectory().getAbsolutePath() + "/unlimiter.json";

        File file = new File(configPath);
        try{
            if(file.createNewFile()){
                JsonArray array = new JsonArray();
                FileWriter writer = new FileWriter(file);
                writer.write(gson.toJson(array));
                writer.close();
            }
        } catch (IOException e){
            logger.error("[Unlimiter] "+"Error making/reading config file", e);
        }

        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(configPath));
        } catch (IOException e) {
            logger.error("[Unlimiter] "+"Error making/reading config file", e);
        }

        if(reader != null){
            try {
                JsonArray array = gson.fromJson(reader, JsonArray.class);
                for(JsonElement element : array){
                    if(element instanceof JsonObject){
                        JsonObject config = (JsonObject) element;
                        ConfigEntry entry = gson.fromJson(config, ConfigEntry.class);
                        if(entry != null){
                            configuredAttributes.put(entry.attributeName, entry);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("[Unlimiter] "+"Error making/reading config file", e);
            }
        }

        log("So this is the power of Ultra Instinct.");
    }

    @Override
    public void onInitialize() {
        log("Found the following config entries:");
        for (ConfigEntry entry : configuredAttributes.values()) {
            entry.log(s -> log(s));
        }
        ServerStartCallback.EVENT.register(
            (server) -> {
                    log("Found the following config entries:");
                    for (ConfigEntry entry : configuredAttributes.values()) {
                        entry.log(s -> log(s));
                    }
                    for (Object a : attributes) {
                        if (a instanceof ClampedEntityAttribute) {
                            if (a instanceof ConfigClampedAttribute) {
                                ClampedEntityAttribute attribute = (ClampedEntityAttribute) a;
                                ConfigClampedAttribute attributeConfig = (ConfigClampedAttribute) a;
                                if (configuredAttributes.containsKey(attribute.getId())) {
                                    log("Config loaded for " + attribute.getId());
                                    ConfigEntry entry = configuredAttributes.get(attribute.getId());
                                    attributeConfig.setMax(entry.max);
                                    attributeConfig.setMin(entry.min);
                                } else if (configuredAttributes.containsKey(attribute.getName())) {
                                    log("Config loaded for " + attribute.getName());
                                    ConfigEntry entry = configuredAttributes.get(attribute.getName());
                                    attributeConfig.setMax(entry.max);
                                    attributeConfig.setMin(entry.min);
                                } else {
                                    log("Using default for " + attribute.getId());
                                    attributeConfig.setMax(Double.MAX_VALUE);
                                    attributeConfig.setMin(-Double.MAX_VALUE);
                                }
                            }
                        }
                    }
            }
        );
    }

    public static void log(CharSequence message){
        logger.info("[Unlimiter] "+message);
    }

}