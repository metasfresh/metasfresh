package de.metas.util.time;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
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
	private static final List<TemporalUnit> supportedTemporalUnits = ImmutableList.of(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS, ChronoUnit.HOURS, ChronoUnit.MINUTES, ChronoUnit.SECONDS, ChronoUnit.NANOS);

	public static Duration toWorkDuration(@NonNull final BigDecimal durationBD, @NonNull final TemporalUnit unit)
	{
		return toWorkDuration(durationBD, unit, 4, RoundingMode.UNNECESSARY);
	}

	public static Duration toWorkDuration(final @NonNull BigDecimal durationBD, final @NonNull TemporalUnit unit, final int precision, final RoundingMode roundingMode)
	{
		BigDecimal duration = durationBD;
		TemporalUnit currentUnit = unit;
		long durationLong = -1;
		while (durationLong < 0)
		{
			try
			{
				durationLong = duration.setScale(precision, roundingMode).longValueExact();
			}
			catch
			(final ArithmeticException ae)
			{
				duration = getEquivalentInSmallerTemporalUnit(duration, currentUnit);
				currentUnit = supportedTemporalUnits.get(supportedTemporalUnits.indexOf(currentUnit) + 1);
			}
		}
		Check.assumeGreaterOrEqualToZero(durationLong, StringUtils.formatMessage("For {} only natural numbers are supported", unit));
		return Duration.of(durationLong, currentUnit);
	}

	public static Duration toWorkDurationRoundUp(@NonNull final BigDecimal durationBD, @NonNull final TemporalUnit unit)
	{
		return toWorkDuration(durationBD, unit, 0, RoundingMode.UP);
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

	private static BigDecimal getEquivalentInSmallerTemporalUnit(@NonNull final BigDecimal durationBD, @NonNull final TemporalUnit unit)
	{
		if (unit == ChronoUnit.DAYS)
		{
			return durationBD.multiply(BigDecimal.valueOf(8));// This refers to work hours, not calendar hours
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

}
