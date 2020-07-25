package com.exfmple.myAppCourseAmount;


import java.sql.*;
public class DBWorker {
    private  final  String HOST = "jdbc:sqlserver://localhost;databaseName=db_monotickets";
    private  final String USERNAME = "pamatixxx";
    private final String PASSWORD = "0660561221";

    private Connection connection;

    public DBWorker(){
        try {
            connection = DriverManager.getConnection(HOST,USERNAME,PASSWORD);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}