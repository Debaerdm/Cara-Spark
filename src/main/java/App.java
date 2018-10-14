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

    /*
		* TODO :
		*	- Add collect/produce actions on the view
		*	- Add a view to allow inter-mountain connections
		*	- Reduce the dynamic update overhead in app.js $scope.updateAll method
		*	- Write the powerpoint
		*	- Setup git repositories with several milestones
		*	- Allow the view of someone else's dungeon (optional)
     */

}
