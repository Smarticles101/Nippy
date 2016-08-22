import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.*;

public class Connection {
	String server;
	String nick;
	String login;
	String channel;
	Socket socket;
	BufferedWriter writer;
	BufferedReader reader;
	ArrayList channelList;
	
	public Connection(String server, int Port, String nick) throws IOException {
		channelList = new ArrayList<ArrayList<String>>();
		socket = new Socket(server, 6667);
		writer = new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		);

		reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream( ))
		);
		
		message("NICK " + nick + "\r\n");
		message("USER " + login + " * 8 :Testing!\r\n");
	}
	
	public void join(String channel) throws IOException {
		message("JOIN " + channel + "\r\n");
	}
	
	public void message(String message) throws IOException {
		writer.write(message);
		//System.out.println(message);
		writer.flush();
	}
	
	public void channelMessage(String message, String channel) throws IOException {
		message("PRIVMSG " + channel + " :" + message + "\r\n");
	}
	
	public String getMessage(String channel) throws IOException {
		return reader.readLine();
	}
}
