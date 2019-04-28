import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

import javax.swing.JFrame;

public class Game extends JFrame implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener
{
	static int width, height;
	private Rectangle winSize;
	private BufferedImage br;
	private Random r;
	int x, y, countr, log, bWidth, bHeight, ro, col;
	static int jStrength;
	static int jHeight;
	static int room, xM, yM, transperency = 255;
	static Player dood;
	boolean up, down, left, right, press, blub=true, dubleJumpBuffr = true;
	static boolean click = false;
	static int[] delay, vel, dam, pow;
	static ArrayList<ArrayList<Monster>> mon;
	private ArrayList<Boolean> played = new ArrayList<Boolean>();
	private Image[] logo;
	private Button play;
	private Font f;
	static ArrayList<Tile> tiles, blocks;
	private Scanner myFile;
	int[][]map;
	boolean[] bools = new boolean[3];
	BufferedImage spear;

	public Game()
	{
		super("FIANLLLILSS!!");

		winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		width = (int)winSize.getWidth();
		height = (int)winSize.getHeight();
		dood = new Player();
		r = new Random();

		room = 0;
		mon = new ArrayList<ArrayList <Monster>>();
		mon.add(new ArrayList<Monster>());
		played.add(false);

		for(int i = 0; i < 0; i++)
			//mon.add(new Monster(r.nextInt((int)(width/3))+(int)(width/3) , r.nextInt((int)(height/3))+(int)(height/3)));

			System.out.println(mon.size());

		log = 0;
		logo = new Image[20];

		for(int i = 0; i < logo.length; i++)
		{
			logo[i] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo/logo" +(i+1) +".png")).getScaledInstance((int)(width*13/15), (int)(height/4), Image.SCALE_SMOOTH);
		}

		tiles = new ArrayList<Tile>();
		blocks = new ArrayList<Tile>();

		play = new Button(new Color(255,0,55), (int)(width*13/30), (int)(height*2/3), (int)(width*4/30), (int)(height/9));

		f = new Font("Arial", Font.PLAIN, 50);

		jHeight = jStrength = (int)(height/50);

		addMouseWheelListener(this);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		setSize(width,height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		animate();
	}


	static void setJump()
	{
		jHeight = jStrength;
	}

	public int[][] getMap(Scanner scanner)
	{
		Vector<String[]> lines = new Vector<String[]>();

		int widtht = 0;
		int heightt = 0;		

		while(scanner.hasNextLine()){
			String[] str_tiles = scanner.nextLine().split(" ");

			if(str_tiles.length > widtht)
				widtht = str_tiles.length;

			lines.add(str_tiles);
			heightt++;
		}

		int[][] mapt = new int[heightt][widtht];

		int yCount = 0;
		for(String[] strAry : lines){

			for(int xCount = 0; xCount < strAry.length; xCount++){
				mapt[yCount][xCount] = Integer.parseInt(strAry[xCount]);
			}

			yCount++;
		}

		scanner.close();
		return mapt;
	}

	public void updateMap()
	{
		try
		{
			map = getMap(new Scanner(new File("maps/map" +room +".txt")));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}

		mon.add(new ArrayList<Monster>());
		played.add(false);

		bWidth = (int)((width)/map[0].length);
		bHeight = (int)((height)/map.length);

		blocks.clear();
		Image wall = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Textures/wall.png")).getScaledInstance( bWidth, bHeight, Image.SCALE_SMOOTH);


		for(int r = 0; r < map.length; r++)
			for(int c = 0; c < map[r].length; c++)
				if(map[r][c] == 1)
					blocks.add(new Tile(c*bWidth, r*bHeight, bWidth, bHeight, wall));
				else if(map[r][c] == 2)
					if(!played.get(room))
					{
						mon.get(room).add(new Monster( (c-1)*bWidth , (r)*bHeight));

					}
		played.set(room, true);
		//blocks.add(new Block((bWidth * c) - (int)(bWidth/2) + 25  , (bHeight * r)  - (int)(bHeight/2)+48  , bWidth/2, bHeight/2));

		//if(blub)
		//{
		tiles.clear();

		Image plain = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Textures/brick_plain.png")).getScaledInstance( bWidth, bHeight, Image.SCALE_SMOOTH);
		Image mossy = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Textures/brick_mossy.png")).getScaledInstance( bWidth, bHeight, Image.SCALE_SMOOTH);
		Image crack = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Textures/brick_crack.png")).getScaledInstance( bWidth, bHeight, Image.SCALE_SMOOTH);


		for(int r = 0; r < map.length; r++)
			for(int c = 0; c < map[r].length; c++)
			{
				int rand = new Random().nextInt(10);
				if(rand==0)
					tiles.add(new Tile(c*bWidth, r*bHeight, bWidth, bHeight, mossy));
				else if(rand == 1)
					tiles.add(new Tile(c*bWidth, r*bHeight, bWidth, bHeight, crack));
				else
					tiles.add(new Tile(c*bWidth, r*bHeight, bWidth, bHeight, plain));
			}

		dood.setImage(bWidth, bHeight);
		for(Monster mons : mon.get(room))
			mons.setImage(bWidth, bHeight);
	}

	public void setLogo()
	{
		log += (r.nextInt(3)-1)*(r.nextInt(2)+1);

		if(log>19)
			log = 0;
		else if(log < 0)
			log = 19;
	}

	public void paint(Graphics w)
	{
		if(br==null)
			br = (BufferedImage)(createImage(getWidth(),getHeight()));

		Graphics2D t = (Graphics2D) br.createGraphics();
		t.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		t.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

		if(room == 0)
		{
			t.setColor(new Color( 0, 0, 0));
			t.fillRect(0, 0, width, height);

			t.drawImage(logo[r.nextInt(20)], (int)(width/15), (int)(height/4), this);
			play.draw(t);
		}
		else if(room != 0)
		{
			t.setColor(new Color(173, 134, 44));
			//t.fillRect(0, 0, width, height);

			for(Tile tile : tiles)
				tile.draw(t, this);

			dood.draw(t, this);

			for(Monster m : mon.get(room))
				m.draw(t, press, this);

			for(Tile blk : blocks)
				blk.draw(t, this);

			t.setColor(Color.BLUE);
			t.fillRect(250, 120, (int)(dood.getEnergy()/10) , 15);

			t.setColor(new Color(  (int)(255 * (dood.getMH() - dood.getH())/dood.getMH())  ,  (int)(255 * dood.getH() / dood.getMH()) , 0));
			t.fillRect(250, 140, (int)(400*dood.getH()/dood.getMH()), 15);
		}

		t.setColor(new Color(225, 0, 0));
		t.drawOval(xM-5, yM-5, 10, 10);
		t.drawOval(xM-6, yM-6, 12, 12);

		if(room != 0)
		{
			t.setColor(new Color(0,0,0,transperency));
			t.fillRect(0, 0, width, height);

			if(dood.getH() <= 0)
			{
				t.setColor(new Color(0,255,100,transperency));
				t.setFont(f);

				t.drawString("      Game Over.", 200, 200);
				t.drawString("Better Luck Next Time, \n    Noob", 200, 250);
			}
			else if(transperency > 0)// && dood.getH() > 0)
				transperency--;
		}

		w.drawImage( br, 0, 0, null);
	}

	void setJumpBuffer()
	{
		dubleJumpBuffr = true;
	}

	public void animate()
	{
		while(true) 
		{
			try
			{ 
				Thread.sleep(20); 
			}
			catch(InterruptedException ex)
			{ 
				Thread.currentThread().interrupt(); 
			}

			if(room == 0)
			{
				try
				{ 
					Thread.sleep(80); 
				}
				catch(InterruptedException ex)
				{ 
					Thread.currentThread().interrupt(); 
				}

				setLogo();
			}
			else if(room != 0)
			{
				if(!dubleJumpBuffr)
					up = false;

				if(up)
				{
					if(jHeight > 0)
					{
						jHeight--;
					}
					else
					{
						up = false;
						//jHeight = jStrength;
					}
				}
				else
				{
					dubleJumpBuffr = true;
				}

				if(!dood.isNotInAir())
					dubleJumpBuffr = false;

				dood.move(up, left, right, press);

				if(dood.getH() > 0)
				{
					if(press)
					{

					}
				}

				try
				{
					for(Monster m : mon.get(room))
					{
						//m.damage(room);
						m.move(up, left, right);
					}
				}
				catch(Exception e)
				{
					System.out.println("Array dongoof");
				}

				//dude.damage(room);

				if(dood.xP - 25 > width)
				{
					room++;
					updateMap();
					dood.xP=0;
				}
				else if(dood.xP + 25 < 0)
				{
					room--;
					updateMap();
					dood.xP=width;
				}

				countr++;
			}

			repaint();
		}
	}

	public void mousePressed(MouseEvent m)
	{
		if(room == 0)
		{
			if(m.getXOnScreen() >= (int)(width*13/30) && m.getXOnScreen() <= (int)(width*13/30)+(int)(width*4/30) && m.getYOnScreen() >= (int)(height*2/3) && m.getYOnScreen() <= (int)(height*2/3)+(int)(height/9))
			{
				play.press = true;
			}
		}
		else
		{
			press = true;
		}
	}

	public void mouseReleased(MouseEvent m)
	{
		if(room == 0)
		{
			if(m.getXOnScreen() >= (int)(width*13/30) && m.getXOnScreen() <= (int)(width*13/30)+(int)(width*4/30) && m.getYOnScreen() >= (int)(height*2/3) && m.getYOnScreen() <= (int)(height*2/3)+(int)(height/9))
			{
				play.press = false;
				room = 1;
				updateMap();
			}
		}
		else
		{
			press = false;
		}
	}

	public void mouseMoved(MouseEvent m)
	{
		if(room != 0)
		{
			setCoords(m.getXOnScreen(),m.getYOnScreen());
			dood.setStuff(m.getXOnScreen(), m.getYOnScreen());
		}
	}


	public void setCoords(int X, int Y)
	{
		xM = X;
		yM = Y;
	}

	public void keyPressed(KeyEvent k)
	{
		if(room != 0)
		{
			if(k.getKeyCode() == k.VK_W)
				up = true;

			if(k.getKeyCode() == k.VK_S)
				down = true;

			if(k.getKeyCode() == k.VK_A)
				left = true;

			if(k.getKeyCode() == k.VK_D)
				right = true;

			if(k.getKeyCode() == k.VK_M)
			{
				dood.setEnergy(2000);
			}

			if(k.getKeyCode() == k.VK_N)
			{
				dood.setEnergy(0);
			}

			if(k.getKeyCode() == k.VK_H)
			{
				dood.setHealth(20000);
			}

			if(k.getKeyCode() == k.VK_K)
			{
				dood.setHealth(-20000);
			}

			if(k.getKeyCode() == k.VK_SHIFT)
				dood.setSpeed((int)(width/150));
		}
	}

	public void keyReleased(KeyEvent k)
	{
		if(room != 0)
		{
			if(k.getKeyCode() == k.VK_W)
			{
				up = false;
				//jHeight = jStrength;
			}

			if(k.getKeyCode() == k.VK_S)
				down = false;

			if(k.getKeyCode() == k.VK_A)
				left = false;

			if(k.getKeyCode() == k.VK_D)
				right = false;


			if(k.getKeyCode() == k.VK_SHIFT)
				dood.setSpeed((int)(width/300));
		}
	}

	public void mouseWheelMoved(MouseWheelEvent w)
	{
		if(room != 0)
		{

		}
	}

	public void mouseDragged(MouseEvent m)
	{
		//if(room != 0)
		setCoords(m.getXOnScreen(),m.getYOnScreen());

		if(room != 0)
			dood.setStuff(m.getXOnScreen(), m.getYOnScreen());
	}

	public void mouseClicked(MouseEvent m){}
	public void mouseEntered(MouseEvent m){}
	public void mouseExited(MouseEvent m){}

	public void keyTyped(KeyEvent k)
	{
		if(room == 1)
		{
		}
	}
}