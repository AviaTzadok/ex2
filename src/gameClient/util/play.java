package gameClient.util;//package gameClient.util;//Usually you will require both swing and awt packages

import gameClient.Ex2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class play extends JFrame implements ActionListener {

    JLabel IdNumber = new JLabel("Enter Id");
    JLabel levelNum = new JLabel("Enter level");
    JTextField IdTextField = new JTextField();
    JTextField LevelField = new JTextField();
    JButton playButton = new JButton("play");
    long id;
    int level;

    public long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public play() {
        super();
        IdNumber = new JLabel("Enter Id");
        levelNum = new JLabel("Enter level");
        IdTextField = new JTextField();
        LevelField = new JTextField();
        playButton = new JButton("play");
        setLayout(null);
        setSize(700, 1000);
        setVisible(true);
        IdNumber.setBounds(50, 150, 100, 30);
        levelNum.setBounds(50, 220, 100, 30);
        IdTextField.setBounds(150, 150, 150, 30);
        LevelField.setBounds(150, 220, 150, 30);
        playButton.setBounds(120, 300, 100, 30);
        add(IdNumber);
        add(levelNum);
        add(IdTextField);
        add(LevelField);
        add(playButton);
        playButton.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            String userText;
            String pwdText;
            userText = IdTextField.getText();
            pwdText = LevelField.getText();
            id = Long.parseLong(userText);
            level = Integer.parseInt(pwdText);
            Thread client = new Thread(new Ex2());
            client.start();
            dispose();


        }

    }

}