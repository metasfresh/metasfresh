package de.metas.payment.sepa.sepamarshaller.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Converts texts to the character sets of the German SEPA direct debit format (pain.008.001.08, GBIC_5) and cuts them to the schema's maximum length, keeping the start.
 * Every character outside the respective set becomes a space; the result is trimmed before it is cut.
 * <ul>
 * <li>Identifiers ({@code MndtId}, {@code MsgId}, {@code PmtInfId}, {@code EndToEndId}): the GBIC_5 pattern {@code [A-Za-z0-9+?/\-:().,' ]}; German umlauts and "ß" are transliterated first.</li>
 * <li>Texts (names, remittance information, address parts): the Deutsche Bundesbank character set, which additionally keeps {@code Ä ä Ö ö Ü ü ß & * $ %}.</li>
 * </ul>
 */
@UtilityClass
public class SepaDkTextConverter
{
	public static final int IDENTIFIER_MAX_LENGTH = 35;

	private static final ImmutableMap<Character, String> IDENTIFIER_TRANSLITERATIONS = ImmutableMap.<Character, String>builder()
			.put('Ä', "AE")
			.put('Ö', "OE")
			.put('Ü', "UE")
			.put('ä', "ae")
			.put('ö', "oe")
			.put('ü', "ue")
			.put('ß', "ss")
			.build();

	private static final String IDENTIFIER_SPECIAL_CHARS = "+?/-:().,' ";
	private static final String TEXT_SPECIAL_CHARS = "':?,-(+.)/ ÄäÖöÜüß&*$%";

	/**
	 * @return {@code null} for a blank input; a single space if a non-blank input has nothing left after the conversion (the element is mandatory and must not be empty)
	 */
	@Nullable
	public static String toIdentifier(@Nullable final String text)
	{
		if (Check.isBlank(text))
		{
			return null;
		}
		return spaceIfEmpty(cut(convert(text, true), IDENTIFIER_MAX_LENGTH));
	}

	/**
	 * For mandatory texts.
	 *
	 * @return {@code null} for a blank input; a single space if a non-blank input has nothing left after the conversion (the element is mandatory and must not be empty)
	 */
	@Nullable
	public static String toText(@Nullable final String text, final int maxLength)
	{
		if (Check.isBlank(text))
		{
			return null;
		}
		return spaceIfEmpty(cut(convert(text, false), maxLength));
	}

	/**
	 * For optional texts.
	 *
	 * @return {@code null} if nothing is left after the conversion, so that the element is left out
	 */
	@Nullable
	public static String toOptionalText(@Nullable final String text, final int maxLength)
	{
		if (Check.isBlank(text))
		{
			return null;
		}
		final String result = cut(convert(text, false), maxLength);
		return result.isEmpty() ? null : result;
	}

	private static String convert(@NonNull final String text, final boolean identifier)
	{
		final StringBuilder result = new StringBuilder(text.length());
		text.codePoints().forEach(codePoint -> {
			if (identifier && Character.isBmpCodePoint(codePoint) && IDENTIFIER_TRANSLITERATIONS.containsKey((char)codePoint))
			{
				result.append(IDENTIFIER_TRANSLITERATIONS.get((char)codePoint));
			}
			else if (isAllowed(codePoint, identifier ? IDENTIFIER_SPECIAL_CHARS : TEXT_SPECIAL_CHARS))
			{
				result.appendCodePoint(codePoint);
			}
			else
			{
				result.append(' ');
			}
		});
		return result.toString().trim();
	}

	private static boolean isAllowed(final int codePoint, @NonNull final String specialChars)
	{
		return (codePoint >= 'a' && codePoint <= 'z')
				|| (codePoint >= 'A' && codePoint <= 'Z')
				|| (codePoint >= '0' && codePoint <= '9')
				|| specialChars.indexOf(codePoint) >= 0;
	}

	private static String cut(@NonNull final String text, final int maxLength)
	{
		return text.length() <= maxLength ? text : text.substring(0, maxLength);
	}

	private static String spaceIfEmpty(@NonNull final String text)
	{
		return text.isEmpty() ? " " : text;
	}
}
