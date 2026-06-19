/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.vatid;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Pure format validator for EU VAT identification numbers.
 *
 * <p>Performs structural format checking only — no checksum computation and no online lookup.
 * A value that has the right structure but a wrong check digit is accepted ({@code true}).
 */
public final class EUVatIdFormatValidator
{
	private EUVatIdFormatValidator()
	{
	}

	/**
	 * EU VAT number structural formats (format-only, no checksum).
	 * Keys are the 2-letter country prefix; patterns are anchored full-match.
	 * Normalised input (trimmed, spaces/dots/hyphens removed, uppercased) is matched against these.
	 */
	private static final Map<String, Pattern> PATTERNS_BY_PREFIX = ImmutableMap.<String, Pattern>builder()
			.put("AT", Pattern.compile("ATU\\d{8}"))
			.put("BE", Pattern.compile("BE[01]\\d{9}"))
			.put("BG", Pattern.compile("BG\\d{9,10}"))
			.put("CY", Pattern.compile("CY\\d{8}[A-Z]"))
			.put("CZ", Pattern.compile("CZ\\d{8,10}"))
			.put("DE", Pattern.compile("DE\\d{9}"))
			.put("DK", Pattern.compile("DK\\d{8}"))
			.put("EE", Pattern.compile("EE\\d{9}"))
			.put("EL", Pattern.compile("EL\\d{9}"))
			.put("ES", Pattern.compile("ES[A-Z0-9]\\d{7}[A-Z0-9]"))
			.put("FI", Pattern.compile("FI\\d{8}"))
			.put("FR", Pattern.compile("FR[A-Z0-9]{2}\\d{9}"))
			.put("HR", Pattern.compile("HR\\d{11}"))
			.put("HU", Pattern.compile("HU\\d{8}"))
			.put("IE", Pattern.compile("IE(\\d{7}[A-W]|\\d[A-Z0-9+*]\\d{5}[A-W]|\\d{7}[A-W][AH])"))
			.put("IT", Pattern.compile("IT\\d{11}"))
			.put("LT", Pattern.compile("LT(\\d{9}|\\d{12})"))
			.put("LU", Pattern.compile("LU\\d{8}"))
			.put("LV", Pattern.compile("LV\\d{11}"))
			.put("MT", Pattern.compile("MT\\d{8}"))
			.put("NL", Pattern.compile("NL[A-Z0-9+*]{10}\\d{2}"))
			.put("PL", Pattern.compile("PL\\d{10}"))
			.put("PT", Pattern.compile("PT\\d{9}"))
			.put("RO", Pattern.compile("RO\\d{2,10}"))
			.put("SE", Pattern.compile("SE\\d{12}"))
			.put("SI", Pattern.compile("SI\\d{8}"))
			.put("SK", Pattern.compile("SK\\d{10}"))
			.put("XI", Pattern.compile("XI(\\d{9}|\\d{12}|GD\\d{3}|HA\\d{3})"))
			.build();

	/**
	 * Returns {@code true} if the given VAT-ID value is acceptable to store, {@code false} if it must be rejected.
	 *
	 * <ul>
	 *   <li>Null, empty, or blank values are always accepted ({@code true}).</li>
	 *   <li>Values whose first two normalised characters are not a recognised EU country prefix are
	 *       accepted without further checking — lenient pass-through for Swiss, UK, bare local numbers,
	 *       and any other non-EU formats.</li>
	 *   <li>Values with a recognised EU prefix are validated against that country's structural pattern.
	 *       A structural match returns {@code true}; no match returns {@code false}.</li>
	 * </ul>
	 *
	 * <p>Normalisation applied before checking (does not mutate the input):
	 * trim → remove all spaces, dots, and hyphens → uppercase.
	 */
	public static boolean isValidFormat(@Nullable final String vatId)
	{
		if (vatId == null || vatId.trim().isEmpty())
		{
			return true;
		}

		final String normalised = normalise(vatId);
		if (normalised.length() < 2)
		{
			// Too short to have a recognisable EU prefix — accept leniently
			return true;
		}

		final String prefix = normalised.substring(0, 2);
		final Pattern pattern = PATTERNS_BY_PREFIX.get(prefix);
		if (pattern == null)
		{
			// Unknown / non-EU prefix — accept without checking
			return true;
		}

		return pattern.matcher(normalised).matches();
	}

	/**
	 * Normalises a VAT-ID value for structural matching:
	 * trims leading/trailing whitespace, removes all embedded spaces, dots, and hyphens, and uppercases the result.
	 */
	private static String normalise(final String vatId)
	{
		return vatId.trim()
				.replace(" ", "")
				.replace(".", "")
				.replace("-", "")   // strip formatted-number separators (e.g. PL-123-456-78-90)
				.toUpperCase();
	}
}
