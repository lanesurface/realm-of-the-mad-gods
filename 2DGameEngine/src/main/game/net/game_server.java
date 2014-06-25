package main.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import main.game.game;
import main.game.entities.player_mp;
import main.game.net.packets.Packet00Login;
import main.game.net.packets.packet;
import main.game.net.packets.packet.PacketTypes;

public class game_server extends Thread {
	private DatagramSocket socket;
	private game game;
	private List<player_mp> connectedPlayers = new ArrayList<player_mp>();
	
	public game_server(game game)	{
		this.game = game;
		try	{
			this.socket = new DatagramSocket(1331);
		}
		catch (SocketException e)	{
			e.printStackTrace();
		}
	}
	public void run()	{
		while (true)	{
			byte [] data = new byte [1024];
			DatagramPacket packet = new DatagramPacket(data,data.length);
			try {
				socket.receive(packet);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
			/*String message = new String(packet.getData());
			System.out.println("CLIENT ["+packet.getAddress().getHostAddress()+":"+packet.getPort()+"]> " + message);
			if (message.trim().equalsIgnoreCase("ping"))	{
				sendData("pong".getBytes(),packet.getAddress(),packet.getPort());
			}*/
		}
	}
	private void parsePacket(byte[] data,InetAddress address,int port) {
		String message = new String(data).trim();
		PacketTypes type = packet.lookupPacket(message.substring(0,2));
		switch (type)	{
		default:
		case INVALID:
			break;
		case LOGIN:
			Packet00Login packet = new Packet00Login(data);
			System.out.println("["+address.getHostAddress()+":"+port+"]"+packet.getUsername()+" has connected");
			player_mp player = null;
			if (address.getHostAddress().equalsIgnoreCase("127.0.0.1"))	{
				player = new player_mp(game.level,100,100,game.input,packet.getUsername(),address,port);
			}
			else	{
				player = new player_mp(game.level,100,100,packet.getUsername(),address,port);
			}
			if (player != null)	{
				this.connectedPlayers.add(player);
				game.level.addEntity(player);
				game.player = player;
			}
			break;
		case DISCONNECT:
			break;
		}
	}
	public void sendData(byte [] data,InetAddress ipAddress,int port)	{
		DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress,1331);
		try	{
			this.socket.send(packet);
		}
		catch (IOException e)	{
			e.printStackTrace();
		}
	}
	public void sendDataToAllClients(byte[] data) {
		for (player_mp p : connectedPlayers)	{
			sendData(data,p.ipAddress,p.port);
		}
	}
}