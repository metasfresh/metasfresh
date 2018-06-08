package de.metas.i18n;

import java.text.SimpleDateFormat;
import java.util.Set;

import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;

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
public final class DateTimeTranslatableString implements ITranslatableString
{
	public static final DateTimeTranslatableString ofDate(final java.util.Date date)
	{
		return new DateTimeTranslatableString(date.getTime(), false);
	}

	public static final DateTimeTranslatableString ofDateTime(final java.util.Date date)
	{
		return new DateTimeTranslatableString(date.getTime(), true);
	}

	private final long timestamp;
	private final int displayType;

	private DateTimeTranslatableString(final long timestamp, final boolean dateTime)
	{
		this.timestamp = timestamp;
		displayType = dateTime ? DisplayType.DateTime : DisplayType.Date;
	}

	@Override
	public String translate(final String adLanguage)
	{
		final Language language = Language.getLanguage(adLanguage);
		final SimpleDateFormat dateFormat = DisplayType.getDateFormat(displayType, language);
		final String dateStr = dateFormat.format(new java.util.Date(timestamp));
		return dateStr;
	}

	@Override
	public String getDefaultValue()
	{
		final SimpleDateFormat dateFormat = DisplayType.getDateFormat(displayType);
		final String dateStr = dateFormat.format(new java.util.Date(timestamp));
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
