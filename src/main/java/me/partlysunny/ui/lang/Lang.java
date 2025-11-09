package me.partlysunny.ui.lang;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import shell.ShellMain;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Lang {

    private static Gson gson = new Gson();

    private static JsonObject loadedLanguage = null;

    public static void load(String language) {
        InputStream languageResource = ShellMain.class.getResourceAsStream(language + ".json");
        if (languageResource == null) {
            System.out.println("Language file not found: " + language);
            return;
        }
        JsonReader reader = new JsonReader(new InputStreamReader(languageResource));
        JsonObject lang = gson.fromJson(reader, JsonObject.class);
        if (lang == null) {
            System.out.println("Language file not found: " + language);
            return;
        }
        loadedLanguage = lang;
    }

    public static String get(String path) {
        if (loadedLanguage == null) {
            return "NO LANGUAGE";
        }
        if (!loadedLanguage.has(path)) {
            return path;
        }
        return loadedLanguage.get(path).getAsString();
    }

}
