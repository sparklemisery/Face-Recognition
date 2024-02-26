package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PictureBeforeSend extends JFrame {
    private JLabel picScreen;
    private JButton sendButton;
    private JButton exitButton;
    public PictureBeforeSend(ImageIcon icon){
        setLayout(null);

        picScreen = new JLabel();
        picScreen.setBounds(0,0,640,480);
        add(picScreen);

        exitButton = new JButton("Exit");
        exitButton.setBounds(300,480,80,40);
        add(exitButton);

        sendButton = new JButton("send");
        sendButton.setBounds(400,480,80,60);
        add(sendButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PictureBeforeSend.this.dispose();
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = RequestAPI.sendPostRequest(RequestAPI.toByteArray(RequestAPI.toBufferedImage(icon)));
                if(data.equals("0")){
                    System.out.println("not found any faces ");
                }
                else{
                    PictureAfterProcess pap = new PictureAfterProcess(icon,data);
                    picScreen.setIcon(pap.icon);
                }


            }
        });

        picScreen.setIcon(icon);
        setSize(new Dimension(640,560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
