package de.metas.util.time;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

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
	private static final List<TemporalUnit> supportedTemporalUnits = ImmutableList.of(ChronoUnit.DAYS, ChronoUnit.HOURS, ChronoUnit.MINUTES, ChronoUnit.SECONDS, ChronoUnit.MILLIS);
	public static final int WORK_HOURS_PER_DAY = 8;

	/**
	 * Calculated an integer duration from a possibly non-integer source, by using a lower temporal unit (if needed). To be used in a "work" context, where a work day is equivalent to 8 hours.
	 * *<p>Example:
	 * <table border>
	 * <caption><b>Work duration conversion example</b></caption>
	 * <tr valign=top><th>Input value</th><th>Input {@link TemporalUnit}</th>
	 * <th>Output value</th><th>Output {@link TemporalUnit}</th>
	 * </tr>
	 * <tr><td align=right>0.5</td>  <td>{@link ChronoUnit#DAYS}</td><td align=right>4</td>  <td>{@link ChronoUnit#HOURS}</td></tr>
	 * <tr><td align=right>0.5</td>  <td>{@link ChronoUnit#HOURS}</td><td align=right>30</td>  <td>{@link ChronoUnit#MINUTES}</td></tr>
	 * <tr><td align=right>0.5</td>  <td>{@link ChronoUnit#MINUTES}</td><td align=right>30</td>  <td>{@link ChronoUnit#SECONDS}</td></tr>
	 * <tr><td align=right>0.5</td>  <td>{@link ChronoUnit#SECONDS}</td><td align=right>500</td>  <td>{@link ChronoUnit#MILLIS}</td></tr>
	 * </table>
	 * </p>
	 *
	 * @param duration the possibly non-integer duration value
	 * @param unit     the unit in which duration is expressed
	 * @return a {@link java.time.Duration} equivalent to the value and given time unit, but which is expressed as a Natural number.
	 * @throws RuntimeException if the given {@code duration} cannot be expressed as a natural number by using a smaller Temporal unit.
	 */
	public static Duration toWorkDuration(final @NonNull BigDecimal duration, final @NonNull TemporalUnit unit)
	{
		Check.assumeGreaterOrEqualToZero(duration, "Only positive work durations can be used");

		BigDecimal currentDuration = duration;
		TemporalUnit currentUnit = unit;

		while (NumberUtils.stripTrailingDecimalZeros(currentDuration).scale() != 0 && !currentUnit.equals(ChronoUnit.NANOS))
		{
			currentDuration = getEquivalentInSmallerTemporalUnit(currentDuration, currentUnit);
			currentUnit = supportedTemporalUnits.get(supportedTemporalUnits.indexOf(currentUnit) + 1);
		}
		final boolean longValueFound = NumberUtils.stripTrailingDecimalZeros(currentDuration).scale() == 0;
		Check.assume(longValueFound, StringUtils.formatMessage("For {} only natural numbers are supported", unit));
		final long durationLong = currentDuration.longValueExact();
		return Duration.of(durationLong, currentUnit);
	}

	public static Duration toWorkDurationRoundUp(@NonNull final BigDecimal durationBD, @NonNull final TemporalUnit unit)
	{
		return toWorkDuration(durationBD.setScale(0, RoundingMode.UP), unit);
	}

	public static BigDecimal toBigDecimal(@NonNull final Duration duration, @NonNull final TemporalUnit unit)
	{
		return BigDecimal.valueOf(toLong(duration, unit));
	}

	public static Duration fromBigDecimal(@NonNull final BigDecimal duration, @NonNull final TemporalUnit unit)
	{
		return Duration.of(duration.longValue(), unit);
	}

	public static int toInt(@NonNull final Duration duration, @NonNull final TemporalUnit unit)
	{
		return (int)toLong(duration, unit);
	}

	public static long toLong(@NonNull final Duration duration, @NonNull final TemporalUnit unit)
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

	private static BigDecimal getEquivalentInSmallerTemporalUnit(@NonNull final BigDecimal durationBD, @NonNull final TemporalUnit unit)
	{
		if (unit == ChronoUnit.DAYS)
		{
			return durationBD.multiply(BigDecimal.valueOf(WORK_HOURS_PER_DAY));// This refers to work hours, not calendar hours
		}
		if (unit == ChronoUnit.HOURS)
		{
			return durationBD.multiply(BigDecimal.valueOf(60));
		}
		if (unit == ChronoUnit.MINUTES)
		{
			return durationBD.multiply(BigDecimal.valueOf(60));
		}
		if (unit == ChronoUnit.SECONDS)
		{
			return durationBD.multiply(BigDecimal.valueOf(1000));
		}
		throw Check.newException("No smaller temporal unit defined for {}", unit);
	}

	public static boolean isCompleteDays(@NonNull final Duration duration)
	{
		if (duration.isZero())
		{
			return true;
		}

		final Duration daysAsDuration = Duration.ofDays(duration.toDays());
		return daysAsDuration.equals(duration);
	}
}
