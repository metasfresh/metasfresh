package de.metas.ui.web.window.datatypes.json;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;

import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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
public final class DateTimeConverters
{
	private static JSONDateConfig getConfig()
	{
		return JSONDateConfig.DEFAULT;
	}

	public static String toJson(@NonNull final LocalDate localDate)
	{
		final JSONDateConfig config = getConfig();
		return toJson(localDate, config);
	}

	@VisibleForTesting
	static String toJson(@NonNull final LocalDate localDate, @NonNull final JSONDateConfig config)
	{
		final DateTimeFormatter formatter = config.getLocalDateFormatter();
		return formatter.format(localDate);
	}

	public static String toJson(@NonNull final Instant instant, @NonNull final ZoneId zoneId)
	{
		final JSONDateConfig config = getConfig();
		return toJson(instant, zoneId, config);
	}

	@VisibleForTesting
	static String toJson(@NonNull final Instant instant, @NonNull final ZoneId zoneId, @NonNull final JSONDateConfig config)
	{
		final ZonedDateTime dateConv = instant.atZone(zoneId);
		return config.getZonedDateTimeFormatter().format(dateConv);
	}

	public static String toJson(@NonNull final LocalTime localTime)
	{
		final JSONDateConfig config = getConfig();
		return toJson(localTime, config);
	}

	public static String toJson(@NonNull final LocalTime localTime, @NonNull final JSONDateConfig config)
	{
		final DateTimeFormatter formatter = config.getLocalTimeFormatter();
		return formatter.format(localTime);
	}

	public static String toJson(@NonNull final ZonedDateTime zonedDateTime, @NonNull final ZoneId zoneId)
	{
		final JSONDateConfig config = getConfig();
		return toJson(zonedDateTime, zoneId, config);
	}

	@VisibleForTesting
	static String toJson(@NonNull final ZonedDateTime zonedDateTime, @NonNull final ZoneId zoneId, @NonNull final JSONDateConfig config)
	{
		final ZonedDateTime dateConv = TimeUtil.convertToTimeZone(zonedDateTime, zoneId);
		return config.getZonedDateTimeFormatter().format(dateConv);
	}

	public static String toJson(@NonNull final ZoneId zoneId)
	{
		return zoneId.getId();
	}

	public static Object fromJson(
			@Nullable final String valueStr,
			@NonNull final DocumentFieldWidgetType widgetType)
	{
		return fromObject(valueStr, widgetType);
	}

	public static Object fromObject(
			@Nullable final Object valueObj,
			@NonNull final DocumentFieldWidgetType widgetType)
	{
		if (widgetType == DocumentFieldWidgetType.LocalDate)
		{
			return fromObjectToLocalDate(valueObj);
		}
		else if (widgetType == DocumentFieldWidgetType.LocalTime)
		{
			return fromObjectToLocalTime(valueObj);
		}
		else if (widgetType == DocumentFieldWidgetType.ZonedDateTime)
		{
			return fromObjectToZonedDateTime(valueObj);
		}
		else if (widgetType == DocumentFieldWidgetType.Timestamp)
		{
			return fromObjectToInstant(valueObj);
		}
		else
		{
			throw new AdempiereException("Invalid date/time type: " + widgetType);
		}
	}

	public static LocalDate fromObjectToLocalDate(final Object valueObj)
	{
		return fromObjectTo(valueObj,
				LocalDate.class,
				DateTimeConverters::fromJsonToLocalDate,
				TimeUtil::asLocalDate);
	}

	private static LocalDate fromJsonToLocalDate(final String valueStr)
	{
		final JSONDateConfig config = getConfig();
		return LocalDate.parse(valueStr, config.getLocalDateFormatter());
	}

	private static LocalTime fromObjectToLocalTime(final Object valueObj)
	{
		return fromObjectTo(valueObj,
				LocalTime.class,
				DateTimeConverters::fromJsonToLocalTime,
				TimeUtil::asLocalTime);
	}

	private static LocalTime fromJsonToLocalTime(final String valueStr)
	{
		final JSONDateConfig config = getConfig();
		return LocalTime.parse(valueStr, config.getLocalTimeFormatter());
	}

	public static ZonedDateTime fromObjectToZonedDateTime(final Object valueObj)
	{
		return fromObjectTo(valueObj,
				ZonedDateTime.class,
				DateTimeConverters::fromJsonToZonedDateTime,
				TimeUtil::asZonedDateTime);
	}

	private static ZonedDateTime fromJsonToZonedDateTime(final String valueStr)
	{
		final JSONDateConfig config = getConfig();
		return ZonedDateTime.parse(valueStr, config.getZonedDateTimeFormatter());
	}

	public static Instant fromObjectToInstant(final Object valueObj)
	{
		return fromObjectTo(valueObj,
				Instant.class,
				DateTimeConverters::fromJsonToInstant,
				TimeUtil::asInstant);
	}

	private static Instant fromJsonToInstant(final String valueStr)
	{
		final JSONDateConfig config = getConfig();
		return ZonedDateTime.parse(valueStr, config.getTimestampFormatter())
				.toInstant();
	}

	private static <T> T fromObjectTo(
			final Object valueObj,
			@NonNull final Class<T> type,
			@NonNull final Function<String, T> fromJsonConverer,
			@NonNull final Function<Object, T> fromObjectConverter)
	{
		if (valueObj == null
				|| JSONNullValue.isNull(valueObj))
		{
			return null;
		}
		else if (type.isInstance(valueObj))
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
			else
			{
				return fromJsonConverer.apply(json);
			}
		}
		else if (valueObj instanceof StringLookupValue)
		{
			final String key = ((StringLookupValue)valueObj).getIdAsString();
			if (Check.isEmpty(key))
			{
				return null;
			}
			else
			{
				return fromJsonConverer.apply(key);
			}
		}
		else
		{
			return fromObjectConverter.apply(valueObj);
		}
	}
}
