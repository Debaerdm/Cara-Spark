import model.*;
import java.util.HashMap;
import java.util.Map;

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

		get(API_CONTEXT+"/money", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getMoney(), JsonTransformer.getInstance());

		post(API_CONTEXT+"/build", ACCEPT_TYPE,  (request, response) -> {
			Dungeon dungeon = Mountain.getInstance().getDungeonsMap().get(request.ip());

			Map<String, Object> map = JsonTransformer.getInstance().parse(request.body());

			String item = (String) map.get("item");

			String label = "";
			String imagePath = "";

			if(item.equals("DIRT")) {
				dungeon.setTile(new EmptyTile(dungeon, false, (int) map.get("row"), (int) map.get("col")));
				Tile tile = dungeon.getTile((int) map.get("row"), (int) map.get("col"));
				tile.update();
				label = "Mur detruit !";
				imagePath = tile.getImagePath();
			} else {
				BuildingType buildingType = BuildingType.create(ItemType.valueOf(item));
				dungeon.build(buildingType, (int) map.get("row"), (int) map.get("col"));
				label = "Vous avez construit une station pour recolter de la "+item.toLowerCase()+" (cout : "+buildingType.getCost()+")";
				imagePath = buildingType.getImagePath();
			}


			Map<String, Object> result = new HashMap<>();
			result.put("label", label);
			result.put("image", imagePath);
			result.put("row", map.get("row"));
			result.put("col", map.get("col"));
			result.put("code", 200);

			return result;
		}, JsonTransformer.getInstance());
	}
	
}

