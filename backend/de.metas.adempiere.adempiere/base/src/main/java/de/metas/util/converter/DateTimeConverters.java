/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.util.converter;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Function;

@UtilityClass
public class DateTimeConverters
{
	@Nullable
	public static Object fromObject(@NonNull final Object valueObj, final int displayType, @NonNull final ZoneId zoneId)
	{
		switch (displayType)
		{
			case DisplayType.Date:
				return fromObjectToLocalDate(valueObj, zoneId);
			case DisplayType.Time:
				return fromObjectToLocalTime(valueObj, zoneId);
			case DisplayType.DateTime:
			{
				try
				{
					return fromObjectToZonedDateTime(valueObj, zoneId);
				}
				catch (final Exception e)
				{
					return fromObjectToInstant(valueObj, zoneId);
				}
			}
		}

		throw new AdempiereException("Invalid date/time type: " + displayType);
	}

	@NonNull
	public static LocalDate fromJsonToLocalDate(final String valueStr)
	{
		final JSONDateConfig config = JSONDateConfig.DEFAULT;
		return LocalDate.parse(valueStr, config.getLocalDateFormatter());
	}

	@NonNull
	public static LocalTime fromJsonToLocalTime(final String valueStr)
	{
		final JSONDateConfig config = JSONDateConfig.DEFAULT;
		return LocalTime.parse(valueStr, config.getLocalTimeFormatter());
	}

	@NonNull
	public static ZonedDateTime fromJsonToZonedDateTime(final String valueStr)
	{
		final JSONDateConfig config = JSONDateConfig.DEFAULT;
		return ZonedDateTime.parse(valueStr, config.getZonedDateTimeFormatter());
	}

	@NonNull
	public static Instant fromJsonToInstant(final String valueStr)
	{
		final JSONDateConfig config = JSONDateConfig.DEFAULT;
		return ZonedDateTime.parse(valueStr, config.getTimestampFormatter())
				.toInstant();
	}

	@Nullable
	private static LocalDate fromObjectToLocalDate(final Object valueObj, final ZoneId zoneId)
	{
		return fromObjectTo(valueObj,
							LocalDate.class,
							DateTimeConverters::fromJsonToLocalDate,
							(object) -> TimeUtil.asLocalDate(object, zoneId));
	}

	@Nullable
	private static LocalTime fromObjectToLocalTime(final Object valueObj, final ZoneId zoneId)
	{
		return fromObjectTo(valueObj,
							LocalTime.class,
							DateTimeConverters::fromJsonToLocalTime,
							(object) -> TimeUtil.asLocalTime(object, zoneId));
	}

	@Nullable
	private static ZonedDateTime fromObjectToZonedDateTime(final Object valueObj, final ZoneId zoneId)
	{
		return fromObjectTo(valueObj,
							ZonedDateTime.class,
							DateTimeConverters::fromJsonToZonedDateTime,
							(object) -> TimeUtil.asZonedDateTime(object, zoneId));
	}

	@Nullable
	private static Instant fromObjectToInstant(final Object valueObj, final ZoneId zoneId)
	{
		return fromObjectTo(valueObj,
							Instant.class,
							DateTimeConverters::fromJsonToInstant,
							(object) -> TimeUtil.asInstant(object, zoneId));
	}

	@Nullable
	private static <T> T fromObjectTo(
			@NonNull final Object valueObj,
			@NonNull final Class<T> type,
			@NonNull final Function<String, T> fromJsonConverter,
			@NonNull final Function<Object, T> fromObjectConverter)
	{
		if (type.isInstance(valueObj))
		{
			return type.cast(valueObj);
		}
		else if (valueObj instanceof CharSequence)
		{
			final String json = valueObj.toString().trim();
			if (json.isEmpty())
			{
				return null;
			}
			if (json.length() == 21 && json.charAt(10) == ' ') // json string - possible in JDBC format (`2016-06-11 00:00:00.0`)
			{
				final Timestamp timestamp = Timestamp.valueOf(json);
				return fromObjectConverter.apply(timestamp);
			}
			else
			{
				return fromJsonConverter.apply(json);
			}
		}
		else
		{
			return fromObjectConverter.apply(valueObj);
		}
	}
}
