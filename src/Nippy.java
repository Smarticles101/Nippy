import java.io.*;
import java.util.Scanner;
import java.util.Optional;

public class Nippy {
	public static void main(String args[]) {
		try {
			final InputHandler irc = new InputHandler("irc.freenode.net", 6667, "nippy-client");

			new Thread() {
				public void run() {
					Scanner in = new Scanner(System.in);
					String input = null;

					while((input = in.nextLine()) != null) {
						irc.handleInput(input);
					}
				}
			}.start();

			String line = null;

			while ((line = irc.getMessage()) != null) {
				if(line != "") {
					System.out.println(line);

				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
