package me.lucyy.comfyelections;

import me.lucyy.squirtgun.format.FormatProvider;
import me.lucyy.squirtgun.format.TextFormatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

/**
 * TODO replace this with something nicer
 */
public class Formatter implements FormatProvider {

	private final Map<TextDecoration, Character> decoStrings = Map.of(
			TextDecoration.OBFUSCATED, 'k',
			TextDecoration.BOLD, 'l',
			TextDecoration.STRIKETHROUGH, 'm',
			TextDecoration.UNDERLINED, 'n',
			TextDecoration.ITALIC, 'o'
	);

	private String serialiseFormatters(TextDecoration... formatters) {
		if (formatters == null) {
			return null;
		}
		StringBuilder out = new StringBuilder();
		for (TextDecoration deco : formatters) {
			out.append(decoStrings.get(deco));
		}
		return out.toString();
	}

	@Override
	public Component formatMain(@NotNull String s, TextDecoration... formatters) {
		return TextFormatter.format("&f" + s, serialiseFormatters(formatters), false);
	}

	@Override
	public Component formatAccent(@NotNull String s, TextDecoration... formatters) {
		return TextFormatter.format("&e" + s, serialiseFormatters(formatters), false);
	}

	@Override
	public Component getPrefix() {
		return Component.text("temp prefix >> ");
	}
}
