package com.example.tsf_app;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSql
{
    public static void writeToDatabase(String user_fullname, String user_surname, String user_email, String user_cellphone, String user_address, String user_password)
        {
            //database connection
            String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
            String user = "postgres"; //username
            String password = "Lebohang"; // password

            String txtFullname = user_fullname;
            String txtSurname = user_surname;
            String txtEmail = user_email;
            String txtCellphone = user_cellphone;
            String txtAddress = user_address;
            String txtPassword = user_password;

            String query = "INSERT INTO " +
                    "public.\"userDetailsTable\"" +
                    "(user_fullname, user_surname, user_email, user_address, user_cellphone, user_password) " +
                    "VALUES (?, ?, ?, ?, ?, ? )";

            try(Connection con = DriverManager.getConnection(url,user,password);
                PreparedStatement pst = con.prepareStatement(query)){
                pst.setString(1,txtFullname);
                pst.setString(2,txtSurname);
                pst.setString(3,txtEmail);
                pst.setString(4,txtCellphone);
                pst.setString(5,txtAddress);
                pst.setString(6,txtPassword);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null,"Account successfully created! \nYou are now able to sign in..");
            }catch (SQLException ex){
                Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(),ex);
            }
        }

}
