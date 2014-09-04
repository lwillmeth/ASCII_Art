import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GUI_DisplayPanel extends JPanel
{
	private final String standard_filepath = "src//imgs//loadimage.png", 
				            ascii_filepath = "src//imgs//waitimage.png",
				            arrow_filepath = "src//imgs//arrow.png";
	
	private JLabel standard_label, ascii_label;
	private JButton convert_button;
	private JPanel container_panel; //need reference to use for dialog boxes
	
	
	//******************************//
	// ** Constructor ** //
	//*****************************//
	public GUI_DisplayPanel(JPanel container_panel) throws IOException
	{	
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder( 0,	 //top
														15,   //left
														0, 	 //bottom 
														15)); //right
		this.container_panel = container_panel;
		this.displayStandardImage();
		this.setupConvertButton();
		this.displayAsciiImage();
	}
	
		
	
	//******************************//
	//** display standard image to covert **//
	//*****************************//
	private void displayStandardImage() throws IOException
	{
		//** load image - set label to imageicon **//
	    	this.standard_label = new JLabel(this.loadImage(standard_filepath));
		//******************************//
		
		this.standard_label.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(this.standard_label, BorderLayout.LINE_START);
	}
	
	
	
	//******************************//
	//** display converted ACSII image **// 
	//*****************************//
	private void displayAsciiImage() throws IOException
	{	
		//** load image - set label to imageicon **//
			this.ascii_label = new JLabel(this.loadImage(ascii_filepath));
		//******************************//
		 
		this.ascii_label.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(this.ascii_label, BorderLayout.LINE_END);
	}
	
	
	
	//******************************//
	// ** Convert Button ** //
	//*****************************//
	private void setupConvertButton() throws IOException
	{
		this.convert_button = new JButton();
		
		
		//** load image -set button to imageicon **//
			this.convert_button.setIcon(this.loadImage(arrow_filepath));
		//******************************//
		
		
		//**remove button background**//
		this.convert_button.setFocusable(false);
		this.convert_button.setBorder(null); 
		this.convert_button.setContentAreaFilled(false);
		//*****************************//
		
		
		//** nested ActionListener **//
		this.convert_button.addActionListener
		( 
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent ae) 
				{
					if(ae.getSource() == convert_button)
					{
						JOptionPane.showMessageDialog(container_panel,
											   "Feature coming soon!",
											    			  "Sorry :(",
										 JOptionPane.WARNING_MESSAGE);
						
						
						////////////////////////////
						// CALL CONVERT CODE HERE //
						
						
															//<<<<<<<<<<<<<<<<*********************
						
						
						
						
						///////////////////////////
						
						//changeAsciiIcon(icon); //set image to converted ascii image
					}
				}
			}
		);
		//*****************************//
		
		this.add(convert_button, BorderLayout.CENTER);
	}
	
	//******************************//
	//** Loads an image, returns ascii representation **// 
	//*****************************//	
	public char[][] convertImgToAscii(String filepath) throws IOException
	{
		final int MAX_SIZE = 50;
		BufferedImage image = ImageIO.read(new File(filepath));

		// our available characters, is less more? Who knows.
		String ascii_chars = "@&%#=+:-.  ";
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
		// Return a 2d array of characters representing the image:
		char[][] result = new char[num_rows][num_cols];

		// block size is based on aspect ratio
		int block_w = (int)(image.getWidth()/num_cols);
		int block_h = (int)(image.getHeight()/num_rows);

		// make an array of ints to hold the pixel values of each block
		int[] pixels = new int[block_w*block_h];

		// interpret the pixels for each block and find their avg brightness
		for(int row=0; row<num_rows; row++){
			for(int col=0; col<num_cols; col++){
				int block_avg = 0;
				// fill the pixels array with the values from this block
				image.getRGB(col*block_w, row*block_h,
				block_w, block_h, pixels, 0, block_w);
				for(int rgb:pixels){
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
	
	
	//******************************//
	// ** load image - return ImageIcon ** //
	//*****************************//
	public ImageIcon loadImage(String filepath) throws IOException
	{	
		BufferedImage img = ImageIO.read(new File(filepath)); 
		return new ImageIcon(img);
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
	
}
