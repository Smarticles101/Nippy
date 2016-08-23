import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MessageHandler extends ConnectionHandler implements Runnable {
	List<String> channelList;
	List<List<String>> userList;
	int currentUserIndex;
	
	public MessageHandler(String server, int port, String nick) throws IOException {
		super(server, port, nick);
		channelList = new ArrayList<String>();
		userList = new ArrayList<List<String>>();
		currentUserIndex = 0;
	}
	
	public void run() {
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		
		try {
			if(input.startsWith("/")) {
				if(input.startsWith("/msg")) {
					int firstSpaceIndex = input.indexOf(' ');
					int secondSpaceIndex = input.indexOf(' ', firstSpaceIndex+1);
					super.message("PRIVMSG " + input.substring(firstSpaceIndex, secondSpaceIndex) + " :" + input.substring(secondSpaceIndex+1) + "\r\n");
				}
				
				if(input.startsWith("/channel ")) {
					if(input.startsWith("/channel list ")) {
						
					} else if(input.startsWith("/channel switch ")) {
						
					} else {
						System.out.println("Usage:\n" + 
													"/channel list" + 
													"/channel switch");
					}
				}
			} else {
				this.channelMessage(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMessage() throws IOException {
		String line = reader.readLine();

		if(line.contains(" JOIN ") && line.indexOf(":")<line.indexOf("!")) {
			String channel = line.substring(line.indexOf("JOIN"));
		}
		
		if(line.contains("PRIVMSG" + currentChannel + " :")) {
			return line;
		} else {
			return "";
		}
	}
	
	public void addUser(int channelIndex, String userName) {
		userList.get(channelIndex).add(userName);
	}

	public void removeUser(int channelIndex, String userName) {
		userList.get(channelIndex).remove(userList.get(channelIndex).indexOf(userName));
	}

	public List<String> getUsers(int channelIndex) {
		return userList.get(channelIndex);
	}

	public void setCurrentChannel(String channel) {
		currentChannel = channel;
	}
	
	public void channelMessage(String message) throws IOException {
		message("PRIVMSG " + currentChannel + " :" + message + "\r\n");
	}
	
	public void join(String channel) throws IOException {
		message("JOIN " + channel + "\r\n");
	}
}
