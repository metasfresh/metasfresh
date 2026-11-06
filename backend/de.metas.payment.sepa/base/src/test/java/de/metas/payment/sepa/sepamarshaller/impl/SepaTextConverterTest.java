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
	class isSepaIdentifier
	{
		@ParameterizedTest
		@ValueSource(strings = { "MREF-(A/B)+1.2", "A+?/-:().,' B", "abcXYZ019" })
		void allowed(final String text)
		{
			assertThat(SepaTextConverter.isSepaIdentifier(text)).isTrue();
		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = { "MREF-Ä&ö/001", "Straße", "é", "a_b", "€", "a😀b" })
		void notAllowed(final String text)
		{
			assertThat(SepaTextConverter.isSepaIdentifier(text)).isFalse();
		}

		@Test
		void maxLength35()
		{
			assertThat(SepaTextConverter.isSepaIdentifier(repeat("A", 35))).isTrue();
			assertThat(SepaTextConverter.isSepaIdentifier(repeat("A", 36))).isFalse();
		}
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
				"A&B          | A B",            // identifiers keep the basic character set only, so "&" still becomes a space
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
		void whitespaceRuns_areNotCollapsed()
		{
			// unlike texts, identifiers are converted as before: only character by character
			assertThat(SepaTextConverter.toIdentifier("A  B")).isEqualTo("A  B");
		}

		@Test
		void cutTo35Characters()
		{
			assertThat(SepaTextConverter.toIdentifier(repeat("A", 40))).isEqualTo(repeat("A", 35));
		}

		@Test
		void onlyDisallowedCharacters_isNull()
		{
			assertThat(SepaTextConverter.toIdentifier("€€")).isNull();
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
				"Bäcker & Söhne € Großhandel | Baecker & Soehne Grosshandel",   // umlauts and ß are transliterated, & is kept, € becomes a space
				"Société Générale, Łódź      | Societe Generale, Lodz",          // accents are dropped; Ł has no decomposition and is transliterated
				"Søren Ærø, Œuvre, Đorđe     | Soren AEro, OEuvre, Dorde",       // letters without a decomposition are transliterated
				"Þórður Ðórsson              | THordur Dorsson",                 // Icelandic letters without a decomposition
				"Rechnung „Nr. 1“            | Rechnung Nr. 1",                  // trimmed and whitespace collapsed after the conversion
				"a😀b              | a b",                             // a character outside the BMP is ONE space, not two
				"50% * 2 $                   | 50 2",
				"Meier & Söhne GmbH & Co.     | Meier & Soehne GmbH & Co.",     // & is allowed for the German banks (DFÜ-Abkommen Anlage 3)
				"A&B                         | A&B",
		})
		void converted(final String text, final String expected)
		{
			assertThat(SepaTextConverter.toText(text, 70)).isEqualTo(expected);
		}

		@Test
		void whitespaceRunInside_isCollapsedToOneSpace()
		{
			assertThat(SepaTextConverter.toText("Meier \t\n  Söhne", 70)).isEqualTo("Meier Soehne");
		}

		@Test
		void decomposedInput_isConvertedLikeComposedInput()
		{
			// "Müller" with "u" followed by a combining diaeresis (U+0308), as some sources deliver it
			assertThat(SepaTextConverter.toText("Mu\u0308ller", 70)).isEqualTo("Mueller");
		}

		@Test
		void cutAfterTrim_mayEndWithASpace()
		{
			assertThat(SepaTextConverter.toText("abc def", 4)).isEqualTo("abc ");
		}

		@Test
		void onlyDisallowedCharacters_isNull()
		{
			assertThat(SepaTextConverter.toText("€ € €", 70)).isNull();
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
