package com.leme.cookbook.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leme.cookbook.model.Baking;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class ReadJsonUtil {

    public static List<Baking> loadJSONFromObject(Context context) {

        String json = null;

        try {

            InputStream is = context.getAssets().open("baking.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return convertJsonToBaking(json);

    }

    private static List<Baking> convertJsonToBaking(String json) {

        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Baking>>(){}.getType();
        return gson.fromJson(json, collectionType);

    }

}
