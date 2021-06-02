package me.lucyy.comfyelections.command.argument;

import me.lucyy.comfyelections.election.Election;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.command.argument.AbstractArgument;
import me.lucyy.squirtgun.command.context.CommandContext;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.stream.Collectors;

public class ElectionArgument extends AbstractArgument<Election> {

	private final ElectionManager manager;

	public ElectionArgument(ElectionManager manager, String name, String desc, boolean isOptional) {
		super(name, desc, isOptional);
		this.manager = manager;
	}

	@Override
	public Election getValue(Queue<String> args, CommandContext<?> ctx) {
		String value = args.poll();
		if (value == null) return null;
		return manager.getCurrentElections().stream()
				.filter(x -> x.getTitle().toLowerCase(Locale.ROOT).equals(value.toLowerCase(Locale.ROOT)))
				.findFirst().orElse(null);
	}

	@Override
	public @Nullable List<String> tabComplete(Queue<String> args, CommandContext<?> ctx) {
		String value = args.poll();
		if (value == null) return null;
		return manager.getCurrentElections().stream()
				.map(Election::getTitle)
				.filter(title -> title.toLowerCase(Locale.ROOT).startsWith(value.toLowerCase(Locale.ROOT)))
				.collect(Collectors.toList());
	}
}
