/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2014 ObsidianBox <http://obsidianbox.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.obsidianbox.ember.gui;

import com.github.wolf480pl.jline_log4j2_appender.JLineConsoleAppender;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.message.Message;
import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.gui.appender.JavaFXAppender;

import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class Frontend extends Application {
    public static void init(String[] args) {
        launch(args);
    }
    private final Queue<Message> logQueue = new LinkedBlockingQueue<>();
    private Game game;
    private TextArea outputArea;

    @Override
    public void start(Stage stage) {
        // Our pane
        final Pane pane = new Pane();

        // Our scene
        final Scene scene = new Scene(pane);

        // Our controls
        outputArea = new TextArea();
        final TextField inputField = new TextField();
        final Button sendButton = new Button("Send");
        final CheckBox sayCheckBox = new CheckBox("Say");

        // Add our controls to the pane
        pane.getChildren().addAll(outputArea, inputField, sendButton, sayCheckBox);

        // Set the control properties
        outputArea.setWrapText(true);
        outputArea.setEditable(false);
        sendButton.setPrefWidth(60);

        // Set the X and Y positions
        outputArea.setLayoutX(5);
        outputArea.setLayoutY(5);
        sayCheckBox.setLayoutX(5);
        inputField.setLayoutX(55);


        // Scene height resize event
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            outputArea.setPrefHeight(scene.getHeight() - inputField.getHeight() - 15);
            inputField.setLayoutY(scene.getHeight() - inputField.getHeight() - 5);
            sendButton.setLayoutY(scene.getHeight() - sendButton.getHeight() - 5);
            sayCheckBox.setLayoutY(scene.getHeight() - sayCheckBox.getHeight() - 10);
        });

        // Scene width resize event
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            outputArea.setPrefWidth(scene.getWidth() - 10);
            inputField.setPrefWidth(scene.getWidth() - sendButton.getWidth() - 10 - inputField.getLayoutX());
            sendButton.setLayoutX(scene.getWidth() - sendButton.getWidth() - 5);
        });

        // Send Button Click event
        sendButton.setOnAction(event -> {
            if (inputField.getText().isEmpty()) {
                return;
            }
            outputArea.appendText((sayCheckBox.isSelected() ? "say " : "") + inputField.getText() + "\n");
            inputField.clear();
        });

        // Set our scene
        stage.setScene(scene);

        // Set our title
        stage.setTitle("Ember - 1.0.0");

        // Set our default width and height
        stage.setWidth(750);
        stage.setHeight(450);

        // Set our minimum width and height
        stage.setMinWidth(750);
        stage.setMinHeight(450);

        // Show our work
        stage.show();

        // Focus on the input field
        inputField.requestFocus();

        startGame();
    }

    public TextArea getOutput() {
        return outputArea;
    }

    private void startGame() {
        game = new Game();
//        Appender appender = null;
//        for (Map.Entry<String, Appender> entry : game.logger.getAppenders().entrySet()) {
//            if (entry.getKey().equals("Console")) {
//                appender = entry.getValue();
//            }
//        }
//        if (appender != null && appender instanceof JLineConsoleAppender) {
//            game.logger.addAppender(new JavaFXAppender(this, ((JLineConsoleAppender) appender).getFilter(), appender.getLayout()));
//        }
//        Timer timer = new Timer(true);
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (!logQueue.isEmpty()) {
//                    Platform.runLater(() -> {
//                        pollQueue();
//                    });
//                }
//            }
//        }, 0, 2000);
        game.open(false);
    }

    private void pollQueue() {
        Message rawMessage;
        while ((rawMessage = logQueue.poll()) != null) {
            try {
                outputArea.appendText(rawMessage.getFormattedMessage() + "\n");
            } catch (Exception e) {
                game.logger.error("Exception caught processing log message: " + rawMessage, e);
            }
        }
    }

    public Queue<Message> getLogQueue() {
        return logQueue;
    }
}
