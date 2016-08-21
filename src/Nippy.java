
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
							irc.channelMessage(input, "#nippy");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
			
			irc.join("#nippy");
			String line = null;
			while ((line = irc.getMessage("#nippy")) != null) {
				if(line.startsWith("PING ")) {
					irc.message("PING " + line.substring(5));
				}
				System.out.println(line);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
