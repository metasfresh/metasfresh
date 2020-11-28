package de.metas.i18n;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
final class TimeZoneTranslatableString implements ITranslatableString
{
	public static TimeZoneTranslatableString ofZoneId(@NonNull final ZoneId zoneId, @NonNull final TextStyle textStyle)
	{
		return new TimeZoneTranslatableString(zoneId, textStyle);
	}

	private final ZoneId zoneId;
	private final TextStyle textStyle;

	private TimeZoneTranslatableString(@NonNull final ZoneId zoneId, @NonNull final TextStyle textStyle)
	{
		this.zoneId = zoneId;
		this.textStyle = textStyle;
	}

	@Deprecated
	@Override
	public String toString()
	{
		return zoneId.toString();
	}

	@Override
	public String translate(final String adLanguage)
	{
		final Language language = Language.getLanguage(adLanguage);
		final Locale locale = language.getLocale();
		return zoneId.getDisplayName(textStyle, locale);
	}

	@Override
	public String getDefaultValue()
	{
		return zoneId.getId();
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return ImmutableSet.of();
	}

}
