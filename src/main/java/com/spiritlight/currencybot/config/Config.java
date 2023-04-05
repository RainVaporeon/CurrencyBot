package com.spiritlight.currencybot.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.spiritlight.currencybot.internals.InternalController;
import com.spiritlight.currencybot.util.LockedReference;

import java.io.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Config {
    private static boolean antiLoop = false;

    // note: probably could've used self reference if not for static, saving the burden
    private static final InternalController.AccessKey key = new InternalController.AccessKey();

    public static final LockedReference<String> TOKEN = new LockedReference<>(null, key);
    public static final LockedReference<String> API_KEY = new LockedReference<>(null, key);

    public static void read() throws IOException {
        File config = new File("config/CurrencyBot.json");
        if (config.exists()) {
            try {
                JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader("config/CurrencyBot.json"));
                String token = jsonObject.get("token").getAsString();
                String apiKey = jsonObject.get("apiKey").getAsString();
                TOKEN.set(token, key);
                API_KEY.set(apiKey, key);
            } catch (NullPointerException exception) {
                System.out.println("New configuration files found?");
                if(antiLoop) return;
                antiLoop = true;
                write();
                read();
            }
        } else {
            // Generates a new file, overwriting the previous one
            File root = new File("config");
            root.mkdir();
            config.createNewFile();
            write();
        }
    }

    public static void write() throws IOException {
        JsonWriter writer = new JsonWriter(new FileWriter("config/CurrencyBot.json"));
        writer.beginObject();
        writer.name("token").value("");
        writer.name("apiKey").value("");
        writer.endObject();
        writer.close();
    }

}
