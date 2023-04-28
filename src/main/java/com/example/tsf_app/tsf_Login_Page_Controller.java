package com.example.tsf_app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class tsf_Login_Page_Controller {

    @FXML
    private Hyperlink sign_Up_Link;
    @FXML
    private Hyperlink forgotPassword_Link;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField PasswordField;

//__________________________________________________________________________________
    // if already registered - user can access the application
    @FXML
    protected void logIntoApp()
    {
        //database connection

        //database connection
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password

        if(txtEmail.getText().isEmpty() || PasswordField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Both Password field and Email field are mandatory to login!");
        }
        else
        {
                //check if the data is available in the database
                PreparedStatement ps;
                ResultSet resultSet;

                String user_Name = txtEmail.getText();
                String user_Pass = PasswordField.getText();

                String query = "select * from public.\"userDetailsTable\" where user_email = ? and user_password = ? "; // to get the username(email) and password from the database

           try
            {
            ps = DriverManager.getConnection(url,user,password).prepareStatement(query);

            ps.setString(1,user_Name);
            ps.setString(2,user_Pass);

            resultSet = ps.executeQuery();

            if(resultSet.next())
            {
                JOptionPane.showMessageDialog(null,"Login success!");

            }
            else
            {
                JOptionPane.showMessageDialog(null,"Login failed!");

            }

            }catch (SQLException ex){
               Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
               lgr.log(Level.SEVERE, ex.getMessage(),ex);
            }
        }
    }
    //____________________________________________________________________________________________
    String otpPin;
    @FXML
    protected void goToRegisterPage(){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/registration_Form.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("LOGIN");
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    protected void goToOTP_Page(){

        //generate otp and send it to the user
        int otpDigitsLen = 5;
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpDigitsLen);

        for (int i = 0; i < otpDigitsLen; i++) {
            otp.append(digits.charAt(random.nextInt(digits.length())));

        }

        otpPin = otp.toString().trim();
        //just testing - the otp will be sent via email
        //the email specifically for otp required
        //the app will have access and full control to email to send otp
        JOptionPane.showMessageDialog(null, "YOUR ONE TIME PIN IS \nOTP : "
                + otpPin + "\nTHE OTP EXPIRES IN 5 MINUTES","TITLE",JOptionPane.INFORMATION_MESSAGE);

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/forgotPassword.fxml"));
            Stage stage = new Stage();
//-------------------------------------------------------------------------potential error here
           // ForgotPassword_Controller forgotPasswordController = new ForgotPassword_Controller(otpPin); // <-- pass OTP value to constructor
          //  fxmlLoader.setController(forgotPasswordController);
//-------------------------------------------------------------------------------------------------------------------------------------------
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("OTP PAGE");
            stage.setScene(scene);
            stage.showAndWait();
            
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}
