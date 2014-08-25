package main.game.level.tiles;

import main.game.gfx.Screen;
import main.game.level.Level;

public class BaseTile extends Tile {
	protected int tileID;
	protected int tileColor;
	
	public BaseTile(int id,int x,int y,int tileColor,int levelColor) {
		super(id, false, false,levelColor);
		this.tileID = x + y;
		this.tileColor = tileColor;
	}
	public void tick()	{
		//blank
	}
	public void render(Screen screen,Level level,int x,int y) {
		screen.render(x, y,tileID,tileColor,0x00,1);
	}
}
