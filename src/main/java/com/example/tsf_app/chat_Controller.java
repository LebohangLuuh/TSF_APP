package com.example.tsf_app;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
public class chat_Controller implements Initializable
{
    @FXML
    private ScrollPane chat_Scroll;
    @FXML
    private Button send_Msg;
    @FXML
    private TextField txt_Type_Msg;
    @FXML
    private VBox vbox_Msg;
    @FXML
    private Label lblDate;
    private Client client;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        LocalDateTime now = LocalDateTime.now();
        String timeStamp = now.format(DateTimeFormatter.ofPattern("HH:mm"));

        try {
            client = new Client(new Socket("localhost",5432));
            System.out.println("Connected to the server");
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        vbox_Msg.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue)
            {
                chat_Scroll.setVvalue((Double) newValue);
            }
        });

        client.receiveMsgFromServer(vbox_Msg);

        send_Msg.setOnAction(event -> {
            String msgToSend = txt_Type_Msg.getText(); // removing spaces so that users don't send empty messages
            if (!msgToSend.isEmpty())
            {

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5,5,5,10));

                Text msgText = new Text(msgToSend);
                msgText.setFont(Font.getDefault()); // set the default font for the message text
                msgText.setFill(Color.WHITE); // set the text color

                Text newtimeStamp = new Text(timeStamp); // create a new Text object for the timestamp
                newtimeStamp.setFont(Font.font(8)); // set the font size
                newtimeStamp.setFill(Color.GREENYELLOW); // set the text color

                TextFlow textFlow = new TextFlow(msgText, new Text("\n"), newtimeStamp); // add the timestamp to the TextFlow

                textFlow.setStyle("-fx-background-color: #192841FF;-fx-background-radius: 20;-fx-border-radius: 25;-fx-border-color: white");//colors to be changed later on
                textFlow.setPadding(new Insets(5,10,5,10));

                hBox.getChildren().add(textFlow);
                vbox_Msg.getChildren().add(hBox);

                client.sendMsgToServer(msgToSend);
                txt_Type_Msg.clear();
            }
        });
    }
    public static  void addLabel(String msgFromServer, VBox vbox)
    {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-border-color:green; -fx-border-radius-radius:50");
        textFlow.setPadding(new Insets(5,10,5,10));
        // text.setFill(Color.color(0.934,0.945,0.996));

        hBox.getChildren().add(textFlow);

        Platform.runLater(() -> vbox.getChildren().add(hBox));
    }
    @FXML
    protected void goBack()
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 335, 600);
            //change app icon to logo
            Image image = new Image("logo_transparent_background.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}