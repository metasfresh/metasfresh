/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.util.time;

import lombok.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HmmUtils
{
	private static final Pattern hmmPattern = Pattern.compile("^-?[0-9]+:[0-5][0-9]$");

	@NonNull
	public static String secondsToHmm(final long seconds)
	{
		final long hours = seconds / 3600;
		final long hoursRemainder = seconds - (hours * 3600);
		final long mins = hoursRemainder < 0 ? -( hoursRemainder / 60 ) : hoursRemainder / 60;

		return hours + ":" + (mins < 10L ? "0" + mins : mins);
	}

	public static long hmmToSeconds(@NonNull final String hmm)
	{
		if (!matches(hmm))
		{
			throw new RuntimeException("Wrong format! Was expecting a value in format: " + hmmPattern.toString() + ", but received: " + hmm);
		}

		final String[] parts = hmm.split(":");

		final long hours = Long.parseLong(parts[0]);
		final long minutes = Long.parseLong(parts[1]);

		return (hours * 3600) + (minutes * 60);
	}

	public static boolean matches(@NonNull final String hmmValue)
	{
		final Matcher matcher = hmmPattern.matcher(hmmValue);

		return matcher.matches();
	}
}
