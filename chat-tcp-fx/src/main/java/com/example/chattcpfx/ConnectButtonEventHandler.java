package com.example.chattcpfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectButtonEventHandler implements javafx.event.EventHandler<ActionEvent> {
    @FXML
private TextField hostField;
    @FXML
private TextField portField;
    @FXML
private  TextField loginField;
    @Override
    public void handle(ActionEvent actionEvent) {
        String hostValue=hostField.getText();
        String portValue=portField.getText();
        String loginValue=loginField.getText();
        int port=Integer.parseInt(portValue);
        NetClient netClient=new NetClient();
        netClient.setLogin(loginValue);
        netClient.setHost(hostValue);
        netClient.setPort(port);
        try {
            netClient.connect();
            SentMessageController.netClient=netClient;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("successfully connected");
        try {
            Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("client-chat-tcp-fx.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    }

