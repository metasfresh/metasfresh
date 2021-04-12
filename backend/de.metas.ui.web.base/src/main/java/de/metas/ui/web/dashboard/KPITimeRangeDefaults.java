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

@Builder
@Value
public class KPITimeRangeDefaults
{
	public static final KPITimeRangeDefaults DEFAULT = builder().build();

	@Nullable Duration defaultTimeRange;
	@Nullable Duration defaultTimeRangeEndOffset;

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
		if (defaultTimeRangeEndOffset != null)
		{
			to = to.plus(defaultTimeRangeEndOffset);
		}

		return to;
	}

	private Instant calculateFrom(@NonNull final Instant to)
	{
		if (defaultTimeRange == null || defaultTimeRange.isZero())
		{
			return Instant.ofEpochMilli(0);
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
