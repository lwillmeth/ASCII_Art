import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI_DisplayPanel extends JPanel
{
	private final String loadimage_filepath = "src//imgs//loadimage.png";
	private String imagetoconvert_filepath = "";
	private JLabel standard_label, ascii_label;
	private JTextArea textarea;
	
	//******************************//
	// ** Constructor ** //
	//*****************************//
	public GUI_DisplayPanel()
	{	
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder( 0,	 //top
														15,   //left
														0, 	 //bottom 
														15)); //right
		this.displayStandardImage();
		this.setupTextArea();
	}
	
		
	
	//******************************//
	//** setup standard label image - is image to be covert **//
	//*****************************//
	private void displayStandardImage()
	{
		//** load image - set label to imageicon **//
    	this.standard_label = new JLabel(GUI_Main.loadImage(loadimage_filepath));
		this.standard_label.setBorder(BorderFactory.createLoweredBevelBorder());
		this.standard_label.setSize(400, 400);
		this.add(this.standard_label, BorderLayout.LINE_START);
	}
	
	
	
	//******************************//
	//** setup TextArea to display converted image in Ascii art  **// 
	//*****************************//
	private void setupTextArea()
	{	
		textarea = new JTextArea(120,120);
		textarea.setFont(new Font("Courier", Font.BOLD, 6));
		textarea.setBorder(BorderFactory.createLoweredBevelBorder());
		textarea.setEditable(false);
		
		this.add(textarea, BorderLayout.LINE_END);
	}
	
	
	
	//******************************//
	// ** Convert Image to Ascii ** //
	//*****************************//
	public void convertImageToAscii(BufferedImage image)
	{
		// our available characters, is less more? Who knows.
		final String ascii_chars = "@&%#=+:-.  ";

		// find aspect ratio and orientation for the image.
		double img_ratio = (double)image.getWidth()/image.getHeight();
		int num_rows, num_cols;
	
		if(img_ratio>1)
		{
			// image is vertical
			num_rows = (int)(GUI_Main.ASCII_MAX_SIZE/img_ratio);
			num_cols = GUI_Main.ASCII_MAX_SIZE;
		}
		else
		{
			// image is horizontal or square
			num_rows = GUI_Main.ASCII_MAX_SIZE;
			num_cols = (int)(GUI_Main.ASCII_MAX_SIZE/img_ratio);
		}
		// Return a 2d array of characters representing the image:
		char[][] ascii_img = new char[num_rows][num_cols];

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
				ascii_img[row][col] = ascii_chars.charAt(block_avg/24);
			}
		}
		
		//output text to textarea
		this.textarea.setText(""); //clear textarea
		for(int i = 0; i < ascii_img.length; i++) 
		{
			for(int j = 0; j < ascii_img[i].length; j++) 
				this.textarea.append(" " + ascii_img[i][j]);
			this.textarea.append("\n");
		}
	}

	
	
	//******************************//
	//** -change image icon being displayed **// 
	//*****************************//
	public void changeStandardIcon(ImageIcon icon)
	{
		standard_label.setIcon(icon);
	}
	
	public void changeAsciiIcon(ImageIcon icon)
	{
		ascii_label.setIcon(icon);
	}
	
	
	
	//*****************************//
	//** ImageToConvert_filepath - setter/getter - changes filepath to image to be converted **//
	//*****************************//
	public String getImageToConvert_filepath()
	{
		if(this.imagetoconvert_filepath == null)
			return this.loadimage_filepath;
		else
			return imagetoconvert_filepath;
	}

	public void setImageToConvert_filepath(String waitingtoconvert_filepath)
	{
		this.imagetoconvert_filepath = waitingtoconvert_filepath;
	}
	
}
