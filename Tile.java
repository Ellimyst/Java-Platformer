import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Tile
{
	public int x, y, w, h;
	private Image image;
	
	public Tile(int X, int Y, int W, int H, Image i)
	{
		x = X;
		y = Y;
		w = W;
		h = H;
		
		image = i;
	}
	
	public void draw(Graphics g, Game map)
	{
		g.drawImage(image, x, y, map);
	}
	
	public boolean collides(int X, int Y, int W, int H)
	{
		boolean bool = ((X < x+w && X+W > x) && (Y < y+h && Y+H > y));
		
		return bool;
	}
}