import static model.config.Constants.RESOURCE_FOLDER;
import static model.config.Constants.RESOURCE_IMAGES;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import controller.MountainController;
import model.Mountain;

public class App {

	public static void main(String[] args) {
		Mountain.getInstance();
	    port(4567);
	    staticFileLocation(RESOURCE_FOLDER);
	    staticFileLocation(RESOURCE_IMAGES);
	    new MountainController();
    }

}
