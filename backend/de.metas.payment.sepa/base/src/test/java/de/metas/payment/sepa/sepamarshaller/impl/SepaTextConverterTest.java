package de.metas.payment.sepa.sepamarshaller.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class SepaTextConverterTest
{
	private static String repeat(final String str, final int count)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++)
		{
			sb.append(str);
		}
		return sb.toString();
	}

	@Nested
	class toIdentifier
	{
		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = { "   " })
		void blank_isNull(final String text)
		{
			assertThat(SepaTextConverter.toIdentifier(text)).isNull();
		}

		@ParameterizedTest
		@CsvSource(delimiter = '|', value = {
				"MREF-Ä&ö/001 | MREF-AE oe/001", // umlauts are transliterated, "&" becomes a space
				"Straße       | Strasse",
				"Müller-Lüdenscheidt é | Mueller-Luedenscheidt e",
		})
		void converted(final String text, final String expected)
		{
			assertThat(SepaTextConverter.toIdentifier(text)).isEqualTo(expected);
		}

		@Test
		void allowedSpecialCharacters_areKept()
		{
			assertThat(SepaTextConverter.toIdentifier("A+?/-:().,'B")).isEqualTo("A+?/-:().,'B");
		}

		@Test
		void cutTo35Characters()
		{
			assertThat(SepaTextConverter.toIdentifier(repeat("A", 40))).isEqualTo(repeat("A", 35));
		}

		@Test
		void onlyDisallowedCharacters_isOneSpace()
		{
			assertThat(SepaTextConverter.toIdentifier("€€")).isEqualTo(" ");
		}
	}

	@Nested
	class toText
	{
		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = { "   " })
		void blank_isNull(final String text)
		{
			assertThat(SepaTextConverter.toText(text, 70)).isNull();
		}

		@ParameterizedTest
		@CsvSource(delimiter = '|', value = {
				"Bäcker & Söhne € Großhandel | Baecker   Soehne   Grosshandel", // umlauts and ß are transliterated, & and € become a space
				"Société Générale, Łódź      | Societe Generale,  odz",         // accents are dropped; a letter without a base letter (Ł) becomes a space
				"Rechnung „Nr. 1“            | Rechnung  Nr. 1",                 // trimmed after the conversion
				"a😀b              | a b",                             // a character outside the BMP is ONE space, not two
				"50% * 2 $                   | 50    2",
		})
		void converted(final String text, final String expected)
		{
			assertThat(SepaTextConverter.toText(text, 70)).isEqualTo(expected);
		}

		@Test
		void cutAfterTrim_mayEndWithASpace()
		{
			assertThat(SepaTextConverter.toText("abc def", 4)).isEqualTo("abc ");
		}

		@Test
		void onlyDisallowedCharacters_isOneSpace()
		{
			assertThat(SepaTextConverter.toText("€ € €", 70)).isEqualTo(" ");
		}
	}

	@Nested
	class toOptionalText
	{
		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = { "   ", "€ € €" })
		void blankOrNothingLeft_isNull(final String text)
		{
			assertThat(SepaTextConverter.toOptionalText(text, 140)).isNull();
		}

		@Test
		void cutToMaxLength()
		{
			assertThat(SepaTextConverter.toOptionalText(repeat("Y", 150), 140)).isEqualTo(repeat("Y", 140));
		}
	}
}
