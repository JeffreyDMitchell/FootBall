import javax.swing.*;

public class Start 
{

	public static void main(String[] args)
	{
		System.setProperty("sun.java2d.opengl", "true");
		createFrame(900, 700);
		
	}
	
	public static void createFrame(int h, int w)
	{
		
		JFrame f = new JFrame("FOOOOT BOOOOOOL");
		Code c = new Code(h, w);
		
		f.setVisible(true);
		f.setSize(w, h);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(c);

	}
	
}
