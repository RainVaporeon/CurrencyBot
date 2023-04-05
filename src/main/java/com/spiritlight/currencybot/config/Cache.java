package com.spiritlight.currencybot.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.spiritlight.currencybot.Main;
import com.spiritlight.currencybot.misc.PriceInfo;

import java.io.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Cache {

    private static boolean flag = false;

    public static void read() throws IOException {
        File config = new File("config/cache.json");
        if (config.exists()) {
            try {
                JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader("config/cache.json"));
                JsonArray arr = jsonObject.getAsJsonArray("data");
                for (JsonElement jsonElement : arr) {
                    JsonObject element = (JsonObject) jsonElement;
                    Main.appendInfo(PriceInfo.fromJson(element));
                }
            } catch (NullPointerException exception) {
                System.out.println("New configuration files found?");
                if(flag) throw new RuntimeException("Null iterated!");
                flag = true;
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
        JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter("config/cache.json")));
        writer.beginObject();
        writer.name("data");
        writer.beginArray();
        for(PriceInfo pi : Main.getPriceInfo()) {
            writer.jsonValue(pi.toJson().toString());
        }
        writer.endArray();
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
