package de.metas.util.time;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class DurationUtils
{
	public static Duration toDuration(@NonNull final BigDecimal durationBD, @NonNull final TemporalUnit unit)
	{
		final long durationLong = durationBD.setScale(0, RoundingMode.UP).longValueExact();
		return Duration.of(durationLong, unit);
	}

	public static BigDecimal toBigDecimal(@NonNull final Duration duration, @NonNull final TemporalUnit unit)
	{
		return BigDecimal.valueOf(toLong(duration, unit));
	}

	public static int toInt(@NonNull final Duration duration, @NonNull final TemporalUnit unit)
	{
		return (int)toLong(duration, unit);
	}

	private static long toLong(@NonNull final Duration duration, @NonNull final TemporalUnit unit)
	{
		if (unit == ChronoUnit.SECONDS)
		{
			return duration.getSeconds();
		}
		else if (unit == ChronoUnit.MINUTES)
		{
			return duration.toMinutes();
		}
		else if (unit == ChronoUnit.HOURS)
		{
			return duration.toHours();
		}
		else if (unit == ChronoUnit.DAYS)
		{
			return duration.toDays();
		}
		else
		{
			throw Check.newException("Cannot convert " + duration + " to " + unit);
		}
	}

}
