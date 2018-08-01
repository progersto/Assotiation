package com.natife.assotiation.utils;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListGenerator {

    private static final String EASE_LEVEL = "lowLevel";
    private static final String NORMAL_LEVEL = "mediumLevel";
    private static final String HARD_LEVEL = "hardLevel";

    public static List<String> createListSelectedLevel(Context context, int difficultLevel) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        String stringInGson = "";
        try {
            is = am.open("words.txt");
            stringInGson = convertStreamToString(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createListSelectedLevel(stringInGson, difficultLevel);
    }

    private static String convertStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }

    private static List<String> createListSelectedLevel(String sss, int difficultLevel) {
        List<String> list = new ArrayList<>();
        String level = "";
        try {
            switch (difficultLevel) {
                case 1:
                    level = EASE_LEVEL;
                    break;
                case 2:
                    level = NORMAL_LEVEL;
                    break;
                case 3:
                    level = HARD_LEVEL;
                    break;
            }

            JSONObject obj = new JSONObject(sss);
            JSONArray messages = (JSONArray) obj.get(level);

            for (int i = 0; i < messages.length(); i++) {
                list.add(messages.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
