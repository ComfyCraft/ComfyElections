package me.lucyy.comfyelections.election;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ElectionManager {

	private final Set<Election> elections;

	public ElectionManager() {
		elections = new HashSet<>(); // TODO - load from config
	}

	public Election createElection(String name, LocalDateTime finish) {
		Election election = new Election(name, finish);
		elections.add(election);
		// TODO save to config
		return election;
	}

	public Set<Election> getCurrentElections() {
		return elections;
	}
}
