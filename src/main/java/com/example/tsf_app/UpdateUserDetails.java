package com.example.tsf_app;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateUserDetails {
        public void editUserDetails (String userFullname, String userSurname, String userEmail, String
        userCellphone, String userAddress){
            //database connection
            String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
            String user = "postgres"; //username
            String password = "Lebohang"; // password

            //prepare the query
            String query = "UPDATE public.\"userDetailsTable\" SET user_fullname = ?, user_surname = ?, user_email = ?, user_cellphone = ?, user_address = ? WHERE user_fullname = ?";

            try (Connection con = DriverManager.getConnection(url, user, password);
                 PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, userFullname);
                pst.setString(2, userSurname);
                pst.setString(3, userEmail);
                pst.setString(4, userCellphone);
                pst.setString(5, userAddress);
                pst.setString(6, userFullname);

                pst.executeUpdate();

                //display a message dialog
                JOptionPane.showMessageDialog(null, "Update successful!");
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

