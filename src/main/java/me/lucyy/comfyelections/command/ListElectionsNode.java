package me.lucyy.comfyelections.command;

import me.lucyy.comfyelections.election.Election;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.comfyelections.util.DurationFormatter;
import me.lucyy.squirtgun.command.context.CommandContext;
import me.lucyy.squirtgun.command.node.CommandNode;
import me.lucyy.squirtgun.format.FormatProvider;
import me.lucyy.squirtgun.format.TextFormatter;
import me.lucyy.squirtgun.platform.audience.PermissionHolder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;

public class ListElectionsNode implements CommandNode<PermissionHolder> {

	private final ElectionManager manager;

	public ListElectionsNode(ElectionManager manager) {
		this.manager = manager;
	}

	@Override
	public @Nullable Component execute(CommandContext<PermissionHolder> context) {
		final FormatProvider fmt = context.getFormat();
		Component out = Component.newline()
				.append(TextFormatter.formatTitle("Current Elections", fmt))
				.append(Component.newline()).append(Component.newline());

		LocalDateTime now = LocalDateTime.now();

		for (Election election : manager.getCurrentElections()) {
			out = out.append(fmt.formatMain(election.getTitle() + " - "))
					.append(fmt.formatAccent(
							DurationFormatter.format(Duration.between(now, election.getFinishTime()))
					))
			.append(Component.newline());
		}

		out = out.append(Component.newline())
				.append(TextFormatter.formatTitle("*", fmt));

		return out;
	}

	@Override
	public @Nullable String getPermission() {
		return "comfyelections.list";
	}

	@Override
	public @NotNull String getName() {
		return "list";
	}

	@Override
	public String getDescription() {
		return "Lists all current elections";
	}
}
