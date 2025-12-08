/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.payment.sepa.api;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@UtilityClass
public class SepaUtils
{

	/**
	 * coming from the SEPA specifications https://www.sepaforcorporates.com/sepa-implementation/valid-xml-characters-sepa-payments/
	 */
	private static final Pattern SEPA_COMPLIANT_PATTERN = Pattern.compile(
			"([a-zA-Z0-9.,;:'\\+\\-/()\\*\\[\\]{}\\\\`´~ ]|[!\"#%&<>÷=@_$£]|[àáâäçèéêëìíîïñòóôöùúûüýßÀÁÂÄÇÈÉÊËÌÍÎÏÒÓÔÖÙÚÛÜÑ])*");

	private static Map<String, String> SEPA_REPLACEMENT_MAP = new HashMap<>();

	static
	{
		SEPA_REPLACEMENT_MAP.put("’", "'");
		SEPA_REPLACEMENT_MAP.put("“", "\"");
		SEPA_REPLACEMENT_MAP.put("INVALID_CHAR", "_"); // Define replacement for any non-compliant character
	}

	/**
	 * Cleans a given input string, replacing non-compliant SEPA characters with their respective
	 * mapped values, or an underscore for unsupported characters.
	 *
	 * @param input the string to sanitize
	 * @return the sanitized SEPA-compliant string
	 */
	@Nullable
	public static String replaceForbiddenChars(@Nullable final String input)
	{
		if (input == null)
		{
			return null;
		}

		// Apply specific replacements from SEPA_REPLACEMENT_MAP
		String sanitizedText = input;
		for (Map.Entry<String, String> entry : SEPA_REPLACEMENT_MAP.entrySet())
		{
			if (!entry.getKey().equals("INVALID_CHAR"))
			{ // Exclude INVALID_CHAR replacement here
				sanitizedText = sanitizedText.replace(entry.getKey(), entry.getValue());
			}
		}

		// Use SEPA_COMPLIANT_PATTERN to retain only compliant characters
		StringBuilder compliantText = new StringBuilder();
		for (int i = 0; i < sanitizedText.length(); i++)
		{
			String currentChar = String.valueOf(sanitizedText.charAt(i));
			if (SEPA_COMPLIANT_PATTERN.matcher(currentChar).matches())
			{
				compliantText.append(currentChar);
			}
			else
			{
				// Use the map's value for invalid characters (underscore)
				compliantText.append(SEPA_REPLACEMENT_MAP.get("INVALID_CHAR"));
			}
		}

		return compliantText.toString();
	}
}