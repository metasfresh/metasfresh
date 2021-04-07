package de.metas.ui.web.dashboard;

import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Builder
@Value
public class KPITimeRangeDefaults
{
	public static final KPITimeRangeDefaults DEFAULT = builder().build();

	Duration defaultTimeRange;
	Duration defaultTimeRangeEndOffset;

	public TimeRange createTimeRange(
			@Nullable final Instant from,
			@Nullable final Instant to)
	{
		final Instant toEffective = to != null ? to : calculateTo();
		final Instant fromEffective = from != null ? from : calculateFrom(toEffective);
		return TimeRange.main(fromEffective, toEffective);
	}

	public TimeRange createTimeRange(
			@Nullable final java.util.Date dateFrom,
			@Nullable final java.util.Date dateTo)
	{
		final Instant from = dateFrom == null ? null : dateFrom.toInstant();
		final Instant to = dateTo == null ? null : dateTo.toInstant();
		return createTimeRange(from, to);
	}

	private Instant calculateTo()
	{
		Instant to = SystemTime.asInstant();
		final Duration defaultTimeRangeEndOffset = getDefaultTimeRangeEndOffset();
		if (defaultTimeRangeEndOffset != null)
		{
			to = to.plus(defaultTimeRangeEndOffset);
		}

		return to;
	}

	private Instant calculateFrom(@NonNull final Instant to)
	{
		final Duration defaultTimeRange = getDefaultTimeRange();
		if (defaultTimeRange == null || defaultTimeRange.isZero())
		{
			return Instant.MIN;
		}
		else
		{
			return to.minus(defaultTimeRange.abs());
		}
	}

	public KPITimeRangeDefaults compose(final KPITimeRangeDefaults fallback)
	{
		return builder()
				.defaultTimeRange(CoalesceUtil.coalesce(getDefaultTimeRange(), fallback.getDefaultTimeRange()))
				.defaultTimeRangeEndOffset(CoalesceUtil.coalesce(getDefaultTimeRangeEndOffset(), fallback.getDefaultTimeRangeEndOffset()))
				.build();
	}

	public static class KPITimeRangeDefaultsBuilder
	{
		public KPITimeRangeDefaultsBuilder defaultTimeRangeFromString(final String defaultTimeRangeStr)
		{
			return defaultTimeRange(parseDurationOrNull(defaultTimeRangeStr));
		}

		public KPITimeRangeDefaultsBuilder defaultTimeRangeEndOffsetFromString(final String defaultTimeRangeEndOffsetStr)
		{
			return defaultTimeRangeEndOffset(parseDurationOrNull(defaultTimeRangeEndOffsetStr));
		}

		@Nullable
		private static Duration parseDurationOrNull(@Nullable final String durationStr)
		{
			return durationStr == null || Check.isBlank(durationStr) ? null : Duration.parse(durationStr);
		}
	}

}
