
public class Ball 
{

	final int rad = 10;
	final double drag = .99;
	
	double x, y, xVel, yVel;
	
	public Ball()
	{
		
		x = (int) (Math.random() * 400);
		y = (int) (Math.random() * 400);
		
		xVel = 0;
		yVel = 0;
		
	}
	
	public Ball(double x, double y)
	{
		
		this.x = x;
		this.y = y;
		
		xVel = 0;
		yVel = 0;
		
	}
}
