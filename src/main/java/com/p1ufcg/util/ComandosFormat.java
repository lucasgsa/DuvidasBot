package com.p1ufcg.util;

public class ComandosFormat {
	public static boolean comandoDuvidaValido(String entrada) {
		if (entrada.contains(Configs.duvidaCommand)) {
			if (entrada.substring(0, Configs.duvidaCommand.length()).equals(Configs.duvidaCommand) ) {
				// o comando está contido no inicio.
				if (entrada.length() < Configs.duvidaCommand.length()+2) return false;
				if (entrada.charAt(Configs.duvidaCommand.length()) == ' ') {
					return true;
				}
				return false;
				
			}
		}
		return false;
	}
	
	public static String getDuvida(String entrada) {
		return entrada.substring(Configs.duvidaCommand.length()+1, entrada.length());
	}
}
