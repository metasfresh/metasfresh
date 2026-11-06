package de.metas.payment.sepa.sepamarshaller.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.text.Normalizer;
import java.util.regex.Pattern;

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
 * Converts texts of a direct debit file to the SEPA character set and cuts them to the schema's maximum length, keeping the start.
 * <ul>
 * <li>Identifiers (e.g. the message ID) keep only the basic SEPA character set {@code [A-Za-z0-9/?:().,'+\- ]}, which the schema enforces for them.</li>
 * <li>Texts (names, addresses, remittance information) additionally keep {@code &}.</li>
 * </ul>
 * The German banks accept more than the basic set in texts: the DFÜ-Abkommen, Anlage 3, and the Deutsche Bundesbank's
 * "Technische Spezifikationen SEPA-Lastschriften" (November 2024) list {@code Ä ä Ö ö Ü ü ß & * $ %} as allowed.
 * Of these, only {@code &} is kept: it is common in company names ("Müller & Co."), while banks in other countries may not accept the others,
 * and the Bundesbank itself recommends writing "Ä" as "AE" and "ß" as "ss".
 * So umlauts, "ß" and letters such as "ø" or "Ł" are transliterated, other letters lose their accents (é → e), and every character still outside the set becomes a space;
 * in texts, each run of whitespace then becomes one space. The result is trimmed before and after it is cut, so a cut text never ends with a space. A text with no character left yields {@code null}.
 */
@UtilityClass
public class SepaTextConverter
{
	public static final int IDENTIFIER_MAX_LENGTH = 35;

	private static final ImmutableMap<Character, String> TRANSLITERATIONS = ImmutableMap.<Character, String>builder()
			.put('Ä', "AE")
			.put('Ö', "OE")
			.put('Ü', "UE")
			.put('ä', "ae")
			.put('ö', "oe")
			.put('ü', "ue")
			.put('ß', "ss")
			.put('ẞ', "SS")
			// letters without a Unicode decomposition into a base letter and an accent
			.put('Æ', "AE")
			.put('æ', "ae")
			.put('Œ', "OE")
			.put('œ', "oe")
			.put('Ø', "O")
			.put('ø', "o")
			.put('Ł', "L")
			.put('ł', "l")
			.put('Đ', "D")
			.put('đ', "d")
			.put('Ð', "D")
			.put('ð', "d")
			.put('Þ', "TH")
			.put('þ', "th")
			.build();

	private static final Pattern MULTIPLE_SPACES = Pattern.compile(" {2,}");

	private static final String SPECIAL_CHARS = "+?/-:().,' ";
	/**
	 * Allowed in texts, but not in identifiers
	 */
	private static final String TEXT_ONLY_SPECIAL_CHARS = "&";

	/**
	 * @return {@code true} if the text is non-empty, at most {@link #IDENTIFIER_MAX_LENGTH} long and consists only of characters of the basic SEPA character set,
	 * i.e. it can be written unchanged as an identifier that must match exactly what was agreed (e.g. a mandate reference).
	 */
	public static boolean isSepaIdentifier(@Nullable final String text)
	{
		return text != null
				&& !text.isEmpty()
				&& text.length() <= IDENTIFIER_MAX_LENGTH
				&& text.chars().allMatch(SepaTextConverter::isAllowed);
	}

	/**
	 * For mandatory identifiers.
	 *
	 * @return {@code null} for a blank input or if nothing is left after the conversion; the caller has to tell the two apart (the element is mandatory, so the latter must be refused)
	 */
	@Nullable
	public static String toIdentifier(@Nullable final String text)
	{
		if (Check.isBlank(text))
		{
			return null;
		}
		return StringUtils.trimBlankToNull(cut(convert(text, false), IDENTIFIER_MAX_LENGTH));
	}

	/**
	 * For mandatory texts.
	 *
	 * @return {@code null} for a blank input or if nothing is left after the conversion; the caller has to tell the two apart (the element is mandatory, so the latter must be refused)
	 */
	@Nullable
	public static String toText(@Nullable final String text, final int maxLength)
	{
		if (Check.isBlank(text))
		{
			return null;
		}
		return StringUtils.trimBlankToNull(cut(collapseWhitespace(convert(text, true)), maxLength));
	}

	/**
	 * For optional texts.
	 *
	 * @return {@code null} if nothing is left after the conversion, so that the element is left out
	 */
	@Nullable
	public static String toOptionalText(@Nullable final String text, final int maxLength)
	{
		return toText(text, maxLength);
	}

	private static String convert(@NonNull final String text, final boolean isText)
	{
		final StringBuilder result = new StringBuilder(text.length());
		// composed first, so that a letter delivered as base letter + combining mark is transliterated like the single character
		Normalizer.normalize(text, Normalizer.Form.NFC).codePoints().forEach(codePoint -> {
			if (Character.isBmpCodePoint(codePoint) && TRANSLITERATIONS.containsKey((char)codePoint))
			{
				result.append(TRANSLITERATIONS.get((char)codePoint));
			}
			else
			{
				final int baseLetter = withoutAccent(codePoint);
				result.append(isAllowed(baseLetter) || (isText && TEXT_ONLY_SPECIAL_CHARS.indexOf(baseLetter) >= 0) ? (char)baseLetter : ' ');
			}
		});
		return result.toString().trim();
	}

	/**
	 * @return the letter without its accent (é → e), or the code point itself if it has no such decomposition
	 */
	private static int withoutAccent(final int codePoint)
	{
		final String decomposed = Normalizer.normalize(new String(Character.toChars(codePoint)), Normalizer.Form.NFD);
		final boolean isLetterWithMarks = decomposed.length() > 1
				&& decomposed.codePoints().skip(1).allMatch(cp -> Character.getType(cp) == Character.NON_SPACING_MARK);
		return isLetterWithMarks ? decomposed.codePointAt(0) : codePoint;
	}

	private static boolean isAllowed(final int codePoint)
	{
		return (codePoint >= 'a' && codePoint <= 'z')
				|| (codePoint >= 'A' && codePoint <= 'Z')
				|| (codePoint >= '0' && codePoint <= '9')
				|| SPECIAL_CHARS.indexOf(codePoint) >= 0;
	}

	/**
	 * Every character outside the SEPA character set already became a space, so a run of whitespace is a run of spaces here
	 */
	private static String collapseWhitespace(@NonNull final String text)
	{
		return MULTIPLE_SPACES.matcher(text).replaceAll(" ");
	}

	private static String cut(@NonNull final String text, final int maxLength)
	{
		return text.length() <= maxLength ? text : text.substring(0, maxLength);
	}
}
