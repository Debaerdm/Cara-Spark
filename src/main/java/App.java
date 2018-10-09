import model.Mountain;

import static spark.Spark.*;

public class App {

	public static void main(String[] args) {
	    port(4567);
	    staticFileLocation("/public");
	    new MountainResource(new Mountain("Test"));
    }

}
