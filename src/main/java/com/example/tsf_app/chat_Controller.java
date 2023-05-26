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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
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
    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
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

                Text text = new Text(msgToSend);
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-border-color: #192841 ;-fx-border-radius: 50;");//colors to be changed later on
                textFlow.setPadding(new Insets(5,10,5,10));
                //text.setFill(Color.color(0.934,0.945,0.996));

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

        textFlow.setStyle("-fx-border-color:blue; -fx-border-radius-radius:50");//colors to be changed later on
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
}