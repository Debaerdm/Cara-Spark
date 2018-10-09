import com.google.gson.Gson;
import spark.ResponseTransformer;

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

}