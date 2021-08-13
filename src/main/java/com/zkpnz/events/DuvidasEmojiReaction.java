package com.zkpnz.events;

import java.awt.Color;
import java.util.EnumSet;
import java.util.function.Consumer;

import com.zkpnz.model.Duvida;
import com.zkpnz.repository.DuvidaRepository;
import com.zkpnz.util.Configs;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

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
							message.clearReactions().queue(voidMessage -> {
								message.addReaction(Configs.duvidaRespondida).queue();
							});
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
		
		ChannelAction<TextChannel> channelAction = createPrivateTextChannel(roomName, categoria, event.getGuild(), duvida.getUidAluno(), duvida.getUidMonitor());
	
		Consumer<TextChannel> callback = (channel) ->  {
			enviarMensagemInicial(channel, duvida);
			duvidaRepository.defineSala(messageId, channel.getIdLong());
	      };
		
		channelAction.queue(callback);
	
	}
	
	private void enviarMensagemInicial(TextChannel channel, Duvida duvida) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle("Sala para dúvida - "+duvida.getMessageId());
		embedBuilder.setDescription("Um monitor foi colocado na sala junto com você.\nA sua dúvida vai ser respondida!");
		embedBuilder.setColor(new Color(0, 200, 0));
		embedBuilder.addField("Dúvida:", ">>> " + duvida.getQuestion(), false);
		embedBuilder.addField("Bons estudos.", "Quando acabar, basta digitar '!finalizar' 🙂", true);
		channel.sendMessage(embedBuilder.build()).queue();
	}
	
	private ChannelAction<TextChannel> createPrivateTextChannel(String name, Category categoria, Guild guild, Long alunoID, Long monitorID) {
		return guild.createTextChannel(name, categoria)
				.addRolePermissionOverride(guild.getPublicRole().getIdLong(), null, EnumSet.of(Permission.VIEW_CHANNEL))
				.addMemberPermissionOverride(alunoID, EnumSet.of(Permission.VIEW_CHANNEL), null)
				.addMemberPermissionOverride(monitorID, EnumSet.of(Permission.VIEW_CHANNEL), null);
	}
	
}
