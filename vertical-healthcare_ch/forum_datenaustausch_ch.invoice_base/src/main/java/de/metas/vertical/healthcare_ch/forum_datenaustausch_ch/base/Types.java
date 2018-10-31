package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base;

import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class Types
{
	public enum RequestType
	{
		INVOICE("invoice"),

		REMINDER("reminder");

		@Getter
		private final String value;

		private RequestType(String value)
		{
			this.value = value;
		}
	}

	public enum Language
	{
		DE("de", "de_CH"),

		IT("it", "it_CH"),

		FR("fr", "fr_CH");

		public static Language ofXmlValue(@NonNull final String xmlValue)
		{
			return Check.assumeNotNull(
					LANGUAGES.get(xmlValue),
					"There needs to be a language for the given xmlValue={}", xmlValue);
		}

		@Getter
		private final String xmlValue;
		@Getter
		private final String metasfreshValue;

		private static final Map<String, Language> LANGUAGES = ImmutableMap.of(
				DE.getXmlValue(), DE,
				IT.getXmlValue(), IT,
				FR.getXmlValue(), FR);

		private Language(String xmlValue, String metasfreshValue)
		{
			this.xmlValue = xmlValue;
			this.metasfreshValue = metasfreshValue;
		}
	}

	public enum Mode
	{
		PRODUCTION("production"),

		TEST("test");

		private static final Map<String, Mode> MODES = ImmutableMap.of(
				PRODUCTION.getXmlValue(), PRODUCTION,
				TEST.getXmlValue(), TEST);

		public static Mode ofXmlValue(@NonNull final String xmlValue)
		{
			return Check.assumeNotNull(
					MODES.get(xmlValue),
					"There needs to be a mode for the given xmlValue={}", xmlValue);
		}

		@Getter
		private final String xmlValue;

		private Mode(String xmlValue)
		{
			this.xmlValue = xmlValue;
		}
	}

}
