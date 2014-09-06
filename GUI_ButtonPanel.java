import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
//import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.Timer;

import org.imgscalr.Scalr;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class GUI_ButtonPanel extends JPanel
{
	private JButton load_button, capture_button, save_button, print_button;
	private GUI_DisplayPanel display_panel; //i need a reference to the display_panel class
	
	private final String hd_button_filepath = "src//imgs//harddrivebutton.png",
			        capture_button_filepath = "src//imgs//capturebutton.png", 
				   hover_hd_button_filepath = "src//imgs//harddrivebuttonHover.png",
			  hover_capture_button_filepath = "src//imgs//capturebuttonHover.png",
			    temp_capturedimage_filepath = "temp.png";
	
	//******************************//
	// ** Constructor ** //
	//*****************************//
	public GUI_ButtonPanel(GUI_DisplayPanel display_panel) throws IOException
	{
		this.setBorder(BorderFactory.createEmptyBorder( 10,	 //top -create spacing
														0,   //left
														80, //bottom 
														0)); //right
		this.display_panel = display_panel;
		this.setupLoadButton();
		this.setupCaptureButton();
		//this.setupSaveButton();
		//this.setupPrintButton();
	}
	
	
	
	//******************************//
	// ** Load Button setup ** //
	//*****************************//
	private void setupLoadButton() throws IOException
	{
		this.load_button = new JButton();
		
		//** button image **//
		this.load_button.setIcon(GUI_Main.loadImage(hd_button_filepath));
		
		//**remove button background**//
		this.load_button.setBorder(null); 
		this.load_button.setContentAreaFilled(false);
		
		//** nested ActionListener **//
		this.load_button.addActionListener
		( 
			new ActionListener() 
			{	
				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					if(ae.getSource() == load_button) //when button pressed..
					{	
						//** -calls and runs filechooser method..returns filepath as string
						//** -then brings filepath into resizeImage...a resized bufferedimage is returned
						//** -then a new Imageicon is made with resized bufferedimage as icon
						try
						{
							String temp = fileChooser();
							ImageIcon icon = new ImageIcon( resizeImage(temp));
							
							display_panel.setWaitingtoconvert_filepath(temp);//setting the image that will now be converted
							display_panel.changeStandardIcon(icon); //change display image
							display_panel.convertImageToAscii(); 
						} 
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		);
		
		//** nested MouseListener - mouse hover - change image **//
		final ImageIcon icon_hd_button = GUI_Main.loadImage(hd_button_filepath);
		final ImageIcon hover_hd_button = GUI_Main.loadImage(hover_hd_button_filepath);
		
		this.load_button.addMouseListener(new MouseListener() 
		{            
			@Override
			public void mouseReleased(MouseEvent arg0) {}           
			@Override
			public void mousePressed(MouseEvent arg0) {}            
			@Override
			public void mouseExited(MouseEvent arg0) { load_button.setIcon(icon_hd_button); }           
			@Override
			public void mouseEntered(MouseEvent arg0) { load_button.setIcon(hover_hd_button); }           
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});

		this.add(load_button);	
	}
	
	
	
	//******************************//
	// ** Capture Button setup ** //
	//*****************************//
	private void setupCaptureButton() throws IOException
	{
		this.capture_button = new JButton();
		
		//** button image **//
		this.capture_button.setIcon( GUI_Main.loadImage(capture_button_filepath) );
		
		//**remove button background**//
		this.capture_button.setBorder(null); 
		this.capture_button.setContentAreaFilled(false);
		
		//** nested ActionListener **//
		this.capture_button.addActionListener
		( 
			new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					if(ae.getSource() == capture_button)
					{
						try {
							webCam(); // -runs/takes webcam pic - saved as temp_capturedimage_filepath
							
							ImageIcon icon = new ImageIcon( resizeImage(temp_capturedimage_filepath) ); 
							
							display_panel.setWaitingtoconvert_filepath(temp_capturedimage_filepath); //setting the image that will now be converted
							display_panel.changeStandardIcon(icon); //change display image
							
							display_panel.convertImageToAscii(); 
						} 
						catch (IOException e) { e.printStackTrace(); }
					}
				}
			}
		);
		
		//** nested MouseListener - mouse hover - change image **//
		final ImageIcon icon_capture_button = GUI_Main.loadImage(capture_button_filepath);
		final ImageIcon hover_capture_button = GUI_Main.loadImage(hover_capture_button_filepath);
		
		this.capture_button.addMouseListener(
			new MouseListener() 
			{
				@Override
				public void mouseReleased(MouseEvent arg0) {}           
				@Override
				public void mousePressed(MouseEvent arg0) {}            
				@Override
				public void mouseExited(MouseEvent arg0) { capture_button.setIcon(icon_capture_button); }           
				@Override
				public void mouseEntered(MouseEvent arg0) { capture_button.setIcon(hover_capture_button); }           
				@Override
				public void mouseClicked(MouseEvent arg0) {}
			}
		);

		this.add(capture_button);
	}
	
	
	
	//******************************//
	// ** Save Button setup** //
	//*****************************//
	private void setupSaveButton()
	{
		this.save_button = new JButton("Save");
		
		
		//** button image**//
		
		//** nested ActionListener **//
		this.save_button.addActionListener
		( 
			new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					if(ae.getSource() == save_button)
					{
						JOptionPane.showMessageDialog(display_panel,
								"Feature coming soon!",
								"Sorry :(",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		);
		this.add(save_button);
	}
	
	
	
	//******************************//
	// ** Print Button setup ** //
	//*****************************//
	private void setupPrintButton()
	{
		this.print_button = new JButton("Print");
		
		//** button image**//
		
		//** nested ActionListener **//
		this.print_button.addActionListener
		( 
			new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					if(ae.getSource() == print_button)
					{
						JOptionPane.showMessageDialog(display_panel,
								"Feature coming soon!",
								"Sorry :(",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		);
		
		this.add(this.print_button);
	}
	
	
	
	//******************************//
	// ** fileChooser - returns filepath String ** //
	//*****************************//
	private String fileChooser()
	{
		JFileChooser filechooser = new JFileChooser();
		String filepath = "";
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
		{
			File file = filechooser.getSelectedFile();
			filepath = file.getAbsolutePath(); 
		}
		return filepath;
	}
	
	
	
	//******************************//
	// ** resize image - takes filepath - returns BufferedImage** //
	//*****************************//
	private BufferedImage resizeImage(String filepath) throws IOException
	{	
		int max_size = 470; 
		//Scalr scalr = new Scalr(); //**imported Library (credit imgScalr)
		BufferedImage image = ImageIO.read(new File(filepath));
		
	    return image = Scalr.resize(image, max_size);
	}
	
	
	
	//******************************//
	//** webCam - opens webcam..takes pic..saves to file
	//*****************************//
	private void webCam() throws IOException
	{	
		//**get default webcam..set resolution..open it
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open();
		BufferedImage image = webcam.getImage();

		//**save image to temp_capturedimage_filepath
		try{
			ImageIO.write(image, "PNG", new File(this.temp_capturedimage_filepath));
		} 
		catch (IOException e){ e.printStackTrace(); }
		webcam.close();
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
}