package me.lucyy.comfyelections.election;

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

	public Election(String title, LocalDateTime finishTime) {
		this.title = title;
		this.finishTime = finishTime;
	}

	public Set<UUID> getCandidates() {
		return candidates;
	}

	public void addVote(UUID voter, UUID candidate) {
		Preconditions.checkArgument(candidates.contains(candidate), "Invalid candidate");
		Preconditions.checkState(LocalDateTime.now().isBefore(getFinishTime()), "Election has already closed");
		votes.put(voter, candidate);
	}

	public void addCandidate(UUID candidate) {
		// TODO use a multimap?
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
