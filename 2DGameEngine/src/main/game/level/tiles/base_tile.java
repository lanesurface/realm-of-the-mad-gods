package main.game.level.tiles;

import main.game.gfx.screen;
import main.game.level.level;

public class base_tile extends tile {
	protected int tileID;
	protected int tileColor;
	
	public base_tile(int id,int y,int x,int tileColor,int levelColor) {
		super(id, false, false,levelColor);
		this.tileID = x + y;
		this.tileColor = tileColor;
	}
	public void tick()	{
		
	}
	public void render(screen screen, level level, int x, int y) {
		screen.render(x, y, tileID, tileColor,0x00,1);
	}
}
