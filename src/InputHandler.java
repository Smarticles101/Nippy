import java.io.IOException;

public class InputHandler extends OutputHandler {
	InputHandler(String server, int port, String nick) throws IOException {
		super(server, port, nick);
	}
	
	public void handleInput(String input) {										// TODO:
		try {																	//		more commands!
			if(input.startsWith("/")) {											//		add /channel list
				if(input.startsWith("/msg")) {									//		add /channel switch
					handleMsg(input);											//		work on array lists of channels and people in channels
				}

				if(input.startsWith("/channel ")) {
					handleChannel(input);
				}
				
				if(input.startsWith("/nick ")) {
					handleNick(input);
				}
			} else {
				channelMessage(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void message(String message) throws IOException {
		super.message(message);
	}
	
	public void handleNick(String input) throws IOException {
		message("NICK " + input.substring(6) + "\r\n");
	}
	
	public void handleChannel(String input) throws IOException {
		if(input.startsWith("/channel list ")) {
			
		} else if(input.startsWith("/channel switch ")) {
			super.setCurrentChannel(input.substring(16));
		} else if(input.startsWith("/channel join ")) {
			join(input.substring(14));
		} else {
			System.out.println("Usage:\n" + 
							   "/channel list" + 
							   "/channel switch <channel>" +
							   "/channel join <channel>");
		}
	}
	
	public void handleMsg(String input) throws IOException {
		int firstSpaceIndex = input.indexOf(' ');
		int secondSpaceIndex = input.indexOf(' ', firstSpaceIndex+1);
		super.message("PRIVMSG " + input.substring(firstSpaceIndex, secondSpaceIndex) + " :" + input.substring(secondSpaceIndex+1) + "\r\n");
	}
	
	public void join(String channel) throws IOException {
		super.message("JOIN " + channel + "\r\n");
	}
}
