package com.example.alan_.sbgb.models.database;

/**
 * Created by alan_ on 17/11/2017.
 */

public class QueryHelper {
    tableCareers career = new tableCareers();
    tableUsers users = new tableUsers();
    tableGenres genres = new tableGenres();
    tableBooks books = new tableBooks();
    tableAccounts accounts = new tableAccounts();
    tableSearch search = new tableSearch();

    /*SESIÓN*/
    public String validateUser(String username, String password){
        return "select "+accounts.getUsername()+" from "+accounts.getTableName()+" where "+accounts.getUsername()+"='"+username+"' AND "+accounts.getPassword()+"='"+password+"'";
    }
    public String searchUser(String username){
        return "select "+accounts.getNC()+" from "+accounts.getTableName()+" where "+accounts.getUsername()+"='"+username+"'";
    }
}
