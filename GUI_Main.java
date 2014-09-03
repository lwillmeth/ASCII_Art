//////////////////////////////////////////
//		description needed here 		//
//////////////////////////////////////////
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI_Main
{	
	private JFrame frame;
	
	public static void main(String[] args) throws IOException 
	{
		new GUI_Main();
	}
	
	public GUI_Main() throws IOException
	{
		this.setupFrame();
	}
	
	
	//******************************//
	//*****************************//
	private void setupFrame() throws IOException
	{
		this.frame = new JFrame ("ASCII Project");
		this.frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(1024, 768);
		this.frame.setLocationRelativeTo(null); //centers frame
		
		this.setupPanels(frame);
		
		this.frame.setVisible(true);
	}
	
	
	//******************************//
	//*****************************//
	private void setupPanels(JFrame fame) throws IOException
	{
		JPanel container_panel, title_panel;
		String banner_filepath = "src//imgs//banner.png";
		
		
		//*****************************//
		// ** instantiate panels..etc **//
		container_panel = new JPanel();
		title_panel = new JPanel();
		
		JLabel banner_label = new JLabel();
		JTextArea textarea = new JTextArea();
		GUI_DisplayPanel display_panel = new GUI_DisplayPanel(container_panel); //need to pass panel for displaying dialog box messages
		GUI_ButtonPanel button_panel = new GUI_ButtonPanel(display_panel); //pass in panel to use methods
		//*****************************//
		
		
		//*****************************//
		// ** setup container panel **//
		container_panel.setLayout(new BorderLayout()); // **main layout
		
		this.frame.add(container_panel);
		container_panel.add(title_panel, BorderLayout.PAGE_START);
		container_panel.add(display_panel, BorderLayout.CENTER);// needs own class for logic
		container_panel.add(button_panel,BorderLayout.PAGE_END);//needs own class for logic
		//*****************************//
		
		
		//*****************************//
		// ** setup title panel - load banner image **//
		title_panel.setBorder(BorderFactory.createEmptyBorder( 20,	 //top -create spacing
															   0,   //left
															   0,   //bottom 
															   0)); //right
		try 
		{
			BufferedImage img = ImageIO.read(new File(banner_filepath)); 
			ImageIcon icon = new ImageIcon(img);

			banner_label.setIcon(icon);
		} 
		catch (IOException e) { e.printStackTrace(); }
				
		title_panel.add(banner_label);
		//*****************************//
		
		
	}

}
