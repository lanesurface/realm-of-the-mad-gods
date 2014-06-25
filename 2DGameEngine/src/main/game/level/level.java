package main.game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import main.game.entities.entity;
import main.game.gfx.screen;
import main.game.level.tiles.tile;

public class level {
	private byte[] tiles;
	public int width;
	public int height;
	public List<entity> entities = new ArrayList<entity>();
	private String imagePath;
	private BufferedImage image;
	
	public level(String imagePath)	{
		if (imagePath != null)	{
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		}
		else	{
			this.width = 64;
			this.height = 64;
			tiles = new byte[width * height];
			this.generateLevel();
		}
	}
	private void loadLevelFromFile()	{
		try	{
			this.image = ImageIO.read(level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte [width * height];
			this.loadTiles();
		}
		catch (IOException e)	{
			e.printStackTrace();
		}
	}
	private void loadTiles()	{
		int [] tileColors = this.image.getRGB(0,0,width,height,null,0,width);
		for (int y = 0; y < height; y++)	{
			for (int x = 0; x < width; x++)	{
				tileCheck: for (tile t : tile.tiles)	{
					if (t != null && t.getLevelColor() == tileColors[x + y * width])	{
						this.tiles[x + y * width] = t.getID();
						break tileCheck;
					}
				}
			}
		}
	}
	private void saveLevelToFile()	{
		try	{
			ImageIO.write(image,"png",new File(level.class.getResource(this.imagePath).getFile()));
		}
		catch (IOException e)	{
			e.printStackTrace();
		}
	}
	public void alterTile(int x,int y,tile newTile)	{
		this.tiles[x + y * width] = newTile.getID();
		image.setRGB(x, y, newTile.getLevelColor());
	}
	public void generateLevel()	{
		for (int y = 0; y < height; y++)	{
			for (int x = 0; x < width; x++)	{
				if (x * y%10 < 7)	{
					tiles [x + y * width] = tile.GRASS.getID();
				}
				else	{
					tiles [x + y * width] = tile.STONE.getID();
				}
			}
		}
	}
	public void tick()	{
		for (entity e : entities)	{
			e.tick();
		}
		for (tile t  : tile.tiles) {
			if (t == null) break;
			t.tick();
		}
	}
	public void renderTiles(screen screen,int xOffset,int yOffset)	{
		if (xOffset < 0) xOffset = 0;
		if (xOffset > ((width << 3) - screen.width)) xOffset = ((width << 3) - screen.width);
		if (yOffset < 0) yOffset = 0;
		if (yOffset > ((height << 3) - screen.height)) yOffset = ((height << 3) - screen.height);
		
		screen.setOffset(xOffset,yOffset);
		
		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++)	{
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++)	{
				getTile(x,y).render(screen,this,x << 3,y << 3);
			}
		}
	}
	public void renderEntities(screen screen)	{
		for (entity e : entities)	{
			e.render(screen);
		}
	}
	public tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height) return tile.VOID;
		return tile.tiles[tiles[x + y * width]];
	}
	public void addEntity(entity entity)	{
		this.entities.add(entity);
	}
}