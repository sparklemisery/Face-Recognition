package org.example;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PictureAfterProcess {
    ImageIcon icon;
    String name;
    double probability ;
    JSONObject position;
    int x1,y1,x2,y2;

    public PictureAfterProcess(ImageIcon icon , String jsonData){
        BufferedImage image = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        icon.paintIcon(null,g2d,0,0);
        g2d.dispose();

        Graphics2D g = image.createGraphics();
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(2));
        g.setFont(new Font("Arial", Font.BOLD, 16));


        JSONArray jsonArray = new JSONArray(jsonData);
        for(int i =0; i < jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);

            String name = obj.getString("name");
            double probability = obj.getDouble("probability");
            JSONObject position = obj.getJSONObject("position");
            int x1 = position.getInt("x1");
            int y1 = position.getInt("y1");
            int x2 = position.getInt("x2");
            int y2 = position.getInt("y2");
            g.drawRect(x1,y1,x2-x1,y2-y1);
            g.drawString(name + "-"+(double)Math.ceil(probability * 100)/100, x1, y1-20);
        }
        g.dispose();
        this.icon = new ImageIcon(image);
    }

}
