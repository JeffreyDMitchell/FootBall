public class Player 
{
	
	boolean AI;
	
	int behavior = 0, eagerness = 50;
	
	final int rad = 15;
	final double drag = .95;
	
	double x, y, xVel, yVel, desX = 0, desY = 0;
	
	int[] control = new int[4];
	
	public Player()
	{
		
		xVel = 0;
		yVel = 0;
		
		x = Math.random() * 500;
		y = Math.random() * 500;
		
		AI = true;
		
	}
	
	public Player(double x, double y)
	{
		
		xVel = 0;
		yVel = 0;
		
		this.x = x;
		this.y = y;
		
		AI = true;

		//eagerness = (int) (60 * (Math.random() - .5) + 50);
		
	}
	
	public Player(double x, double y, boolean AI)
	{
		
		xVel = 0;
		yVel = 0;
		
		this.AI = AI;
		
		this.x = x;
		this.y = y;

		
		
	}
	
}
