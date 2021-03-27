package com.p1ufcg.launcher;

import javax.security.auth.login.LoginException;

import com.p1ufcg.events.DuvidasCommandsRoom;
import com.p1ufcg.events.DuvidasEmojiReaction;
import com.p1ufcg.events.DuvidasReceiver;
import com.p1ufcg.repository.DuvidaRepository;
import com.p1ufcg.util.Configs;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot {
	private JDA jda;
	
	private DuvidaRepository duvidaRepository;
	
	public void initializeJDA() throws LoginException {
		// Bot login.
		String discordToken = Configs.discordToken;
		
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
