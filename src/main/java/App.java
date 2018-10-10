import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import model.Mountain;

public class App {

	public static void main(String[] args) {
		Mountain.getInstance();
	    port(4567);
	    staticFileLocation("/public");
	    staticFileLocation("/public/images");
	    new MountainResource();
    }

}
