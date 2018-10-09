import model.Mountain;

import static spark.Spark.get;

public class MountainResource {
    private static final String API_CONTEXT = "/api";

    public MountainResource() {
        setupEndpoints();
    }

    private void setupEndpoints() {
        get(API_CONTEXT + "/mountain", "application/json", (request, response) -> Mountain.getInstance().getDungeonsMap(), new JsonTransformer());

        /*put(API_CONTEXT + "/todos/:id", "application/json", (request, response)

                -> todoService.update(request.params(":id"), request.body()), new JsonTransformer());*/
    }

}
