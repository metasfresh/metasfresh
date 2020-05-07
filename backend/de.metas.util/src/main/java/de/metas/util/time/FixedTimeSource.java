package de.metas.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * A {@link TimeSource} implementation which returns a preset time.
 *
 * Mainly this class is used in testing.
 *
 * @author tsa
 *
 */
@EqualsAndHashCode
@ToString
public class FixedTimeSource implements TimeSource
{
	private final ZonedDateTime date;

	public static FixedTimeSource ofLocalDateTime(final LocalDateTime localDateTime)
	{
		return new FixedTimeSource(localDateTime.atZone(ZoneId.systemDefault()));
	}

	public static FixedTimeSource ofZonedDateTime(final ZonedDateTime zonedDateTime)
	{
		return new FixedTimeSource(zonedDateTime);
	}

	/**
	 *
	 * @param year
	 * @param month 1..12
	 * @param day
	 * @param hour 0..23
	 * @param minute
	 * @param second
	 */
	@Deprecated
	public FixedTimeSource(final int year, final int month, final int day, final int hour, final int minute, final int second)
	{
		this(LocalDate.of(year, month, day)
				.atTime(LocalTime.of(hour, minute, second))
				.atZone(ZoneId.systemDefault()));
	}

	/**
	 * @deprecated Please use {@link #ofLocalDateTime(LocalDateTime)}
	 */
	@Deprecated
	public FixedTimeSource(final LocalDateTime localDateTime)
	{
		this(localDateTime.atZone(ZoneId.systemDefault()));
	}

	private FixedTimeSource(@NonNull final ZonedDateTime date)
	{
		this.date = date;
	}

	@Override
	public long millis()
	{
		return date.toInstant().toEpochMilli();
	}

	@Override
	public ZoneId zoneId()
	{
		return date.getZone();
	}

	public ZonedDateTime asZonedDateTime()
	{
		return date;
	}
}
