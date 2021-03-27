package com.p1ufcg.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.p1ufcg.model.Duvida;
import com.p1ufcg.util.DuvidaStatus;

public class DuvidaRepository {
	private static EntityManager entityManager;
	
	public DuvidaRepository() {
		EntityManagerFactory entityManagerFactory =Persistence.createEntityManagerFactory("DuvidasDB");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	public void criarDuvida(String question, Long alunoUID, Long messageId) {
		
		Duvida duvida = new Duvida(question, alunoUID, messageId);
		
		entityManager.getTransaction().begin();
		entityManager.persist(duvida);
		entityManager.getTransaction().commit();
	}
	
	public void adicionarMonitor(Long messageId, Long monitorId) {
		
		entityManager.getTransaction().begin();
		
		Duvida duvida = entityManager.find(Duvida.class, messageId);
		
		duvida.setStatus(DuvidaStatus.EmAndamento);
		duvida.setUidMonitor(monitorId);
		
		entityManager.persist(duvida);
		
		entityManager.getTransaction().commit();
		entityManager.refresh(duvida);
	}
	
	public void defineSala(Long messageId, Long roomId) {
		entityManager.getTransaction().begin();
		
		Duvida duvida = entityManager.find(Duvida.class, messageId);
		
		duvida.setStatus(DuvidaStatus.EmAndamento);
		duvida.setUidRoom(roomId);
		
		entityManager.persist(duvida);
		
		entityManager.getTransaction().commit();
		entityManager.refresh(duvida);
	}
	
	public boolean contemSala(Long roomId) {
		entityManager.getTransaction().begin();
		List<Duvida> duvidas = entityManager.createQuery("SELECT d FROM Duvida AS d where uidRoom="+roomId).getResultList();
		entityManager.getTransaction().commit();
		if (duvidas.isEmpty()) return false;
		return true;
	}
	
	public void removerSala(Long roomId) {
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE FROM Duvida where uidRoom="+roomId);
		entityManager.getTransaction().commit();
	}
	
	public boolean duvidaIniciada(Long messageId) {
		entityManager.getTransaction().begin();
		Duvida duvida = entityManager.find(Duvida.class, messageId);
		entityManager.getTransaction().commit();
		if (duvida == null) return false;
		if (duvida.getStatus() == DuvidaStatus.Iniciada) return true;
		return false;
	}
	
	public Duvida getDuvida(Long messageId) {
		Duvida duvida = entityManager.find(Duvida.class, messageId);
		System.out.println("aqui "+duvida.getUidAluno());
		return duvida;
	}
}
