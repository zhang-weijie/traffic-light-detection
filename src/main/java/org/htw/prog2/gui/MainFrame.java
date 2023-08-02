package org.htw.prog2.gui;
/*
 * Name: Weijie Zhang
 * Matrikelnummer: s0582504
 */

import org.htw.prog2.ampel.Configuration;
import org.htw.prog2.ampel.FormatException;
import org.htw.prog2.ampel.Light;
import org.htw.prog2.ampel.LightImage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Weijie Zhang, weijiezhangpku@gmail.com
 * @version 1.0
 */
public class MainFrame extends JFrame {
    private ImagePanel imagePanel;
    private JMenuBar menuBar;
    private JPanel controlPanel;
    private JPanel switchPanel;
    private JButton preButton;
    private JButton nextButton;
    private JPanel optionPanel;
    private JButton analyseButton;
    private JLabel resLabel;
    private JPanel showBorderPanel;
    private JLabel showBordersLabel;
    private JCheckBox showBordersCheckBox;

    private int listIndex = -1;
    private ArrayList<LightImage> lightImages = new ArrayList<>();

    public MainFrame() {
        super("Ampel-Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        initImagePanel();
        initControlPanel();
        initMenu();
        setSize(560, 450);
    }

    private void initControlPanel(){
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        initSwitchPanel();
        initOptionPanel();
        add(controlPanel,BorderLayout.SOUTH);
    }

    private void initSwitchPanel(){
        switchPanel = new JPanel();
        switchPanel.setLayout(new BorderLayout());

        preButton = new JButton("pre");
        preButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listIndex > 0){
                    imagePanel.setImage(lightImages.get(--listIndex));
                    imagePanel.repaint();
                }
                updateResLabel();
            }
        });
        preButton.setEnabled(false);

        nextButton = new JButton("next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listIndex < lightImages.size() - 1){
                    imagePanel.setImage(lightImages.get(++listIndex));
                    imagePanel.repaint();
                }
                updateResLabel();
            }
        });
        nextButton.setEnabled(false);

        switchPanel.add(preButton, BorderLayout.WEST);
        switchPanel.add(nextButton, BorderLayout.EAST);

        controlPanel.add(switchPanel,BorderLayout.NORTH);
    }

    private void updateResLabel(){
        List<Light> lights = imagePanel.getConfiguration().getLights();
        String activeLightName = imagePanel.getImage().getActiveLight(lights);
        resLabel.setText("Analysis result: " + activeLightName);
    }

    private void initOptionPanel(){
        optionPanel = new JPanel();
        optionPanel.setLayout(new BorderLayout());

        analyseButton = new JButton("Analyze");
        analyseButton.setEnabled(false);
        analyseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateResLabel();
            }
        });

        resLabel = new JLabel("Analysis result: N/A",JLabel.CENTER);

        showBorderPanel = new JPanel();
        showBordersLabel = new JLabel("Show borders:");
        showBordersCheckBox = new JCheckBox();
        showBordersCheckBox.setEnabled(false);
        showBordersCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagePanel.setShowBorder(!imagePanel.isShowBorder());
            }
        });
        showBorderPanel.add(showBordersLabel);
        showBorderPanel.add(showBordersCheckBox);

        optionPanel.add(analyseButton, BorderLayout.WEST);
        optionPanel.add(resLabel, BorderLayout.CENTER);
        optionPanel.add(showBorderPanel, BorderLayout.EAST);

        controlPanel.add(optionPanel, BorderLayout.SOUTH);
    }

    private void initImagePanel(){
        imagePanel = new ImagePanel();
        add(imagePanel,BorderLayout.CENTER);
    }

    private void initMenu(){
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadConfig = new JMenuItem("Load configuration");
        loadConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                chooser.setFileFilter(new FileNameExtensionFilter("Configuration file", "txt"));
                if(chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        Configuration config = new Configuration(file);
                        imagePanel.setConfig(config);
                        if  (imagePanel.getImage() != null){
                            preButton.setEnabled(lightImages.size() > 1);
                            nextButton.setEnabled(lightImages.size() > 1);
                            analyseButton.setEnabled(true);
                            showBordersCheckBox.setEnabled(true);
                        }
                        revalidate();
                    } catch (IOException | FormatException exception) {
                        JOptionPane.showMessageDialog(getParent(),
                                exception.getMessage(),
                                "Error reading config file",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JMenuItem loadImage = new JMenuItem("Load LFA image");
        loadImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                chooser.setMultiSelectionEnabled (true);
                chooser.setFileFilter(new FileNameExtensionFilter("LFA image file", "jpg"));
                if(chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    File[] files = chooser.getSelectedFiles();
                    try {
                        if (!lightImages.isEmpty())
                            lightImages.clear();
                        for (File file: files)
                            lightImages.add(new LightImage(file));
                        imagePanel.setImage(lightImages.get(0));
                        listIndex = 0;
                        if  (imagePanel.getConfiguration() != null){
                            preButton.setEnabled(lightImages.size() > 1);
                            nextButton.setEnabled(lightImages.size() > 1);
                            analyseButton.setEnabled(true);
                            showBordersCheckBox.setEnabled(true);
                        }
                        revalidate();
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(getParent(),
                                exception.getMessage(),
                                "Error reading image file",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        fileMenu.add(loadConfig);
        fileMenu.add(loadImage);
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

}
