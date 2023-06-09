package com.example.tsf_app;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.DriverManager.getConnection;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
public class tsf_Login_Page_Controller
{
    @FXML
    private Label lblCompanyName,lbl_Status_Msg,lbl_Bio_Msg, lbl_Feedback;
    @FXML
    private Hyperlink sign_Up_Link;
    @FXML
    private Hyperlink forgotPassword_Link;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnLogin_Org;
    @FXML
    private Button btnBack;
    @FXML
    private TextArea txtStatus;
    @FXML
    private Button btn_Pro_Pic;
    @FXML
    private Button btnEdit_profile;
    @FXML
    private TextField txtEmail,txtCompany_Email, txtCompany_Name,txtFullname,txtSurname;
    @FXML
    private PasswordField PasswordField,txtCompany_Password;
    @FXML
    private ImageView img_User_Profile,pic_Upload;
    @FXML
    private AnchorPane slider;
    @FXML
    private Button btnExit, btnMenuBack, btnMenu, btnShareApp;
    @FXML
    private Button btnPost;
    @FXML
    private ImageView post_Image;
    @FXML
    private Button btnAdd_Img;
    @FXML
    private TilePane fileTilePane;
    @FXML
    private TextField txt_Cap;
    private Window stage;
    String otpPin;
    private int user_id;
    @FXML
    protected void logIntoApp() //LOGIN AS AN INDIVIDUAL
    {//database connection
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password

        if(txtEmail.getText().isEmpty() || PasswordField.getText().isEmpty())
        {
            lbl_Feedback.setTextFill(Color.RED);
            lbl_Feedback.setText("Both Password field and Email field \nare mandatory to login!");
           // showMessageDialog(null,"Both Password field and Email field \nare mandatory to login!");
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
            ps = getConnection(url,user,password).prepareStatement(query);

            ps.setString(1,user_Name);
            ps.setString(2,user_Pass);

            resultSet = ps.executeQuery();

            if(resultSet.next())
            {
                lbl_Feedback.setTextFill(Color.GREEN);
                lbl_Feedback.setText("Login success!");
                //JOptionPane.showMessageDialog(null,"Login success!");
                try
                {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 335, 600);
                    stage.setTitle("TSF E-COMMERCE ");
                    //change app icon to logo
                    Image image = new Image("logo_transparent_background.png");
                    stage.getIcons().add(image);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException exc)
                {
                    throw new RuntimeException(exc);
                }
            }
            else
            {
                lbl_Feedback.setTextFill(Color.RED);
                lbl_Feedback.setText("Ensure that you entered the \ncorrect e-mail and password..");
               // showMessageDialog(null,"Ensure that you entered the \ncorrect e-mail and password..");
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
            showMessageDialog(null,"Both Password field and Email field \nare mandatory to login!");
        }
        else
        {
            PreparedStatement ps_Org;
            ResultSet resultSet_Org;
            String organization_Name = txtEmail.getText();
            String organization_Pass = PasswordField.getText();

            String org_Query = "select * from public.\"organizationDetailsTable\" where \"company_Email\" = ? and \"company_Password\" = ? ";
            try {
                ps_Org = getConnection(url,user,password).prepareStatement(org_Query);

                ps_Org.setString(1,organization_Name);
                ps_Org.setString(2,organization_Pass);
                resultSet_Org = ps_Org.executeQuery();

                if (resultSet_Org.next())
                {
                    // lblCompanyName.setText(resultSet_Org.getString("\"company_Name\""));//automatically set company name // causes error
                    showMessageDialog(null,"Login success!");

                    try
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Landingpage_Organization.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load(), 335, 600);
                        stage.setTitle("TSF E-COMMERCE ORGANIZATION");
                        //change app icon to logo
                        Image image = new Image("logo_transparent_background.png");
                        stage.getIcons().add(image);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException exc)
                    {
                        throw new RuntimeException(exc);
                    }
                }
                else
                {
                    showMessageDialog(null,"Ensure that you entered \nthe correct e-mail and password..");
                }
            }catch (SQLException ex){
                Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(),ex);
            }
        }
    }
    @FXML
    protected void goToOrganizationPage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/Organization_Reg_Page.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("REGISTER");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void goToChat_Screen() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat_Page.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("Social");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);

            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void goToSignInPage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(TSF_Application.class.getResource("/com/example/tsf_app/TSF_APP_LOGIN_PAGE.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("LOGIN");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
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
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void goToUser_Type_Page() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/User_type.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("TYPE OF USER");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void goToUser_Profile() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/user_Profile.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("TYPE OF USER");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    protected void goToOTP_Page() {//generate otp and send it to the user
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
        showMessageDialog(null, "YOUR ONE TIME PIN IS \nOTP : "
                + otpPin + "\nTHE OTP EXPIRES IN 5 MINUTES","TITLE", INFORMATION_MESSAGE);
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/tsf_app/forgotPassword.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("OTP PAGE");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
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
    protected void goBack()
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("ABOUT APP");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    private Image profileImage;
    //@FXML
    //private Image upload;
    //trying to add the background to a pro pic
   /* BackgroundImage backgroundImage = new BackgroundImage(
            new Image("PROFILE.png", 110, 110, true, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    Background backgroundPic = new Background(backgroundImage);
    img_User_Profile.setBackground(backgroundPic);*/
    @FXML
    protected void choosePro_Pic() // go to the device storage to chose picture
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            profileImage = new Image(selectedFile.toURI().toString());
            img_User_Profile.setImage(profileImage);
        }
    }
    public void initialize() //set profile pic on imageview
    {
        // other initialization code
        if (profileImage != null) {
            img_User_Profile.setImage(profileImage);
        }
    }
    @FXML
    protected void openSlider() {
        slider.setVisible(true);
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
    protected void closeSlider() {
        slider.setVisible(true);
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
    public void handleEditProfile(ActionEvent event) // want to update database with edited details from the profile page
    {
        String userFullname = txtFullname.getText();
        String userSurname = txtSurname.getText();
        String userEmail = txtEmail.getText();
        //LocalDate userD_O_B = txt_D_O_B.getValue();

        UpdateUserDetails updateUserDetails = new UpdateUserDetails();
        updateUserDetails.editUserDetails(userFullname, userSurname, userEmail);
    }
  /* @FXML
   public void deleteAccount ()// deleting an account button
    {//database connection
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password

        try (Connection con = getConnection(url, user, password))
        { //msg box to confirm to delete the account
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to continue?");
            alert.setContentText("This action cannot be undone.");

            //resizing the width of the msg box
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/alertSizeStyle.css").toExternalForm());
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                // if user clicks OK delete account..
                PreparedStatement prepSt = con.prepareStatement("DELETE from public.\"userDetailsTable\" WHERE user_id = ? ");
                //int userId = Integer.parseInt(String.valueOf(user_id));
                prepSt.setInt(1,user_id);
                prepSt.executeUpdate();

                showMessageDialog(null, "Account deleted successful!"); //display a message for deleted account
            }
            else
            {// Cancel the action and go back to the edit profile page
                goToUser_Profile();
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }*/
    @FXML
    private void edit_lbl_Status_Msg()
    {   //update status on the profile and insert into the database
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Status");
        dialog.setGraphic(null);
        dialog.setHeaderText(null);

        // Set the position of the content text
        Label lblPrompt = new Label("Update your new status:");
        lblPrompt.setStyle("-fx-padding: 10px 0px 0px 0px;");
        lblPrompt.setTextFill(Color.WHITE);
        lblPrompt.setStyle("-fx-font-weight: bold");
        lblPrompt.setStyle("-fx-font-size: 14");
        GridPane.setConstraints(lblPrompt, 0, 0);
        GridPane.setHalignment(lblPrompt, HPos.CENTER);
        GridPane.setValignment(lblPrompt, VPos.TOP);

        // Set the size of the dialog
        dialog.getDialogPane().setPrefWidth(300);
        dialog.getDialogPane().setStyle("-fx-background-color: #192841");

        // Add the content text to the dialog
        GridPane grid = new GridPane();
        grid.add(lblPrompt, 0, 0);
        grid.add(dialog.getEditor(), 0, 1);
        dialog.getDialogPane().setContent(grid);

        // adding colors to the buttons and Get the button nodes
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);

        // Set the color of the buttons using CSS
        okButton.setStyle("-fx-background-color: #192841;-fx-border-radius: 50;-fx-border-color: #00ff00 ;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-background-radius: 50");
        cancelButton.setStyle("-fx-background-color: #192841;-fx-border-radius: 50;-fx-border-color: #FF0000 ;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-background-radius: 50");

        // Show the dialog and get the result
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
             String newStatus = result.get();
            lbl_Status_Msg.setText(newStatus);
            //update_Status_Msg(userId, newStatus); // Pass the user ID and new status to the updateStatus() method
        }
    }
    @FXML
    private void edit_lbl_Bio_Msg()
    {   //update bio on the profile and insert into the database
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Bio");
        dialog.setGraphic(null);
        dialog.setHeaderText(null);

        // Set the position of the content text
        Label lblPrompt = new Label("Update your new bio:");
        lblPrompt.setStyle("-fx-padding: 10px 0px 0px 0px;");
        lblPrompt.setTextFill(Color.WHITE);
        lblPrompt.setStyle("-fx-font-weight: bold");
        lblPrompt.setStyle("-fx-font-size: 14");
        GridPane.setConstraints(lblPrompt, 0, 0);
        GridPane.setHalignment(lblPrompt, HPos.CENTER);
        GridPane.setValignment(lblPrompt, VPos.TOP);

        // Set the size of the dialog
        dialog.getDialogPane().setPrefWidth(300);
        dialog.getDialogPane().setStyle("-fx-background-color: #192841");

        // Add the content text to the dialog
        GridPane grid = new GridPane();
        grid.add(lblPrompt, 0, 0);
        grid.add(dialog.getEditor(), 0, 1);
        dialog.getDialogPane().setContent(grid);

        // adding colors to the buttons and Get the button nodes
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);

        // Set the color of the buttons using CSS
        okButton.setStyle("-fx-background-color: #192841;-fx-border-radius: 50;-fx-border-color: #00ff00 ;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-background-radius: 50");
        cancelButton.setStyle("-fx-background-color: #192841;-fx-border-radius: 50;-fx-border-color: #FF0000 ;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-background-radius: 50");

        // Show the dialog and get the result
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String newBio = result.get();
            lbl_Bio_Msg.setText(newBio);
            //update_Status_Msg(userId, newStatus); // Pass the user ID and new status to the updateStatus() method
        }
    }
    public void upload_Post(ActionEvent event) // button to push the post to feed screen & add to profile page
    {
        btnPost.setVisible(true);
    }
    public void openPost_chooser(ActionEvent event) //go to page to choose the media for a post
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/upload_Post.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("CHOOSE MEDIA");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    public void goTo_Feed_Page(ActionEvent event) //go to page to FEEDS PAGE OF the media for POSTED
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            stage.setTitle("FEEDS");
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
    @FXML
    private void Add_Img_Post(ActionEvent event) //choosing files to be posted from device storage either 1 or more
    {
        btnPost.setVisible(false);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.mp4"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null && selectedFiles.size() <= 10) {
            ObservableList<Image> images = FXCollections.observableArrayList();
            for (File file : selectedFiles) {
                try {
                    Image image = new Image(file.toURI().toString());
                    images.add(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fileTilePane.getChildren().clear();
            int numRows = getNumRows(selectedFiles.size());
            fileTilePane.setPrefRows(numRows);
            for (Image image : images) {
                ImageView imageView = new ImageView();
                //making the imageview resize with the number of file selected
                if (numRows == 1)
                {
                    imageView.setFitWidth(250);
                    imageView.setFitHeight(200);
                } else if (numRows == 2)
                {
                    imageView.setFitWidth(125);
                    imageView.setFitHeight(200);
                }else if (numRows > 2)
                {
                    imageView.setFitWidth(80);
                    imageView.setFitHeight(120);
                }
                else
                {
                    imageView.setFitWidth(80);
                    imageView.setFitHeight(80);
                }
                imageView.setImage(image);

                fileTilePane.getChildren().add(imageView);
            }
        }
        btnAdd_Img.setVisible(false);
        btnPost.setVisible(true);
    }
    private int getNumRows(int numFiles) //checking the number of selected files from the device and allocate enough memory
    {
        if (numFiles < 4)
        {
            return numFiles;
        } else if (numFiles <= 6) {
            return 3;
        } else if (numFiles <= 8) {
            return 4;
        } else {
            return 5;
        }
    }
    /* @FXML // taking the status msg and profile pic into the database of user_Profile
    private void update_Status_Msg(int userId, String newStatus) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/tsf_Database"; //est connection
        String user = "postgres"; //username
        String password = "Lebohang"; // password
        //to update the database
        String query = "UPDATE public.\"user_Profile\" " +
                "SET status_Msg = newStatus " +
                "WHERE user_id = userId";

        PreparedStatement statement = getConnection(url,user,password).prepareStatement(query);
        statement.setString(1, newStatus);
        statement.setInt(2, userId);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            System.out.println("No rows were updated.");
        } else {
            System.out.println(rowsAffected + " rows were updated.");
        }
    }*/
}
