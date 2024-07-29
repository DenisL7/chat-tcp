package com.example.chattcpfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;

public class SentMessageController implements EventHandler<ActionEvent> {
    public static NetClient netClient;

    @FXML
    private TextField messageField;
    @FXML
    private TextArea messagesTextArea;



    @Override
    public void handle(ActionEvent actionEvent) {
        netClient.setMessagesTextArea(messagesTextArea);
        String text = messageField.getText();
        netClient.sentMessage(text);
        messageField.setText("");


    }

    public void disconnect(ActionEvent actionEvent) throws IOException {
        netClient.disconnect();
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat-tcp-fx.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
}