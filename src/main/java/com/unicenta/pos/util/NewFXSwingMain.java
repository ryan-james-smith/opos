/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicenta.pos.util;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.formdev.flatlaf.FlatLightLaf;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author user
 */
@Slf4j
public class NewFXSwingMain extends JFrame {

    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                log.error("Error setting LAF "+e.getMessage());
            }

            JFrame frame = new NewFXSwingMain();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public NewFXSwingMain() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(() -> createScene());
    }

    private void createScene() {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        fxContainer.setScene(new Scene(root));
    }

}
