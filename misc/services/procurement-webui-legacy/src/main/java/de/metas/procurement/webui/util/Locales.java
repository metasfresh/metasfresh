package de.metas.procurement.webui.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * #%L
 * de.metas.procurement.webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class Locales
{
	private static final Logger logger = LoggerFactory.getLogger(Locales.class);

	/**
	 * Gets Locale
	 *
	 * @param languageAndCountry language and country (e.g. de_CH)
	 * @param defaultLocale default locale to return if no locale were found
	 * @return locale
	 */
	public static final Locale forStringOrDefault(final String languageAndCountry, final Locale defaultLocale)
	{
		if (languageAndCountry == null || languageAndCountry.isEmpty())
		{
			return defaultLocale;
		}

		try
		{
			final String[] localeParts = languageAndCountry.trim().split("_");
			if (localeParts.length == 0)
			{
				// shall not happen
				return defaultLocale;
			}
			else if (localeParts.length == 1)
			{
				final String language = localeParts[0];
				return new Locale(language);
			}
			else if (localeParts.length == 2)
			{
				final String language = localeParts[0];
				final String country = localeParts[1];
				return new Locale(language, country);
			}
			else
			// if (localeParts.length >= 3)
			{
				final String language = localeParts[0];
				final String country = localeParts[1];
				final String variant = localeParts[2];
				return new Locale(language, country, variant);
			}
		}
		catch (Exception e)
		{
			logger.warn("Failed getting the Locale for " + languageAndCountry + ". Returning default", e);
			return defaultLocale;
		}
	}
}