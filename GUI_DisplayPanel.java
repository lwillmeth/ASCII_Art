import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
	
	private String waitingtoconvert_filepath ="";
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
						try{
							convertImageToAscii();
						} 
						catch (IOException e) { e.printStackTrace(); }
						
						//changeAsciiIcon(icon); //set image to converted ascii image
					}
				}
			}
		);
		//*****************************//
		
		this.add(convert_button, BorderLayout.CENTER);
	}
	
	
	
	//******************************//
	// ** convertImage to Ascii - calls ImageToASCII class code ** //
	//*****************************//
	private void convertImageToAscii() throws IOException
	{
		ImageToASCII convert = new ImageToASCII( this.getWaitingtoconvert_filepath() );
		
		
		//** -runs convert code on passed in temp image **//
		//** -returns a 2D array of chars			    **//	
		char[][] temp = convert.convertImgToAscii(); 
		//*****************************//
		
		
		this.printToFile(temp);
		
		
		
		for(int i=0; i < temp.length; i++)
		{
			System.out.println();
			for(int j=0; j < temp[i].length; j++)
				System.out.print(temp[i][j]);
		}
	}
	
	
	
	//******************************//
	// ** load image - return ImageIcon ** //
	//*****************************//
	private ImageIcon loadImage(String filepath) throws IOException
	{	
		BufferedImage img = ImageIO.read(new File(filepath)); 
		return new ImageIcon(img);
	}
	
	
	
	//******************************//
	// ** prints a 2d array out to text file - takes in 2dchar array ** //
	//*****************************//
	private void printToFile(char[][] temp) throws IOException
	{
		FileWriter write = new FileWriter("temp.txt");
		BufferedWriter buffer = new BufferedWriter(write);
		PrintWriter print = new PrintWriter(buffer);
		
		for(int i=0; i< temp.length; i++)
		{
			print.println();
			for(int j=0; j < temp[i].length; j++)
				print.print(temp[i][j]);
		}
		print.close();	
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
	//** temp_waittoconvert_filepath - setter/getter - changes filepath to image to be converted **//
	//*****************************//
	public String getWaitingtoconvert_filepath()
	{
		return waitingtoconvert_filepath;
	}

	public void setWaitingtoconvert_filepath(String waitingtoconvert_filepath)
	{
		this.waitingtoconvert_filepath = waitingtoconvert_filepath;
	}
	
}
