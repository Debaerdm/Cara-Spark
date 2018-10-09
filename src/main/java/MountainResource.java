import model.Mountain;

import static spark.Spark.get;
import static spark.Spark.post;

public class MountainResource {
	private static final String API_CONTEXT = "/api";

	public MountainResource() {
		setupEndpoints();
	}

	private void setupEndpoints() {
		get(API_CONTEXT + "/mountain", "application/json", (request, response) -> Mountain.getInstance().getDungeonsMap(), new JsonTransformer());

		get(API_CONTEXT + "/join", "application/json", ((request, response) -> {
			if (Mountain.getInstance().getDungeonsMap().containsKey(request.ip())) {
				Mountain.getInstance().addDungeon(request.ip());
			}

			request.session().attribute(request.ip(), "testIP");
			response.redirect(API_CONTEXT+"/dungeon");
			return "";
		}), new JsonTransformer());
		
		get(API_CONTEXT + "/dungeon", "application/json", (request, response) -> request.session().attribute(request.ip()));
	}

}
