package de.metas.ui.web.session;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.compiere.util.CCache;
import org.compiere.util.DisplayType;

import de.metas.i18n.Language;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Value
public class UserSessionLocale
{
	public static final UserSessionLocale get(@NonNull final String adLanguage)
	{
		return cache.getOrLoad(adLanguage, () -> new UserSessionLocale(adLanguage));
	}

	private static final CCache<String, UserSessionLocale> cache = CCache.newCache(UserSessionLocale.class.getName(), 10, 0);

	private final String adLanguage;
	private final char numberDecimalSeparator;
	private final char numberGroupingSeparator;

	private UserSessionLocale(final String adLanguage)
	{
		final Language language = Language.getLanguage(adLanguage);
		if (language == null)
		{
			throw new IllegalArgumentException("No language found for " + adLanguage);
		}
		this.adLanguage = language.getAD_Language();

		final DecimalFormat decimalFormat = DisplayType.getNumberFormat(DisplayType.Amount, language);
		final DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
		numberDecimalSeparator = decimalFormatSymbols.getDecimalSeparator();
		numberGroupingSeparator = decimalFormatSymbols.getGroupingSeparator();
	}
}
