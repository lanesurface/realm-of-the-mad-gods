package main.game.level.tiles;

public class base_solid_tile extends base_tile	{
	public base_solid_tile(int id, int y, int x, int tileColor,int levelColor) {
		super(id, y, x, tileColor,levelColor);
		this.solid = true;
	}
}