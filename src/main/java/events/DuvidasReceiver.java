package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import repository.DuvidaRepository;

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
		if (message.contains(util.Configs.duvidaCommand)) {
			comandoDuvida(message, event);
		}
	}
	
	public void comandoDuvida(String message, GuildMessageReceivedEvent event) {
		String mensagemDividida = message.substring(util.Configs.duvidaCommand.length()+1, message.length());
		
		registrarDuvida(mensagemDividida, event);
	}
	
	public void registrarDuvida(String entrada, GuildMessageReceivedEvent event) {
		event.getMessage()
			.addReaction(util.Configs.duvidaHelpReaction)
			.queue();
		criarDuvida(entrada, event);
	}
	
	public void criarDuvida(String entrada,GuildMessageReceivedEvent event) {
		Long uidAluno = event.getMember().getIdLong();
		
		duvidaRepository.criarDuvida(entrada, uidAluno, event.getMessageIdLong());
	}
	

}
