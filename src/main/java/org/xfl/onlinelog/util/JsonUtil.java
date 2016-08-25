package org.xfl.onlinelog.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.xfl.onlinelog.bean.Message;

import java.io.IOException;

/**
 * Created by bl04559 on 2016/8/17.
 */
public class JsonUtil {
    public static <T> T converStringToObject(String jsonStr, Class<T> clazz) {
        Gson gson = new GsonBuilder().create();
        T t = gson.fromJson(jsonStr, clazz);
        return t;
    }

    public static <T> String convertObjectToString(T t, Class<T> clazz) {
        Gson gson = new GsonBuilder().create();
        try {
            return gson.getAdapter(clazz).toJson(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String jsonStr = "{msgType:1, content:'/data/path'}";
        Message message = converStringToObject(jsonStr, Message.class);
        System.out.printf(message.getContent());
        System.out.printf(convertObjectToString(message, Message.class));

    }
}
