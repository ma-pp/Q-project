// package io.pivotal

// ketika belum punya kotlin di ubuntu 18, install kotlin dengan cara: sudo snap install --classic kotlin

// running this project:
// kotlinc simple-webserver.kt
// kotlin Simple_webserverKt (tanpa suffix .class)

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

fun main(args: Array<String>) {
    val server = HttpServer.create(InetSocketAddress(8000), 0)
    server.createContext("/test", MyHandler())
    server.executor = null // creates a default executor
    server.start()
}

class MyHandler : HttpHandler {
    override fun handle(t: HttpExchange) {
        val response = "This is the response"
        t.sendResponseHeaders(200, response.length.toLong())
        val os = t.responseBody
        os.write(response.toByteArray())
        os.close()
    }
}
