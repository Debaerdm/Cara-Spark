package controller;

import java.util.HashMap;
import java.util.Map;

import model.Dungeon;
import model.Mountain;
import model.item.BuildingType;
import model.item.ItemType;
import utils.JsonTransformer;

import static spark.Spark.*;

public class MountainController {

	private static final String API_CONTEXT = "/api";
	private static final String ACCEPT_TYPE = "application/json";

	public MountainController() {
		setupEndpoints();
	}

	private void setupEndpoints() {
        path(API_CONTEXT, () -> {
            get("/mountain", (request, response) -> Mountain.getInstance().getDungeonsMap(), JsonTransformer.getInstance());

            post( "/join", ((request, response) -> {
                if (!Mountain.getInstance().getDungeonsMap().containsKey(request.ip())) {
                    Mountain.getInstance().addDungeon(request.ip());
                } else {
                    System.out.println("No dungeon added, there was already one for ip : ("+request.ip()+")");
                }

                redirect.get(API_CONTEXT + "/join", API_CONTEXT + "/dungeon/maps");

                return 200;
            }), JsonTransformer.getInstance());

            path("/dungeon", () -> {
                before((request, response) -> {
                    if (!Mountain.getInstance().getDungeonsMap().containsKey(request.ip())) {
                        halt(401, "You are not welcome here");
                    }
                });

                get("/maps", (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getMap(), JsonTransformer.getInstance());

                get("/exist", (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null), JsonTransformer.getInstance());

                get("/dungeon_total", (request, response) -> Mountain.getInstance().getDungeonsMap().keySet().size(), JsonTransformer.getInstance());

                get("/itemTypes", (request, response) -> BuildingType.itemsValues(), JsonTransformer.getInstance());

                get("/rock", (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null) ? Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.ROCK) : 0, JsonTransformer.getInstance());

                get("/gold", (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null) ? Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.GOLD) : 0, JsonTransformer.getInstance());

                get("/gems", (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null) ? Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.GEMS) : 0, JsonTransformer.getInstance());

                put("/build",  (request, response) -> {
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
                    result.put("bodyLabel", "Bâtiment créé !");
                    result.put("row", row);
                    result.put("col", col);
                    result.put("code", 200);

                    return result;
                }, JsonTransformer.getInstance());
            });

            after((request, response) -> response.type(ACCEPT_TYPE));
        });
	}

}

