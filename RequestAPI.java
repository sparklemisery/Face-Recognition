package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestAPI {
    public static BufferedImage toBufferedImage(ImageIcon icon) {
        // Create a BufferedImage with the same dimensions as the ImageIcon
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);

        // Paint the ImageIcon onto the BufferedImage
        icon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);

        return bufferedImage;
    }

    public static byte[] toByteArray(BufferedImage image) {
        try {
            // Create a ByteArrayOutputStream to store the image data
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Write the BufferedImage to the ByteArrayOutputStream as the specified format
            ImageIO.write(image, "jpg", outputStream);

            // Get the byte array from the ByteArrayOutputStream
            byte[] imageData = outputStream.toByteArray();

            // Close the ByteArrayOutputStream
            outputStream.close();

            return imageData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String sendPostRequest(byte[] imageData) {
        String data = null;
        try {
            URL url = new URL("http://127.0.0.1:5000/facerecognition");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set content type based on the image format (e.g., image/jpeg)
            connection.setRequestProperty("Content-Type", "image/jpeg");

            // Write image data to the request body
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(imageData);
            }

            // Read response from the server
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = reader.readLine();
                    System.out.println(response);
                    data = response;
                }
            } else {
                System.out.println("Failed to send request. Response code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
