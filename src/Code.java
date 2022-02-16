
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Code extends JPanel implements ActionListener, KeyListener
{
	
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Ball> balls = new ArrayList<Ball>();
	ArrayList<Goal> goals = new ArrayList<Goal>();
	ArrayList<Particle> particles = new ArrayList<Particle>();
	
	Player p1, p2;
	
	Timer t;
	
	JCheckBox test = new JCheckBox("AI Considerations");

	//screen width and screen height, pixel count
	int SW = 493, SH = 470, score = 0;
	long time = System.currentTimeMillis();
	
	public Code(int h, int w)
	{
		
		SW = w - 7;
		SH = h - 30;
		
		add(test);
		
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		setBackground(new Color(58, 154, 64));
		
		t = new Timer(5, this);
		
		setupGame();

		t.start();
		
		
		
	}
	
	public void setupGame()
	{

		p1 = new Player(346, 495,  false);
		p2 = new Player(347, 375, false);
		
		//adding player one
		goals.add(new Goal(346, 0)); 
		goals.add(new Goal(346, 870));
		players.add(p1);
		players.add(new Player(346, 1000));
		//players.add(new Player(380, 495));
		balls.add(new Ball(346, 435));
		
	}
	
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		//drawing the field, dear god this is rough IT LOOKS BAD DONT READ IT
		g.setColor(Color.RED);
		
		g.fillRect(650, 355, 20, 160);
		
		g.setColor(Color.BLUE);
		
		g.fillRect(650, 355, 20, 80 + (((score * 80 / 1000) > 80 || (score * 80 / 1000) < -80) ? ((score * 80 / 1000) > 80 ? 80 : -80) : (score * 80 / 1000)));
		
		g.setColor(Color.WHITE);
		
		g.drawRect(100, 700, 493, 500);
		g.drawRect(100, -1, 493, 160);
		g.drawOval(246, 335, 200, 200);
		g.fillOval(341, 430, 10, 10);
		g.drawLine(10, 435, 683, 435);
		g.drawLine(10, 0, 10, 900);
		g.drawLine(683, 0, 683, 900);
		 
		g.drawRect(650, 355, 20, 160);
		
		
		
		for(int i = 0; i < players.size(); i++)
		{
			
			Player a = players.get(i);
			
			//player team is based on index in players ArrayList. even number are team red, odd team blue
			g.setColor(i % 2 == 0 ? Color.RED : Color.BLUE);
			
			g.fillOval((int) a.x - a.rad, (int) a.y - a.rad, a.rad * 2, a.rad * 2);
			
			g.setColor(Color.WHITE);
			
			g.drawOval((int) a.x - a.rad, (int) a.y - a.rad, a.rad * 2, a.rad * 2);
			
			if(a.AI && test.isSelected())
			{
				
				g.drawString((a.behavior == 0 ? "attacking" : "seeking"), (int) (a.x - 15), (int) (a.y - 20));
				//g.drawString("eagerness: " + a.eagerness, (int) (a.x - 15), (int) (a.y - 25));
				
				g.drawLine((int) a.x, (int) a.y, (int) a.desX, (int) a.desY);
				
				g.drawOval((int) a.desX - (10 + a.rad * 2) / 2, (int) a.desY - (10 + a.rad * 2) / 2, 10 + a.rad * 2, 10 + a.rad * 2);
				
				g.drawLine((int) balls.get(0).x, (int) balls.get(0).y, (int) a.desX, (int) a.desY);
				
			}
				
		}
		
		for(int i = 0; i < balls.size(); i++)
		{
			
			Ball b = balls.get(i);
			
			g.setColor(Color.BLACK);
			
			g.fillOval((int) b.x - b.rad, (int) b.y - b.rad, b.rad * 2, b.rad * 2);
			
			g.setColor(Color.WHITE);
			
			g.drawOval((int) b.x - b.rad, (int) b.y - b.rad, b.rad * 2, b.rad * 2);
			
		}
		
		for(int i = 0; i < goals.size(); i++)
		{
			
			Goal c = goals.get(i);
			
			g.drawOval(c.x - 100, c.y - 100, c.rad * 2, c.rad * 2);
			
		}
		
		for(Particle p : particles)
		{
			
			g.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
			
			g.drawOval((int) (p.x - 2), (int) (p.y - 2), 4, 4);
			
		}
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == t)
		{
			
			//System.out.println(System.currentTimeMillis() - time);
			
			//stime = System.currentTimeMillis();
			
			// players.get(0).xVel += (players.get(0).control[2] + players.get(0).control[3]) * 0.25;
			// players.get(0).yVel += (players.get(0).control[0] + players.get(0).control[1]) * 0.25;
			
			for(int i = players.size() - 1; i >= 0; i--)
			{
				
				Player a = players.get(i);

				if(!a.AI)
				{

					a.xVel += (a.control[2] + a.control[3]) * 0.25;
					a.yVel += (a.control[0] + a.control[1]) * 0.25;
				}
				
				//decaying velocity
				a.xVel *= a.drag;
				a.yVel *= a.drag;
				
				//capping velocity
				double vel = Math.sqrt(Math.pow(a.xVel, 2) + Math.pow(a.yVel, 2));
				
				if(vel > 2)
				{
					
					a.xVel *= 2 / vel;
					a.yVel *= 2 / vel;
					
				}
					
				//updating position based on velocity
				a.x += a.xVel;
				a.y += a.yVel;
				
				//keeping players in bounds, bounces on collision	
				if(a.x > SW - a.rad || a.x < a.rad)
				{
					
					a.x = a.x > SW - a.rad ? SW - a.rad : a.rad;
					a.xVel *= -1;
					
					
				}
				
				if(a.y > SH - a.rad|| a.y < a.rad)
				{
					
					a.y = a.y > SH - a.rad ? SH - a.rad : a.rad;
					a.yVel *= -1;
					
				}
					
				for(int j = 0; j < balls.size(); j++)
				{
					
					Ball b = balls.get(j);
					
					double distance = Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow(a.y - b.y, 2));
					
					if(distance <= a.rad + b.rad)
					{
						
						b.xVel += .08 * (b.x - a.x);
						b.yVel += .08 * (b.y - a.y);
						
					}
					
					if(a.AI)
					{
					
						if(a.behavior == 0)
						{
						
							a.desX = b.x;
							a.desY = b.y;
							
							a.xVel += (a.desX - a.x) / 1000;
							
							a.yVel += (a.desY - a.y) / 1000;

							// double hypot = Math.sqrt(Math.pow((a.desX - a.x), 2) + Math.pow((a.desY - a.y), 2));

							// a.xVel = 2.0 * ((a.desX - a.x) / hypot);
							// a.yVel = 2.0 * ((a.desY - a.y) / hypot);
							
							if(Math.sqrt(Math.pow(a.desX - a.x, 2) + Math.pow(a.desY -  a.y, 2)) > a.eagerness)
							{
								
								a.behavior = 1;
								
							}
						
						}
						else if(a.behavior == 1)
						{
							
							
							
							double goalDist = Math.sqrt(Math.pow(b.x - goals.get(i % 2).x, 2) + Math.pow(b.y - goals.get(i % 2).y, 2));
							
							a.desX = b.x + ((b.x - goals.get(i % 2).x) / goalDist) * 40;
							a.desY = b.y + ((b.y - goals.get(i % 2).y) / goalDist) * 40;
							
							a.xVel += (a.desX - a.x) / 500;
							a.yVel += (a.desY - a.y) / 500;

							// double hypot = Math.sqrt(Math.pow((a.desX - a.x), 2) + Math.pow((a.desY - a.y), 2));

							// a.xVel = 2.0 * ((a.desX - a.x) / hypot);
							// a.yVel = 2.0 * ((a.desY - a.y) / hypot);
							
							if(Math.sqrt(Math.pow(a.desX - a.x, 2) + Math.pow(a.desY - a.y, 2)) < 10)
							{
								
								//System.out.println("suh");
								
								a.behavior = 0;
								
							}
								
						}
					
					}
					
				}
				
			}
			
			for(int i = 0; i < balls.size(); i++)
			{
				
				Ball b = balls.get(i);
				
				//decaying velocity
				b.xVel *= b.drag;
				b.yVel *= b.drag;
				
				//capping velocity
				double vel = Math.sqrt(Math.pow(b.xVel, 2) + Math.pow(b.yVel, 2));
				
				if(vel > 200)
				{
					
					b.xVel *= 200 / vel;
					b.yVel *= 200 / vel;
					
				}
					
				//updating position based on velocity
				b.x += b.xVel;
				b.y += b.yVel;
				
				//keeping ball in bounds, bouncing on collision
				if(b.x > SW - b.rad * 2 || b.x < b.rad * 2)
				{
					
					b.x = b.x > SW - b.rad * 2 ? SW - b.rad * 2 : b.rad * 2;
					b.xVel *= -1;
					
				}
				
				if(b.y > SH - b.rad * 2 || b.y < b.rad * 2)
				{
					
					b.y = b.y > SH - b.rad * 2 ? SH - b.rad * 2 : b.rad * 2;
					b.yVel *= -1;
					
				}
				
				for(int j = 0; j < goals.size(); j++)
				{
					
					Goal c = goals.get(j);
					
					if(Math.sqrt(Math.pow(b.x - c.x, 2) + Math.pow(b.y - c.y, 2)) < c.rad - b.rad)
					{
						
						score += 2 * j - 1;
						particles.add(new Particle(b.x, b.y));
						
						if(Math.abs(score) > 1000);
							//System.exit(0);
							
					}
					
				}
				
			}
			
			for(int i = 0; i < particles.size(); i++)
			{
				
				Particle p = particles.get(i);
				
				p.x += p.xVel;
				p.y += p.yVel;
				
				p.xVel *= 0.98;
				p.yVel *= 0.98;
				
				if(p.x > SW || p.x < 0)
				{
					
					p.x = p.x > SW ? SW : 0;
					p.xVel *= -1;
					
				}
				
				if(p.y > SH || p.y < 0)
				{
					
					p.y = p.y > SH ? SH : 0;
					p.yVel *= -1;
					
				}
				
				p.age++;
				
				if(p.age > 500)
					particles.remove(i);
					
				
			}
			
			//System.out.println(particles.size());
			
			repaint();
			requestFocus();
			
			//System.out.println(System.currentTimeMillis() - time);
			
		}
		
	}
	
	public void keyPressed(KeyEvent e)
	{	
		
		switch(e.getKeyCode())
		{
		
			case KeyEvent.VK_W:
				p1.control[0] = -1;
				break;
				
			case KeyEvent.VK_S:
				p1.control[1] = 1;
				break;
				
			case KeyEvent.VK_A:
				p1.control[2] = -1;
				break;
				
			case KeyEvent.VK_D:
				p1.control[3] = 1;
				break;

			case KeyEvent.VK_UP:
				p2.control[0] = -1;
				break;
				
			case KeyEvent.VK_DOWN:
				p2.control[1] = 1;
				break;
				
			case KeyEvent.VK_LEFT:
				p2.control[2] = -1;
				break;
				
			case KeyEvent.VK_RIGHT:
				p2.control[3] = 1;
				break;
		
		}
		
	}
	
	public void keyReleased(KeyEvent e)
	{
		
		switch(e.getKeyCode())
		{
		
			case KeyEvent.VK_W:
				p1.control[0] = 0;
				break;
				
			case KeyEvent.VK_S:
				p1.control[1] = 0;
				break;
				
			case KeyEvent.VK_A:
				p1.control[2] = 0;
				break;
				
			case KeyEvent.VK_D:
				p1.control[3] = 0;
				break;

			case KeyEvent.VK_UP:
				p2.control[0] = 0;
				break;
				
			case KeyEvent.VK_DOWN:
				p2.control[1] = 0;
				break;
				
			case KeyEvent.VK_LEFT:
				p2.control[2] = 0;
				break;
				
			case KeyEvent.VK_RIGHT:
				p2.control[3] = 0;
				break;
		
		}
		
	}
	
	public void keyTyped(KeyEvent e)
	{
		
		
		
	}
	
}
