import model.Mountain;

import static spark.Spark.*;

public class App {

	public static void main(String[] args) {
		Mountain.deserialize();
	    port(4567);
	    staticFileLocation("/public");
	    new MountainResource();
    }

}
