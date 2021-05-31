package me.lucyy.comfyelections.command;

import me.lucyy.comfyelections.election.Election;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.command.context.CommandContext;
import me.lucyy.squirtgun.command.node.CommandNode;
import me.lucyy.squirtgun.format.FormatProvider;
import me.lucyy.squirtgun.format.TextFormatter;
import me.lucyy.squirtgun.platform.PermissionHolder;
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
		FormatProvider fmt = context.getFormat();
		Component out = Component.newline()
				.append(TextFormatter.formatTitle("Current Elections", fmt))
				.append(Component.newline()).append(Component.newline());

		LocalDateTime now = LocalDateTime.now();

		for (Election election : manager.getCurrentElections()) {
			out = out.append(fmt.formatMain(election.getTitle() + " - "))
					.append(fmt.formatAccent(
							Duration.between(election.getFinishTime(), now).toString()
					));
		}

		return out;
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
