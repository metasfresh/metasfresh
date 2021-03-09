package de.metas.procurement.webui.model;

import java.util.Locale;
import java.util.Map;

import com.google.gwt.thirdparty.guava.common.base.Joiner;
import com.google.gwt.thirdparty.guava.common.base.Strings;

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

final class TranslationHelper
{
	private static final Joiner languageTagJoiner = Joiner.on('_').skipNulls();
	
	public static <T extends AbstractTranslationEntity<?>> T getTranslation(final Map<String, T> records, final Locale locale)
	{
		if(records == null || records.isEmpty())
		{
			return null;
		}
		if(locale == null)
		{
			return null;
		}
		
		final String language = locale.getLanguage();
		final String country = locale.getCountry();
		final String variant = locale.getVariant();
		
		if (!Strings.isNullOrEmpty(variant))
		{
			final String languageTag = languageTagJoiner.join(language, country, variant);
			final T recordTrl = records.get(languageTag);
			if(recordTrl != null)
			{
				return recordTrl;
			}
		}

		if (!Strings.isNullOrEmpty(country))
		{
			final String languageTag = languageTagJoiner.join(language, country);
			final T recordTrl = records.get(languageTag);
			if(recordTrl != null)
			{
				return recordTrl;
			}
		}

		if (!Strings.isNullOrEmpty(language))
		{
			final String languageTag = language;
			final T recordTrl = records.get(languageTag);
			if(recordTrl != null)
			{
				return recordTrl;
			}
		}

		return null;
	}
}
