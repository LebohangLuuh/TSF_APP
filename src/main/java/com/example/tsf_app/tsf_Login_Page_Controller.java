package com.example.tsf_app;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Label lblCompanyName;
    @FXML
    private Hyperlink sign_Up_Link;
    @FXML
    private Hyperlink forgotPassword_Link;
    @FXML
    private Button btnLogin,btnLogin_Org, btnBack;
    @FXML
    private TextField txtEmail,txtCompany_Email, txtCompany_Name;
    @FXML
    private PasswordField PasswordField,txtCompany_Password;
    @FXML
    private Label menu;
    @FXML
    private Label menuBack;
    @FXML
    private AnchorPane slider;
    @FXML
    private Button btnExit, btnMenuBack, btnMenu;
    @FXML
    protected void logIntoApp() //LOGIN AS AN INDIVIDUAL
    {//database connection
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password

        if(txtEmail.getText().isEmpty() || PasswordField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Both Password field and Email field \nare mandatory to login!");
        }
        else
        {//check if the data is available in the database
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
                try
                {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 335, 600);
                    stage.setTitle("TSF E-COMMERCE ");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException exc)
                {
                    throw new RuntimeException(exc);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Ensure that you entered the \ncorrect e-mail and password..");
            }
            }catch (SQLException ex){
               Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
               lgr.log(Level.SEVERE, ex.getMessage(),ex);
            }
        }
    }
    @FXML
    protected void logIntoApp_Org() //LOGIN AS ORGANIZATION
    {//database connection
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password

        if (txtEmail.getText().isEmpty() || PasswordField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Both Password field and Email field \nare mandatory to login!");
        }
        else
        {
            PreparedStatement ps_Org;
            ResultSet resultSet_Org;
            String organization_Name = txtEmail.getText();
            String organization_Pass = PasswordField.getText();


            String org_Query = "select * from public.\"organizationDetailsTable\" where \"company_Email\" = ? and \"company_Password\" = ? ";
            try {
                ps_Org = DriverManager.getConnection(url,user,password).prepareStatement(org_Query);

                ps_Org.setString(1,organization_Name);
                ps_Org.setString(2,organization_Pass);
                resultSet_Org = ps_Org.executeQuery();

                if (resultSet_Org.next())
                {
                  //  lblCompanyName.setText(resultSet_Org.getString("\'company_Name\'"));//automatically set company name // causes error
                    JOptionPane.showMessageDialog(null,"Login success!");
                    try
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Landingpage_Organization.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load(), 335, 600);
                        stage.setTitle("TSF E-COMMERCE ORGANIZATION");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException exc)
                    {
                        throw new RuntimeException(exc);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Ensure that you entered \nthe correct e-mail and password..");
                }
            }catch (SQLException ex){
                Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(),ex);
            }
        }
    }
    @FXML
    protected void goToOrganizationPage()
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/Organization_Reg_Page.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("REGISTER");
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void goToSignInPage(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(TSF_Application.class.getResource("/com/example/tsf_app/TSF_APP_LOGIN_PAGE.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("LOGIN");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void goToRegisterPage(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/registration_Form.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("REGISTER");
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void goToUser_Type_Page()
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/User_type.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("TYPE OF USER");
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void goToUser_Profile()
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/user_Profile.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("TYPE OF USER");
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    String otpPin;
    @FXML
    protected void goToOTP_Page()
    {//generate otp and send it to the user
        int otpDigitsLen = 5;
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpDigitsLen);

        for (int i = 0; i < otpDigitsLen; i++)
        {
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
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("OTP PAGE");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void closeApp()
    {
                System.exit(0);
    }
    @FXML
    protected void appInfo() // show brief description about the app
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/about_App.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("ABOUT APP");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    protected void goBack() // show brief description about the app
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("ABOUT APP");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void openSlider()
    {
        if (slider!=null)
            slider.setTranslateX(-180);
        btnMenu.setOnAction(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.3));
            slide.setNode(slider);
            slide.setToX(0);
            slide.play();
            slider.setTranslateX(-180);

            slide.setOnFinished(e ->{
            btnMenu.setVisible(false);
            btnMenuBack.setVisible(true);
        });
        });
    }
    @FXML
    protected void closeSlider()
    {
           // slider.setTranslateX(-180);
        btnMenuBack.setOnAction(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.3));
            slide.setNode(slider);
            slide.setToX(-180);
            slide.play();
            slider.setTranslateX(0);

            slide.setOnFinished(e ->{
            btnMenu.setVisible(true);
            btnMenuBack.setVisible(false);
        });
        });
    }
}
