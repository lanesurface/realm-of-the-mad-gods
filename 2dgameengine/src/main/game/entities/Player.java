package main.game.entities;

import java.awt.Color;

import main.game.Game;
import main.game.InputHandler;
import main.game.gfx.Colors;
import main.game.gfx.Font;
import main.game.gfx.Screen;
import main.game.level.Level;

public class Player extends Mob	{
	private InputHandler input;
	private int color = Colors.get(-1,142,420,543);
	private int scale = 1;
	protected boolean isSwimming  = false;
	protected boolean isLava = false;
	private int tickCount = 0;
	private String username;
	//health
	private int health = 400;
	private int hearts = 6;
	private long totalHealth = health * hearts;
	
	public Player(Level level,int x,int y,InputHandler input,String username) {
		super(level,"player",x,y,1);
		this.input = input;
		this.username = username;
	}
	public void tick() {
		int xa = 0;
		int ya = 0;
		if (input != null)	{
			if (input.up.isPressed())	ya--;
			if (input.down.isPressed())	ya++;
			if (input.left.isPressed())	xa--;
			if (input.right.isPressed())xa++;
		}
		if (xa != 0 || ya != 0)	{
			move(xa,ya);
			isMoving = true;
		}
		else	{
			isMoving = false;
		}
		if (hearts == 0) {
			//if player has no username, return anonymous in place
			if (getUsername().isEmpty())	{
				System.out.println("Player: [Anonymous] died!");
			}
			//otherwise, return the username that the player game
			else	{
				System.out.println("Player: ["+getUsername()+"] died!");
			}
			//TODO: Handle players death
			System.exit(0);
		}
		if (level.getTile(this.x >> 3,this.y >> 3).getID() == 3) isSwimming = true;
		if (level.getTile(this.x >> 3,this.y >> 3).getID() != 3) isSwimming = false;
		if (level.getTile(this.x >> 3,this.y >> 3).getID() == 7) isLava = true;
		if (level.getTile(this.x >> 3,this.y >> 3).getID() != 7) isLava = false;
		tickCount++;
	}
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 28;
		int walkingSpeed = 4; //default 4
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if (movingDir == 1)	xTile += 2;
		else if (movingDir > 1) {
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}
		
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 - 4;
		
		if (isSwimming)	{
			int waterColor = 0;
			yOffset += 4;
			if (tickCount % 60 < 15)	{
				waterColor = Colors.get(-1,-1,225,-1);
			}
			else if (15 <= tickCount % 60 && tickCount % 60 < 30)	{
				yOffset -= 1;
				waterColor = Colors.get(-1,225,115,-1);
			}
			else if (30 <= tickCount % 60 && tickCount % 60 < 45)	{
				waterColor = Colors.get(-1,115,-1,225);
			}
			else	{
				yOffset -= 1;
				waterColor = Colors.get(-1,225,115,-1);
			}
			screen.render(xOffset,yOffset + 3,0 + 27 * 32,waterColor,0x00,1);
			screen.render(xOffset + 8,yOffset + 3,0 + 27 * 32,waterColor,0x01,1);
		}
		if (isLava)	{
			int lavaColor = 0;
			yOffset += 4;
			//take away health
			totalHealth--;
			//TODO: Make a non-dependent health system
			if (totalHealth % health == 0) hearts--;
			
			if (tickCount % 60 < 15)	{
				lavaColor = Colors.get(-1,-1,512,-1);
			}
			else if (15 <= tickCount % 60 && tickCount % 60 < 30)	{
				yOffset -= 1;
				lavaColor = Colors.get(-1,401,512,-1);
			}
			else if (30 <= tickCount % 60 && tickCount % 60 < 45)	{
				lavaColor = Colors.get(-1,401,-1,500);
			}
			else	{
				yOffset -= 1;
				lavaColor = Colors.get(-1,401,512,-1);
			}
			screen.render(xOffset,yOffset + 3,0 + 27 * 32,lavaColor,0x00,1);
			screen.render(xOffset + 8,yOffset + 3,0 + 27 * 32,lavaColor,0x01,1);
		}
		screen.render(xOffset + (modifier * flipTop),yOffset,xTile + yTile * 32,color,flipTop,scale);
		screen.render(xOffset + modifier - (modifier * flipTop),yOffset,(xTile + 1) + yTile * 32,color,flipTop,scale);
		if (!isSwimming && !isLava)	{
			screen.render(xOffset + (modifier * flipBottom),yOffset + modifier,xTile + (yTile + 1) * 32,color,flipBottom,scale);
			screen.render(xOffset + modifier - (modifier * flipBottom),yOffset + modifier,(xTile + 1) + (yTile + 1) * 32,color,flipBottom,scale);
		}
		if (username != null)	{
			Font.render(username,screen,xOffset - (username.length() - 1) / 2 * 8,yOffset - 10,Colors.get(-1,-1,-1,555),1);
		}
		String heartString = "Hearts: ["+Integer.toString(hearts)+"]";
		Font.render(heartString, screen, xOffset - (heartString.length()) / 2 * 8, yOffset - 20, Colors.get(-1,-1,-1,555), 1);
	}
	public boolean hasCollided(int xa,int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		
		for (int x = xMin; x < xMax; x++)	{
			if (isSolidTile(xa,ya,x,yMin))	{
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++)	{
			if (isSolidTile(xa,ya,x,yMax))	{
				return true;
			}
		}
		for (int y = yMin; y < xMax; y++)	{
			if (isSolidTile(xa,ya,xMin,y))	{
				return true;
			}
		}
		for (int y = yMin; y < xMax; y++)	{
			if (isSolidTile(xa,ya,xMax,y))	{
				return true;
			}
		}
		return false;
	}
	public String getUsername()	{
		return this.username;
	}
}
