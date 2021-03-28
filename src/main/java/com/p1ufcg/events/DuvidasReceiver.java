package com.p1ufcg.events;

import com.p1ufcg.repository.DuvidaRepository;
import com.p1ufcg.util.ComandosFormat;
import com.p1ufcg.util.Configs;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DuvidasReceiver extends ListenerAdapter {
	
	DuvidaRepository duvidaRepository;
	
	public DuvidasReceiver(DuvidaRepository duvidaRepository) {
		
		this.duvidaRepository = duvidaRepository;
		
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		checarComando(event);
	}

	private void checarComando(GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw().toLowerCase();
		if (ComandosFormat.comandoDuvidaValido(message)) {
			comandoDuvida(message, event);
		}
	}
	
	public void comandoDuvida(String message, GuildMessageReceivedEvent event) {
		String mensagemDividida = ComandosFormat.getDuvida(message);
		
		registrarDuvida(mensagemDividida, event);
	}
	
	public void registrarDuvida(String entrada, GuildMessageReceivedEvent event) {
		event.getMessage()
			.addReaction(Configs.duvidaHelpReaction)
			.queue();
		criarDuvida(entrada, event);
	}
	
	public void criarDuvida(String entrada,GuildMessageReceivedEvent event) {
		Long uidAluno = event.getMember().getIdLong();
		
		duvidaRepository.criarDuvida(entrada, uidAluno, event.getMessageIdLong());
	}
	

}
