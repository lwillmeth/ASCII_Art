import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class ascii_img{
  public ascii_img(){
    try{
      final int MAX_SIZE = 80;
      BufferedImage image = ImageIO.read(new File("testimage.jpg"));
      char[] ascii_chars = {'@', 'Q', '%', 'O', 'o', '=', '+', ':', '-', '.', ' '};
      int block_w = image.getWidth() / MAX_SIZE;
      int block_h = image.getHeight() / MAX_SIZE;
      System.out.format("Each block is %d by %d pixels, and there are "
        +"80x80 blocks.\n", block_w, block_h, block_h*MAX_SIZE);

      // make an array of ints to hold the pixel values of each block
      int[] pixels = new int[block_w*block_h];

      for(int row=0; row<MAX_SIZE; row++){
        for(int col=0; col<MAX_SIZE; col++){
          int block_avg = 0;
          // fill the pixels array with the values from this block
          image.getRGB(col*block_w, row*block_h,
            block_w, block_h, pixels, 0, block_w);
          // add greyscale brightness of each pixel to get total for the block
          for(int rgb:pixels){
            block_avg += (((rgb>>16)&0xFF) + ((rgb>>8)&0xFF) + (rgb&0xFF) )/3;
          }
          // divide the total by block size to find average pixel brightness
          block_avg /= (block_w*block_h);
          // print the character for this block
          System.out.format("%c", ascii_chars[block_avg/25]);
        }
        System.out.println();
      }
    }catch (IOException e){
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args){
    new ascii_img();
  }
}
