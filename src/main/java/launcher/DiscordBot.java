package launcher;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.security.auth.login.LoginException;

import org.json.simple.parser.ParseException;

import events.DuvidasCommandsRoom;
import events.DuvidasEmojiReaction;
import events.DuvidasReceiver;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import repository.DuvidaRepository;

public class DiscordBot {
	private JDA jda;
	
	private DuvidaRepository duvidaRepository;
	
	public void initializeJDA() throws LoginException {
		// Bot login.
		String discordToken = util.Configs.discordToken;
		
		JDABuilder jdaBuilder = JDABuilder.createDefault(discordToken);
		
		jda = jdaBuilder.build();
		
		newListener(new DuvidasReceiver(duvidaRepository));
		newListener(new DuvidasEmojiReaction(duvidaRepository));
		newListener(new DuvidasCommandsRoom(duvidaRepository));
	}
	
	public void initializaDB() {
		this.duvidaRepository = new DuvidaRepository();
	}
	
	public void newListener(ListenerAdapter listener) {
		jda.addEventListener(listener);
	}
}
