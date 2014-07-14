package com.cider3D.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class ScreenCapture extends Thread {
	//all class variables
	private String imageType;
	private File imageFile;
	private int width;
	private int height;
	private int bpp;
	private ByteBuffer buffer;
	
	public ScreenCapture(String imageType,File imageFile)	{
		this.imageType = imageType;
		this.imageFile = imageFile;
		
		GL11.glReadBuffer(GL11.GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0,0,width,height,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE,buffer);
	}
	//start a new thread for saving image data
	public void start()	{
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < width; x++)	{ //if we are still within the bounds of our width, continue and add one to x
			for(int y = 0; y < height; y++)	{ //if we are still within the bounds of our height, continue and add one to y
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}
		try {ImageIO.write(image, imageType, imageFile);}
		catch (IOException e) {e.printStackTrace();}
	}
}
