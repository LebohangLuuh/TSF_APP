package com.example.tsf_app;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
public class TSF_Controller
{
    @FXML
    private Button btnRegister, btnSignIn;
    @FXML
    private TextField txtFullname,txtSurname, txtEmail, txtAddress, txtCellphone;
    @FXML
    private PasswordField txtPassword, txtConfirmPassword;
    @FXML
    private TextField txtCompany_Name, txtCompany_Confirm_Password, txtCompany_Password, txtCompany_Email;
    @FXML
    private TextField txtCompany_Reg_Num, txtCompany_Address, txtCompany_Phone_Num, txtCompany_Category;
    @FXML
    private Button company_Btn_Reg;
    @FXML
    private CheckBox tickBox;
    @FXML
    private Label reg_Feedback;
    @FXML
    protected void getData(ActionEvent actionEvent) {
      if(txtFullname.getText().isEmpty() ||txtSurname.getText().isEmpty() ||txtEmail.getText().isEmpty()||txtPassword.getText().isEmpty() ||txtConfirmPassword.getText().isEmpty())
      {//ALERT THE USER THAT ALL FIELDS MUST BE FILLED!!
          reg_Feedback.setTextFill(Color.RED);
          reg_Feedback.setText("Please fill all the mandatory fields above!!");
         // JOptionPane.showMessageDialog(null,"Please fill all the mandatory fields above!!");
      }
      else
        if (txtPassword.getText().equals(txtConfirmPassword.getText()))
        {//validate the users input to minimize possible wrong information
            if(txtFullname.getText().contains("0-9") || txtSurname.getText().contains("0-9") ||!txtEmail.getText().contains(".") ||!txtEmail.getText().contains("@") )
            {//alert user of any mistakes done!
                reg_Feedback.setTextFill(Color.RED);
                reg_Feedback.setText("Please provide valid details in order to become a member..");
                //JOptionPane.showMessageDialog(null,"Please provide valid details in order to become a member..");
            }
            else {
                JavaPostgreSql.writeToDatabase(txtFullname.getText(), txtSurname.getText(), txtEmail.getText(),txtPassword.getText());
            }
        }
        else
        {//LET THE USER KNOW THE PASSWORDS DID NOT MATCH
            reg_Feedback.setTextFill(Color.RED);
            reg_Feedback.setText("The Passwords did not match!!");
            //JOptionPane.showMessageDialog(null, "The Password and Confirm password did not match!!");
        }
    }
    public void goToSignInPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/TSF_APP_LOGIN_PAGE.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 335, 600);
        stage.setTitle("LOGIN");
        //change app icon to logo
        Image image = new Image("logo_transparent_background.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void getOrganisation_Data () {
        if(txtCompany_Name.getText().isEmpty() ||txtCompany_Email.getText().isEmpty()||txtCompany_Reg_Num.getText().isEmpty()||txtCompany_Category.getText().isEmpty()||txtCompany_Phone_Num.getText().isEmpty()||txtCompany_Address.getText().isEmpty()
                ||txtCompany_Password.getText().isEmpty()||txtCompany_Confirm_Password.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please fill all the mandatory fields above!!");
        }
        else
            if (txtCompany_Password.getText().equals(txtCompany_Confirm_Password.getText()))  // check if passwords match
         {
             if(txtCompany_Name.getText().matches(".*\\d.*") ||!txtCompany_Email.getText().contains(".") || !txtCompany_Email.getText().contains("@") || txtCompany_Phone_Num.getText().length() < 10 || txtCompany_Phone_Num.getText().length() > 12 || txtCompany_Phone_Num.getText().contains("A-Z"))
             {
                 JOptionPane.showMessageDialog(null, "Enter valid Organization details..");
             }
            else
            JavaPostgreSql.writeToOrganization_Table(txtCompany_Name.getText(),txtCompany_Email.getText(),txtCompany_Reg_Num.getText(),txtCompany_Category.getText(),txtCompany_Phone_Num.getText(),txtCompany_Address.getText(),txtCompany_Password.getText());
         }
      else
      {
          JOptionPane.showMessageDialog(null, "The Password and Confirm password did not match!!");
      }
    }
    @FXML
    public void Allow_Registration()// only show the register button after the checkbox is ticked
    {
        btnRegister.setVisible(tickBox.isSelected());
    }
 }