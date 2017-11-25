package com.example.alan_.sbgb.models.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;
import com.example.alan_.sbgb.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan_ on 01/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String library_itcg = "library_itcg2.db";

    tableCareers career = new tableCareers();
    tableUsers users = new tableUsers();
    tableGenres genres = new tableGenres();
    tableBooks books = new tableBooks();
    tableAccounts accounts = new tableAccounts();
    tableSearch search = new tableSearch();

    QueryHelper query = new QueryHelper();

    public DatabaseHelper(Context context) {
        super(context, library_itcg, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(accounts.getStringQuery());
        db.execSQL(books.getStringQuery());
        db.execSQL(genres.getStringQuery());
        db.execSQL(search .getStringQuery());
        db.execSQL(users.getStringQuery());
        db.execSQL(career.getStringQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+users.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+search.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+books.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+books.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+accounts.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+career.getTableName());
        onCreate(db);
    }

    private static final String TAG = "MyActivity";

    public boolean loginUser(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query.validateUser(userName, password),null);

        if (res.moveToFirst() == true){
            return true;
        }else{
            return false;
        }
    }

    public void truncateTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        if(tableName=="accounts"){
            query="DELETE FROM "+users.getTableName()+";";
        }else if(tableName=="books"){
            query="DELETE FROM "+search.getTableName()+";";
        }else if(tableName=="genres"){
            query="DELETE FROM "+books.getTableName()+";";
        }else if(tableName=="search"){
            query="DELETE FROM "+accounts.getTableName()+";";
        }else if(tableName=="users"){
            query="DELETE FROM "+genres.getTableName()+";";
        }else if(tableName=="career"){
            query="DELETE FROM "+career.getTableName()+";";
        }
        db.execSQL(query);
        db.execSQL("VACUUM;");
    }

    public void deleteTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+users.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+search.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+books.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+books.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+accounts.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+career.getTableName());
        onCreate(db);
    }

    public String newUser(String username, String email, String password, String typeUser, String name, String lastName, String semester, String career){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query.searchUser(username),null);  
        if (res.moveToFirst() == true){
            return "El usuario ya existe";
        }else{
            ContentValues contentAccount = new ContentValues();
            contentAccount.put(accounts.getUsername(), username);
            contentAccount.put(accounts.getEmail(), email);
            contentAccount.put(accounts.getPassword(), password);
            contentAccount.put(accounts.getTypeUser(), typeUser);
            long idAccount = db.insert(accounts.getTableName(),null ,contentAccount);

            ContentValues contentUser = new ContentValues();
            contentUser.put(users.getNC(), idAccount);
            contentUser.put(users.getName(), name);
            contentUser.put(users.getLastName(), lastName);
            contentUser.put(users.getSemester(), semester);
            contentUser.put(users.getCareer(), career);
            db.insert(accounts.getTableName(),null ,contentUser);

            if(idAccount<=0){
                return "El usuario no se ha podido registrar";
            }else{
                return "Usuario Registrado";
            }
        }
    }

    public List getCareers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+career.getTableName(),null);
        List careers = new ArrayList();
        careers.add("Seleccione su carrera");
        for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()){
            careers.add(res.getString(1));
        }
        return careers;
    }

    public String insertCareers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(career.getCareer(), "ITICS");
        db.insert(career.getTableName(),null ,contentValues);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(career.getCareer(), "ISC");
        db.insert(career.getTableName(),null ,contentValues2);

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(career.getCareer(), "IGE");
        db.insert(career.getTableName(),null ,contentValues3);

        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(career.getCareer(), "ADMON");
        db.insert(career.getTableName(),null ,contentValues4);

        ContentValues contentValues5 = new ContentValues();
        contentValues5.put(career.getCareer(), "CP");
        db.insert(career.getTableName(),null ,contentValues5);

        ContentValues contentValues6 = new ContentValues();
        contentValues6.put(career.getCareer(), "I.E");
        long result = db.insert(career.getTableName(),null ,contentValues6);
        if(result>0)
            return "insertadois";
        else
            return "no insertadio";
    }

    public String getQueryCareer(){
        return career.getStringQuery();
    }
    public String getQueryGenres(){
        return genres.getStringQuery();
    }
    public String getQueryBooks(){
        return books.getStringQuery();
    }
    public String getQueryAccounts(){
        return accounts.getStringQuery();
    }
    public String getQuerySearch(){
        return search.getStringQuery();
    }
    public String getQueryUsers(){
        return users.getStringQuery();
    }
}
