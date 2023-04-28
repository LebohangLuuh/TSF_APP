package com.example.tsf_app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ForgotPassword_Controller {

    @FXML
    private Button btnSendOtp;
    @FXML
    private TextField txtOTP;
    String otpPin;

   /* public ForgotPassword_Controller(String otpPin) {
        this.otpPin = otpPin;
    }*/

    @FXML
    protected void setNew_Password() {
        //compare the otp and if they match
        // open set new password page
        //set new password and overwrite the old one
        //go to login page
//--------------------------------------------------------------------------------

        //compare entered otp with the provided otp function
        //if they match - go to login page
            if(txtOTP.getText().trim().equals(otpPin))
            {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(TSF_Application.class.getResource("/com/example/tsf_app/set_new_password.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 335, 600);
                    stage.setTitle("SET NEW PASSWORD");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Enter valid one time!");
            }
        }
}
