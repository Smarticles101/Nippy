import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class OutputHandler extends ConnectionHandler {
	List<String> channelList;
	List<List<String>> userList;
	List<List<String>> msgList;
	int currentUserIndex;
	String currentChannel;
	
	public OutputHandler(String server, int port, String nickname, String identity, String realName, boolean invisible) throws IOException {
		super(server, port, nickname, identity, realName, invisible);
		channelList = new ArrayList<String>();
		userList = new ArrayList<List<String>>();
		msgList = new ArrayList<List<String>>();
		currentUserIndex = 0;
		currentChannel = null;
	}

	public String getMessage() throws IOException {
		String line = super.getMessage();
		System.out.println(line);

		if(line.startsWith("PING ")) {
			message("PING " + line.substring(5));
			return "";
		}
		
		if(line.contains(" JOIN ") && line.indexOf(":")<line.indexOf("!")) {
			String channel = line.substring(line.indexOf("JOIN")+5);
			setCurrentChannel(channel);
			channelList.add(channel);
			userList.add(new ArrayList<String>());
			msgList.add(new ArrayList<String>());
			return "";
		}
		
		if(line.contains(super.nick + " = " + currentChannel + " :")) {
			final String usrList = line.substring(line.indexOf(super.nick)+super.nick.length()+3+currentChannel.length() + 2);
			
			new Thread() {
				public void run() {
			
					for(String s : usrList.split(" ")) {
						addUser(channelList.indexOf(currentChannel), s);
					}
				}
			}.start();
			
			return "";
		}
		
		if(currentChannel == null || currentChannel == "") {
			int secondColon = line.indexOf(':', 1);
			//System.out.println("secondcolon " + secondColon);
			if(line.contains("!")) {
				int nickEnd = line.indexOf('!');
				//System.out.println("nickend " + nickEnd);
				if(nickEnd < secondColon) {
					if(line.contains("PRIVMSG ")) {
						if(line.contains("PRIVMSG " + currentChannel + " :")) {
							msgList.get(channelList.indexOf(currentChannel)).add(line);
							return line.substring(1,nickEnd) + " " + line.substring(secondColon);
						} else {
							String channel = line.substring(line.indexOf("PRIVMSG ") + 8, line.indexOf(" ", line.indexOf("PRIVMSG " + 8)));
							msgList.get(channelList.indexOf(channel)).add(line);
						}
					} else {
						nickEnd = line.indexOf('!');
						String message = line.substring(secondColon);
						return line.substring(1, nickEnd) + " " + message;
					}
				}
			} else if(secondColon != -1) {
				String message = line.substring(secondColon);
				return  message;
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
