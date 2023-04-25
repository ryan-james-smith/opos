/*
 * Copyright (C) 2016-2017 uniCenta <info at unicenta.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.unicenta.pos.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * SwingFXWebView
 */
public class FXWeb extends JPanel
{
    private Stage     stage;
    private WebView   browser;
    private JFXPanel  jfxPanel;
    private JButton   swingButton;
    private WebEngine webEngine;

    public FXWeb()
    {
        initComponents();
    }

   public static void main(String... args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.getContentPane().add(new FXWeb());
            frame.setMinimumSize(new Dimension(640, 480));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    private void initComponents() {
        jfxPanel = new JFXPanel();
        createScene();
        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);
        swingButton = new JButton();
        swingButton.addActionListener((ActionEvent e) -> Platform.runLater(() -> webEngine.reload()));
        swingButton.setText("Reload");
        add(swingButton, BorderLayout.SOUTH);
    }

    /**
     * createScene Note: Key is that Scene needs to be created and run on
     * "FX user thread" NOT on the AWT-EventQueue Thread
     */
    private void createScene() {
        Platform.startup(() -> {
            stage = new Stage();
            stage.setTitle("Hello Java FX");
            stage.setResizable(true);
            Group root = new Group();
            Scene scene = new Scene(root, 80, 20);
            stage.setScene(scene);
            browser = new WebView();
            webEngine = browser.getEngine();
            webEngine.load("https://unicenta.com/pages/configure-unicenta-opos/");
            ObservableList<Node> children = root.getChildren();
            children.add(browser);
            jfxPanel.setScene(scene);
        });
    }
}
