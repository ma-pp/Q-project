import java.io.IOException
import java.io.OutputStream
import java.io.InputStream
import java.net.InetSocketAddress
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
/*
Tujuh baris di atas dikenal oleh kotlinc compiler
sebagai header file. 
*/

object sqws2 {
    @Throws(Exception::class)
    @JvmStatic fun main(args:Array<String>) {
        val server = HttpServer.create(InetSocketAddress(8000), 0)
        server.createContext("/test", MyHandler())
        // server.setExecutor(null!!) // creates a default executor
        server.start()
    }
    
    internal class MyHandler:HttpHandler {
        @Throws(IOException::class)
        public override fun handle(t:HttpExchange) {
            // karena java adalah bahasa static typed, maka t.getRequestURI(); tidak bisa diassigned ke dalam InputStream
            // InputStream is = t.getRequestURI(); // error: incompatible types: URI cannot be converted to InputStream
            println(t.getRequestURI())
            val response = "This is the response"
            t.sendResponseHeaders(200, response.length.toLong())
            val os = t.getResponseBody()
            os.write(response.toByteArray())
            os.close()
        }
    }
}
