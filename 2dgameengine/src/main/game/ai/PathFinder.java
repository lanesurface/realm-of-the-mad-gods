package main.game.ai;

public class PathFinder	{
	/**
	 * using the a* algorithm for calculations
	 * f = g + h
	 * g = the movement cost to move from the starting point A to a given square on the grid, following the path generated to get there
	 * h = the estimated movement cost to move from that given square on the grid to the final destination, point B
	 */
	public static int xPos;
	public static int yPos;
	
	public int targetPosX;
	public int targetPosY;
	
	public PathFinder(int xPos, int yPos, int targetPosX, int targetPosY)	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.targetPosX = targetPosX;
		this.targetPosY = targetPosY;
	}
}