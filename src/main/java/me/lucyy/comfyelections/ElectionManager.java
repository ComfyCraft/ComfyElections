package me.lucyy.comfyelections;

import java.time.LocalDateTime;
import java.util.Set;

public class ElectionManager {

	private final Set<Election> elections;

	public ElectionManager() {
		elections = null; // TODO - load from config
	}

	public void createElection(String name, LocalDateTime finish) {
		// TODO
	}

	public Set<Election> getCurrentElections() {
		return elections;
	}
}
