package main.game.level.tiles;

import java.awt.Color;
import main.game.gfx.colors;
import main.game.gfx.screen;
import main.game.level.level;

public abstract class tile {
	public static final tile [] tiles = new tile[256];
	public static final tile VOID = new base_solid_tile(0,0,0,colors.get(545,-1,-1,-1),0xff000000);
	public static final tile GRASS = new base_tile(2,2,0,colors.get(-1,131,141,-1),0xff00ff00);
	public static final tile STONE = new base_solid_tile(1,1,0,colors.get(-1,333,-1,-1),0xff555555);
	public static final tile WATER = new animated_tile(3,new int [][] {{0,5},{1,5},{2,5},{1,5}},colors.get(-1,004,115,-1),0xff0000ff,500);
	
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColor;
	
	public tile(int id,boolean isSolid,boolean isEmitter,int levelColor)	{
		this.id = (byte) id;
		if (tiles[id] != null) throw new RuntimeException("Duplicate Tile ID on "+id+"!");
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColor = levelColor;
		tiles[id] = this;
	}
	public byte getID()	{
		return id;
	}
	public boolean isSolid()	{
		return solid;
	}
	public boolean isEmitter()	{
		return emitter;
	}
	public int getLevelColor()	{
		return levelColor;
	}
	public abstract void tick();
	public abstract void render(screen screen, level level, int x, int y);
}