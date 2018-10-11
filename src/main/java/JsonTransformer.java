import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.ResponseTransformer;

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
        Map<String, Object> value = new HashMap<>();

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        value.put("item", jsonObject.get("buildItem").getAsString());
        value.put("row", jsonObject.get("row").getAsInt());
        value.put("col", jsonObject.get("col").getAsInt());

        return value;
    }

}