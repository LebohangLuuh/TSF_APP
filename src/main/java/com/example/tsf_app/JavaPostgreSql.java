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
        {//database connection
            String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
            String user = "postgres"; //username
            String password = "Lebohang"; // password

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
                pst.setString(1, user_fullname);
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
    public static void writeToOrganization_Table(String company_Name, String company_Email,String company_Reg_Num, String company_Category, String company_Phone_Num, String company_Address, String company_Password) //from organization input page to the database
    {//database connection
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password


        String txtCompany_Name = company_Name;
        String txtCompany_Email = company_Email;
        String txtCompany_Reg_Num = company_Reg_Num;
        String txtCompany_Category = company_Category;
        String txtCompany_Phone_Num = company_Phone_Num;
        String txtCompany_Address = company_Address;
        String txtCompany_Password = company_Password;


        String query = "INSERT INTO " +
                "public.\"organizationDetailsTable\"" +
                "(\"company_Name\", \"company_Email\",\"company_Reg_Num\", \"company_Category\", \"company_Phone_Num\", \"company_Address\", \"company_Password\") " +
                "VALUES (?, ?, ?, ?, ?, ? ,?)";


        try(Connection con = DriverManager.getConnection(url,user,password);
            PreparedStatement pst = con.prepareStatement(query)){
            pst.setString(1,txtCompany_Name);
            pst.setString(2,txtCompany_Email);
            pst.setString(3,txtCompany_Reg_Num);
            pst.setString(4,txtCompany_Category);
            pst.setString(5,txtCompany_Phone_Num);
            pst.setString(6,txtCompany_Address);
            pst.setString(7,txtCompany_Password);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Organization Account successfully created! \nYou are now able to sign in..");
        }catch (SQLException ex){
            Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(),ex);
        }
    }
}
