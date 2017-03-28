import java.awt.image.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.imageio.*;

public class ImageProcessor {

    public void encrypt(File image, File book, String outLocation) {

        // ===================================
        // PREPARE FILES
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("Start time: " + timeStamp);
        System.out.println("\nprocessing...");

        // prepare image
        BufferedImage img = null;
        try {
            img = ImageIO.read(image);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        // prepare novel
        BufferedReader br;
        StringBuffer bookString = new StringBuffer();
        try {
            br = new BufferedReader(new FileReader(book));
            String line = br.readLine();
            while (line != null) {
                bookString.append(line);
                bookString.append("{nl}");
                line = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // END PREPARE FILES
        // ==============================================
        // START PROCESSING

        int width = img.getWidth();
        int height = img.getHeight();
        int bookIndex = 0;

        outerloop:
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {

                if (bookIndex >= bookString.length()) {
                    break outerloop;
                }

                // value to encrypt
                int value = (int) bookString.charAt(bookIndex) - 32;

                // get pixel value
                int pixel = img.getRGB(w, h);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                int bestDistance = 100;
                int best1 = 0;
                int best2 = 0;
                int best3 = 0;

                for (int i = 0; i < 178481; i++) {

                    int newValue = value + 94 * i;

                    int h1 = newValue / 1048576;
                    newValue -= h1 * 1048576;
                    int h2 = newValue / 65536;
                    newValue -= h2 * 65536;

                    int c1 = 16 * h1 + h2;

                    h1 = newValue / 4096;
                    newValue -= h1 * 4096;
                    h2 = newValue / 256;
                    newValue -= h2 * 256;

                    int c2 = 16 * h1 + h2;

                    h1 = newValue / 16;
                    h2 = newValue - h1 * 16;

                    int c3 = 16 * h1 + h2;

                    int distance = Math.abs(red - c1);
                    distance += Math.abs(green - c2);
                    distance += Math.abs(blue - c3);

                    if (distance < 9) {
                        best1 = c1;
                        best2 = c2;
                        best3 = c3;
                        break;
                    } else if (distance < bestDistance) {
                        best1 = c1;
                        best2 = c2;
                        best3 = c3;
                    }
                }
                // System.out.println("rgb: " + red + "/" + green + "/" + blue);
                // System.out.println("new: " + best1 + "/" + best2 + "/" + best3);

                bookIndex++;

                pixel = (best1 << 16) | (best2 << 8) | (best3 << 0);
                img.setRGB(w, h, pixel);
            }
        }
        // write image
        try {
            ImageIO.write(img, "png", new File(outLocation));
        } catch (IOException e) {
            System.out.println(e);
        }
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("End time: " + timeStamp);

    }

    public void decrypt(File image, String outLocation) {

        // prepare image
        BufferedImage img = null;
        try {
            img = ImageIO.read(image);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        int width = img.getWidth();
        int height = img.getHeight();

        StringBuffer bookString = new StringBuffer();

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {

                // get pixel value
                int pixel = img.getRGB(w, h);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                String h1 = Integer.toHexString(red);
                while (h1.length() < 2) {
                    h1 = "0" + h1;
                }
                String h2 = Integer.toHexString(green);
                while (h2.length() < 2) {
                    h2 = "0" + h2;
                }
                String h3 = Integer.toHexString(blue);
                while (h3.length() < 2) {
                    h3 = "0" + h3;
                }

                String hex = h1 + h2 + h3;

                int value = (Integer.parseInt(hex, 16) % 94) + 32;
                char c = (char) value;

                bookString.append(c);
            }
        }
        String s = bookString.toString().replaceAll("\\{nl\\}", "\r\n");

        try {
            PrintWriter out = new PrintWriter(outLocation);
            out.println(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
