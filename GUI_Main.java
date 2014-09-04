//////////////////////////////////////////
//		description needed here 		//
//////////////////////////////////////////
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI_Main
{	
	private JFrame frame;
	private final String banner_filepath = "src//imgs//banner.png",
					 background_filepath = "src//imgs//background.png";
	
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
		this.frame.setResizable(false);
		this.frame.setJMenuBar( this.setupMenuBar() ); //returns a setup JMenuBar
		
		this.setupPanels(); 
		
		this.frame.setVisible(true);
	}
	
	
	//******************************//
	//*****************************//
	private void setupPanels() throws IOException
	{
		JPanel container_panel, title_panel;
		
		
		// ** instantiate panels..etc **//
		container_panel = new JPanel();
		title_panel = new JPanel();
		
		JLabel banner_label = new JLabel();
		JTextArea textarea = new JTextArea();
		GUI_DisplayPanel display_panel = new GUI_DisplayPanel(container_panel); //need to pass panel for displaying dialog box messages
		GUI_ButtonPanel button_panel = new GUI_ButtonPanel(display_panel); //pass in panel to use methods
		//*****************************//
		
		
		// ** setup container panel **//
		container_panel.setLayout(new BorderLayout()); // **main layout
		container_panel.setBackground(Color.blue);
		
		this.frame.add(container_panel);
		container_panel.add(title_panel, BorderLayout.PAGE_START);
		container_panel.add(display_panel, BorderLayout.CENTER);// needs own class for logic
		container_panel.add(button_panel,BorderLayout.PAGE_END);//needs own class for logic
		//*****************************//
		
		
		// ** setup title panel - load banner image **//
		title_panel.setBorder(BorderFactory.createEmptyBorder( 20,	 //top -create spacing
															    0,   //left
															   20,   //bottom 
															    0)); //right
		try { 
			banner_label.setIcon(this.loadImage(banner_filepath)); 
		} 
		catch (IOException e) { e.printStackTrace(); }
				
		title_panel.add(banner_label);
		//*****************************//
	}
	
	
	
	//******************************//
	// ** setup MenuBar - returns JMenuBar** //
	//*****************************//
	private JMenuBar setupMenuBar()
	{
		JMenuBar menu_bar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		JMenu about = new JMenu("About");
		JMenu instructions = new JMenu("Instructions");
		
		menu_bar.add(file);
		menu_bar.add(about);
		menu_bar.add(instructions);
		
		final JMenuItem exit = new JMenuItem("Exit");
		final JMenuItem comingsoon = new JMenuItem("Coming Soon");
		final JMenuItem comingsoon_2 = new JMenuItem("Coming Soon");
		
		file.add(exit);
		about.add(comingsoon);
		instructions.add(comingsoon_2);
		
		//** nested actionlistener **//
		exit.addActionListener
		( 
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent ae) 
				{
					if(ae.getSource() == exit)
						frame.dispose();
				}
			}
		);
		//******************************//
		
		return menu_bar;
	}
	
	
	
	//******************************//
	// ** load image- return ImageIcon** //
	//*****************************//
	public ImageIcon loadImage(String filepath) throws IOException
	{	
		BufferedImage img = ImageIO.read(new File(filepath)); 
		return new ImageIcon(img);
	}

}
