package com.cider3D.engine.gfx;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class DisplayUtils {
	public static void setDisplayMode(int width,int height,boolean fullscreen)	{
		//return if the requested display mode is already set
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen))	{
			return;
		}
		try	{
			DisplayMode targetDisplayMode = null;
			
			if (fullscreen)	{
				DisplayMode [] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				
				for (int i=0;i<modes.length;i++)	{
					DisplayMode current = modes[i];
					
					if ((current.getWidth() == width) && (current.getHeight() == height))	{
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq))	{
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))	{
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()))	{
							targetDisplayMode = current;
							break;
						}
					}
				}
			}
			else	{
				targetDisplayMode = new DisplayMode(width,height);
			}
			if (targetDisplayMode == null)	{
				System.out.println("Failed to find value mode: "+width+"x"+height+"fs="+fullscreen);
				return;
			}
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
		}
		catch (LWJGLException e)	{
			System.out.println("Unable to setup mode"+width+"x"+height+"fullscreen="+fullscreen+e);
		}
	}
}
