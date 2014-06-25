package main.game.entities;

import main.game.gfx.screen;
import main.game.level.level;

public abstract class entity {
	public int x,y;
	protected level level;
	
	public entity(level level)	{
		init(level);
	}
	public final void init(level level)	{
		this.level = level;
	}
	public abstract void tick();
	public abstract void render(screen screen);
}
