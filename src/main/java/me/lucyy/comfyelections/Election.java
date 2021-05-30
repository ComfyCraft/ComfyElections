package me.lucyy.comfyelections;

import com.google.common.base.Preconditions;
import java.util.*;

public class Election {

	private final Set<UUID> candidates = new HashSet<>();
	private final Map<UUID, UUID> votes = new HashMap<>();

	public Set<UUID> getCandidates() {
		return candidates;
	};

	public void addVote(UUID voter, UUID candidate) {
		Preconditions.checkArgument(candidates.contains(candidate), "Invalid candidate");
		votes.put(voter, candidate);
	}
}
