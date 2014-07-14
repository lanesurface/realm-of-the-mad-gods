package com.cider3D.engine;

import java.io.File;
import javax.swing.JOptionPane;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import com.cider3D.engine.gfx.DisplayUtils;
import com.cider3D.engine.gfx.ScreenCapture;

public class InputHandler {
	//make mouse coordinates available to all classes
	public static int mouseX;
	public static int mouseY;
	
	public static void handle(int delta)	{
		//left mouse button
		if (Mouse.isButtonDown(0))	{
			int mouseX = Mouse.getX();
			int mouseY = Mouse.getY();
			
			System.out.println("MOUSE DOWN at X: " + mouseX + " Y: " + mouseY);
		}
		//right mouse button
		if (Mouse.isButtonDown(1))	{
			System.out.println("Right Mouse Button Handled!");
		}
		//space
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))	{
			System.out.println("Space Handled!");
		}
		//w,s,a,d and up,down,left,right key handling
		while (Keyboard.next())	{
			if (Keyboard.getEventKeyState())	{
				//handle presses
				if (Keyboard.getEventKey() == Keyboard.KEY_W || Keyboard.getEventKey() == Keyboard.KEY_UP) Game.yPos+=0.35f * delta;
				if (Keyboard.getEventKey() == Keyboard.KEY_A || Keyboard.getEventKey() == Keyboard.KEY_LEFT) Game.xPos-=0.35f * delta;
				if (Keyboard.getEventKey() == Keyboard.KEY_S || Keyboard.getEventKey() == Keyboard.KEY_DOWN) Game.yPos-=0.35f * delta;
				if (Keyboard.getEventKey() == Keyboard.KEY_D || Keyboard.getEventKey() == Keyboard.KEY_RIGHT) Game.xPos+=0.35f * delta;
				
				//f11 (fullscreen) handling
				if (Keyboard.getEventKey() == Keyboard.KEY_F11)	{
					DisplayUtils.setDisplayMode(800,600,!Display.isFullscreen());
				}
				//f1 (vSync) handling for tear-free graphics
				if (Keyboard.getEventKey() == Keyboard.KEY_F1)	{
					Game.vSync = !Game.vSync;
					Display.setVSyncEnabled(Game.vSync);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F2)	{
					ScreenCapture capture;
					capture = new ScreenCapture("JPG",new File("/ScreenShots/test.jpg"));
					capture.start();
				}
			}
			//handle releases for control keys (w,s,a,d and up,down,left,right)
			else	{
				if (Keyboard.getEventKey() == Keyboard.KEY_W || Keyboard.getEventKey() == Keyboard.KEY_UP) /*handle w and up*/;
				if (Keyboard.getEventKey() == Keyboard.KEY_S || Keyboard.getEventKey() == Keyboard.KEY_DOWN) /*handle s and down*/;
				if (Keyboard.getEventKey() == Keyboard.KEY_A || Keyboard.getEventKey() == Keyboard.KEY_LEFT) /*handle a and left*/;
				if (Keyboard.getEventKey() == Keyboard.KEY_D || Keyboard.getEventKey() == Keyboard.KEY_RIGHT) /*handle d and right*/;
			}
		}
	}
}