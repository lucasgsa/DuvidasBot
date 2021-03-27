package com.p1ufcg.launcher;
import java.io.IOException;

import javax.security.auth.login.LoginException;

public class BotLauncher {
	public static void main(String[] args) throws LoginException, IOException {
		DiscordBot db = new DiscordBot();
		db.initializaDB();
		db.initializeJDA();
	}
}
