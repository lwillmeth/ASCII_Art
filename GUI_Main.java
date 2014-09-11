import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GUI_Main
{	
	public static final int ASCII_MAX_SIZE = 55;
	public static final String loadimage_filepath = "src//imgs//loadimage.png";
	
	private JFrame frame;
	private final String banner_filepath = "src//imgs//banner.png",
					  authorpic_filepath = "src//imgs//authorPic.png";
	
	public static void main(String[] args)
	{
		new GUI_Main();
	}
	
	public GUI_Main()
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
	private void setupPanels()
	{
		// ** instantiate panels..etc **//
		JPanel container_panel = new JPanel();
		JPanel title_panel = new JPanel();
		JLabel banner_label = new JLabel();
		
		GUI_DisplayPanel display_panel = new GUI_DisplayPanel();
		GUI_ButtonPanel button_panel = new GUI_ButtonPanel(display_panel);
		
		// ** setup title panel - load banner image **//
		title_panel.setBorder(BorderFactory.createEmptyBorder( 20,	 //top -create spacing
															    0,   //left
															   20,   //bottom 
															    0)); //right
		banner_label.setIcon(loadImage(banner_filepath)); 
		title_panel.add(banner_label);
		
		// ** setup container panel **//
		container_panel.setLayout(new BorderLayout()); // **main layout
		container_panel.setBackground(Color.blue);
		container_panel.add(title_panel, BorderLayout.PAGE_START);
		container_panel.add(display_panel, BorderLayout.CENTER);// needs own class for logic
		container_panel.add(button_panel,BorderLayout.PAGE_END);//needs own class for logic
		this.frame.add(container_panel);
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
		final JMenuItem about_item = new JMenuItem("Authors");
		final JMenuItem comingsoon_2 = new JMenuItem("Coming Soon");
		
		file.add(exit);
		about.add(about_item);
		instructions.add(comingsoon_2);
		
		//** loads the inner menu items panels **//
		this.setupMenuItemPanels(about_item);
		
		//** nested actionlistener **//
		exit.addActionListener
		( 
			new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					if(ae.getSource() == exit)
						frame.dispose();
				}
			}
		);
		return menu_bar;
	}
	
	
	
	//******************************//
	//** -sets up inner menuitem panels that display info	**//
	//*****************************//
	private void setupMenuItemPanels(final JMenuItem about_item)
	{
		final JPanel about_panel = new JPanel();
		JLabel label = new JLabel( loadImage(authorpic_filepath) );
		about_panel.add(label);
		
		about_item.addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					if(ae.getSource() == about_item)
					{
						JFrame message_frame = new JFrame("Authors");
						message_frame.setSize(400, 455);
						message_frame.setLocationRelativeTo(null);
						message_frame.add(about_panel);
						message_frame.setVisible(true);
					}
				}
			}
		);
	}
	
	
	
	//******************************//
	// ** load image- return ImageIcon** //
	//*****************************//
	public static ImageIcon loadImage()
	{	
		return new ImageIcon(loadBufferedImage(loadimage_filepath));
	}
	// Overloaded
	public static ImageIcon loadImage(String filepath)
	{	
		return new ImageIcon(loadBufferedImage(filepath));
	}
	
	
	
	//******************************//
	// ** load image- return BufferedImage** //
	//*****************************//
	public static BufferedImage loadBufferedImage(String filepath)
	{
		try{
			return (BufferedImage)ImageIO.read(new File(filepath));
		}catch (Exception e){
			System.out.println("Image \""+filepath+"\" not found.");
			try{
				return (BufferedImage)ImageIO.read(new File(loadimage_filepath));
			}catch (Exception error){
				System.out.println("Default image not found.");
				return null;
			}
		}
	}
}
