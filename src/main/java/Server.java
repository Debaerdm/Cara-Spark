import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashSet;
import java.util.Set;

import static spark.Spark.*;

public class Server {

    /*private static Set<String> ips = new HashSet<>();

    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/public"); //index.html is served at localhost:4567 (default port)
        staticFiles.expireTime(600);

        get("/index", route);

        after((req, res) -> res.type("application/json"));
    }

    public static Route route = (Request request, Response response) -> {
        ips.add(request.ip());
        System.out.println(ips.toString());

        return "Hello, World !";
    };*/
}


