import java.awt.*;

public class Button
{
	private int x, y, w, h;
	boolean press;
	
	public Button(Color c, int sx, int sy, int wi, int hi)
	{
		x = sx;
		y = sy;
		w = wi;
		h = hi;
		press = false;
	}
	
	public void draw(Graphics2D g)
	{
		Color fill = new Color(0,100,20);
		Color border = new Color(0,50,0);
		
        g.setStroke(new BasicStroke(((int)(w/18))));
		
		if(press)
			g.setColor(fill);
		else
			g.setColor(border);
		
		g.fillRect(x, y, w, h);
		
		if(press)
			g.setColor(border);
		else
			g.setColor(fill);
		
		g.drawRect(x, y, w, h);
	}
	
	
}
