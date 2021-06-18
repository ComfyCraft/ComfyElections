package me.lucyy.comfyelections.command;

import me.lucyy.comfyelections.command.argument.ElectionArgument;
import me.lucyy.comfyelections.election.Election;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.comfyelections.util.DurationFormatter;
import me.lucyy.squirtgun.command.argument.CommandArgument;
import me.lucyy.squirtgun.command.context.CommandContext;
import me.lucyy.squirtgun.command.node.AbstractNode;
import me.lucyy.squirtgun.command.node.CommandNode;
import me.lucyy.squirtgun.format.FormatProvider;
import me.lucyy.squirtgun.format.TextFormatter;
import me.lucyy.squirtgun.platform.Platform;
import me.lucyy.squirtgun.platform.audience.PermissionHolder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ElectionInfoNode extends AbstractNode<PermissionHolder> {

    private final CommandArgument<Election> electionArg;
    private final Platform platform;

    public ElectionInfoNode(@NotNull ElectionManager manager, Platform platform) {
        super("info", "Shows info about a current election", null);
        this.platform = platform;
        electionArg = new ElectionArgument(manager, "election", "The election to show", false);
    }

    @Override
    public @Nullable Component execute(CommandContext<PermissionHolder> context) {
        final FormatProvider fmt = context.getFormat();
        final Election election = context.getArgumentValue(electionArg);
        Objects.requireNonNull(election); // required argument - guaranteed not-null

        final String timeRemaining = DurationFormatter.format(
                Duration.between(LocalDateTime.now(), election.getFinishTime())
        );

        AtomicReference<Component> out = new AtomicReference<>(Component.newline()
                .append(TextFormatter.formatTitle("Election Info", fmt))
                .append(Component.newline())
                .append(fmt.formatAccent(timeRemaining)).append(fmt.formatMain(" left"))
                .append(Component.newline())
                .append(Component.newline())
                .append(TextFormatter.centreText("Candidates", fmt, " ")));

        election.results().forEach((uuid, count) -> {
            String username = platform.getPlayer(uuid).getUsername();
            out.set(out.get().append(fmt.formatAccent(username))
                    .append(fmt.formatMain(" - "))
                    .append(fmt.formatAccent(count + " votes"))
                    .append(Component.newline())
            );
        });

        return out.get().append(Component.newline()).append(TextFormatter.formatTitle("*", fmt));
    }

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return List.of(electionArg);
    }
}
