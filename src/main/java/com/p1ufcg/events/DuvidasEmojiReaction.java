package com.p1ufcg.events;

import java.util.EnumSet;

import com.p1ufcg.model.Duvida;
import com.p1ufcg.repository.DuvidaRepository;
import com.p1ufcg.util.Configs;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DuvidasEmojiReaction extends ListenerAdapter {
	
	DuvidaRepository duvidaRepository;
	
	public DuvidasEmojiReaction(DuvidaRepository duvidaRepository) {
		this.duvidaRepository = duvidaRepository;
	}
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		checarReact(event);
	}

	private void checarReact(GuildMessageReactionAddEvent event) {
		Long messageId = event.getMessageIdLong();
		if (duvidaRepository.duvidaIniciada(messageId)) {
			if (reacterIsMonitor(event)) {
				duvidaRepository.adicionarMonitor(messageId, event.getMember().getIdLong());
				createRoom(event);
				event.getChannel().retrieveMessageById(messageId).queue(
						message -> {
							message.clearReactions().queue();
							message.addReaction(Configs.duvidaRespondida).queue();
						}
						);
				}
		}
	}
	
	private boolean reacterIsMonitor(GuildMessageReactionAddEvent event) {
		for (Role role:event.getMember().getRoles()) {
			if (role.getName().equals(Configs.monitorRole)) {
				return true;
			}
		}
		return false;
	}
	
	private void createRoom(GuildMessageReactionAddEvent event) {
		Long messageId = event.getMessageIdLong();
		Duvida duvida = duvidaRepository.getDuvida(messageId);
		String roomName = Configs.roomPrefix+event.getMessageId();
	
		Category categoria = event.getMember().getGuild().getCategoriesByName("duvidas", true).get(0);
		
		TextChannel channel = createPrivateTextChannel(roomName, categoria, event.getGuild(), duvida.getUidAluno(), duvida.getUidMonitor());
	
		enviarMensagemInicial(channel, duvida);
		
		duvidaRepository.defineSala(messageId, channel.getIdLong());
	
	}
	
	private void enviarMensagemInicial(TextChannel channel, Duvida duvida) {
		String texto = "Um monitor foi colocado na sala junto com você.\n"
				+ "A sua dúvida vai ser respondida!\n"
				+ "Dúvida: "+ duvida.getQuestion() + "\n"
				+ "Bons estudos. 🙂";
		channel.sendMessage(texto).queue();
	}
	
	private TextChannel createPrivateTextChannel(String name, Category categoria, Guild guild, Long alunoID, Long monitorID) {
		return guild.createTextChannel(name, categoria)
				.addRolePermissionOverride(guild.getPublicRole().getIdLong(), null, EnumSet.of(Permission.VIEW_CHANNEL))
				.addMemberPermissionOverride(alunoID, EnumSet.of(Permission.VIEW_CHANNEL), null)
				.addMemberPermissionOverride(monitorID, EnumSet.of(Permission.VIEW_CHANNEL), null).complete();
	}
	
}
