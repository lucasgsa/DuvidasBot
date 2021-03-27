package com.p1ufcg.events;

import com.p1ufcg.repository.DuvidaRepository;
import com.p1ufcg.util.Configs;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DuvidasCommandsRoom extends ListenerAdapter {
	
	DuvidaRepository duvidaRepository;
	
	public DuvidasCommandsRoom(DuvidaRepository duvidaRepository) {
		this.duvidaRepository = duvidaRepository;

	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		checarComando(event);
	}
	
	private void checarComando(GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw().toLowerCase();
		if (message.contains(Configs.finalizarDuvidaCommand)) {
			if (checarSala(event)) {
				removerSala(event);
			}
		}
	}
	
	private boolean checarSala(GuildMessageReceivedEvent event) {
		return duvidaRepository.contemSala(event.getChannel().getIdLong());
	}
	
	private void removerSala(GuildMessageReceivedEvent event) {
		Long idRoom = event.getChannel().getIdLong();
		duvidaRepository.removerSala(event.getChannel().getIdLong());
		event.getChannel().delete().queue();;
	}
}