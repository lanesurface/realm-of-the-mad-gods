package main.game.level.tiles;

import main.game.gfx.Colors;
import main.game.gfx.Screen;
import main.game.level.Level;

public abstract class Tile {
	public static final Tile [] tiles = new Tile[256];
	public static final Tile VOID = new BaseSolidTile(0,0,0,Colors.get(000,-1,-1,-1),0xff000000);
	public static final Tile STONE = new BaseSolidTile(1,1,0,Colors.get(-1,333,-1,-1),0xff555555);
	public static final Tile GRASS = new BaseTile(2,2,0,Colors.get(-1,131,141,-1),0xff00ff00);
	public static final Tile WATER = new AnimatedTile(3,new int [][] {{0,5},{1,5},{2,5},{1,5}},Colors.get(-1,004,115,-1),0xff0000ff,500);
	public static final Tile SAND = new BaseTile(4,3,0,Colors.get(-1,553,552,-1),0xffcaf438);
	public static final Tile WOOD = new BaseSolidTile(5,4,0,Colors.get(-1,431,541,-1),0xffa0823a);
	public static final Tile POKADOT_CARPET = new BaseTile(6,5,0,Colors.get(-1,333,334,-1),0xffaaaaaa);
	public static final Tile LAVA = new AnimatedTile(7,new int [][] {{3,5},{4,5},{5,5}},Colors.get(-1,501,402,-1),0xffc62b2b,500);
	
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColor;
	
	public Tile(int id,boolean isSolid,boolean isEmitter,int levelColor)	{
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
	public abstract void render(Screen screen,Level level,int x,int y);
}