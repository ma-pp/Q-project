// File ini dibuat untuk belajar menggunakan webserver dengan java supaya bisa diconvert ke dalam kotlin.

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpServ {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            // karena java adalah bahasa static typed, maka t.getRequestURI(); tidak bisa diassigned ke dalam InputStream 
            // InputStream is = t.getRequestURI(); // error:  incompatible types: URI cannot be converted to InputStream
            // System.out.println(t.getRequestURI());
            System.out.println(t.getPathInfo());
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}