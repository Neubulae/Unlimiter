package marioandweegee3.unlimiter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

public class Unlimiter implements ModInitializer {
    private static Logger logger = LogManager.getFormatterLogger();

    @Override
    public void onInitialize() {
        log("Breaking Past all Limits...");
        log("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
        log("So this is the power of Ultra Instinct.");
    }

    public static void log(CharSequence message){
        logger.info("[Unlimiter] "+message);
    }

}