package me.lucyy.comfyelections.election;

import java.util.UUID;

public class ElectionCandidate {
	private final UUID player;
	private final Election election;

	public ElectionCandidate(UUID player, Election election) {
		this.player = player;
		this.election = election;
	}

	public UUID getPlayer() {
		return player;
	}

	public Election getElection() {
		return election;
	}
}
