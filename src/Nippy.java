
import java.io.*;
import java.util.Scanner;

public class Nippy {
	public static void main(String args[]) {
		try {
			final Connection irc = new Connection("chat.freenode.net", 6667, "nippy-client");
			
			new Thread() {
				Scanner in = new Scanner(System.in);
				String input = null;
				public void run() {
					while((input = in.nextLine()) != null) {
						try {
							if(input.startsWith("/msg")) {
								int firstSpaceIndex = input.indexOf(' ');
								int secondSpaceIndex = input.indexOf(' ', firstSpaceIndex+1);
								irc.channelMessage(input.substring(secondSpaceIndex+1) ,input.substring(firstSpaceIndex, secondSpaceIndex));
							} else {
								irc.channelMessage(input, "#nippy");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
			
			irc.join("#nippy");
			String line = null;
			
			/*while ((line = irc.getMessage("#nippy")) != null) {
				if (line.indexOf("004") >= 0) {
					// We are now logged in.
					break;
				} else if (line.indexOf("433") >= 0) {
					System.out.println("Nickname is already in use.");
					return;
				}
			}*/
			
			while ((line = irc.getMessage("#nippy")) != null) {
				//System.out.println(line);
				if(line.startsWith("PING ")) {
					irc.message("PING " + line.substring(5));
				}
				int secondColon = line.indexOf(':', 1);
				//System.out.println("secondcolon " + secondColon);
				if(line.contains("!")) {
					int nickEnd = line.indexOf('!');
					//System.out.println("nickend " + nickEnd);
					if(nickEnd < secondColon) {
						nickEnd = line.indexOf('!');
						String message = line.substring(secondColon);
						System.out.println(line.substring(1, nickEnd) + " " + message);
					}
				} else if(secondColon != -1) {
					String message = line.substring(secondColon);
					System.out.println(message);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
