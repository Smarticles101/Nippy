import java.io.IOException;

public class InputHandler extends OutputHandler {
	InputHandler(String server, int port, String nickname, String identity, String realName, boolean invisible) throws IOException {
		super(server, port, nickname, identity, realName, invisible);
	}
	
	public void handleInput(String input) throws IOException {
		if(input.startsWith("/")) {
			if(input.startsWith("/msg")) {
				int firstSpaceIndex = input.indexOf(' ');
				int secondSpaceIndex = input.indexOf(' ', firstSpaceIndex+1);
				super.message("PRIVMSG " + input.substring(firstSpaceIndex, secondSpaceIndex) + " :" + input.substring(secondSpaceIndex+1) + "\r\n");											
			}
			
			if(input.startsWith("/join")) {
				super.message("JOIN " + input.substring(6) + "\r\n");
			}
			
			if(input.startsWith("/nick")) {
				super.message("NICK " + input.substring(6) + "\r\n");
			}
		} else {
			channelMessage(input);
		}
	}
}