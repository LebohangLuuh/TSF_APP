package com.example.tsf_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class TSF_Controller {
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnSignIn;
    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtCellphone;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;

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
    protected void getData(ActionEvent actionEvent) {

        //TO BE REMOVE BEFORE THE ACTUAL APPS LAUNCHES
        JOptionPane.showMessageDialog(null,"Fullname                      : "
                +txtFullname.getText() +
        "\nSurname                          : "+txtSurname.getText() +
                "\nEmail                             : "+txtEmail.getText() +
                "\nMobile Number                : "+txtCellphone.getText() +
                "\nAddress                         : "+txtAddress.getText() +
                "\nPassword                        : "+txtPassword.getText() +
                "\nConfirm Password         : "+txtConfirmPassword.getText());



      if(txtFullname.getText().isEmpty() ||txtSurname.getText().isEmpty() ||txtEmail.getText().isEmpty() ||txtAddress.getText().isEmpty() ||txtCellphone.getText().isEmpty() ||txtPassword.getText().isEmpty() ||txtConfirmPassword.getText().isEmpty())
      {
          //ALERT THE USER THAT ALL FIELDS MUST BE FILLED!!
          JOptionPane.showMessageDialog(null,"Please fill all the mandatory fields above!!");
      }
      else
        if (txtPassword.getText().equals(txtConfirmPassword.getText()))
        {
            JavaPostgreSql.writeToDatabase(txtFullname.getText(), txtSurname.getText(), txtEmail.getText(), txtCellphone.getText(), txtAddress.getText(), txtPassword.getText());
        }
        else
        {
            //LET THE USER KNOW THE PASSWORDS DID NOT MATCH
            JOptionPane.showMessageDialog(null, "The Password and Confirm password did not match!!");
        }
    }
}