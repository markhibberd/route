package io.mth.route.demo;

import io.mth.route.Path;
import io.mth.route.RequestSignature;
import io.mth.route.j.*;

import java.util.Arrays;
import java.util.List;

import static io.mth.route.j.Method.*;

public class FunnelJavaDemo {
    private static F<String, String> f = new F<String, String>() {
        public String apply(String v1) {
            return "[" + v1 + "]";
        }
    };

    public static void main(String[] args) {
        Handlers h = new DefaultHandlers();
        Routes routes = new DefaultRoutes();
        Paths paths = new DefaultPaths();
        Requests requests = new DefaultRequests();
        List<Handler<String>> handlers = Arrays.asList(
            h.id(POST, "/foo/bar", f),
            h.is(GET, "/foo/bar/baz", "exact match"),
            h.is(GET, "/foo/bar/baz/bob", "exact match 2")
        );
        Route route = routes.build(handlers, "base case");
        Path path = paths.parse("/foo/bar/baz/bob");
        RequestSignature request = requests.request(GET, path);
        System.out.println("dispatch = " + route.route(request));
    }
}
