import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player
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
	int indx, deathIndx = 0, deathBuffr = 0, lulz = 0, derp = 0, indxbla = 0, hitCount = 3, hitCounter = 0, counter = 25, speed = 5, hitType = new Random().nextInt(4), counterHit = 0;
	//Image speart;
	Image[] deathLeft, deathRight, runLeft, runRight, hitLeft_0, hitRight_0, hitRight_1, hitLeft_1, hitLeft_2, hitRight_2, hitLeft_3, hitRight_3, fallLeft, fallRight;
	int[] hitFrames = new int[]{ 12 , 9 , 12 , 21};
	BufferedImage spear;
	boolean inAir, hitting = false, moving = false, lPress = false, pressToggle = false;

	public Player()
	{
		xP = yP = 500;
		iT = 0;
		maxH = 10000;
		health = maxH;
		f = false;
		action = "stand";
		dir = "right";
		indx = 0;
		maxEn = 2000;
		energy = 2000;
		eCntr=0;
	}

	boolean isNotInAir() { return !inAir; }

	void setImage(int width, int height)
	{
		w = (int)(width);
		h = height * 2;
		makeImgs("stand");
		makeImgs("jump");
		//makeImgs("fall");
		makeImgs("hit");//System.out.println("wsoihgfvoishgvoisgvjsiogjvm");

		//speart = (((Toolkit.getDefaultToolkit().getImage("Sprites/Player/spear.png").getScaledInstance(width, (int)(height/4), Image.SCALE_SMOOTH))));

		runLeft = new Image[6];
		runRight = new Image[6];

		for(int i = 0; i < 6; i++)
		{
			runLeft[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/run_left_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);
			runRight[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/run_right_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);
		}

		hitLeft_0 = new Image[4];
		hitRight_0 = new Image[4];

		for(int i = 0; i < 4; i++)
		{
			hitLeft_0[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/hit_left_0_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitLeft_0[i]);
			hitRight_0[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/hit_right_0_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitRight_0[i]);
		}

		hitLeft_1 = new Image[3];
		hitRight_1 = new Image[3];

		for(int i = 0; i < 3; i++)
		{
			hitLeft_1[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/hit_left_1_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);
			hitRight_1[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/hit_right_1_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);
		}

		hitLeft_2 = new Image[4];
		hitRight_2 = new Image[4];

		for(int i = 0; i < 4; i++)
		{
			hitLeft_2[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/hit_left_2_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitLeft_0[i]);
			hitRight_2[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/hit_right_2_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitRight_0[i]);
		}

		hitLeft_3 = new Image[7];
		hitRight_3 = new Image[7];

		for(int i = 0; i < 7; i++)
		{
			hitLeft_3[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/hit_left_3_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitLeft_0[i]);
			hitRight_3[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/hit_right_3_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitRight_0[i]);
		}

		deathLeft = new Image[5];
		deathRight = new Image[5];

		for(int i = 0; i < 5; i++)
		{
			deathLeft[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/dead_left_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitLeft_0[i]);
			deathRight[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/dead_right_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitRight_0[i]);
		}

		fallLeft = new Image[8];
		fallRight = new Image[8];

		for(int i = 0; i < 8; i++)
		{
			fallLeft[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/fall_left_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitLeft_0[i]);
			fallRight[i] = Toolkit.getDefaultToolkit().getImage("Sprites/Player/fall_right_" +(i+1) +".png").getScaledInstance(w,h, Image.SCALE_SMOOTH);//System.out.println(hitRight_0[i]);
		}
	}

	void makeImgs(String s)
	{
		temp = Toolkit.getDefaultToolkit().getImage("Sprites/Player/" +s +"_right.png");
		image.add(temp.getScaledInstance(w,h, Image.SCALE_SMOOTH));
		url.add(s+"_right");
		temp = Toolkit.getDefaultToolkit().getImage("Sprites/Player/" +s +"_left.png");
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
		/*
		energy += en;

		if(en<0)
			en = 0;

		if(en>maxEn)
			en = maxEn;
		 */
		energy = maxEn;
	}
	int i = 0;
	int invTime = 0;

	public void knockBack()
	{


		if(invTime == 0 && new Random().nextInt(40) == 0)
		{
			setHealth(-200);
			invTime = 1;
			try
			{
				URL url = this.getClass().getClassLoader().getResource("Sounds/ouch.wav");
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(url));
				clip.start();
			}
			catch(Exception e)
			{
				System.out.println("Dood says, no sound found/can be played.");
			}
		}

	}

	public void move(boolean up, boolean left, boolean right, boolean press)
	{

		boolean bool;

		if(health > 0)
		{
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
						System.out.println("Dood says, no sound found/can be played.");
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
						System.out.println("Dood says, no sound found/can be played.");
					}

					//counterHit = hitFrames[hitType];
				}


				if(up)
				{
					time = 0;
					yVel = -(int)(h/11);

					if(yVel == 0)
						yVel = -(int)(h/11);

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
						Game.setJump();
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

						for(Monster m : Game.mon.get(Game.room))
							if(Math.sqrt(Math.pow(m.xP-xP,2) + Math.pow(m.yP-yP,2)) < 100) // Math.sqrt((x - xInit) ^2 + (y - yInit) ^2)
							{
								if((dir.equals("left") &&m.xP < xP) || (dir.equals("right") && m.xP > xP))
								{
									m.setHealth(-200);
									m.knockBack();
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

							if(!moving && !inAir)
							{
								int gobble = new Random().nextInt(3), type = -1;

								if(gobble == 0)
									type = 0;
								else if(gobble == 1)
									type = 2;
								else if(gobble == 2)
									type = 3;

								hitType = type;
							}
							else if(!moving && inAir)
							{
								hitType = new Random().nextInt(3)+1;
							}
							else if(moving && !inAir)
							{
								hitType = new Random().nextInt(2);
							}
							else if(moving && inAir)
							{
								hitType = new Random().nextInt(2)+1;
							}


							//hitType = new Random().nextInt(4);

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

		}
		//System.out.println(counter +"   " +i);
		i++;
	}

	void setSpeed(int spd)
	{
		speed = spd;
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
			else if(hitType == 3)
			{
				if(dir.equals("left"))
					return hitLeft_3[(int)(hitCount/3)];
				else if(dir.equals("right"))
					return hitRight_3[(int)(hitCount/3)];
			}
		}


		return null;
	}
	int boss = 0;


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

	public void draw(Graphics2D g, Game game)
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
		//int bob = -1;


		if(health > 0)
		{
			if(action.equals("fall") && invTime > 1)
			{
				if(invTime >= 1 && invTime < 5)
				{
					if(dir.equals("right"))
						g.drawImage(fallRight[0], xP-(int)(w/2), yP-(int)(h/2), game);
					else
						g.drawImage(fallLeft[0], xP-(int)(w/2), yP-(int)(h/2), game);
				}
				else if(invTime >= 5 && invTime < 10)
				{
					if(dir.equals("right"))
						g.drawImage(fallRight[1], xP-(int)(w/2), yP-(int)(h/2), game);
					else
						g.drawImage(fallLeft[1], xP-(int)(w/2), yP-(int)(h/2), game);
				}
				else if(invTime >= 10 && invTime < 15)
				{
					if(dir.equals("right"))
						g.drawImage(fallRight[2], xP-(int)(w/2), yP-(int)(h/2), game);
					else
						g.drawImage(fallLeft[2], xP-(int)(w/2), yP-(int)(h/2), game);
				}
				else if(invTime >= 15 && invTime < 20)
				{
					if(dir.equals("right"))
						g.drawImage(fallRight[3], xP-(int)(w/2), yP-(int)(h/2), game);
					else
						g.drawImage(fallLeft[3], xP-(int)(w/2), yP-(int)(h/2), game);
				}
				else if(invTime >= 20 && invTime < 70)
				{
					if(dir.equals("right"))
						g.drawImage(fallRight[4], xP-(int)(w/2), yP-(int)(h/2), game);
					else
						g.drawImage(fallLeft[4], xP-(int)(w/2), yP-(int)(h/2), game);
				}
				else if(invTime >= 70 && invTime < 75)
				{
					if(dir.equals("right"))
						g.drawImage(fallRight[5], xP-(int)(w/2), yP-(int)(h/2), game);
					else
						g.drawImage(fallLeft[5], xP-(int)(w/2), yP-(int)(h/2), game);
				}
				else if(invTime >= 75 && invTime < 80)
				{
					if(dir.equals("right"))
						g.drawImage(fallRight[6], xP-(int)(w/2), yP-(int)(h/2), game);
					else
						g.drawImage(fallLeft[6], xP-(int)(w/2), yP-(int)(h/2), game);
				}
				else if(invTime >= 80 && invTime < 85)
				{
					if(dir.equals("right"))
						g.drawImage(fallRight[7], xP-(int)(w/2), yP-(int)(h/2), game);
					else
						g.drawImage(fallLeft[7], xP-(int)(w/2), yP-(int)(h/2), game);
				}
			}
			else
			{
				if(!hitting)
				{
					if(!action.equals("run") && !action.equals("fall"))// || inAir)
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
		else
		{


			if(deathBuffr < 90000)
			{
				if(dir.equals("left"))
					g.drawImage( deathLeft[deathIndx], xP-(int)(w/2), yP-(int)(h/2), game);
				else if(dir.equals("right"))
					g.drawImage( deathRight[deathIndx], xP-(int)(w/2), yP-(int)(h/2), game);
				//System.out.println(deathLeft.length);
				if(deathBuffr % 15 == 0 && deathIndx < 4)
				{
					deathIndx++;
				}

				if(Game.transperency < 255 && deathBuffr%2 == 0) Game.transperency++;

				deathBuffr++;
			}

		}

		//if(hitting)

		//boss++;
		//System.out.println(boss +"  buss");
		//System.out.println(bob);
	}

}