package deepmehtait.com.imdbsearch.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Deep on 07-Apr-16.
 */
// Helper class for read, write on DB
public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DataBaseName="History.db";
    public static final String TableName="viewhistory";
    public static final String imdbID="imdbID";
    public static final String Poster="Poster";
    public static final String title="title";
    public static final String year="year";
    public static final String type="type";

    // Create DB to store viewed movie/tv series
    public SqliteHelper(Context context) {
        super(context, DataBaseName, null, 1);

    }

    // Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery="create table if not exists " + TableName +" (imdbID TEXT PRIMARY KEY,Poster TEXT,title TEXT,year TEXT,type TEXT)";
        db.execSQL(createTableQuery);

    }

    // Drop Table for new table creation
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }

    // Insert viewed movie/tv series
    public boolean insertData(String imdbID,String Poster,String title, String year,String type){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("imdbID",imdbID);
        contentValues.put("Poster",Poster);
        contentValues.put("title",title);
        contentValues.put("year",year);
        contentValues.put("type", type);
        long result=db.insert(TableName,null,contentValues);
        if(result ==-1){
            return false;
        }else{
            return true;
        }

    }

    // Get list of all Movies for Movie Fragment display
    public Cursor getAllMovies(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from "+TableName+" where type='movie'";
        Cursor res=db.rawQuery(query, null);
        return res;
    }

    // Get list of all Movies for TV Series Fragment display
    public Cursor getAllSeries(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from "+TableName+" where type='series'";
        Cursor res=db.rawQuery(query, null);
        return res;
    }
}
