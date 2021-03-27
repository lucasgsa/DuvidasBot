package com.p1ufcg.model;

import javax.persistence.Id;
import javax.persistence.Table;

import com.p1ufcg.util.DuvidaStatus;

import javax.persistence.Entity;

@Entity
public class Duvida {
	
	@Id
	private Long messageId;
	
	private Long uidAluno;
	
	private Long uidMonitor;
	
	private Long uidRoom;

	private String question;
	
	private DuvidaStatus status;
	
	public Duvida() {
		super();
	}
	
	public Duvida(String question, Long uidAluno, Long messageId) {
		super();
		this.messageId = messageId;
		this.uidAluno = uidAluno;
		this.question = question;
		this.status = DuvidaStatus.Iniciada;
	}

	@Override
	public String toString() {
		return "Duvida [messageId=" + messageId + ", uidAluno=" + uidAluno + ", uidMonitor=" + uidMonitor
				+ ", question=" + question + ", status=" + status + "]";
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getUidAluno() {
		return uidAluno;
	}

	public void setUidAluno(Long uidAluno) {
		this.uidAluno = uidAluno;
	}

	public Long getUidMonitor() {
		return uidMonitor;
	}

	public void setUidMonitor(Long uidMonitor) {
		this.uidMonitor = uidMonitor;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public DuvidaStatus getStatus() {
		return status;
	}

	public void setStatus(DuvidaStatus status) {
		this.status = status;
	}
	
	public Long getUidRoom() {
		return uidRoom;
	}

	public void setUidRoom(Long uidRoom) {
		this.uidRoom = uidRoom;
	}
	
}
