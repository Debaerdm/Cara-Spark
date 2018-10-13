package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import spark.ResponseTransformer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JsonTransformer implements ResponseTransformer {

    private static JsonTransformer jsonTransformer;

    private JsonTransformer() {}

    public static JsonTransformer getInstance() {
        if(jsonTransformer == null) {
            jsonTransformer = new JsonTransformer();
        }
        return jsonTransformer;
    }

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    public Map<String, Object> parse(String json) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();

        return gson.fromJson(json, type);
    }

}