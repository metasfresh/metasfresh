package de.metas.ui.web.dashboard;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.Instant;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class TimeRange
{
	public static TimeRange main(@NonNull final Instant from, @NonNull final Instant to)
	{
		return new TimeRange(true, from, to, Duration.ZERO);
	}

	public static TimeRange offset(@NonNull final TimeRange mainRange, @NonNull final Duration offset)
	{
		final boolean mainTimeRange = false;
		final Instant from = mainRange.getFrom().plus(offset);
		final Instant to = mainRange.getTo().plus(offset);
		return new TimeRange(mainTimeRange, from, to, offset);
	}

	@JsonProperty("fromMillis") long fromMillis;
	@JsonIgnore Instant from;

	@JsonProperty("toMillis") long toMillis;
	@JsonIgnore Instant to;

	@JsonIgnore boolean mainTimeRange;
	@JsonIgnore Duration offset;

	private TimeRange(
			final boolean mainTimeRange,
			@NonNull final Instant from,
			@NonNull final Instant to,
			@NonNull final Duration offset)
	{
		this.mainTimeRange = mainTimeRange;
		this.from = from;
		this.fromMillis = toMillis(from);
		this.to = to;
		this.toMillis = toMillis(to);
		this.offset = offset;
	}

	private static long toMillis(@NonNull final Instant instant)
	{
		return instant.isAfter(Instant.ofEpochMilli(0))
				? instant.toEpochMilli()
				: 0;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("from", from)
				.add("to", to)
				.add("main", mainTimeRange)
				.add("offset", offset)
				.toString();
	}

	public TimeRange offset(@NonNull final Duration offset)
	{
		final boolean mainTimeRange = false;
		final Instant from = this.from.plus(offset);
		final Instant to = this.to.plus(offset);
		return new TimeRange(mainTimeRange, from, to, offset);
	}
}
