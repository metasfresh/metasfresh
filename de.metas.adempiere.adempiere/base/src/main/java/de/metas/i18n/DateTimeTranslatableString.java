package de.metas.i18n;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
@EqualsAndHashCode
public final class DateTimeTranslatableString implements ITranslatableString
{
	public static final DateTimeTranslatableString ofDate(@NonNull final java.util.Date date)
	{
		return new DateTimeTranslatableString(date.getTime(), false);
	}

	public static final DateTimeTranslatableString ofDate(@NonNull final LocalDate date)
	{
		final long epochMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
		final boolean dateTime = false;
		return new DateTimeTranslatableString(epochMillis, dateTime);
	}

	public static final DateTimeTranslatableString ofDateTime(@NonNull final java.util.Date date)
	{
		return new DateTimeTranslatableString(date.getTime(), true);
	}

	public static final DateTimeTranslatableString ofDateTime(@NonNull final LocalDateTime date)
	{
		final long epochMillis = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		final boolean dateTime = true;
		return new DateTimeTranslatableString(epochMillis, dateTime);
	}

	public static final DateTimeTranslatableString ofDateTime(@NonNull final ZonedDateTime date)
	{
		final long epochMillis = date.toInstant().toEpochMilli();
		final boolean dateTime = true;
		return new DateTimeTranslatableString(epochMillis, dateTime);
	}

	public static final DateTimeTranslatableString ofTime(@NonNull final LocalTime time)
	{
		final long epochMillis = TimeUtil.asLocalDateTime(time).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return new DateTimeTranslatableString(epochMillis, DisplayType.Time);
	}

	public static final DateTimeTranslatableString ofObject(@NonNull final Object obj)
	{
		final int displayType = -1;
		return ofObject(obj, displayType);
	}

	public static final DateTimeTranslatableString ofObject(@NonNull final Object obj, final int displayType)
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
			else
			{
				return ofDateTime(date);
			}
		}
		else if (obj instanceof LocalDate)
		{
			return ofDate((LocalDate)obj);
		}
		else if (obj instanceof LocalDateTime)
		{
			return ofDateTime((LocalDateTime)obj);
		}
		else if (obj instanceof ZonedDateTime)
		{
			return ofDateTime((ZonedDateTime)obj);
		}
		else if (obj instanceof LocalTime)
		{
			return ofTime((LocalTime)obj);
		}
		else
		{
			throw new AdempiereException("Cannot create " + DateTimeTranslatableString.class + " from " + obj + " (" + obj.getClass() + ")");
		}
	}

	private final long epochMillis;
	private final int displayType;

	private DateTimeTranslatableString(final long epochMillis, final boolean dateTime)
	{
		this.epochMillis = epochMillis;
		displayType = dateTime ? DisplayType.DateTime : DisplayType.Date;
	}

	private DateTimeTranslatableString(final long epochMillis, final int displayType)
	{
		this.epochMillis = epochMillis;
		this.displayType = displayType;
	}

	@Override
	public String translate(final String adLanguage)
	{
		final Language language = Language.getLanguage(adLanguage);
		final SimpleDateFormat dateFormat = DisplayType.getDateFormat(displayType, language);
		final String dateStr = dateFormat.format(new java.util.Date(epochMillis));
		return dateStr;
	}

	@Override
	public String getDefaultValue()
	{
		final SimpleDateFormat dateFormat = DisplayType.getDateFormat(displayType);
		final String dateStr = dateFormat.format(new java.util.Date(epochMillis));
		return dateStr;
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
