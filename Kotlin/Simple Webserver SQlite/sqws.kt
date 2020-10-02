import com.sun.net.httpserver.HttpServer
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.sql.* // Connection, DriverManager, SQLException
import java.util.Properties

// kotlinc sqws.kt; kotlin -cp ".:./sqlite-jdbc-3.32.3.2.jar" SqwsKt


fun main(args: Array<String>) { // Konsep dalam python mirip seperti def 
	// val conn = DriverManager.getConnection( "jdbc:sqlite:/home/u/dev/Q-Project/Mobile/Kotlin/Simple Webserver SQlite/sampledb.db")
    val conn = DriverManager.getConnection( "jdbc:sqlite:/home/u/dev/Q-db/quran.db")
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    try {  // Konsep dalam python mirip try except
        //stmt = conn!!.createStatement()
        stmt = conn.createStatement()
        resultset = stmt!!.executeQuery("SELECT * FROM words;")
        if (stmt.execute("SELECT * FROM words;")) {
            resultset = stmt.resultSet
        }
        // while (resultset!!.next()) {
        //     println(resultset.getString("name"))
        // }
    } catch (ex: SQLException) {
        // handle any errors
        ex.printStackTrace()
    }

// https://gist.github.com/joewalnes/4bf3ac8abc143225fe2c75592d314840

    HttpServer.create(InetSocketAddress(8080), 0).apply {
        createContext("/hello") { http ->
            println(http.HttpExchange)
            http.responseHeaders.add("Content-type", "text/plain")
            http.sendResponseHeaders(200, 0);
            PrintWriter(http.responseBody).use { out ->
                out.println( "ok")
            	while (resultset!!.next()) {
            	    out.println(resultset.getString("name"))
            	}
            }
        }
        start()
	}
}