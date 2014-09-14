import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI_DisplayPanel extends JPanel
{
	private JLabel standard_label;
	private JTextArea textarea;
	private ImageIcon currentImage;
	
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
	
		
	
	//***********************************//
	//** Set default image on startup **//
	//*********************************//
	private void displayStandardImage()
	{
		//** load image - set label to imageicon **//
    	this.standard_label = new JLabel(GUI_Main.loadImage());
		this.standard_label.setBorder(BorderFactory.createLoweredBevelBorder());
		this.standard_label.setSize(400, 400);
		this.add(this.standard_label, BorderLayout.LINE_START);
	}
	
	
	
	//*************************************************************//
	//** setup TextArea to display converted image in Ascii art **// 
	//***********************************************************//
	private void setupTextArea()
	{	
		textarea = new JTextArea(120,120);
		textarea.setFont(new Font("Courier", Font.BOLD, 6));
		textarea.setBorder(BorderFactory.createLoweredBevelBorder());
		textarea.setEditable(false);
		
		this.add(textarea, BorderLayout.LINE_END);
	}
	
	
	
	//******************************//
	// ** Convert Image to 2d char array** //
	//*****************************//
	public char[][] convertBufferedImageToAscii(BufferedImage image)
	{
		// See http://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image/9470843#9470843
		// Read image into a 2d array of bytes
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();		

		// Find dimensions and aspect ratio of the image
		final int width = image.getWidth(), height = image.getHeight();
		final double img_ratio = (double)width/height;

		// Find orientation for the image, determine size of rows and columns. 
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
		
		// Prepare an empty 2d array of characters representing the image
		char[][] result = new char[num_rows][num_cols];

		if(image.getAlphaRaster() != null){
			// image has alpha channel, iterate using 4 bytes per pixel.
			final int numChannels = 4;
			for(int row=0; row<num_rows; row++)
			{
				for(int col=0; col<num_cols; col++)
				{
					// iterate over pixels in this block
//					for(int )
				}
				
			}
		}
		
		
		return result;
	}
	
	
	
	//******************************//
	// ** Convert Image to Ascii ** //
	//*****************************//
	public char[][] convertImageToAscii(BufferedImage image)
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

				for(int argb:pixels)
				{
					/*
						rgb is a 32 bit int representing 4 color channels: alpha/r/g/b
						We need a sum of the rgb values for each pixel.
						(rgb>>16)&0xFF) bit shifts the int by 16 places to the right.
						Then reduces to 1 byte by using & with the bit pattern 11111111.
					 */
					if(argb==0) block_avg += 256;
					else block_avg += (( (argb>>16)&0xFF)+((argb>>8)&0xFF)+(argb&0xFF) )/3;
				}
				// divide the total by block size to find average block brightness
				// block_avg could be adjusted up/down to weight the output
				block_avg /= (block_w*block_h);
				ascii_img[row][col] = ascii_chars.charAt(block_avg/24);
			}
		}
		return ascii_img;
	}
	
	
	
	//*******************************************//
	// ** change Ascii image being displayed ** //
	//*****************************************//
	public void setCurrentAscii(BufferedImage image)
	{
		setCurrentAscii(convertImageToAscii(image));
	}
	// Override
	public void setCurrentAscii(char[][] ascii)
	{
		this.textarea.setText(""); //clear textarea
		for(int i = 0; i < ascii.length; i++) 
		{
			for(int j = 0; j < ascii[i].length; j++) 
				this.textarea.append(" " + ascii[i][j]);
			this.textarea.append("\n");
		}
	}

	
	
	//****************************************//
	//** Change image icon being displayed **// 
	//**************************************//
	public void setCurrentIcon(ImageIcon icon)
	{
		// Save icon for later, converting back from JLabel is unreliable.
		this.currentImage = icon;
		standard_label.setIcon(currentImage);
	}
	
	//*******************************************//
	//** Returns the currently displayed icon **// 
	//*****************************************//
	public BufferedImage getCurrentIcon()
	{
		return (BufferedImage)currentImage.getImage();
	}
}
