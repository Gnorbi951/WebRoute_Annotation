package com.codecool.si_practice;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        //server.createContext("/test", new MyHandler());
        //server.createContext("/", new IndexHandler() );
        for(Method m: Routes.class.getMethods()) {
            if(m.isAnnotationPresent(WebRoute.class)) {
                Routes route = new Routes();
                System.out.println(m.invoke(route));
                server.createContext(m.getAnnotation(WebRoute.class).path(), new MyHandler());
            }
        }

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        Routes route = new Routes();
        public void handle(HttpExchange t) throws IOException {
            String response = route.test1();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class IndexHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the index";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
