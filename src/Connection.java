import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Connection {
	String server;
	String nick;
	String login;
	String channel;
	Socket socket;
	BufferedWriter writer;
	BufferedReader reader;
	
	public Connection(String server, int Port, String nick) throws IOException {
		socket = new Socket(server, 6667);
		writer = new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		);

		reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream( ))
		);
		
		message("NICK " + nick + "\r\n");
		message("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
	}
	
	public void join(String channel) throws IOException {
		message("JOIN " + channel + "\r\n");
	}
	
	public void message(String message) throws IOException {
		writer.write(message);
		writer.flush();
	}
	
	public void channelMessage(String message, String channel) throws IOException {
		message("PRIVMSG " + channel + " :" + message + "\r\n");
	}
	
	public String getMessage(String channel) throws IOException {
		return reader.readLine();
	}
	
	/*public static void main(String[] args) throws Exception {
		// The server to connect to and our details.
        Scanner in = new Scanner(System.in);
        String input = null;

		String server = "irc.freenode.net";
		String nick = "simple_bot";
		String login = "simple_bot";

		// The channel which the bot will join.

		String channel = "#irchacks";
		
		// Log on to the server.
		

		// Read lines from the server until it tells us we have connected.

		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.indexOf("004") >= 0) {
				// We are now logged in.
				break;
			} else if (line.indexOf("433") >= 0) {
				System.out.println("Nickname is already in use.");
				return;
			}
	}

		// Keep reading lines from the server.
	}*/
}
