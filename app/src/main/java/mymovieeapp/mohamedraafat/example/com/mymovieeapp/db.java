package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class db extends SQLiteOpenHelper {
    private static final int database_version=1;
    private static final String database_name="movie.db";
    public String table_name="popular_movies";
    public String table_name2="Top_Rated_movies";
    public String table_name3="loved_movies";
    public String original_title="original_title";
    public String poster_path="poster_path";
    public String overview="overview";
    public String vote_average="vote_average";
    public String adult="adult";
    public String ids="id";
    public String title="title";
    public String release_date="release_date";
    public String backdrop_path="backdrop_path";
    public String create="create table if not exists "+table_name+" ( "+adult+" text , "+ids+" integer , "+title+" text , "+backdrop_path+" text , "+original_title+" text , "+poster_path+" text , "+overview+" text , "+vote_average+" integer , "+release_date+"  text );";
    public String create2="create table if not exists "+table_name2+" ( "+adult+" text , "+ids+" integer , "+title+" text , "+backdrop_path+" text , "+original_title+" text , "+poster_path+" text , "+overview+" text , "+vote_average+" integer , "+release_date+"  text );";
    public String create3="create table if not exists "+table_name3+" ( "+adult+" text , "+ids+" integer , "+title+" text , "+backdrop_path+" text , "+original_title+" text , "+poster_path+" text , "+overview+" text , "+vote_average+" integer , "+release_date+"  text );";



    public db(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
        db.execSQL(create2);
        db.execSQL(create3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + create);
        db.execSQL("DROP TABLE IF EXIST" + create2);
        db.execSQL("DROP TABLE IF EXIST" + create3);
        onCreate(db);
    }
    public void insert( String original_title, String poster_path, String overview, double vote_average, String release_date,String  adult, int ids,String backdrop_path,String title){
        ContentValues values= new ContentValues();
        values.put(this.original_title,original_title);
        values.put(this.poster_path,poster_path);
        values.put(this.overview,overview);
        values.put(this.vote_average,vote_average);
        values.put(this.release_date, release_date);
        values.put(this.backdrop_path, backdrop_path);
        values.put(this.ids, ids);
        values.put(this.title, title);
        values.put(this.adult, adult);
        this.getWritableDatabase().insert(table_name, null, values);
    }
    public Cursor selcet_all(db database){
        SQLiteDatabase list=database.getWritableDatabase();
        String[] columns={original_title,poster_path,overview,vote_average,release_date,adult,ids,title,backdrop_path};
        Cursor cr=list.query(table_name,columns,null,null,null,null,null);
        return cr;
    }
    public void insert_Rated( String original_title, String poster_path, String overview, double vote_average, String release_date,String  adult, int ids,String backdrop_path,String title){
        ContentValues values= new ContentValues();
        values.put(this.original_title,original_title);
        values.put(this.poster_path,poster_path);
        values.put(this.overview,overview);
        values.put(this.vote_average,vote_average);
        values.put(this.release_date, release_date);
        values.put(this.backdrop_path, backdrop_path);
        values.put(this.ids, ids);
        values.put(this.title, title);
        values.put(this.adult, adult);
        this.getWritableDatabase().insert(table_name2, null, values);
    }
    public Cursor selcet_all_Rated(db database){
        SQLiteDatabase list=database.getWritableDatabase();
        String[] columns={original_title,poster_path,overview,vote_average,release_date,adult,ids,title,backdrop_path};
        Cursor cr=list.query(table_name2,columns,null,null,null,null,null);
        return cr;
    }
    public void insert_fav( String original_title, String poster_path, String overview, double vote_average, String release_date,String  adult, int ids,String backdrop_path,String title,Context context){
        ContentValues values= new ContentValues();
        values.put(this.original_title,original_title);
        values.put(this.poster_path,poster_path);
        values.put(this.overview,overview);
        values.put(this.vote_average,vote_average);
        values.put(this.release_date, release_date);
        values.put(this.backdrop_path, backdrop_path);
        values.put(this.ids, ids);
        values.put(this.title, title);
        values.put(this.adult, adult);
        this.getWritableDatabase().insert(table_name3, null, values);
    }
    public Cursor selcetfavourit(db database){
        SQLiteDatabase list=database.getWritableDatabase();
        String[] columns={original_title,poster_path,overview,vote_average,release_date,adult,ids,title,backdrop_path};
        Cursor cr=list.query(table_name3,columns,null,null,null,null,null);
        return cr;
    }
}


