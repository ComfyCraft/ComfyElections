package me.lucyy.comfyelections;

import com.google.common.base.Preconditions;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Election {

	private final Set<UUID> candidates = new HashSet<>();
	private final Map<UUID, UUID> votes = new HashMap<>();
	private final String title;
	private LocalDateTime finishTime;

	public Election(String title) {
		this.title = title;
	}

	public Set<UUID> getCandidates() {
		return candidates;
	}

	public void addVote(UUID voter, UUID candidate) {
		Preconditions.checkArgument(candidates.contains(candidate), "Invalid candidate");
		votes.put(voter, candidate);
	}

	public Map<UUID, Long> results() {
		return votes.keySet().stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	// todo range check
	public void setFinishTime(LocalDateTime newFinishTime) {
		finishTime = newFinishTime;
	}

	public String getTitle() {
		return title;
	}
}
