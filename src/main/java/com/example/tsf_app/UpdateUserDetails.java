package com.example.tsf_app;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateUserDetails {
    public void editUserDetails(String userFullname, String userSurname, String userEmail,
                                String userCellphone, String userAddress, LocalDate txt_D_O_B)
    {
        //database connection
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password

        //prepare the query
        String query = "UPDATE public.\"userDetailsTable\" " +
                "SET user_fullname = ?, user_surname = ?, user_address = ?, user_cellphone = ?, \"user_DOB\" = '?' " +
                "WHERE user_email = ? AND user_id = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, userFullname);
            pst.setString(2, userSurname);
            pst.setString(3, userAddress);
            pst.setString(4, userCellphone);
            pst.setString(5, userEmail);
            pst.setDate(6, (txt_D_O_B == null) ? null : java.sql.Date.valueOf(txt_D_O_B));
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


