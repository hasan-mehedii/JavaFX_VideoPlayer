package com.example.musicplayer;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
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
    void openSongMenu(ActionEvent event) {
        try {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);

            if (file != null) {
                System.out.println("File selected: " + file.getAbsolutePath()); // Debugging file path
                // Load the media but don't play it automatically
                Media media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                mediaView.setMediaPlayer(player); // Make sure this is being set

                // Link volume control to the player
                volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (player != null) {
                        player.setVolume(newValue.doubleValue() / 100);
                    }
                });

                // Make sure the player has a playback rate of 1 (normal speed)
                player.setRate(1.0); // Explicitly setting the playback rate to 1.0
            } else {
                System.out.println("No file selected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // This method will play the current media when the Play button is clicked
    @FXML
    void play(ActionEvent event) {
        if (player != null) {
            System.out.println("Attempting to play media..."); // Debugging the play method
            if (player.getStatus() == MediaPlayer.Status.PAUSED || player.getStatus() == MediaPlayer.Status.STOPPED) {
                // Check if the player was paused or stopped and then play
                player.play(); // Play the media
                System.out.println("Media is now playing.");
            } else if (player.getStatus() == MediaPlayer.Status.READY) {
                // If the media is ready but hasn't been played yet, start playing
                player.play();
                System.out.println("Media is now playing.");
            } else {
                System.out.println("Player is already playing.");
            }
        } else {
            System.out.println("Player is not initialized or no media loaded.");
        }
    }

    @FXML
    void playPause(ActionEvent event) {
        if (player != null) {
            System.out.println("Attempting to toggle media...");

            // If the player is in PAUSED or STOPPED state, play the media
            if (player.getStatus() == MediaPlayer.Status.PAUSED || player.getStatus() == MediaPlayer.Status.STOPPED) {
                // If the media was paused or stopped, we play it
                player.play();
                System.out.println("Media is now playing.");
            }
            // If the player is already playing, pause the media
            else if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                player.pause();
                System.out.println("Media paused.");
            }
            // If the player is in READY state, start playing
            else if (player.getStatus() == MediaPlayer.Status.READY) {
                player.play();
                System.out.println("Media is now playing.");
            }
            // Add a general case to log if something unexpected happens
            else {
                System.out.println("Unexpected player state: " + player.getStatus());
            }
        } else {
            System.out.println("Player is not initialized or no media loaded.");
        }
    }


    // This method will pause the current media
    @FXML
    void pause(ActionEvent event) {
        if (player != null) {
            player.pause(); // Pause the media
            System.out.println("Media paused.");
        } else {
            System.out.println("Player is not initialized.");
        }
    }

    // This method is a placeholder for a previous song action
    @FXML
    void previous(ActionEvent event) {
        System.out.println("Previous button clicked");
        // Add your logic to go to the previous song
    }

    // This method is a placeholder for a next song action
    @FXML
    void next(ActionEvent event) {
        System.out.println("Next button clicked");
        // Add your logic to go to the next song
    }

    // This method will forward the media by 10 seconds
    @FXML
    void forward(ActionEvent event) {
        if (player != null) {
            Duration currentTime = player.getCurrentTime();
            Duration seekTime = currentTime.add(Duration.seconds(10));

            // Ensure seek time doesn't go beyond media duration
            if (seekTime.greaterThan(player.getMedia().getDuration())) {
                seekTime = player.getMedia().getDuration();
            }

            player.seek(seekTime);
            System.out.println("Media forwarded by 10 seconds.");
            if (player.getStatus() != MediaPlayer.Status.PLAYING) {
                player.play(); // Ensure audio plays after seeking
            }
        } else {
            System.out.println("Player is not initialized.");
        }
    }

    // This method will backward the media by 10 seconds
    @FXML
    void backward(ActionEvent event) {
        if (player != null) {
            Duration currentTime = player.getCurrentTime();
            Duration seekTime = currentTime.subtract(Duration.seconds(10));

            // Ensure seek time doesn't go before the beginning
            if (seekTime.lessThan(Duration.ZERO)) {
                seekTime = Duration.ZERO;
            }

            player.seek(seekTime);
            System.out.println("Media rewound by 10 seconds.");
            if (player.getStatus() != MediaPlayer.Status.PLAYING) {
                player.play(); // Ensure audio plays after seeking
            }
        } else {
            System.out.println("Player is not initialized.");
        }
    }
}
