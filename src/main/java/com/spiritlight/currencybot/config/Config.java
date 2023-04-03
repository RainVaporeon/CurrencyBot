package com.spiritlight.currencybot.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Config {
    private static boolean antiLoop = false;

    public static String TOKEN;
    public static String API_KEY;

    public static void read() throws IOException {
        File config = new File("config/CurrencyBot.json");
        if (config.exists()) {
            try {
                JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader("config/CurrencyBot.json"));
                TOKEN = jsonObject.get("token").getAsString();
                API_KEY = jsonObject.get("apiKey").getAsString();
            } catch (NullPointerException exception) {
                System.out.println("New configuration files found?");
                if(antiLoop) return;
                antiLoop = true;
                write();
                read();
            }
        } else {
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

    public static boolean save() {
        try {
            write();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
