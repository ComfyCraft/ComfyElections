package me.lucyy.comfyelections.util;

import java.time.Duration;

public class DurationFormatter {
	public static String format(Duration d) {
		long days = d.toDays();
		d = d.minusDays(days);
		long hours = d.toHours();
		d = d.minusHours(hours);
		long minutes = d.toMinutes();
		d = d.minusMinutes(minutes);
		long seconds = d.getSeconds();
		return (days == 0 ? "" : days + " days, ") +
				(hours == 0 ? "" : hours + " hours, ") +
				(minutes == 0 ? "" : minutes + " minutes, ") +
				(seconds == 0 ? "" : seconds + " seconds");
	}
}
