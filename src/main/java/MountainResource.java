import model.Mountain;

import static spark.Spark.get;

public class MountainResource {
    private static final String API_CONTEXT = "/api/v1";

    private final Mountain mountain;

    public MountainResource(Mountain mountain) {
        this.mountain = mountain;
        setupEndpoints();
    }

    private void setupEndpoints() {
       /* post(API_CONTEXT + "/todos", "application/json", (request, response) -> {
            todoService.createNewTodo(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());*/

        get(API_CONTEXT + "/index", "application/json", (request, response) -> mountain.getName(), new JsonTransformer());

        /*put(API_CONTEXT + "/todos/:id", "application/json", (request, response)

                -> todoService.update(request.params(":id"), request.body()), new JsonTransformer());*/
    }

}
