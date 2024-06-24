package de.metas.i18n;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.TimeZone;


/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@EqualsAndHashCode
final class DateTimeTranslatableString implements ITranslatableString
{
	static DateTimeTranslatableString ofDate(@NonNull final java.util.Date date)
	{
		return new DateTimeTranslatableString(date.toInstant(), false);
	}

	static DateTimeTranslatableString ofDate(@NonNull final LocalDate date)
	{
		final Instant instant = date.atStartOfDay(SystemTime.zoneId()).toInstant();
		final boolean dateTime = false;
		return new DateTimeTranslatableString(instant, dateTime);
	}

	static DateTimeTranslatableString ofDateTime(@NonNull final java.util.Date date)
	{
		return new DateTimeTranslatableString(date.toInstant(), true);
	}

	public static DateTimeTranslatableString ofDateTime(@NonNull final ZonedDateTime date)
	{
		final Instant instant = date.toInstant();
		final boolean dateTime = true;
		return new DateTimeTranslatableString(instant, dateTime);
	}

	public static DateTimeTranslatableString ofDateTime(@NonNull final LocalDateTime localDateTime)
	{
		final Instant instant = localDateTime.atZone(SystemTime.zoneId()).toInstant();
		final boolean dateTime = true;
		return new DateTimeTranslatableString(instant, dateTime);
	}

	public static DateTimeTranslatableString ofDateTime(@NonNull final Instant instant)
	{
		final boolean dateTime = true;
		return new DateTimeTranslatableString(instant, dateTime);
	}

	private static DateTimeTranslatableString ofTime(@NonNull final LocalTime time)
	{
		final Instant instant = LocalDate.now()
				.atTime(time)
				.atZone(SystemTime.zoneId())
				.toInstant();

		return new DateTimeTranslatableString(instant, DisplayType.Time);
	}

	static DateTimeTranslatableString ofObject(@NonNull final Object obj)
	{
		return ofObject(obj, -1);
	}

	static DateTimeTranslatableString ofObject(@NonNull final Object obj, final int displayType)
	{
		if (obj instanceof java.util.Date)
		{
			final java.util.Date date = (java.util.Date)obj;

			if (displayType == DisplayType.Date)
			{
				return ofDate(date);
			}
			else if (displayType == DisplayType.DateTime)
			{
				return ofDateTime(date);
			}
			else // default:
			{
				return ofDateTime(date);
			}
		}
		else if (obj instanceof LocalDate)
		{
			return ofDate((LocalDate)obj);
		}
		else if (obj instanceof LocalTime)
		{
			return ofTime((LocalTime)obj);
		}
		else if (obj instanceof LocalDateTime)
		{
			return ofDateTime((LocalDateTime)obj);
		}
		else if (obj instanceof Instant)
		{
			return ofDateTime((Instant)obj);
		}
		else if (obj instanceof ZonedDateTime)
		{
			return ofDateTime((ZonedDateTime)obj);
		}
		else if (obj instanceof InstantAndOrgId)
		{
			return ofDateTime(((InstantAndOrgId)obj).toInstant());
		}
		else if (obj instanceof LocalDateAndOrgId)
		{
			return ofDate(((LocalDateAndOrgId)obj).toLocalDate());
		}
		else
		{
			throw new AdempiereException("Cannot create " + DateTimeTranslatableString.class + " from " + obj + " (" + obj.getClass() + ")");
		}
	}

	private final Instant instant;
	private final int displayType;

	private DateTimeTranslatableString(@NonNull final Instant instant, final boolean dateTime)
	{
		this(instant,
				dateTime ? DisplayType.DateTime : DisplayType.Date);
	}

	private DateTimeTranslatableString(@NonNull final Instant instant, final int displayType)
	{
		this.instant = instant;
		this.displayType = displayType;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getDefaultValue();
	}

	@Override
	public String translate(final String adLanguage)
	{
		final Language language = Language.getLanguage(adLanguage);
		final SimpleDateFormat dateFormat = DisplayType.getDateFormat(displayType, language);
		dateFormat.setTimeZone(TimeZone.getTimeZone(SystemTime.zoneId()));
		return dateFormat.format(toDate());
	}

	private java.util.Date toDate()
	{
		return java.util.Date.from(instant);
	}

	@Override
	public String getDefaultValue()
	{
		final SimpleDateFormat dateFormat = DisplayType.getDateFormat(displayType);
		dateFormat.setTimeZone(TimeZone.getTimeZone(SystemTime.zoneId()));
		return dateFormat.format(toDate());
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return ImmutableSet.of();
	}

	@Override
	public boolean isTranslatedTo(final String adLanguage)
	{
		return true;
	}
}
