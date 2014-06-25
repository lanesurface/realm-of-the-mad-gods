package main.game.entities;

import main.game.level.level;
import main.game.level.tiles.tile;

public abstract class mob extends entity	{
	protected String name;
	protected int speed;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	
	public mob(level level,String name,int x,int y,int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	public void move(int xa,int ya)	{
		if (xa != 0 && ya != 0)	{
			move(xa,0);
			move(0,ya);
			numSteps --;
			return;
		}
		numSteps++;
		//collision detection
		if (!hasCollided(xa,ya))	{
			//up
			if (ya < 0) movingDir = 0;
			//down
			if (ya > 0) movingDir = 1;
			//left (i think)
			if (xa < 0) movingDir = 2;
			//right (i think)
			if (xa > 0) movingDir = 3;
			
			x += xa * speed;
			y += ya * speed;
		}
	}
	public abstract boolean hasCollided(int xa,int ya);
	protected boolean isSolidTile(int xa,int ya,int x,int y)	{
		if (level == null) return false;
		tile lastTile = level.getTile((this.x + x) >> 3,(this.y + y) >> 3);
		tile newTile = level.getTile((this.x + x + xa) >> 3,(this.y + y + ya) >> 3);
		if (!lastTile.equals(newTile) && newTile.isSolid())	{
			return true;
		}
		return false;
	}
	public String getName()	{
		return name;
	}
}
