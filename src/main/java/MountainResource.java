import model.Mountain;

import static spark.Spark.get;

public class MountainResource {
	private static final String API_CONTEXT = "/api";

	public MountainResource() {
		setupEndpoints();
	}

	private void setupEndpoints() {
		get(API_CONTEXT + "/mountain", "application/json", (request, response) -> Mountain.getInstance().getDungeonsMap(), JsonTransformer.getInstance());

		get(API_CONTEXT + "/join", "application/json", ((request, response) -> {
			if (!Mountain.getInstance().getDungeonsMap().containsKey(request.ip())) {
				Mountain.getInstance().addDungeon(request.ip());
			} else {
				System.out.println("No dungeon added, there was already one for ip : ("+request.ip()+")");
			}

			request.session().attribute(request.ip(), "testIP");
			response.redirect("/#/dungeon");
			return "";
		}), JsonTransformer.getInstance());
		
		get(API_CONTEXT + "/dungeon", "application/json", (request, response) -> JsonTransformer.getInstance().render(Mountain.getInstance().getDungeonsMap().get(request.ip()).getMap()));
	}

}
