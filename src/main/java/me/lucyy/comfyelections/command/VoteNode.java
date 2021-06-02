package me.lucyy.comfyelections.command;

import me.lucyy.comfyelections.command.argument.ElectionCandidateArgument;
import me.lucyy.comfyelections.election.ElectionCandidate;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.command.argument.CommandArgument;
import me.lucyy.squirtgun.command.context.CommandContext;
import me.lucyy.squirtgun.command.node.CommandNode;
import me.lucyy.squirtgun.platform.PermissionHolder;
import me.lucyy.squirtgun.platform.SquirtgunPlayer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;

public class VoteNode implements CommandNode<PermissionHolder> {

	private final CommandArgument<ElectionCandidate> playerArgument;
	private final ElectionManager manager;

	public VoteNode(ElectionManager manager) {
		this.manager = manager;
		playerArgument = new ElectionCandidateArgument(manager, "candidate", "Who to vote for", false);
	}

	@Override
	public @Nullable String getPermission() {
		return "comfyelections.vote";
	}

	@Override
	public @Nullable Component execute(CommandContext<PermissionHolder> context) {
		if (!(context.getTarget() instanceof SquirtgunPlayer)) {
			return context.getFormat().formatMain("The console can't vote.");
		}

		SquirtgunPlayer player = (SquirtgunPlayer) context.getTarget();

		// this is safe because it's a required argument
		ElectionCandidate candidate = Objects.requireNonNull(context.getArgumentValue(playerArgument));

		if (candidate.getElection().getCandidates().contains(player.getUuid())) {
			return context.getFormat().formatMain("You can't vote in an election that you're registered for.");
		}

		candidate.getElection().addVote(player.getUuid(), candidate.getPlayer());

		// todo nice message
		return context.getFormat().formatMain("You voted.");
	}

	@Override
	public @NotNull String getName() {
		return "vote";
	}

	@Override
	public String getDescription() {
		return "Vote for someone";
	}
}
