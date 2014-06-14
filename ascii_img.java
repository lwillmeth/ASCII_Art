import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class ascii_img{
  public ascii_img(){
    try{
      final int MAX_SIZE = 80;
      BufferedImage image = ImageIO.read(new File("grid.jpg"));
      int block_w = image.getWidth() / MAX_SIZE;
      int block_h = image.getHeight() / MAX_SIZE;
      System.out.format("Each block is %d by %d pixels, and there are "
        +"80x80 blocks.\n", block_w, block_h, block_h*MAX_SIZE);
      
      // make an array of ints to hold the pixel values of each block
      int[] pixels = new int[block_w*block_h];

      image.getRGB(0,0,block_w,block_h,pixels,0,block_w);
      int shade=0;
      int single=0;
      // int r,g,b;
      int index=1;
      for(int rgb:pixels){
        // sum the average rgb values for each pixel in the block
        // r = (rgb>>16)&0xFF;
        // g = (rgb>>8)&0xFF;
        // b = (rgb)&0xFF;
        //single = (r+g+b)/3;
        single = ( ((rgb>>16)&0xFF) + ((rgb>>8)&0xFF) + (rgb&0xFF) )/3;
        shade += single;
        //System.out.format("[%3d/%3d/%3d]=%3d ",r,g,b,single);
        System.out.format("%3d ",single);
        if(index++%block_w==0)
          System.out.println();
      }
      // divide the sum by block size to find average pixel color in grey
      System.out.println("\n"+shade/(block_w*block_h));
      // for(int i=0; i<pixels.length; i++){

      // }
      // for each row, and each colum of blocks
      // for(int row=0; row<MAX_SIZE; row++){
      //  for(int col=0; col<MAX_SIZE; col++){

      //    // fill the pixels array with the values from this block
      //    image.getRGB(col*block_w, row*block_h,
      //      block_w, block_h, pixels, 0, block_w);

      //    // average the array to find color value for this block
      //  }
      // }
    }catch (IOException e){
      System.out.println("Error!");
    }
    System.out.println("All done!");
  }

  public static void main(String[] args){
    new ascii_img();
  }
}
