package me.lucyy.comfyelections.command;

import me.lucyy.comfyelections.election.Election;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.command.argument.CommandArgument;
import me.lucyy.squirtgun.command.argument.SingleWordArgument;
import me.lucyy.squirtgun.command.context.CommandContext;
import me.lucyy.squirtgun.command.node.CommandNode;
import me.lucyy.squirtgun.format.FormatProvider;
import me.lucyy.squirtgun.platform.audience.PermissionHolder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;

public class CreateElectionNode implements CommandNode<PermissionHolder> {

	private final CommandArgument<String> nameArg =
			new SingleWordArgument("role", "The role to run the election for", false);

	private final CommandArgument<String> timeArg = new SingleWordArgument("time", // TODO proper time
					"The length of time the election will last for (seconds)", false);

	private final ElectionManager manager;

	public CreateElectionNode(ElectionManager manager) {
		this.manager = manager;
	}

	@Override
	public @NotNull List<CommandArgument<?>> getArguments() {
		return List.of(nameArg, timeArg);
	}

	@Override
	public @Nullable String getPermission() {
		return "comfyelections.create";
	}

	@Override
	public @Nullable Component execute(final CommandContext<PermissionHolder> context) {
		final String name = context.getArgumentValue(nameArg);

		if (manager.getCurrentElections().stream()
				.anyMatch(x -> x.getTitle().equals(name))) {
			return context.getFormat().formatMain("An election with that name already exists.");
		}

		int duration;
		try {
			duration = Integer.parseInt(Objects.requireNonNull(context.getArgumentValue(timeArg)));
		} catch (NumberFormatException e) {
			return context.getFormat().formatMain("Time needs to be an integer second count");
		}

		final Election election = manager.createElection(name, LocalDateTime.now().plusSeconds(duration));

		final FormatProvider fmt = context.getFormat();

		return fmt.formatMain("Created a new election for ")
				.append(fmt.formatAccent(election.getTitle()))
				.append(fmt.formatMain(", finishing at "))
				.append(fmt.formatMain(election.getFinishTime().format(
						DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))));
	}

	@Override
	public @NotNull String getName() {
		return "create";
	}

	@Override
	public String getDescription() {
		return "Creates a new election";
	}
}
