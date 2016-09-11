import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class ConnectionHandler {
	String server;
	int port;
	String nick;
	String ident;
	String real;
	int visibility;
	Socket socket;
	BufferedWriter writer;
	BufferedReader reader;

	public ConnectionHandler(String serv, int prt, String nickname, String identity, String realName, boolean invisible) throws IOException {
		server = serv;
		nick = nickname;
		port = prt;
		ident = identity;
		real = realName;
		
		if(invisible) {
			visibility = 8;
		} else {
			visibility = 0;
		}
		
		socket = new Socket(server, prt);
		writer = new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		);

		reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream( ))
		);

		message("NICK " + nick + "\r\n");
		message("USER " + ident + " * " + visibility + " :" + real + "\r\n");
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
