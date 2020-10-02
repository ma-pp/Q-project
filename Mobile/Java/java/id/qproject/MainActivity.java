package id.qproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.*; // Toast; EditText; Button
import android.view.View;
import android.webkit.*;
import android.content.res.AssetManager;
import android.database.sqlite.* ; // .SQLiteDatabase; SQLiteQueryBuilder
import android.content.Context;
import android.util.Log;
import android.database.Cursor;
import java.io.*; // InputStream; OutputStream; IOException; FileOutputStream;
import java.util.*; 

class JavaScriptInterface {
    // Proses dari pertanyaan ke googling:

    // mobile javascript view; mobile masih terlalu luas
    // simple java web server; web server adalah web yang ada koneksinya (e.g. http)
    // android javascript webview callback; spesifikasi di android dan webview. Webview (view renderer for web)
    
    Context mContext;
    protected static final String DATABASE_NAME = "quran.db";
    private static final int DATABASE_VERSION = 1;
    private Cursor cursor;
    private SQLiteAssetHelperLite helper;

    JavaScriptInterface(Context c) {
        mContext = c;
    }


    @JavascriptInterface
    public String getUri(String uri) {
        return generatePage(uri);
    }
    
    String convertToString(ArrayList<String> strings) {
        StringBuilder builder = new StringBuilder();
        // Append all Integers in StringBuilder to the StringBuilder.
        for (String string : strings) {
            builder.append(string);
            builder.append(" ");
        }
        // Remove last delimiter with setLength.
        // builder.setLength(builder.length() - 1);
        return builder.toString();
    }
    
    // ArrayList<String> flex_template = new ArrayList<String>(); 
    String flex_template = "";

    String en2ar(String num) {
		String ar_num = num.replace("0","٠").replace("1","١").replace("2","٢").replace("3","٣").replace("4","٤").replace("5","٥").replace("6","٦").replace("7","٧").replace("8","٨").replace("9","٩");
		return new StringBuilder(ar_num).reverse().toString();
    }
    
    String generatePage(String uri) {
        flex_template = "";
        Integer search_page;
        String search_ayah = "";
        
        if (uri.contains(":")) {
            search_page = Integer.parseInt(uri.split(":")[0]);
            search_ayah = uri.split(":")[1];
        }else {
            search_page = Integer.parseInt(uri);
        }

        // sqlite
        helper = new SQLiteAssetHelperLite(mContext, DATABASE_NAME , null, DATABASE_VERSION, search_page); 
        cursor = helper.getCursor();
        

        cursor.moveToFirst();
        ArrayList<String> wbw = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String page = cursor.getString(cursor.getColumnIndex("page"));
            String ayah = cursor.getString(cursor.getColumnIndex("ayah"));
            String line = cursor.getString(cursor.getColumnIndex("line"));
            String ar = cursor.getString(cursor.getColumnIndex("ar"));
            String id = cursor.getString(cursor.getColumnIndex("id"));
            wbw.add(page + "#" + ayah + "#" + line + "#" + ar + "#" + id);
            cursor.moveToNext();
        }
        String[] wbw_ar = wbw.toArray(new String[wbw.size()]);
        flex_template += "<div class=\"flex\">";
        int j = 0;
        int wbw_length = cursor.getCount();
        String lastAyah = "1";
        while (j < wbw_length) {
            String[] wbw_pl = wbw_ar[j].split("#");
            String ayahNumber = wbw_pl[1];
            String line = wbw_pl[2];
            if (j < wbw_length - 1) {
                // flex_template += " " + ayahNumber + "--" + wbw_ar[j+1].split("#")[1] ;
                if (!line.equals(wbw_ar[j+1].split("#")[2])) {
                    flex_template += "</div><div class=\"flex\">";
                }
                if (!ayahNumber.equals(wbw_ar[j+1].split("#")[1])){
                    if (ayahNumber.equals(search_ayah)) {
                        flex_template += "<section><span class=\"verseContent yellow-highlight\">"+wbw_pl[3]+""+en2ar(ayahNumber)+ "</span><span class=\"meaning yellow-highlight\">"+wbw_pl[4]+"</span></section>";
                        j++;
                        continue;
                    }
                    flex_template += "<section><span class=\"verseContent\">"+wbw_pl[3]+""+en2ar(ayahNumber)+" </span><span class=\"meaning\">"+wbw_pl[4]+"</span></section>";
                    j++;
                    continue;
                }
            } else {
                if (ayahNumber.equals(search_ayah)) {
                    flex_template += "<section><span class=\"verseContent yellow-highlight\">"+wbw_pl[3]+""+en2ar(ayahNumber)+" </span><span class=\"meaning yellow-highlight\">"+wbw_pl[4]+"</span></section>";
                    break;
                }else{
                    flex_template += "<section><span class=\"verseContent\">"+wbw_pl[3]+""+en2ar(ayahNumber)+" </span><span class=\"meaning \">"+wbw_pl[4]+"</span></section>";
                    break;
                }
            }
            if (ayahNumber.equals(search_ayah)){
                flex_template += "<section><span class=\"verseContent  yellow-highlight\">"+wbw_pl[3]+"</span><span class=\"meaning  yellow-highlight\">"+wbw_pl[4]+"</span></section>";
            }else {
                flex_template += "<section><span class=\"verseContent\">"+wbw_pl[3]+"</span><span class=\"meaning \">"+wbw_pl[4]+"</span></section>";
            }
            j++;
        }
        flex_template += ("</div>");
        return flex_template;
    }
    
    
}

public class MainActivity extends Activity {
    
    /*
    Ditambahkannya SQLiteAssetHelperLite adalah untuk mengambil db yang ada di asset.
    Awalnya hanya butuh SQLiteDatabase.openOrCreateDatabase() untuk mengambil db 
    dari /sdcard; yakni folder yang bisa diakses oleh seluruh app yang diberi
    hak akses oleh AndroidManifest.xml di Android.
    */
    private Context context;
    private GotoDialog jumpto;
    private MainActivity activity;
    private JavaScriptInterface jsInterface = new JavaScriptInterface(this);

    public WebView webView;

    public void gotoPage(String textString) {
        // TODO button gotoPage
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.activity_main);
        this.activity = this;
        // Struktur data view
        webView = (WebView) findViewById(R.id.webview); // (WebView) adalah type casting di java. Kotlin type casting pakai "as"
        // final EditText simpleEditText = (EditText) findViewById(R.id.et_simple);
        // final Button button = (Button) findViewById(R.id.jump_to);

        WebSettings webSettings = webView.getSettings();
        webView.loadUrl("file:///android_asset/raw/parse_flex.html");      
        // webView.loadUrl("javascript:getVal()");
        
        // Ketika pertama kali aplikasi dijalankan
        jsInterface.generatePage("1");

        // webSettings.setLoadWithOverviewMode(true);
        // webSettings.setUseWideViewPort(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webSettings.setJavaScriptEnabled(true);            
        webView.setWebViewClient(new WebViewClient() { // awal: android webview prevent reload on javascript change > (4 jam) > android webview load html with javascript from assets > https://stackoverflow.com/a/44193529/14286806 
            @Override
            public void onPageFinished(WebView view, String url) { 
                view.loadUrl("javascript:document.getElementById('quran-page').innerHTML = '"+jsInterface.flex_template+"'");
            }
        });
        webView.addJavascriptInterface(jsInterface, "AndroidFunction");
    }

}
