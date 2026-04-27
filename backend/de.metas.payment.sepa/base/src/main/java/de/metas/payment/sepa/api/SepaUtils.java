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

import de.metas.banking.BankAccount;
import de.metas.util.Check;
import lombok.NonNull;
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

	/**
	 * Strict ISO-3166-1 alpha-2 country code: exactly two uppercase ASCII letters.
	 * Used to validate {@link BankAccount#getAccountCountry()} before it is written
	 * into a SEPA {@code <Ctry>} element. Catches the common data-quality mistake of
	 * storing a country name (e.g. {@code "Switzerland"}) instead of its ISO code
	 * ({@code "CH"}), which would otherwise produce schema-invalid SEPA XML.
	 */
	private static final Pattern ISO_3166_ALPHA2 = Pattern.compile("^[A-Z]{2}$");

	private final static Map<String, String> SEPA_REPLACEMENT_MAP = new HashMap<>();

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

	/**
	 * Joins {@code a} and {@code b} with a single space, skipping blank values. Returns {@code ""}
	 * when both are blank. Used to assemble the "{zip} {city}" address line on a SEPA PostalAddress
	 * without emitting "null null" / " " when one side is missing.
	 */
	public static String joinNonBlank(@Nullable final String a, @Nullable final String b)
	{
		final boolean hasA = Check.isNotBlank(a);
		final boolean hasB = Check.isNotBlank(b);
		if (hasA && hasB)
		{
			return a + " " + b;
		}
		return hasA ? a : (hasB ? b : "");
	}

	/**
	 * Verifies that the bank account carries a valid ISO-3166-1 alpha-2 country code
	 * (exactly two uppercase ASCII letters), as required for the {@code <Ctry>} field
	 * of a SEPA {@code PostalAddress}. To be called from the marshalers' bank-account
	 * branches whenever any address field is populated, so that data-quality issues
	 * (missing country, country stored as a full name, lowercase) surface as a clear
	 * SEPA exception instead of silently producing schema-invalid XML.
	 */
	public static void assertValidAccountCountry(@NonNull final BankAccount bankAccount)
	{
		final String country = bankAccount.getAccountCountry();
		if (Check.isBlank(country))
		{
			throw new SepaMarshallerException(
					"C_BP_BankAccount has an address but no country set; A_Country is required"
							+ " (C_BP_BankAccount_ID=" + bankAccount.getId().getRepoId() + ")");
		}
		if (!ISO_3166_ALPHA2.matcher(country).matches())
		{
			throw new SepaMarshallerException(
					"C_BP_BankAccount.A_Country '" + country + "' is not a valid ISO-3166 alpha-2 country code;"
							+ " expected two uppercase letters"
							+ " (C_BP_BankAccount_ID=" + bankAccount.getId().getRepoId() + ")");
		}
	}
}
