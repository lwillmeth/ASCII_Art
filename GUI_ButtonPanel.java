import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.imgscalr.AsyncScalr;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;


public class GUI_ButtonPanel extends JPanel
{
	private JButton load_button, capture_button, save_button, print_button;
	private GUI_DisplayPanel display_panel; //i need to call methods from display_panel class
	
	private final String hd_button_filepath = "src//imgs//harddrivebutton.png",
			        capture_button_filepath = "src//imgs//capturebutton.png", 
				   hover_hd_button_filepath = "src//imgs//harddrivebuttonHover.png",
			  hover_capture_button_filepath = "src//imgs//capturebuttonHover.png";
	
	
	
	//******************************//
	// ** Constructor ** //
	//*****************************//
	public GUI_ButtonPanel(GUI_DisplayPanel display_panel) throws IOException
	{
		//this.setLayout(new GridLayout(2,4));
		this.setBorder(BorderFactory.createEmptyBorder( 0,	 //top -create spacing
														0,   //left
														100, //bottom 
														0)); //right
		this.display_panel = display_panel; 
		this.setupLoadButton();
		this.setupCaptureButton();
		//this.setupSaveButton();
		//this.setupPrintButton();
	}
	
	
	
	//******************************//
	// ** Load Button ** //
	//*****************************//
	private void setupLoadButton() throws IOException
	{
		this.load_button = new JButton();
		
		
		//** button image **//
		this.load_button.setIcon(this.loadImage(hd_button_filepath));
		//*****************************//
		
		
		//**remove button background**//
		this.load_button.setBorder(null); 
		this.load_button.setContentAreaFilled(false);
		//*****************************//
		
		
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
							ImageIcon icon = new ImageIcon( resizeImage( fileChooser() ));
							display_panel.changeStandardIcon(icon);
						} 
						catch (IOException e) { e.printStackTrace(); }
						//*****************************//
					}
				}
			}
		);
		//*****************************//
		  
		
		//** nested MouseListener - mouse hover - change image **//
		final ImageIcon icon_hd_button = this.loadImage(hd_button_filepath);
		final ImageIcon hover_hd_button = this.loadImage(hover_hd_button_filepath);
		
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
		//******************************//

		this.add(load_button);	
	}
	
	
	
	//******************************//
	// ** Capture Button ** //
	//*****************************//
	private void setupCaptureButton() throws IOException
	{
		this.capture_button = new JButton();
		
		
		//** button image **//
		this.capture_button.setIcon(this.loadImage(capture_button_filepath));
		//*****************************//
		
		
		//**remove button background**//
		this.capture_button.setBorder(null); 
		this.capture_button.setContentAreaFilled(false);
		//*****************************//
		
		
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
						JOptionPane.showMessageDialog(display_panel,
								"Feature coming soon!",
								"Sorry :(",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		);
		//*****************************//
		
		
		//** nested MouseListener - mouse hover - change image **//
		final ImageIcon icon_capture_button = this.loadImage(capture_button_filepath);
		final ImageIcon hover_capture_button = this.loadImage(hover_capture_button_filepath);
		
		this.capture_button.addMouseListener(new MouseListener() 
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
		});
		//******************************//
		
		this.add(capture_button);
	}
	
	
	
	//******************************//
	// ** Save Button ** //
	//*****************************//
	private void setupSaveButton()
	{
		this.save_button = new JButton("Save");
		
		
		//** button image**//

		//*****************************//
		
		
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
		//*****************************//
		
		this.add(save_button);
	}
	
	
	
	//******************************//
	// ** Print Button ** //
	//*****************************//
	private void setupPrintButton()
	{
		this.print_button = new JButton("Print");
		
		
		//** button image**//

		//*****************************//
		
		
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
		//*****************************//
		
		this.add(print_button);
	}
	
	
	
	//******************************//
	// ** load image- return ImageIcon** //
	//*****************************//
	public ImageIcon loadImage(String filepath) throws IOException
	{	
		BufferedImage img = ImageIO.read(new File(filepath)); 
		return new ImageIcon(img);
	}
	
	
	
	//******************************//
	// ** fileChooser - returns filepath String ** //
	//*****************************//
	public String fileChooser()
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
	// ** resize image - takes filepath ** //
	//*****************************//
	public BufferedImage resizeImage(String filepath) throws IOException
	{	
		Scalr scalr = new Scalr(); //**imported Library (credit imgScalr)
		BufferedImage image = ImageIO.read(new File(filepath));
		
	    return image = scalr.resize(image, 400);
	}
	
}
