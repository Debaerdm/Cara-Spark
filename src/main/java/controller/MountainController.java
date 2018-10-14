package controller;

import java.util.Map;

import model.Dungeon;
import model.Mountain;
import model.item.BuildingType;
import model.item.ItemType;
import utils.JsonTransformer;

import static model.config.Constants.HTTP_NOT_FOUND;
import static model.config.Constants.HTTP_OK;
import static model.config.Constants.HTTP_UNAUTHORIZED;
import static spark.Spark.*;

public class MountainController {

	private static final String API_CONTEXT = "/api";
	private static final String ACCEPT_TYPE = "application/json";

	public MountainController() {
		setupEndpoints();
	}

	private void setupEndpoints() {
        path(API_CONTEXT, () -> {
            // Part 1
            get("/mountain", (request, response) -> Mountain.getInstance().getDungeonsMap(), JsonTransformer.getInstance());

            // Part 3
            post( "/join", ((request, response) -> {
                if (!Mountain.getInstance().getDungeonsMap().containsKey(request.ip())) {
                    Mountain.getInstance().addDungeon(request.ip());
                } else {
                    System.out.println("No dungeon added, there was already one for ip : ("+request.ip()+")");
                }

                redirect.get(API_CONTEXT + "/join", API_CONTEXT + "/dungeon");

                return HTTP_OK;
            }), JsonTransformer.getInstance());

            path("/dungeon", () -> {
                // Part 4
                before((request, response) -> {
                    if (!Mountain.getInstance().getDungeonsMap().containsKey(request.ip())) {
                        halt(HTTP_UNAUTHORIZED, "You are not welcome here");
                    }
                });
                get("/exist", (request, response) -> (Mountain.getInstance().getDungeonsMap().get(request.ip()) != null), JsonTransformer.getInstance());
                get("/dungeon_total", (request, response) -> Mountain.getInstance().getDungeonsMap().keySet().size(), JsonTransformer.getInstance());

                // Part 5
                get("/maps", (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getMap(), JsonTransformer.getInstance());
                get("/itemTypes", (request, response) -> BuildingType.itemsValues(), JsonTransformer.getInstance());

                // Part 6
                get("/rock", (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.ROCK), JsonTransformer.getInstance());
                get("/gold", (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.GOLD), JsonTransformer.getInstance());
                get("/gems", (request, response) -> Mountain.getInstance().getDungeonsMap().get(request.ip()).getItemStock(ItemType.GEMS), JsonTransformer.getInstance());

                // Part 7
                put("/build",  (request, response) -> {
                    Dungeon dungeon = Mountain.getInstance().getDungeonsMap().get(request.ip());
                    Map<String, Object> map = JsonTransformer.getInstance().parse(request.body());
                    ItemType itemType = ItemType.valueOf((String) map.get("buildItem"));
                    int cost = BuildingType.mineCost(itemType);

                    if (dungeon.getItemStock(ItemType.ROCK) - cost < 0) {
                        return HTTP_NOT_FOUND;
                    }

                    int row = ((Double) map.get("row")).intValue();
                    int col = ((Double) map.get("col")).intValue();

                    BuildingType buildingType = BuildingType.create(itemType);
                    dungeon.build(buildingType, row, col);

                    return HTTP_OK;
                }, JsonTransformer.getInstance());
            });

            // Part 2
            after((request, response) -> response.type(ACCEPT_TYPE));
        });
	}

}

