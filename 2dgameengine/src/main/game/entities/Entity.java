package main.game.entities;

import main.game.gfx.Screen;
import main.game.level.Level;

public abstract class Entity {
	public static int x;
	public static int y;
	protected Level level;
	
	public Entity(Level level)	{
		init(level);
	}
	public final void init(Level level)	{
		this.level = level;
	}
	public abstract void tick();
	public abstract void render(Screen screen);
}
