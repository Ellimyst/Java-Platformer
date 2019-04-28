import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Bullet
{
	private int  xP,  yP, dam, size, gun, i = 0, t, caster,lol;
	private double xVel, yVel;
	private double distance;
	boolean bool = true;

	public Bullet(int[] stuff, int vel, int dam, int guni, int casteri)
	{
		xP = stuff[2];
		yP = stuff[3];

		distance = (Math.sqrt(Math.pow(stuff[1] - yP, 2) + Math.pow(stuff[0] - xP, 2)));
		xVel = vel * ((stuff[0] - xP) / distance);
		yVel = vel * ((stuff[1] - yP) / distance);

		gun = guni;

		if(gun == 0)
			size = 5;
		else if(gun == 1)
			size = 4;
		else if(gun == 2)
			size = 25;
		else if(gun == 3)
			size = 25;

		lol = 0;

		t = 255/20;
		caster = casteri;

		this.dam = dam;
	}

	int getType(){ return gun; }
	int getC() { return caster; }

	int x()
	{
		return xP;
	}

	int y()
	{
		return yP;
	}

	int damage()
	{
		return dam;
	}

	public void move()
	{
		if(!(Math.abs(xP) > 10000 && Math.abs(yP) > 10000))
		{
			xP += xVel;
			yP += yVel;
		}

		if(lol <3)
			lol++;

		//if(xP >= Game.width + size || xP <= 0 - size || yP >= Game.height + size || yP <= 0 - size)
		//	Game.bullets.remove(this);
	}

	int getSize() { return size; }

	public void draw(Graphics g)
	{
		if(bool)
			for(Tile b : Game.blocks)
				if(gun == 1)
			{
				if(b.collides(xP-(int)(size/2), yP-(int)(size/2), 0, 0))
					bool = false;
			}
				else
				{
					if(b.collides(xP-(int)(size/2), yP-(int)(size/2), (int)(size*9/10), (int)(size*9/10)))
					bool = false;
				}
		
		if(bool)
		{
			if(gun == 1)
			{
				if(i%2 == 0)
					size+=2;
				i++;

				if(size<35)
				{
					g.setColor(new Color(255,25+(size*4),25,255-(size/2)));
					g.fillOval(xP-size, yP-size, size*2, size*2);
				}

			}
			else if(gun == 2)
			{
				g.setColor(new Color(250,180,20));
			}
			else if(gun == 3)
			{
				g.setColor(new Color(205+new Random().nextInt(51) ,205 + new Random().nextInt(51), 200 + new Random().nextInt(56), new Random().nextInt(100) + 156));
			}
			else
			{
				g.setColor(new Color(255,255,255));
			}

			if(gun != 1)
			{
				if(gun == 3)
				{
					if(lol > 2)
						g.fillOval(xP-size, yP-size, size*2, size*2);
					else
						lol++;
				}
				else
					g.fillOval(xP-size, yP-size, size*2, size*2);
			}
		}
	}
}