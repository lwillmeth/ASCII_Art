import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class ImageToASCII
{	
	private String filepath ="";
	
	public ImageToASCII(String filepath)
	{
		this.filepath = filepath;
	}
	
	
	//******************************//
	//** Loads an image, returns ascii representation **// 
	//*****************************//	
	public char[][] convertImgToAscii() throws IOException
	{
		final int MAX_SIZE = 50;
		BufferedImage image = ImageIO.read(new File( this.filepath ));

		// our available characters, is less more? Who knows.
		String ascii_chars = "@&%#=+:-.  ";

		// find aspect ratio and orientation for the image.
		double img_ratio = (double)image.getWidth()/image.getHeight();
		int num_rows, num_cols;

		if(img_ratio>1)
		{
			// image is vertical
			num_rows = (int)(MAX_SIZE/img_ratio);
			num_cols = MAX_SIZE;
		}
		else
		{
			// image is horizontal or square
			num_rows = MAX_SIZE;
			num_cols = (int)(MAX_SIZE/img_ratio);
		}
		// Return a 2d array of characters representing the image:
		char[][] result = new char[num_rows][num_cols];

		// block size is based on aspect ratio
		int block_w = (int)(image.getWidth()/num_cols);
		int block_h = (int)(image.getHeight()/num_rows);

		// make an array of ints to hold the pixel values of each block
		int[] pixels = new int[block_w*block_h];

		// interpret the pixels for each block and find their avg brightness
		for(int row=0; row<num_rows; row++)
		{
			for(int col=0; col<num_cols; col++)
			{
				int block_avg = 0;

				// fill the pixels array with the values from this block
				image.getRGB(col*block_w, row*block_h,
						block_w, block_h, pixels, 0, block_w);

				for(int rgb:pixels)
				{
					/*
						rgb is a 32 bit int representing 4 color channels: alpha/r/g/b
						We need a sum of the rgb values for each pixel.
						(rgb>>16)&0xFF) bit shifts the int by 16 places to the right.
						Then reduces to 1 byte by using & with the bit pattern 11111111.
					 */
					block_avg += (((rgb>>16)&0xFF) + ((rgb>>8)&0xFF) + (rgb&0xFF) )/3;
				}
				// divide the total by block size to find average block brightness
				// block_avg could be adjusted up/down to weight the output
				block_avg /= (block_w*block_h);
				result[row][col] = ascii_chars.charAt(block_avg/24);
			}
		}
		return result;
	}
}
