package com.cider3D.engine;

import java.io.File;
import java.io.IOException;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final String TITLE = "Cider3D Game Engine";
	
	public static int xPos = WIDTH / 2;
	public static int yPos = HEIGHT / 2;
	public static int zPos = 0;
	
	long lastFrame;
	int fps;
	long lastFPS;
	public static boolean vSync;
	
	//rotation vars
	private float rotate;
	
	//textures
	private Texture texture;
	
	public void start()	{
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.setTitle(TITLE);
			Display.create();
		}
		catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		initGL();
		init();
		getDelta();
		lastFPS = getTime();
		
		while (!Display.isCloseRequested())	{
			int delta = getDelta();
			
			update(delta);
			renderGL();
			
			Display.update();
			Display.sync(60); //cap fps
		}
		Display.destroy();
	}
	public void update(int delta)	{
		InputHandler.handle(delta);
		
		rotate += 0.5f;
		
		//collision detection (usually done in mob entity)
		if (xPos < 0) xPos = 0;
		if (xPos > WIDTH) xPos = WIDTH;
		if (yPos < 0) yPos = 0;
		if (yPos > WIDTH / 12 * 9) yPos = WIDTH / 12 * 9;
		
		updateFPS();
	}
	public int getDelta()	{
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		
		return delta;
	}
	public long getTime()	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	public void updateFPS()	{
		if (getTime() - lastFPS > 1000)	{
			Display.setTitle("FPS: "+fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	public void initGL()	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//hide cursor
		Cursor emptyCursor;
		try {
			emptyCursor = new Cursor(1,1,0,0,1,BufferUtils.createIntBuffer(1),null);
			Mouse.setNativeCursor(emptyCursor);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	//initialize resources
	public void init()	{
		try {
			texture = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("C:/Users/Lane/Documents/Workspace/MineForge/res/Images/grass.png"));
			
			System.out.println("Texture loaded: ["+texture+"]");
			System.out.println(">> Image width: "+texture.getImageWidth());
			System.out.println(">> Image height: "+texture.getImageHeight());
			System.out.println(">> Texture width: "+texture.getTextureWidth());
			System.out.println(">> Texture height: "+texture.getTextureHeight());
			System.out.println(">> Texture ID: "+texture.getTextureID());
		}
		catch (IOException e) {e.printStackTrace();}
	}
	public void renderGL()	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		Color.cyan.bind();
		texture.bind();
		
//		GL11.glLoadIdentity();
//		GL11.glTranslatef(xPos,yPos,0);
//		GL11.glRotatef(rotate,0.0f,0.0f,0.5f);
//		GL11.glTranslatef(-xPos,-yPos,0);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(100,100);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(100+texture.getTextureWidth(),100);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(100+texture.getTextureWidth(),100+texture.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(100,100+texture.getTextureHeight());
		GL11.glEnd();
	}
	public static void main(String [] args)	{
		Game game = new Game();
		game.start();
	}
}