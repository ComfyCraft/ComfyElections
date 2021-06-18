package me.lucyy.comfyelections.election;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Election {

	private final Set<UUID> candidates = new HashSet<>();
	private final BiMap<UUID, UUID> votes = HashBiMap.create();
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
		votes.forcePut(voter, candidate);
	}

	public void addCandidate(UUID candidate) {
		// fixme - potential concurrent access
		votes.inverse().forEach((c, ignored) -> {
			if (candidate.equals(c)) {
				votes.remove(candidate);
			}
		});
		candidates.add(candidate);
	}

	public Map<UUID, Long> results() {
		Map<UUID, Long> collected = votes.keySet().stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		for (UUID candidate : getCandidates()) {
			collected.putIfAbsent(candidate, 0L);
		}
		return collected;
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
