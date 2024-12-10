package com.example.musicplayer;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class HelloController {

    private MediaPlayer player;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider progressSlider;

    @FXML
    private Slider scaleSlider; // Slider to control scale

    @FXML
    void openSongMenu(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.m4v", "*.mov"));
        File file = chooser.showOpenDialog(null);

        if (file != null) {
            Media media = new Media(file.toURI().toString());
            if (player != null) {
                player.stop();
            }
            player = new MediaPlayer(media);
            mediaView.setMediaPlayer(player);

            // Bind volume slider
            volumeSlider.setValue(50); // Default volume
            player.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

            // Update progress slider as video plays
            player.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (!progressSlider.isValueChanging()) {
                    progressSlider.setValue(newTime.toSeconds());
                }
            });

            // Seek when progress slider is moved
            progressSlider.setOnMouseReleased(e -> {
                if (player.getMedia() != null) {
                    player.seek(Duration.seconds(progressSlider.getValue()));
                }
            });

            // Set progress slider max to video duration
            player.setOnReady(() -> {
                progressSlider.setMax(player.getMedia().getDuration().toSeconds());
                setMediaViewScale(1.0); // Default to 100% scale when a new video is loaded
            });

            player.play();
        }
    }

    @FXML
    void play(ActionEvent event) {
        if (player != null) {
            player.play();
        }
    }

    @FXML
    void pause(ActionEvent event) {
        if (player != null) {
            player.pause();
        }
    }

    @FXML
    void playPause(ActionEvent event) {
        if (player != null) {
            if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                player.pause();
            } else {
                player.play();
            }
        }
    }

    @FXML
    void forward(ActionEvent event) {
        if (player != null) {
            player.seek(player.getCurrentTime().add(Duration.seconds(10)));
        }
    }

    @FXML
    void backward(ActionEvent event) {
        if (player != null) {
            player.seek(player.getCurrentTime().subtract(Duration.seconds(10)));
        }
    }

    @FXML
    void updateScale() {
        // Get the value of the scale slider
        double scaleFactor = scaleSlider.getValue();

        // Make the change less sensitive by multiplying by a smaller constant factor
        scaleFactor = 0.25 + scaleFactor * 0.25; // This will map the scale from 0.5 to 2.0, but with more gradual changes

        // Apply the updated scale
        setMediaViewScale(scaleFactor);
    }


    @FXML
    void setScale(ActionEvent event) {
        String scaleText = ((MenuItem) event.getSource()).getText(); // Get the text of the MenuItem
        double scaleFactor = 1.0; // Default to 100%

        // Adjust the scale based on the menu item clicked
        switch (scaleText) {
            case "50%":
                scaleFactor = 0.5;
                break;
            case "100%":
                scaleFactor = 1.0;
                break;
            case "150%":
                scaleFactor = 1.5;
                break;
            case "200%":
                scaleFactor = 2.0;
                break;
        }

        setMediaViewScale(scaleFactor);
    }

    private void setMediaViewScale(double scaleFactor) {
        if (mediaView != null && player != null && player.getMedia() != null) {
            mediaView.setFitWidth(player.getMedia().getWidth() * scaleFactor);
            mediaView.setFitHeight(player.getMedia().getHeight() * scaleFactor);
        }
    }
}
