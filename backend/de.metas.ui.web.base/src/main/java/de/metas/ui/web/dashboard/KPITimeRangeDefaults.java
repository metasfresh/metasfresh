package de.metas.ui.web.dashboard;

import java.time.Duration;

import de.metas.common.util.time.SystemTime;
import de.metas.printing.esb.base.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.Value;

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

	private final Duration defaultTimeRange;
	private final Duration defaultTimeRangeEndOffset;

	public TimeRange createTimeRange(long fromMillis, long toMillis)
	{
		if (toMillis <= 0)
		{
			toMillis = calculateToMillis();
		}

		if (fromMillis <= 0)
		{
			fromMillis = calculateFromMillis(toMillis);
		}

		return TimeRange.main(fromMillis, toMillis);
	}
	
	public TimeRange createTimeRange(final java.util.Date dateFrom, final java.util.Date dateTo)
	{
		final long fromMillis = dateFrom == null ? -1 : dateFrom.getTime();
		final long toMillis = dateTo == null ? -1 : dateTo.getTime();
		return createTimeRange(fromMillis, toMillis);
	}

	private long calculateToMillis()
	{
		long toMillis = SystemTime.millis();
		final Duration defaultTimeRangeEndOffset = getDefaultTimeRangeEndOffset();
		if (defaultTimeRangeEndOffset != null)
		{
			toMillis += defaultTimeRangeEndOffset.toMillis();
		}

		return toMillis;
	}

	private long calculateFromMillis(final long toMillis)
	{
		final Duration defaultTimeRange = getDefaultTimeRange();
		if (defaultTimeRange == null || defaultTimeRange.isZero())
		{
			return 0;
		}
		else
		{
			return toMillis - defaultTimeRange.abs().toMillis();
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

		private static final Duration parseDurationOrNull(final String durationStr)
		{
			return Check.isEmpty(durationStr, true) ? null : Duration.parse(durationStr);
		}
	}

}
