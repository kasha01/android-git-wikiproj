package com.example.Lenovo.myapplication.backend;

import com.google.appengine.api.utils.SystemProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Repository {

    public static Repository instance = null;
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public Repository() {

    }

    private String getConnectionUrl() {
        String url = "";
        try {
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://mytestproject-1812:mysql56/test?user=root";
            } else {
                Class.forName("com.mysql.jdbc.Driver");
                //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TEST", "root", "isql1");
                url = "jdbc:mysql://localhost:3306/TEST?user=root&password=isql1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return url;
        }
    }

    public int SaveWikiBubbleToDb(Integer userid, String content) throws SQLException {
        int index = -1;

        try {
            con = DriverManager.getConnection(getConnectionUrl());
            st = con.createStatement();
            String query = String
                    .format("Insert Into wikibubble (UserId, Content) values(%s, '%s')",
                            userid, content);
            st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                index = rs.getInt(1);
            }
        } finally {
            rs.close();
            st.close();
            con.close();
            return index;
        }
    }

    public void SaveWikiBubbleTags(Integer tagid, String tag) throws SQLException {
        try {
            con = DriverManager.getConnection(getConnectionUrl());
            st = con.createStatement();
            String query = String
                    .format("Insert Into wikibubbletag (TagId, Tag) values(%d, '%s')",
                            tagid, tag);
            st.executeUpdate(query);
        } finally {
            rs.close();
            st.close();
            con.close();
        }
    }
}