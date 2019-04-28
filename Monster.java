import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Monster
{
	int xM, yM, xP, yP, health, iT, maxH, a = 1, w, h;
	boolean f;
	double time = 0;
	int yVel = 0;
	ArrayList<Image> image = new ArrayList<Image>();
	ArrayList<String> url = new ArrayList<String>();
	String action, dir;
	int eCntr, energy, maxEn;
	Image temp;
	int indx, lulz = 0, derp = 0, indxbla = 0, hitCount = 3, hitCounter = 0, counter = 25, speed = 5, hitType = new Random().nextInt(3);
	//Image speart;
	Image[] runLeft, runRight, hitLeft_0, hitRight_0, hitRight_1, hitLeft_1, hitLeft_2, hitRight_2;
	int[] hitFrames = new int[]{ 15 , 9 , 24 };
	BufferedImage spear;
	boolean inAir, hitting = false, moving = false, press = false, pressToggle = false, lPress = false;
	boolean up, left, right;
	int turns = 0;

	public Monster(int xval, int yval)
	{
		xP = xval;
		yP = yval;
		iT = 0;
		maxH = 10000;
		health = maxH;
		f = false;
		action = "stand";
		dir = "right";
		indx = 0;
		maxEn = energy = 2000;
		eCntr=0;
		up = left = right = false;
	}

	void setImage(int width, int height)
	{
		w = (int)(width*1.5);
		h = (int)(height*2.5);
		makeImgs("stand");
		makeImgs("jump");
		makeImgs("fall");
		makeImgs("hit");

		//speart = (((Toolkit.getDefaultToolkit().getImage("Sprites/Player/spear.png").getScaledInstance(width, (int)(height/4), Image.SCALE_SMOOTH))));

		runLeft = new Image[6];
		runRight = new Image[6];

		for(int i = 0; i < 6; i++)
		{
			runLeft[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/run_left_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);
			runRight[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/run_right_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);
		}

		hitLeft_0 = new Image[5];
		hitRight_0 = new Image[5];

		for(int i = 0; i < 5; i++)
		{
			hitLeft_0[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/hit_left_0_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitLeft_0[i]);
			hitRight_0[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/hit_right_0_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitRight_0[i]);
		}

		hitLeft_1 = new Image[3];
		hitRight_1 = new Image[3];

		for(int i = 0; i < 3; i++)
		{
			hitLeft_1[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/hit_left_1_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);
			hitRight_1[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/hit_right_1_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);
		}

		hitLeft_2 = new Image[8];
		hitRight_2 = new Image[8];

		for(int i = 0; i < 8; i++)
		{
			hitLeft_2[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/hit_left_2_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitLeft_0[i]);
			hitRight_2[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/hit_right_2_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitRight_0[i]);
		}
	}

	void makeImgs(String s)
	{
		temp = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/" +s +"_right.png");
		image.add(temp.getScaledInstance(w,h, Image.SCALE_SMOOTH));
		url.add(s+"_right");
		temp = Toolkit.getDefaultToolkit().getImage("Sprites/Monster/" +s +"_left.png");
		image.add(temp.getScaledInstance(w,h, Image.SCALE_SMOOTH));
		url.add(s+"_left");
	}

	Image getImg()
	{
		return image.get(url.indexOf(action+"_"+dir));
	}

	int[] getStuff()
	{
		return new int[]{Game.xM, Game.yM, xP, yP};
	}

	void tF()
	{
		f = !f;
	}

	void setY(int lel){ yP = lel; action = "stand"; inAir = false;}
	void setVel(int lel){ yVel = lel; }


	int getEnergy(){ return energy; }

	void setEnergy(int en)
	{
		energy += en;
		if(en<0)
			en = 0;

		if(en>maxEn)
			en = maxEn;
	}

	public void setUp()
	{
		int bloop;

		if(up) bloop = new Random().nextInt(5)+2;
		else bloop = new Random().nextInt(5)+12;

		if(turns%bloop == 0)
		{
			if(new Random().nextInt(2) == 0)
				up = !up;
		}			
	}

	void setDir()
	{
		if(turns%(new Random().nextInt(10)+15) == 0)
		{
			if(new Random().nextInt(2) == 0)
				left = true;
			else
				left = false;

			right = !left;
		}

	}

	void setPress()
	{
		if(Math.sqrt(Math.pow(Game.dood.xP-xP,2) + Math.pow(Game.dood.yP-yP,2)) < 150 && !hitting && Math.sqrt(Math.pow(Game.dood.xP-xP,2) + Math.pow(Game.dood.yP-yP,2)) < 50) // Math.sqrt((x - xInit) ^2 + (y - yInit) ^2)
		{
			if(new Random().nextInt(5) == 0)
				press = true;
		}
	}

	int invTime = 0;

	public void knockBack()
	{
		if(invTime == 0 && new Random().nextInt(40) == 0)
			invTime = 1;
	}

	int jCount = 0;
	
	public void move(boolean upI, boolean leftI, boolean rightI)
	{

		boolean bool;

		if(health > 0)
		{

			setUp();
			setDir();
			setPress();


			if(Math.sqrt(Math.pow(Game.dood.xP-xP,2) + Math.pow(Game.dood.yP-yP,2)) < 300 && Math.sqrt(Math.pow(Game.dood.xP-xP,2) + Math.pow(Game.dood.yP-yP,2)) > 100)
			{
				if(Game.dood.xP > xP)
				{
					right = true;
					left = false;
				}
				else if(Game.dood.xP < xP)
				{
					right = false;
					left = true;
				}
			}

			if(iT > 0)
				iT--;

			if(invTime == 0)
			{
				if(pressToggle && press)
				{
					lPress = true;
					pressToggle = false;

					try
					{
						URL url = this.getClass().getClassLoader().getResource("Sounds/punch_" +(new Random().nextInt(2)) +".wav");
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(url));
						clip.start();
					}
					catch(Exception e)
					{
						System.out.println("Monster says, no sound found/can be played.");
					}

				}


				if(press && !lPress)
				{
					lPress = true;

					try
					{
						URL url = this.getClass().getClassLoader().getResource("Sounds/punch_" +(new Random().nextInt(2)) +".wav");
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(url));
						clip.start();
					}
					catch(Exception e)
					{
						System.out.println("Monster says, no sound found/can be played.");
					}

					//counterHit = hitFrames[hitType];
				}

				if(jCount > 10)
					up = false;

				if(up)
				{
					time = 0;
					yVel = -5;

					if(yVel == 0)
						yVel = -8;

					bool = true;

					for(Tile b : Game.blocks)
						if(b.collides(xP - (int)(w/2), yP+yVel - (int)(h/2), w, h))
							bool = false;

					//action = "jump";


					if(bool)
					{
						yP += yVel;
						inAir = true;
					}
					else
					{
						yP-=yVel;
						//Game.setJump();
						inAir = false;
					}
					
					jCount++;
				}
				else
				{
					//bool2 = true;
					time+=0.01;
					bool = true;
					derp++;

					if(derp == 1002)
						derp = 0;

					if(derp%3==0)
						yVel = yVel + a;
					yP += yVel;

					//action = "fall";
					//inAir = true;
					for(Tile b : Game.blocks)
						//if(bool2)inAir = true;
						if(b.collides(xP - (int)(w/2), (int)(yP+yVel - (int)(h/2)), w, h))
						{
							bool = false;
							if(b.collides(xP - (int)(w/2), (int)(yP+yVel + (int)(h/2)), w, 0))
							{
								setY(b.y-(int)(h/2));
								inAir = false;
							}
							//bool2 = false;
							setVel(0);
						}

					if(bool)
					{
						yP += (int)yVel;
					}
					else
					{
						//Game.setJump();
						jCount = 0;
					}
				}

				if(left)
				{
					bool = true;

					if(speed == (int)(w/150))
						if(energy - 2 >= 0)
							energy -= 2;
						else
							speed = (int)(w/300);


					for(Tile b : Game.blocks)
						if(b.collides(xP-speed - (int)(w/2), yP - (int)(h/2), w, h))
							bool = false;

					//action = "run";
					dir = "left";

					if(bool)
					{
						xP -= speed;
						moving = true;
					}
					else moving = false;
				}

				if(right)
				{
					bool = true;

					if(speed == (int)(w/150))
						if(energy - 2 >= 0)
							energy -= 2;
						else
							speed = (int)(w/300);


					for(Tile b : Game.blocks)
						if(b.collides(xP+speed - (int)(w/2), yP - (int)(h/2), w, h))
							bool = false;

					//action = "run";
					dir = "right";

					if(bool)
					{
						xP += speed;
						moving = true;
					}
					else moving = false;
				}

				if(left == right) moving = false;

				if((!moving && !inAir))
					action = "stand";
				else if(!moving)
					action = "jump";
				else if(moving)
					action = "run";
				
				if(lPress && counter == 25)// && !hitting)
				{
					if(energy-(2*hitFrames[hitType]) >= 0)
					{
						hitting = true;
						energy-=2;

						if(Math.sqrt(Math.pow(Game.dood.xP-xP,2) + Math.pow(Game.dood.yP-yP,2)) < 100) // Math.sqrt((x - xInit) ^2 + (y - yInit) ^2)
						{
							if((dir.equals("left") && Game.dood.xP < xP) || (dir.equals("right") && Game.dood.xP > xP))
							{
								Game.dood.setHealth(-50*(Game.room*Game.room)/4);
								Game.dood.knockBack();
							}
						}
					}
				}
				//else //if(!press && hitting)
				//hitting = false;
				//System.out.println(hitting);
				hitCounter++;

				if(lPress)
				{
					if(hitCount < hitFrames[hitType]-1)
					{
						if(hitCounter % 3 == 0)
							hitCount++;


					}
					else
					{
						counter--;
						hitting = false;
						if(counter == 0)
						{
							hitCount = 3;
							counter = 25;
							hitting = false;
							pressToggle = true;
							//bob=5;
							lPress = false;

							hitType = new Random().nextInt(3);

							hitCounter = 0;
						}
					}
				}
			}
			else
			{
				bool = true;
				derp++;

				if(derp == 1002)
					derp = 0;

				if(derp%3==0)
					yVel = yVel + a;
				yP += yVel;

				//action = "fall";
				//inAir = true;
				for(Tile b : Game.blocks)
					//if(bool2)inAir = true;
					if(b.collides(xP - (int)(w/2), (int)(yP+yVel - (int)(h/2)), w, h))
					{
						bool = false;
						if(b.collides(xP - (int)(w/2), (int)(yP+yVel + (int)(h/2)), w, 0))
						{
							setY(b.y-(int)(h/2));
							inAir = false;
						}
						//bool2 = false;
						setVel(0);
					}

				if(bool)
				{
					yP += (int)yVel;
				}
				else
					Game.setJump();

				action = "fall";

				if(invTime < 85)
					invTime++;
				else if(invTime >= 85)
				{
					action = "stand";
					invTime = 0;
				}
			}

			eCntr++;

			if(eCntr%5==0 && energy <= 1999)
				energy++;



			turns++;
		}
	}

	void setSpeed(int spd)
	{
		if(spd == 10)
			speed = (int)(w/150);
		else
			spd = (int)(w/300);
	}

	void setStuff(int x, int y)
	{
		xM = x;
		yM = y;
	}

	int getH(){ return health; }
	int getMH(){ return maxH; }

	Image getArrImg()
	{
		if(!hitting)
		{
			if(dir.equals("left"))
				return runLeft[indx];
			else if(dir.equals("right"))
				return runRight[indx];
		}
		else
		{

			if(hitType == -1)
			{
				System.out.println("lulz");
			}
			else if(hitType == 0)
			{
				if(dir.equals("left"))
					return hitLeft_0[(int)(hitCount/3)];
				else if(dir.equals("right"))
					return hitRight_0[(int)(hitCount/3)];
			}
			else if(hitType == 1)
			{
				if(dir.equals("left"))
					return hitLeft_1[(int)(hitCount/3)];
				else if(dir.equals("right"))
					return hitRight_1[(int)(hitCount/3)];
			}
			else if(hitType == 2)
			{
				if(dir.equals("left"))
					return hitLeft_2[(int)(hitCount/3)];
				else if(dir.equals("right"))
					return hitRight_2[(int)(hitCount/3)];
			}
		}


		return null;
	}
	int boss = 0;

	public void draw(Graphics2D g, boolean lPress, Game game)
	{
		/*
		if(press)
		{
			hitting = true;
			hitCount = 3;
			hitCounter = 0;
		}

		if(hitCount < 12)
		{
			action = "hit";
			hitting = true;
		}
		else
		{
			//hitting = false;
			hitCount = 0;
			hitCounter = 0;
		}
		 */

		if(health > 0)
		{
			if(action.equals("fall"))
			{
				g.drawImage(getImg(), xP-(int)(w/2), yP-(int)(h/2), game);
			}
			else
			{
				if(!hitting)
				{
					if(!action.equals("run"))// || inAir)
					{
						g.drawImage(getImg(), xP-(int)(w/2), yP-(int)(h/2), game);
					}
					else
					{
						g.drawImage(getArrImg(), xP-(int)(w/2), yP-(int)(h/2), game);

						if(!inAir || indxbla % 3 == 0)
						{
							if(lulz % (int)(30/speed) == 0)
								indx++;

							lulz++;

							if(indx>=6)
								indx = 1;
						}

						indxbla++;
					}
				}
				else
				{
					g.drawImage(getArrImg(), xP-(int)(w/2), yP-(int)(h/2), game);

				}
			}
		}		
		

		g.setColor(new Color( 255 * (maxH-health)/maxH  , 255*health/maxH , 0) );
		g.fillRect( xP-(int)(w/2) , yP-(int)(h/2)-25 , w*health/maxH , 15 );

		//t.setColor(new Color(  (int)(255 * (dude.getMH() - dude.getH())/dude.getMH())  ,  (int)(255 * dude.getH() / dude.getMH()) , 0));
		//t.fillRect(250, 140, (int)(400*dude.getH()/dude.getMH()), 15);

	}


	public void setHealth(int num)
	{
		if(!(invTime > 0 && invTime < 90))
		{
			health+=num;
			if(health > maxH)
				health = maxH;
			else if(health < 0)
				health = 0;
		}
	}

}