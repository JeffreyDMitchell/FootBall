public class Particle 
{

	double x, y, xVel, yVel, age = 0;
	
	public Particle()
	{
		
		
		
	}
	
	public Particle(double x, double y)
	{
		
		this.x = x;
		this.y = y;
		
		//xVel = Math.random() * 10 - 5;
		//yVel = Math.random() * 10 - 5;
		
		double temp = Math.random() * 2 * Math.PI;
		
		xVel = Math.cos(temp) * 2;
		yVel = Math.sin(temp) * 2;
		
	}
	
}
