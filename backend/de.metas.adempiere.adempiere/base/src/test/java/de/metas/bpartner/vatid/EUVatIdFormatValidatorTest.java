/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.vatid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class EUVatIdFormatValidatorTest
{
	// ----- Empty / blank / null → always valid -----

	@Test
	void null_isValid()
	{
		assertThat(EUVatIdFormatValidator.isValidFormat(null)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "   " })
	void emptyOrBlank_isValid(final String vatId)
	{
		assertThat(EUVatIdFormatValidator.isValidFormat(vatId)).isTrue();
	}

	// ----- Unknown / non-EU prefixes → accepted (lenient) -----

	@ParameterizedTest
	@ValueSource(strings = {
			"CHE-123.456.789",  // Swiss MwSt number
			"GB123456789",      // UK post-Brexit
			"123456",           // bare local number (no alpha prefix)
			"XX999"             // unknown 2-letter prefix
	})
	void unknownPrefix_isValid(final String vatId)
	{
		assertThat(EUVatIdFormatValidator.isValidFormat(vatId)).isTrue();
	}

	// ----- Formatting tolerance (spaces / dots / hyphens stripped, case-insensitive) -----

	@ParameterizedTest
	@ValueSource(strings = {
			"DE 123 456 789",   // spaces between groups
			" DE123456789 ",    // leading/trailing whitespace
			"de123456789"       // lowercase prefix — normalised to uppercase before check
	})
	void formattingVariants_areValid(final String vatId)
	{
		assertThat(EUVatIdFormatValidator.isValidFormat(vatId)).isTrue();
	}

	// ----- Format-only: right shape passes regardless of checksum -----

	@Test
	void de_anyNineDigits_isValid()
	{
		// The validator must NOT compute or verify a check digit
		assertThat(EUVatIdFormatValidator.isValidFormat("DE000000000")).isTrue();
		assertThat(EUVatIdFormatValidator.isValidFormat("DE999999999")).isTrue();
	}

	// ----- Hyphen tolerance -----

	@Test
	void hyphenatedInput_isNormalisedBeforeCheck()
	{
		// Hyphens are stripped during normalisation — formatted national inputs are accepted
		assertThat(EUVatIdFormatValidator.isValidFormat("PL-123-456-78-90")).isTrue();
	}

	// ----- Per-country parametrised: one valid + one invalid example per code -----
	// Format: vatId, expectedValid
	// Valid examples satisfy the structural regex; invalid examples clearly do not.

	@ParameterizedTest
	@CsvSource({
			// AT  ATU\d{8}
			"ATU12345678,   true",
			"AT12345678,    false",   // missing U

			// BE  BE[01]\d{9}
			"BE0123456789,  true",
			"BE123456789,   false",   // only 8 trailing digits after the leading digit (needs 9)

			// BG  BG\d{9,10}
			"BG123456789,   true",
			"BG12345678,    false",   // only 8 digits

			// CY  CY\d{8}[A-Z]
			"CY12345678A,   true",
			"CY1234567A,    false",   // only 7 digits before letter

			// CZ  CZ\d{8,10}
			"CZ12345678,    true",
			"CZ1234567,     false",   // only 7 digits

			// DE  DE\d{9}
			"DE123456789,   true",
			"DE12345,       false",   // too short

			// DK  DK\d{8}
			"DK12345678,    true",
			"DK1234567,     false",   // only 7 digits

			// EE  EE\d{9}
			"EE123456789,   true",
			"EE12345678,    false",   // only 8 digits

			// EL  EL\d{9}
			"EL123456789,   true",
			"EL12345678,    false",   // only 8 digits

			// ES  ES[A-Z0-9]\d{7}[A-Z0-9]
			"ESA1234567B,   true",
			"ES12345678,    false",   // no alpha last char

			// FI  FI\d{8}
			"FI12345678,    true",
			"FI1234567,     false",   // only 7 digits

			// FR  FR[A-Z0-9]{2}\d{9}
			"FRAB123456789, true",
			"FR1234567890,  false",   // too short: 12 chars, needs 13 (FR + 2 alphanums + 9 digits)

			// HR  HR\d{11}
			"HR12345678901, true",
			"HR1234567890,  false",   // only 10 digits

			// HU  HU\d{8}
			"HU12345678,    true",
			"HU1234567,     false",   // only 7 digits

			// IE  IE(\d{7}[A-W]|\d[A-Z0-9+*]\d{5}[A-W]|\d{7}[A-W][AH])
			"IE1234567A,    true",
			"IE123456,      false",   // too short

			// IT  IT\d{11}
			"IT12345678901, true",
			"IT1234567890,  false",   // only 10 digits

			// LT  LT(\d{9}|\d{12})
			"LT123456789,   true",
			"LT12345678,    false",   // only 8 digits (neither 9 nor 12)

			// LU  LU\d{8}
			"LU12345678,    true",
			"LU1234567,     false",   // only 7 digits

			// LV  LV\d{11}
			"LV12345678901, true",
			"LV1234567890,  false",   // only 10 digits

			// MT  MT\d{8}
			"MT12345678,    true",
			"MT1234567,     false",   // only 7 digits

			// NL  NL[A-Z0-9+*]{10}\d{2}
			"NLABCDE1234501, true",
			"NL123456789,   false",   // too short

			// PL  PL\d{10}
			"PL1234567890,  true",
			"PL123456789,   false",   // only 9 digits

			// PT  PT\d{9}
			"PT123456789,   true",
			"PT12345678,    false",   // only 8 digits

			// RO  RO\d{2,10}
			"RO12,          true",
			"RO1,           false",   // only 1 digit

			// SE  SE\d{12}
			"SE123456789012, true",
			"SE12345678901, false",   // only 11 digits

			// SI  SI\d{8}
			"SI12345678,    true",
			"SI1234567,     false",   // only 7 digits

			// SK  SK\d{10}
			"SK1234567890,  true",
			"SK123456789,   false",   // only 9 digits

			// XI  XI(\d{9}|\d{12}|GD\d{3}|HA\d{3})
			"XI123456789,   true",
			"XI12345678,    false",   // only 8 digits (neither 9 nor 12, not GD/HA)
	})
	void perCountry_validAndInvalid(final String vatId, final boolean expectedValid)
	{
		assertThat(EUVatIdFormatValidator.isValidFormat(vatId.trim()))
				.as("vatId=%s", vatId.trim())
				.isEqualTo(expectedValid);
	}
}
