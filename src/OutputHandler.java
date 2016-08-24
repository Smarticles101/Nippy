import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class OutputHandler extends ConnectionHandler {
	List<String> channelList;
	List<List<String>> userList;
	int currentUserIndex;
	String currentChannel;
	
	public OutputHandler(String server, int port, String nick) throws IOException {
		super(server, port, nick);
		channelList = new ArrayList<String>();
		userList = new ArrayList<List<String>>();
		currentUserIndex = 0;
		currentChannel = null;
	}

	public String getMessage() throws IOException {
		String line = reader.readLine();
		System.out.println(line);

		if(line.startsWith("PING ")) {
			message("PING " + line.substring(5));
		}
		
		if(line.contains(" JOIN ") && line.indexOf(":")<line.indexOf("!")) {
			String channel = line.substring(line.indexOf("JOIN")+5);
			setCurrentChannel(channel);
			System.out.println(currentChannel);
		}
		
		if(currentChannel == null || currentChannel == "") {
			int secondColon = line.indexOf(':', 1);
			//System.out.println("secondcolon " + secondColon);
			if(line.contains("!")) {
				int nickEnd = line.indexOf('!');
				//System.out.println("nickend " + nickEnd);
				if(nickEnd < secondColon) {
					nickEnd = line.indexOf('!');
					String message = line.substring(secondColon);
					return line.substring(1, nickEnd) + " " + message;
				}
			} else if(secondColon != -1) {
				String message = line.substring(secondColon);
				return message;
			} else {
				return "";
			}
		} else{
			if(line.contains("PRIVMSG " + currentChannel + " :")) {
				return line;
			} else {
				return "";
			}
		}
		
		return "";
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
	
	public void channelMessage(String message) throws IOException {
		super.message("PRIVMSG " + currentChannel + " :" + message + "\r\n");
	}
	
	public void setCurrentChannel(String channel) {
		currentChannel = channel;
	}
}
