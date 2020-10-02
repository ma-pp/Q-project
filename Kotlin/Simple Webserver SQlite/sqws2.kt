import java.io.IOException
import java.io.File
import java.net.InetSocketAddress
import java.sql.* // Connection, DriverManager, SQLException, Statement, ResultSet
import java.util.Properties
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
/*
Tujuh baris di atas dikenal oleh kotlinc compiler
sebagai header file. 

$ kotlinc sqws2.kt; kotlin sqws2
$ kotlinc sqws2.kt; kotlin -cp ".:./sqlite-jdbc-3.32.3.2.jar" sqws2
*/

var a: String? = null // search by ayah
var p: String? = null // get data on a specific page
fun getSql(uri: String): MutableList<String> {
    
    var url = uri

    if (uri.indexOf("%3A", 0) >= 0) {
        p = url.split("=")[1].split("%3A")[0]
        a = url.split("=")[1].split("%3A")[1]
    }else{
        p = url.split("=")[1]
    }
    
    val conn = DriverManager.getConnection( "jdbc:sqlite:/home/u/dev/Q-db/quran.db")
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    var qlist: MutableList<String> = mutableListOf<String>() // https://stackoverflow.com/questions/37913252/kotlins-list-missing-add-remove-map-missing-put-etc
    try { 
        stmt = conn!!.createStatement()
        // stmt = conn.createStatement()
        resultset = stmt!!.executeQuery("SELECT * FROM words WHERE page=${p};")
        if (stmt.execute("SELECT * FROM words WHERE page=${p};")) {
            resultset = stmt.resultSet
        }
        
        while (resultset!!.next()) {
            qlist.add(resultset.getString("ar") + "#" + resultset.getString("id") + "#" +  resultset.getString("line") + "#" + resultset.getString("ayah"))
            // qlist.add("halo")
        }

    } catch (ex: SQLException) {
        ex.printStackTrace()
    }
    return qlist
}

fun html(ayah: String): String{
    val html = File("./web/parse-flex-mobile.html").readLines() // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/read-lines.html
    // sqws2.html # Digunakan untuk memanggail Global var yang diinisialisasi di suatu objek.

    // untuk ubah dari Array file ke list biasa yang tanpa indent, karena belum bisa string replace ketika datanya masih ada indent
    val hlist: MutableList<String> = mutableListOf<String>()
    for (i in 0 until html.size) { // https://stackoverflow.com/a/48663899/8464862
        val stringI: String = html[i].toString().trimIndent()
        hlist.add(stringI)
    }
    
    for (i in 0 until hlist.size) { // https://stackoverflow.com/a/48663899/8464862 # loop sampai sepanjang len array
        if (hlist[i].equals("---")){
            hlist.set(i, ayah)
        }
    }
    // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/join-to-string.html
    // https://bezkoder.com/kotlin-convert-string-list/#:~:text=The%20jointostring()%20function%20helps,followed%20by%20the%20truncated%20string.
    return hlist.joinToString(separator = "\n")
}

fun en2ar(num: String): String {
    var o: String = ""
    val dict = mapOf (   // https://discuss.kotlinlang.org/t/python-like-dictionaries-in-kotlin/2008
        "0" to "٠",
        "1" to "١",
        "2" to "٢",
        "3" to "٣",
        "4" to "٤",
        "5" to "٥",
        "6" to "٦",
        "7" to "٧",
        "8" to "٨",
        "9" to "٩"
    )

    for (char in num) {
        val c = char.toString()
        if (dict.containsKey(c)) { // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/contains-key.html
            o += dict[c] 
        }
    }

    return o.reversed() // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/reversed.html
}

fun showFlex(uri: String): String{
    val wbw = getSql(uri)
    // html("halo")
    if (a == null) {
        a = ""
    }
    var j = 0
    var lw = wbw.size

    var lastAyah: Int = 1
    var lastLine: Int = 1

    var flex_out: String = "<div id=\"quran-page\"><div class=\"flex\">"

    while (j < lw){
        val wbw_pl = wbw[j].split("#")
        val ayahNumber = wbw_pl[3]
        val line = wbw_pl[2]

        // println(ayahNumber + " " + a)
        if (j < (lw-1)) {
            if (line != wbw[j+1].split("#")[2]) {
                flex_out += "</div><div class=\"flex\">" // https://www.baeldung.com/kotlin-concatenate-strings
            }
            if (ayahNumber != wbw[j+1].split("#")[3]) {
                if (ayahNumber == a) {
                    flex_out += "<section><span class=\"verseContent yellow-highlight\">${wbw_pl[0]}${en2ar(lastAyah.toString())} </span><span class=\"meaning yellow-highlight\">(${wbw_pl[1]})</span></section>"    
                    j += 1
                    continue
                }
                flex_out += "<section><span class=\"verseContent\">${wbw_pl[0]}${en2ar(lastAyah.toString())} </span><span class=\"meaning\">(${wbw_pl[1]})</span></section>"
                j += 1
                continue
            }
        }else{
            if (ayahNumber == a) {
                flex_out += "<section><span class=\"verseContent yellow-highlight\">${wbw_pl[0]}${en2ar(lastAyah.toString())} </span><span class=\"meaning yellow-highlight\">(${wbw_pl[1]})</span></section>"
                break
            }else{
                flex_out += "<section><span class=\"verseContent\">${wbw_pl[0]}${en2ar(lastAyah.toString())} </span><span class=\"meaning\">(${wbw_pl[1]})</span></section>"
                break
            }
        }
        if (ayahNumber == a) {
            flex_out += "<section><span class=\"verseContent yellow-highlight\">${wbw_pl[0]} </span><span class=\"meaning yellow-highlight\">(${wbw_pl[1]})</span></section>"
        }else{
            flex_out += "<section><span class=\"verseContent\">${wbw_pl[0]} </span><span class=\"meaning\">(${wbw_pl[1]})</span></section>"
        }
        

        if (j < lw) {
            lastLine = wbw_pl[2].toInt()
            lastAyah = wbw_pl[3].toInt()
        }
        j += 1
    }
    flex_out += "</div></div>"
    return html(flex_out)
}

object sqws2 {
    // https://gist.github.com/yingshaoxo/426d1b2557f95faa90aededee6697240
    // https://realkotlin.com/tutorials/2018-06-26-multiline-string-literals-in-kotlin/

    @Throws(Exception::class)
    @JvmStatic fun main(args:Array<String>) {
        val server = HttpServer.create(InetSocketAddress(8000), 0)
        server.createContext("/test", HSHandler()) // HS: Html plus Sql
        server.createContext("/font", FontHandler())
        server.createContext("/script", ScriptHandler())
        // server.setExecutor(null!!) // creates a default executor
        server.start()
    }
    
    internal class HSHandler:HttpHandler {
        @Throws(IOException::class)
        public override fun handle(t:HttpExchange) {
            // karena java adalah bahasa static typed, maka t.getRequestURI(); tidak bisa diassigned ke dalam InputStream
            // InputStream is = t.getRequestURI(); // error: incompatible types: URI cannot be converted to InputStream
            val uri = t.getRequestURI()
            val outToHtml = showFlex(uri.toString()) // https://developer.android.com/reference/android/net/Uri#toString()
            // t.sendResponseHeaders(200, response.length.toLong())
            t.sendResponseHeaders(200, 0)
            t.responseHeaders.add("Content-type", "text/html")
            val os = t.getResponseBody()
            os.write(outToHtml.toByteArray())
            os.close()
        }
    }

    internal class FontHandler:HttpHandler {
        @Throws(IOException::class)
        public override fun handle(t:HttpExchange) {
            val uri = t.getRequestURI()
    
            // t.sendResponseHeaders(200, response.length.toLong())
            t.sendResponseHeaders(200, 0)
            var font_file =  File("./web/UthmanicHafs1 Ver13.otf").readBytes()
            t.responseHeaders.add("Content-type", "font/opentype")
            val os = t.getResponseBody()
            os.write(font_file)
            os.close()
        }
    }

    internal class ScriptHandler:HttpHandler {
    @Throws(IOException::class)
    public override fun handle(t:HttpExchange) {
            val uri = t.getRequestURI()
    
            // t.sendResponseHeaders(200, response.length.toLong())
            t.sendResponseHeaders(200, 0)
            var script =  File("./web/script.js").readBytes()
            t.responseHeaders.add("Content-type", "text/javascript")
            val os = t.getResponseBody()
            os.write(script)
            os.close()
        }
    }
}
