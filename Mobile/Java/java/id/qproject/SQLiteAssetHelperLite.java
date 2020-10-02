package id.qproject;

// /var/snap/anbox/common/data/data/umar.hello/databases/quran.db
import android.content.Context;
import android.database.sqlite.*; // .SQLiteDatabase; SQLiteQueryBuilder
import android.util.Log;
import android.database.Cursor;

import java.io.*; // InputStream; OutputStream; IOException; FileOutputStream;

// https://stackoverflow.com/a/32499402/14286806
public class SQLiteAssetHelperLite extends SQLiteOpenHelper {

    private Context context;
    private Integer p;
    public SQLiteAssetHelperLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, Integer p) {
        super(context, name, factory, version);
        this.p = p;
        this.context = context;
    }

    // copy database from assets folder (.db) file to an empty database
    public  void copyFromAssetsFileToFolder( Context c ) throws IOException  { // https://stackoverflow.com/questions/8474821/how-to-get-the-android-path-string-to-a-file-on-assets-folder/8475135#8475135
        //Open your local db as the input stream
        InputStream myInput = c.getAssets().open("databases/quran.db");
        // Path to the just-created empty db
        String outFileName = "/data/data/id.qproject/quran.db";
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public Cursor getCursor() {
        
        try { copyFromAssetsFileToFolder(this.context); } catch (IOException e) { e.printStackTrace(); }

	    int  OPEN_READONLY = 0x00000001; // getCacheDir();  ini fungsi yg jalan tanpa context 
	    String FILENAME = "/data/data/id.qproject/quran.db";
        Log.v( "Umar ", FILENAME );
        
	    String query = "SELECT * FROM words WHERE page="+this.p; // SQLiteDatabase.rawQuery
	    
        SQLiteDatabase db = SQLiteDatabase.openDatabase( FILENAME  , null, OPEN_READONLY );
        Cursor cursor = db.rawQuery(query, null);
        
        return cursor;
    }

    // This function is the consequences of extending/inherit the SQLiteOpenHelper class
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing, yet
    }

    @Override
    public final void onConfigure(SQLiteDatabase db) {
        // not supported!
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        // do nothing 
    }
}

