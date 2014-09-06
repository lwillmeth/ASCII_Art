import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI_DisplayPanel extends JPanel
{
	private final String standard_filepath = "src//imgs//loadimage.png";
	private String waitingtoconvert_filepath ="";
	private JLabel standard_label, ascii_label;
	private JTextArea textarea;
	
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
		this.displayStandardImage();
		this.setupTextArea();
	}
	
		
	
	//******************************//
	//** setup standard label image - is image to be covert **//
	//*****************************//
	private void displayStandardImage() throws IOException
	{
		//** load image - set label to imageicon **//
    	this.standard_label = new JLabel(GUI_Main.loadImage(standard_filepath));
		this.standard_label.setBorder(BorderFactory.createLoweredBevelBorder());
		this.standard_label.setSize(400, 400);
		this.add(this.standard_label, BorderLayout.LINE_START);
	}
	
	
	
	//******************************//
	//** setup TextArea to display converted image in Ascii art  **// 
	//*****************************//
	private void setupTextArea() throws IOException
	{	
		this.textarea = new JTextArea(100,98);
		textarea.setFont(new Font("Lucida Console", Font.BOLD, 8));
		textarea.setBorder(BorderFactory.createLoweredBevelBorder());
		textarea.setEditable(false);
		
		this.add(textarea, BorderLayout.LINE_END);
	}
	
	
	
	//******************************//
	// ** Convert Image to Ascii - calls ImageToASCII class code ** //
	//*****************************//
	public void convertImageToAscii() throws IOException
	{
		ImageToASCII convert = new ImageToASCII( this.getWaitingtoconvert_filepath() );
		
		//** -runs convert code on passed in temp image **//
		//** -returns a 2D array of chars			    **//	
		char[][] temp = convert.convertImgToAscii(); 
	
		//output text to textarea
		this.textarea.setText(""); //clear textarea
		for(int i = 0; i < temp.length; i++) 
		{
			this.textarea.append("\n");
			for(int j = 0; j < temp[i].length; j++) 
				this.textarea.append(" " + temp[i][j]);
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