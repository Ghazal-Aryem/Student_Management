package com.example.loginpage;

import android.util.Log;

import java.sql.*;
public class LayerClass {
    Connection con;
    void Connection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String constr="jdbc:mysql://localhost:3306/mobileapp";
            con=DriverManager.getConnection(constr,"root","");
        }
        catch(Exception e){
            Log.i("DBMS",e.toString()+" Due to Connection");
        }
    }
    int Insert(String Name, String Pass, String Gmail){
        int res=0;
        Connection();
        try{
            String qry="insert into user (u_name,u_password,u_gmail) values (?,?,?)";
            PreparedStatement pst=con.prepareStatement(qry);
            pst.setString(1,"Name");
            pst.setString(2,"Pass");
            pst.setString(3, "Gmail");
            res=pst.executeUpdate();
        }
        catch(Exception ex){
            Log.i("DBMS",ex.toString()+" Due to Insertion");
        }
        return res;
    }
}
