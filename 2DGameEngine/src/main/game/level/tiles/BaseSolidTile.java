package main.game.level.tiles;

public class BaseSolidTile extends BaseTile	{
	public BaseSolidTile(int id, int y, int x, int tileColor,int levelColor) {
		super(id, y, x, tileColor,levelColor);
		this.solid = true;
	}
}