package com.example.musicplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {

    @FXML
    protected void onPlayButtonClick(ActionEvent event) {
        try {
            // Load the hello-view.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Get the current stage (first window)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new stage for the second window
            Stage newStage = new Stage();
            Scene newScene = new Scene(root);

            // Apply the size of the first window to the second window
            newStage.setWidth(currentStage.getWidth());
            newStage.setHeight(currentStage.getHeight());

            // Set the scene and show the new window
            newStage.setScene(newScene);
            newStage.show();

            // Optionally close the first window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExitButtonClick() {
        // Exit the application
        System.exit(0);
    }
}
