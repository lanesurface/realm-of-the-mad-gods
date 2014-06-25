package main.game.entities;

import java.net.InetAddress;
import main.game.input_handler;
import main.game.level.level;

public class player_mp extends player {
	public InetAddress ipAddress;
	public int port;
	
	public player_mp(level level,int x,int y,input_handler input,String username,InetAddress ipAddress,int port) {
		super(level,x,y,input,username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	public player_mp(level level,int x,int y,String username,InetAddress ipAddress,int port) {
		super(level,x,y,null,username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	@Override
	public void tick()	{
		super.tick();
	}
}
