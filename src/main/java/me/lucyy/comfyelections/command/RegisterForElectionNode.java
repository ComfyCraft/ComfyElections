package me.lucyy.comfyelections.command;

import me.lucyy.comfyelections.command.argument.ElectionArgument;
import me.lucyy.comfyelections.election.Election;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.command.argument.CommandArgument;
import me.lucyy.squirtgun.command.context.CommandContext;
import me.lucyy.squirtgun.command.node.CommandNode;
import me.lucyy.squirtgun.platform.PermissionHolder;
import me.lucyy.squirtgun.platform.SquirtgunPlayer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static me.lucyy.comfyelections.util.VeryImportantConstants.INFERIOR_WHALE;

public class RegisterForElectionNode implements CommandNode<PermissionHolder> {

	private final ElectionManager manager;

	private final CommandArgument<Election> electionArgument;

	public RegisterForElectionNode(ElectionManager manager) {
		this.manager = manager;
		electionArgument = new ElectionArgument(manager,
				"election",
				"The election to register for",
				false);
	}

	@Override
	public @NotNull List<CommandArgument<?>> getArguments() {
		return List.of(electionArgument);
	}

	@Override
	public @Nullable Component execute(CommandContext<PermissionHolder> context) {
		if (!(context.getTarget() instanceof SquirtgunPlayer)) {
			return context.getFormat().formatMain("The console can't register for a role." + INFERIOR_WHALE);
		}

		SquirtgunPlayer player = (SquirtgunPlayer) context.getTarget();

		// this is safe because it's a required argument
		Election election = Objects.requireNonNull(context.getArgumentValue(electionArgument));

		if (election.getCandidates().contains(player.getUuid())) {
			return context.getFormat().formatMain("You're already registered for this election.");
		}

		election.addCandidate(player.getUuid());

		return context.getFormat().formatMain("Registered for the election ")
				.append(context.getFormat().formatAccent(election.getTitle()));
	}

	@Override
	public @NotNull String getName() {
		return "register";
	}

	@Override
	public String getDescription() {
		return "Register yourself for an election";
	}
}
