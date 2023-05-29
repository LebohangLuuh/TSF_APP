package com.example.tsf_app;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateUserDetails {
    public void editUserDetails(String userFullname, String userSurname, String userEmail)
    {
        //database connection
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password

        //prepare the query
        String query = "UPDATE public.\"userDetailsTable\" " +
                "SET user_fullname = ?, user_surname = ?, \"user_DOB\" = '?' " +
                "WHERE user_email = ? AND user_id = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, userFullname);
            pst.setString(2, userSurname);
            pst.setString(3, userEmail);
            //pst.setString(7,userFullname);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Update successful!");  //display a message for updated
        } catch (SQLException ex)
        {// handle SQLExceptions
            String errorMessage = ex.getMessage();
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, errorMessage, ex);
        }
    }
}


