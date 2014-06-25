package main.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import main.game.entities.player;
import main.game.gfx.screen;
import main.game.gfx.sprite_sheet;
import main.game.level.level;
import main.game.net.game_client;
import main.game.net.game_server;
import main.game.net.packets.Packet00Login;
import main.game.sfx.audio_player;

public class game extends Canvas implements Runnable	{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Zelda: Realm of the Mad Gods (Pre-Alpha)";
	
	private JFrame frame;
	
	public boolean running = false;
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private int [] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private int colors_array [] = new int [6*6*6];
	
	private screen screen;
	public input_handler input;
	
	//background music
	private audio_player bgMusic;
	
	//declare new level
	public level level;
	public player player;
	
	private game_client socketClient;
	private game_server socketServer;
	
	public game()	{
		setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this,BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public void init()	{
		int index = 0;
		//set default system look and feel
		 try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		for (int r = 0; r < 6; r++)	{
			for (int g = 0; g < 6; g++)	{
				for (int b = 0; b < 6; b++)	{
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					
					colors_array[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
		screen = new screen(WIDTH,HEIGHT,new sprite_sheet("/sprite_sheet.png"));
		input = new input_handler(this);
		
		//bgmusic init
		bgMusic = new audio_player("/Music/overworld.mp3");
		bgMusic.playAudio();
		//load level
		level = new level("/levels/water_test_level_2.png");
		/*player = new player(level,0,0,input,JOptionPane.showInputDialog(this,"Please enter a username"));
		level.addEntity(player);
		
		socketClient.sendData("ping".getBytes());*/
		Packet00Login loginPacket = new Packet00Login(JOptionPane.showInputDialog(this,"Please enter a username"));
		loginPacket.writeData(socketClient);
	}
	public synchronized void start() {
		running = true;
		new Thread(this).start();
		
		if (JOptionPane.showConfirmDialog(this,"Do you want to run a server?") == 0)	{
			socketServer = new game_server(this);
			socketServer.start();
		}
		socketClient = new game_client(this,"localhost");
		socketClient.start();
	}
	public synchronized void stop()	{
		running = false;
	}
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int frames = 0;
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while (running)	{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1)	{
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender)	{
				frames++;
				render();
			}
				
			if (System.currentTimeMillis() - lastTimer >= 1000)	{
				lastTimer += 1000;
				//System.out.println("Frames: "+frames+", Ticks: "+ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}
	public void tick()	{
		tickCount++;
		/*for (int i = 0; i < pixels.length; i++)	{
			pixels[i] = i + tickCount;
		}*/
		level.tick();
	}
	public void render()	{
		BufferStrategy  bs = getBufferStrategy();
		if (bs == null)	{
			createBufferStrategy(3);
			return;
		}
		int xOffset = player.x-(screen.width/2);
		int yOffset = player.y-(screen.height/2);
		
		level.renderTiles(screen, xOffset, yOffset);
		
		level.renderEntities(screen);
		for (int y = 0; y < screen.height; y++)	{
			for (int x = 0; x < screen.width; x++)	{
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors_array[colorCode];
			}
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	public static void main(String[] args)	{
		new game().start();
	}
}
