/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class TestDiacritics
{
	public static final Pattern SEPA_COMPLIANT_PATTERN = Pattern.compile(
			"([a-zA-Z0-9.,;:'\\+\\-/()\\*\\[\\]{}\\\\`´~ ]|[!\"#%&<>÷=@_$£]|[àáâäçèéêëìíîïñòóôöùúûüýßÀÁÂÄÇÈÉÊËÌÍÎÏÒÓÔÖÙÚÛÜÑ])*");

	public static void main(String[] args) throws UnsupportedEncodingException
	{

		String originalText = "Epis d’Ajoie LANDI Centre Broye Société coopérative";
		String sepaText = toSepaCompliantText(originalText);

		String sepaText_v2 = toSepaCompliantText_v2(originalText);

		System.out.println("Original: " + originalText);

		System.out.println("------------------");

		System.out.println("SEPA Compliant: " + sepaText);

		System.out.println("------------------");

		System.out.println("SEPA Compliant: " + sepaText_v2);
	}

	public static String toSepaCompliantText(String text)
	{
		if (text == null)
		{
			return null;
		}

		// Replace specific non-SEPA characters
		String sanitizedText = text.replace("’", "'");

		// Remove remaining non-SEPA characters while preserving basic accents (e.g., é, è, à)
		sanitizedText = Normalizer.normalize(sanitizedText, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 .,'/+-]");
		sanitizedText = pattern.matcher(sanitizedText).replaceAll("");

		return sanitizedText;
	}

	public static String toSepaCompliantText_v2(String text)
	{
		if (text == null)
		{
			return null;
		}

		// Replace curly apostrophe with straight apostrophe
		String sanitizedText = text.replace("’", "'");

		// Use the SEPA_COMPLIANT_PATTERN to retain only compliant characters
		StringBuilder compliantText = new StringBuilder();
		for (int i = 0; i < sanitizedText.length(); i++)
		{
			String currentChar = String.valueOf(sanitizedText.charAt(i));
			if (SEPA_COMPLIANT_PATTERN.matcher(currentChar).matches())
			{
				compliantText.append(currentChar);
			}
		}

		return compliantText.toString();
	}

}
