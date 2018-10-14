package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

import model.Dungeon;
import model.Mountain;
import model.item.BuildingType;
import model.item.ItemType;
import utils.JsonTransformer;

public class MountainController {

	private static final String API_CONTEXT = "/api";
	private static final String ACCEPT_TYPE = "application/json";

	public MountainController() {
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
			response.redirect("/api/dungeon");
			return 200;
		}), JsonTransformer.getInstance());

		get(API_CONTEXT + "/dungeon", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getMap(), JsonTransformer.getInstance());

		get(API_CONTEXT + "/exist", ACCEPT_TYPE, (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null), JsonTransformer.getInstance());

		get(API_CONTEXT + "/dungeon_total", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().keySet().size(), JsonTransformer.getInstance());

		get(API_CONTEXT+"/itemTypes", ACCEPT_TYPE, (request, response) -> BuildingType.itemsValues(), JsonTransformer.getInstance());

		get(API_CONTEXT+"/rock", ACCEPT_TYPE, (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null) ? Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.ROCK) : 0, JsonTransformer.getInstance());

		get(API_CONTEXT+"/gold", ACCEPT_TYPE, (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null) ? Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.GOLD) : 0, JsonTransformer.getInstance());

		get(API_CONTEXT+"/gems", ACCEPT_TYPE, (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null) ? Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.GEMS) : 0, JsonTransformer.getInstance());

		post(API_CONTEXT+"/item", ACCEPT_TYPE, (request, response) -> {
            Dungeon dungeon = Mountain.getInstance().getDungeonsMap().get(request.ip());
		    Map<String, Object> stringObjectMap = JsonTransformer.getInstance().parse(request.body());

		    return dungeon.getTile((Integer) stringObjectMap.get("row"), (Integer) stringObjectMap.get("col"));
        }, JsonTransformer.getInstance());

		post(API_CONTEXT+"/build", ACCEPT_TYPE,  (request, response) -> {
			Dungeon dungeon = Mountain.getInstance().getDungeonsMap().get(request.ip());
			Map<String, Object> map = JsonTransformer.getInstance().parse(request.body());
			ItemType itemType = ItemType.valueOf((String) map.get("buildItem"));
			int cost = BuildingType.mineCost(itemType);

			if (dungeon.getItemStock(ItemType.ROCK) - cost < 0) {
                Map<String, Object> result = new HashMap<>();
                result.put("label", "Construction impossible !");
                result.put("bodyLabel", "Vous n'avez pas assez de pierres !");
                result.put("code", 500);
			    return result;
            }

			String label = "";
			int row = ((Double) map.get("row")).intValue();
			int col = ((Double) map.get("col")).intValue();

			if(cost == -1) {
				dungeon.dig(row, col);
				label = "Mur d\u00e9truit !";
			} else {
				BuildingType buildingType = BuildingType.create(itemType);
				dungeon.build(buildingType, row, col);
				if (itemType == ItemType.ROCK) { label = "Vous avez construit une carri\u00e8re pour "+buildingType.getCost()+" pierres."; }
				else if (itemType == ItemType.GOLD) { label = "Vous avez construit une mine d'or pour "+buildingType.getCost()+" pierres."; }
				else if (itemType == ItemType.GEMS) { label = "Vous avez construit une mine de pierres pr\u00e9cieuses pour "+buildingType.getCost()+" pierres."; }
			}
                Map<String, Object> result = new HashMap<>();
                result.put("label", label);
                result.put("bodyLabel", "Cr\u00e9ation en cours ...");
                result.put("image", imagePath);
                result.put("row", row);
                result.put("col", col);
                result.put("code", 200);

			Map<String, Object> result = new HashMap<>();
			result.put("label", label);
            result.put("bodyLabel", "Cr\u00e9ation en cours ...");
			result.put("row", row);
			result.put("col", col);
			result.put("code", 200);

			return result;
		}, JsonTransformer.getInstance());
	}

}

