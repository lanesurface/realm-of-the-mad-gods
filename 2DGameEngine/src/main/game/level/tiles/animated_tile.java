package main.game.level.tiles;

import main.game.gfx.screen;
import main.game.level.level;

public class animated_tile extends base_tile {
	private int [] [] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private int animationSwitchDelay;
	
	public animated_tile(int id,int [] [] animationCoords,int tileColor,int levelColor,int animationSwitchDelay) {
		super(id,animationCoords [0] [0],animationCoords [0] [1],tileColor,levelColor);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}
	public void tick()	{
		if ((System.currentTimeMillis() - lastIterationTime)>= (animationSwitchDelay))	{
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
			tileID = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
		}
	}
}
