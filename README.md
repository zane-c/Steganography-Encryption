# Steganography-Encryption

Steganography is the practice of concealing messages or information into an image's
pixel data. This software allows you to encrypt entire books, texts, or millions of
bytes of data into a single image without losing any noticeable image quality. The
picture will retain the information ciphered into it while looking identical to the
input image. The encrypted text is compressed into the pixel data so the file size
does change with ciphering.


# Usage

* Add image to the input folder to be encrypted
* Add text that you would like to encrypt into 
* change file parameters in main.java
    
        boolean encrypt = false;

        //FOR USER Encrypt
        String inImage = "input/bench_in.png";
        String inText = "input/TheTimeMachine.txt";
        String outImageLocation = "output/bench_out.png";

        //FOR USER decrypt
        //swap encrypt = true to false
        String toDecrypt = "output/bench_out.png";
        String outTextLocation = "output/hidden_message.txt";
        
        
 * Run
 
Depending on the speed of your computer and input image/text size, encryption can take anyway from 30s to 10m. Due to the nature of iterating through an N x N pixel grid, encryption is O(n^2) run time effiency.


# Results

Encrypting the entire novel "The Time Machine" by H.G. Wells into this simple image of a bench takes about 2 minutes on my computer.

                                 THE TIME MACHINE
                                   H. G. Wells

                                   Chapter I

          The Time Traveller (for so it will be convenient to speak of
      him) was expounding a recondite matter to us.  His grey eyes shone
      and twinkled, and his usually pale face was flushed and animated.
      The fire burned brightly, and the soft radiance of the
      incandescent lights in the lilies of silver caught the bubbles
      that flashed and passed in our glasses.  Our chairs, being his
      patents, embraced and caressed us rather than submitted to be sat
                                   
                                   ...
                                   
                                   
                                   
  ### Before Image
  <img src="https://github.com/zane-c/Steganography-Encryption/blob/master/input/bench_in.png" alt="Drawing" width="400">
  
  ### After Image
  <img src="https://github.com/zane-c/Steganography-Encryption/blob/master/output/bench_out.png" alt="Drawing" width="400">
  
  
  Notice that there is no visible difference to the original image and the image that has the encrypted novel.
