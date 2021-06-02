package me.lucyy.comfyelections.command.argument;

import me.lucyy.comfyelections.election.Election;
import me.lucyy.comfyelections.election.ElectionCandidate;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.command.argument.AbstractArgument;
import me.lucyy.squirtgun.command.context.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.stream.Collectors;

public class ElectionCandidateArgument extends AbstractArgument<ElectionCandidate> {

	private final ElectionManager manager;

	public ElectionCandidateArgument(ElectionManager manager, String name, String description, boolean isOptional) {
		super(name, description, isOptional);
		this.manager = manager;
	}

	@Override
	public ElectionCandidate getValue(Queue<String> args, CommandContext<?> ctx) {
		String electionName = args.poll();
		if (electionName == null) return null;
		Election election = manager.getCurrentElections().stream()
				.filter(e -> e.getTitle().toLowerCase(Locale.ROOT).equals(electionName.toLowerCase(Locale.ROOT)))
				.findFirst().orElse(null);

		if (election == null) return null;

		String candidateName = args.poll();
		if (candidateName == null) return null;

		OfflinePlayer candidate = election.getCandidates().stream()
				.map(Bukkit::getOfflinePlayer)
				.filter(player -> player.getName().toLowerCase(Locale.ROOT).equals(candidateName.toLowerCase(Locale.ROOT)))
				.findFirst().orElse(null);

		if (candidate == null) return null;

		return new ElectionCandidate(candidate.getUniqueId(), election);
	}

	@Override
	public @Nullable List<String> tabComplete(Queue<String> args, CommandContext<?> ctx) {
		String electionName = args.poll();
		if (electionName == null) return null;
		List<String> elections = manager.getCurrentElections().stream()
				.map(Election::getTitle)
				.filter(title -> title.toLowerCase(Locale.ROOT).startsWith(electionName.toLowerCase(Locale.ROOT)))
				.collect(Collectors.toList());

		String playerName = args.poll();
		if (playerName == null) return elections;

		Election election = manager.getCurrentElections().stream()
				.filter(e -> e.getTitle().toLowerCase(Locale.ROOT).equals(electionName.toLowerCase(Locale.ROOT)))
				.findFirst().orElse(null);

		if (election == null) return null;

		return election.getCandidates().stream()
				.map(Bukkit::getOfflinePlayer)
				.map(OfflinePlayer::getName)
				.filter(name -> name.toLowerCase(Locale.ROOT).startsWith(playerName.toLowerCase(Locale.ROOT)))
				.collect(Collectors.toList());
	}
}
