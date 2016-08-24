import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler {
	String server;
	String nick;
	String login;
	Socket socket;
	BufferedWriter writer;
	BufferedReader reader;
	
	public ConnectionHandler(String server, int Port, String nick) throws IOException {
		socket = new Socket(server, Port);
		writer = new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		);

		reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream( ))
		);
		
		message("NICK " + nick + "\r\n");
		message("USER " + login + " * 8 :Testing!\r\n");
	}
	
	public void message(String message) throws IOException {
		writer.write(message);
		System.out.println(message);
		writer.flush();
	}
	
	public String getMessage() throws IOException {
		return reader.readLine();
	}
}
