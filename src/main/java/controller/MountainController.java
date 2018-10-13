package controller;

import model.*;
import model.item.BuildingType;
import model.item.ItemType;
import model.tile.Building;
import model.tile.EmptyTile;
import model.tile.Tile;
import org.slf4j.helpers.FormattingTuple;
import utils.JsonTransformer;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

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

			response.redirect("/#/dungeon");
			return 200;
		}), JsonTransformer.getInstance());

		get(API_CONTEXT + "/dungeon", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getMap(), JsonTransformer.getInstance());

		get(API_CONTEXT + "/exist", ACCEPT_TYPE, (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null), JsonTransformer.getInstance());

		get(API_CONTEXT + "/dungeon_total", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().keySet().size(), JsonTransformer.getInstance());

		get(API_CONTEXT+"/itemTypes", ACCEPT_TYPE, (request, response) -> BuildingType.itemsValues(), JsonTransformer.getInstance());

		get(API_CONTEXT+"/rock", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.ROCK), JsonTransformer.getInstance());

		get(API_CONTEXT+"/gold", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.GOLD), JsonTransformer.getInstance());

		get(API_CONTEXT+"/gems", ACCEPT_TYPE, (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.GEMS), JsonTransformer.getInstance());

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
                result.put("bodyLabel", "Vous avez pas assez de money !");
                result.put("code", 500);
			    return result;
            }

			String label;
			String imagePath;
			int row = ((Double) map.get("row")).intValue();
			int col = ((Double) map.get("col")).intValue();

            System.out.println(cost);

			if(cost == -1) {
				dungeon.dig(row, col);
				label = "Mur detruit !";
				imagePath = dungeon.getTile(row, col).getImagePath();
			} else {
				BuildingType buildingType = BuildingType.create(itemType);
				dungeon.build(buildingType, row, col);
				label = "Vous avez construit une station pour recolter de la "+ itemType.name().toLowerCase()+" (cout : "+buildingType.getCost()+")";
				imagePath = buildingType.getImagePath();
			}

			Map<String, Object> result = new HashMap<>();
			result.put("label", label);
            result.put("bodyLabel", "Creation en cours ...");
			result.put("image", imagePath);
			result.put("row", row);
			result.put("col", col);
			result.put("code", 200);

			return result;
		}, JsonTransformer.getInstance());
	}

}

