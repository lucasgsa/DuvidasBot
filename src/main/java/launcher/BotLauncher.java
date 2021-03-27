package launcher;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import org.json.simple.parser.ParseException;

public class BotLauncher {
	public static void main(String[] args) throws LoginException, IOException, ParseException {
		DiscordBot db = new DiscordBot();
		db.initializaDB();
		db.initializeJDA();
	}
}
