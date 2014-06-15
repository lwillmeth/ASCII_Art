import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class ascii_img{
  public ascii_img(){
    try{
      final int MAX_SIZE = 80;
      BufferedImage image = ImageIO.read(new File("testimage4.jpg"));
      char[] ascii_chars = {'@', 'Q', '%', 'O', 'o', '=', '+', ':', '-', '.', ' '};
      // find aspect ratio and orientation for the image.
      double img_ratio = (double)image.getWidth()/image.getHeight();
      int num_rows, num_cols;
      if(img_ratio>1){
        // image is vertical
        num_rows = (int)(MAX_SIZE/img_ratio);
        num_cols = MAX_SIZE;
      }else{
        // image is horizontal or square
        num_rows = MAX_SIZE;
        num_cols = (int)(MAX_SIZE/img_ratio);
      }
      int block_w = (int)(image.getWidth()/num_cols);
      int block_h = (int)(image.getHeight()/num_rows);
      
      // debugging
      // System.out.println("The image is: "+image.getWidth()+"/"+image.getHeight()+"="+img_ratio);
      // System.out.println("That works out to be ~"+num_cols+"("+block_w+") by "+num_rows+"("+block_h+")");
      // System.out.println("Final image dimensions are: "+num_cols*block_w+" by "+num_rows*block_h);

      // make an array of ints to hold the pixel values of each block
      int[] pixels = new int[block_w*block_h];

      for(int row=0; row<num_rows; row++){
        for(int col=0; col<num_cols; col++){
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
