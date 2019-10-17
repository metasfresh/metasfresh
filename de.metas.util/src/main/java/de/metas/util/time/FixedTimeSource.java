package de.metas.util.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.google.common.base.MoreObjects;

/**
 * A {@link TimeSource} implementation which returns a preset time.
 *
 * Mainly this class is used in testing.
 *
 * @author tsa
 *
 */
public class FixedTimeSource implements TimeSource
{
	private final long millis;

	/**
	 *
	 * @param year
	 * @param month 1..12
	 * @param day
	 * @param hour 0..23
	 * @param minute
	 * @param second
	 */
	public FixedTimeSource(final int year, final int month, final int day, final int hour, final int minute, final int second)
	{
		this(LocalDateTime.of(year, month, day, hour, minute, second));
	}

	public FixedTimeSource(final Date date)
	{
		millis = date.getTime();
	}

	public FixedTimeSource(final LocalDateTime date)
	{
		millis = date.atZone(ZoneId.systemDefault())
				.toInstant()
				.toEpochMilli();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("millis", millis)
				.add("date", Instant.ofEpochMilli(millis))
				.toString();
	}

	@Override
	public long millis()
	{
		return millis;
	}
}
