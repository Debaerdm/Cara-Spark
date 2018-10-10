import model.ItemType;
import model.Mountain;

import static spark.Spark.get;
import static spark.Spark.post;

public class MountainResource {
	private static final String API_CONTEXT = "/api";
	private static final String ACCEPT_TYPE = "application/json";

	public MountainResource() {
		setupEndpoints();
	}

	private void setupEndpoints() {
		get(API_CONTEXT + "/mountain", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap(), JsonTransformer.getInstance());

		get(API_CONTEXT + "/join", ACCEPT_TYPE, ((request, response) -> {
			if (!Mountain.getInstance().getDungeonsMap().containsKey(request.ip())) {
				Mountain.getInstance().addDungeon(request.ip());
			} else {
				System.out.println("No dungeon added, there was already one for ip : ("+request.ip()+")");
			}

			response.redirect("/#/dungeon");
			return 200;
		}), JsonTransformer.getInstance());

		get(API_CONTEXT + "/dungeon", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getMap(), JsonTransformer.getInstance());
		
		get(API_CONTEXT + "/dungeon_total", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().keySet().size(), JsonTransformer.getInstance());

		get(API_CONTEXT+"/itemTypes", ACCEPT_TYPE, (request, response) -> ItemType.values(), JsonTransformer.getInstance());

		post(API_CONTEXT+"/build", ACCEPT_TYPE,  (request, response) -> {
			System.out.println(request.body());
			return 200;
		}, JsonTransformer.getInstance());
	}
	
}

