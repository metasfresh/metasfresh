package de.metas.util.time;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Please consider {@link de.metas.common.util.time.SystemTime}.
 */
@UtilityClass
public class SystemTime
{
	public long millis()
	{
		return de.metas.common.util.time.SystemTime.millis();
	}

	/**
	 * For WEBUI/Json please use de.metas.ui.web.window.datatypes.json.DateTimeConverters#toJson(java.time.ZonedDateTime, java.time.ZoneId)
	 * For NON webui please use use de.metas.organization.IOrgDAO.getTimeZone(OrgId)
	 */
	public ZoneId zoneId()
	{
		return de.metas.common.util.time.SystemTime.zoneId();
	}

	public GregorianCalendar asGregorianCalendar()
	{
		return de.metas.common.util.time.SystemTime.asGregorianCalendar();
	}

	public Date asDate()
	{
		return de.metas.common.util.time.SystemTime.asDate();
	}

	public Timestamp asTimestamp()
	{
		return de.metas.common.util.time.SystemTime.asTimestamp();
	}

	public Instant asInstant()
	{
		return de.metas.common.util.time.SystemTime.asInstant();
	}

	public LocalDateTime asLocalDateTime()
	{
		return de.metas.common.util.time.SystemTime.asLocalDateTime();
	}

	@NonNull
	public LocalDate asLocalDate()
	{
		return de.metas.common.util.time.SystemTime.asLocalDate();
	}

	public ZonedDateTime asZonedDateTime()
	{
		return de.metas.common.util.time.SystemTime.asZonedDateTime();
	}

	public ZonedDateTime asZonedDateTimeAtStartOfDay()
	{
		return de.metas.common.util.time.SystemTime.asZonedDateTimeAtStartOfDay();
	}

	public ZonedDateTime asZonedDateTime(@NonNull final ZoneId zoneId)
	{
		return de.metas.common.util.time.SystemTime.asZonedDateTime(zoneId);
	}

	/**
	 * Same as {@link #asTimestamp()} but the returned date will be truncated to DAY.
	 */
	public Timestamp asDayTimestamp()
	{
		return de.metas.common.util.time.SystemTime.asDayTimestamp();
	}

	/**
	 * "Why not go with {@link #asDayTimestamp()}" you ask?
	 * See https://stackoverflow.com/questions/8929242/compare-date-object-with-a-timestamp-in-java
	 */
	public Date asDayDate()
	{
		return de.metas.common.util.time.SystemTime.asDayDate();
	}

	/**
	 * After invocation of this method, the time returned will be the system
	 * time again.
	 */
	public void resetTimeSource()
	{
		de.metas.common.util.time.SystemTime.resetTimeSource();
	}

	/**
	 * @param newTimeSource the given TimeSource will be used for the time returned by the
	 *                      methods of this class (unless it is null).
	 */
	public void setTimeSource(@NonNull final de.metas.common.util.time.TimeSource newTimeSource)
	{
		de.metas.common.util.time.SystemTime.setTimeSource(newTimeSource);
	}

	public void setFixedTimeSource(@NonNull final ZonedDateTime date)
	{
		de.metas.common.util.time.SystemTime.setFixedTimeSource(date);
	}

	public void setFixedTimeSource(@NonNull final String zonedDateTime)
	{
		de.metas.common.util.time.SystemTime.setFixedTimeSource(zonedDateTime);
	}
}
