package main.game.net.packets;

import main.game.net.game_client;
import main.game.net.game_server;

public class Packet00Login extends packet {
	private String username;
	
	public Packet00Login(byte [] data) {
		super(00);
		this.username = readData(data);
	}
	public Packet00Login(String username) {
		super(00);
		this.username = username;
	}
	@Override
	public void writeData(game_client client) {
		client.sendData(getData());
	}
	@Override
	public void writeData(game_server server) {
		server.sendDataToAllClients(getData());
	}
	public byte [] getData()	{
		return ("00"+this.username).getBytes();
	}
	public String getUsername()	{
		return username;
	}
}
