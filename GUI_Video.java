import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GUI_Video extends JFrame{
	private final int ASCII_MAX_SIZE = 50;
	private JTextArea textarea;
	private char[][] ascii_img = new char[ASCII_MAX_SIZE][ASCII_MAX_SIZE];
	private Webcam webcam = Webcam.getDefault();
	
	public GUI_Video(){

		webcam.setViewSize(new Dimension(320,240));
		webcam.open();
		
		JFrame frame = new JFrame("Video demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		WebcamPanel wpanel = new WebcamPanel(webcam);		
		
		frame.add(wpanel);
		frame.add(textarea);
		
		frame.pack();
		frame.setVisible(true);
		
//		while(true){
//			try{
//				ascii_img = convertImgToAscii(webcam.getImage());
//			}catch(IOException e){
//				e.printStackTrace();
//			}
//			textarea.setText("");
//			for(int i = 0; i < ascii_img.length; i++){
//				for(int j = 0; j < ascii_img[i].length; j++) 
//					textarea.append(" " + ascii_img[i][j]);
//				textarea.append("\n");
//			}
//		}
	}

	public static void main(String[] args){
		new GUI_Video();
	}
	
//	@Override
//	public void run(){
//		
//		while(true){
//			try{
//				ascii_img = convertImgToAscii(webcam.getImage());
//			}catch(IOException e){
//				e.printStackTrace();
//			}
//			textarea.setText("");
//			for(int i = 0; i < ascii_img.length; i++){
//				for(int j = 0; j < ascii_img[i].length; j++) 
//					textarea.append(" " + ascii_img[i][j]);
//				textarea.append("\n");
//			}
//		}
//	}
	
	//******************************//
	//** Loads an image, returns ascii representation **// 
	//*****************************//	
	public char[][] convertImgToAscii(BufferedImage image) throws IOException
	{

		// our available characters, is less more? Who knows.
		String ascii_chars = "@&%#=+:-.  ";

		// find aspect ratio and orientation for the image.
		double img_ratio = (double)image.getWidth()/image.getHeight();
		int num_rows, num_cols;
	
		if(img_ratio>1)
		{
			// image is vertical
			num_rows = (int)(ASCII_MAX_SIZE/img_ratio);
			num_cols = ASCII_MAX_SIZE;
		}
		else
		{
			// image is horizontal or square
			num_rows = ASCII_MAX_SIZE;
			num_cols = (int)(ASCII_MAX_SIZE/img_ratio);
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