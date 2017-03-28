import java.io.*;

public class Main {

    public static void main(String[] args) {
        ImageProcessor processor = new ImageProcessor();

        boolean encrypt = false;

        //FOR USER Encrypt
        String inImage = "input/bench_in.png";
        String inText = "input/TheTimeMachine.txt";
        String outImageLocation = "output/bench_out.png";

        //FOR USER decrypt
        //swap encrypt = true to false
        String toDecrypt = "output/bench_out.png";
        String outTextLocation = "output/hidden_message.txt";


        if (encrypt) {
            // Encrypt

            File imageFile = new File(inImage);
            File bookFile = new File(inText);

            processor.encrypt(imageFile, bookFile, outImageLocation);
            System.out.println("done.");

        } else {
            // Decrypt

            File encryptedImage = new File(toDecrypt);

            processor.decrypt(encryptedImage, outTextLocation);
            System.out.println("done.");
        }
    }
}
