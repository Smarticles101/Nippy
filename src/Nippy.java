
import java.io.*;public class Nippy
 {
	public static void main(String args[]) {
		try {
			Connection irc = new Connection("chat.freenode.net", 6667, "nippy-client");
			
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
