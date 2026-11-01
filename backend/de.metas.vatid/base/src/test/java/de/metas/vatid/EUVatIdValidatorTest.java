/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link EUVatIdValidator#isValid(String)}.
 *
 * Valid test vectors are taken directly from python-stdnum doctests
 * (https://github.com/arthurdejong/python-stdnum) — the authoritative reference
 * for each country's check-digit algorithm.
 *
 * Invalid variants mutate one digit/character so the check digit no longer matches.
 */
class EUVatIdValidatorTest
{
	// ===================================================================
	// Edge cases — null / blank / too-short → accepted; unrecognised prefix → rejected
	// ===================================================================

	@Test
	void null_isValid()
	{
		assertThat(EUVatIdValidator.isValid(null)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "   " })
	void emptyOrBlank_isValid(final String vatId)
	{
		assertThat(EUVatIdValidator.isValid(vatId)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = { "A", "." })
	void tooShortToHavePrefix_isAccepted(final String vatId)
	{
		// A non-blank value that normalises to fewer than two characters can't carry a country prefix → accepted.
		assertThat(EUVatIdValidator.isValid(vatId)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = { "US123456789", "XX999", "123456" })
	void unrecognisedPrefix_isRejected(final String vatId)
	{
		// Apart from the empty/too-short cases above, a value is accepted only if it is a supported country's
		// valid VAT-ID; an unrecognised prefix is rejected.
		assertThat(EUVatIdValidator.isValid(vatId)).isFalse();
	}

	// ===================================================================
	// Formatting tolerance — spaces / dots / hyphens stripped, case-insensitive
	// ===================================================================

	@ParameterizedTest
	@ValueSource(strings = {
			"DE 136 695 976",    // spaces between groups — valid DE number (stdnum/de/vat.py: 'DE 136,695 976')
			" DE136695976 ",     // leading/trailing whitespace
			"de136695976",       // lowercase prefix
			"DE136.695.976",     // dots as separators
	})
	void formattingVariants_areValid(final String vatId)
	{
		assertThat(EUVatIdValidator.isValid(vatId)).isTrue();
	}

	// ===================================================================
	// Per-country: one REAL checksum-valid vector and one checksum-invalid variant
	// ===================================================================

	@ParameterizedTest
	@CsvSource({
			//
			// AT — Austria
			// Source: stdnum/at/uid.py — valid: ATU13585627
			// Invalid: mutate last digit 7→8
			"ATU13585627, true",
			"ATU13585628, false",

			//
			// BE — Belgium
			// Source: stdnum/be/vat.py — valid: BE0428759497  (validate('BE 428759497') = '0428759497')
			// Invalid: mutate last digit 7→8
			"BE0428759497, true",
			"BE0428759498, false",

			//
			// BG — Bulgaria (9-digit legal entity)
			// Source: stdnum/bg/vat.py — valid: BG175074752
			// Invalid: mutate last digit 2→3
			"BG175074752, true",
			"BG175074753, false",

			//
			// BG — Bulgaria (10-digit personal / other)
			// Source: stdnum/bg/vat.py — checkBgPersonal: weights(4,3,2,7,6,5,4,3,2); check=(11-sum%11)%11
			// Generated: body=752316926, sum=158, sum%11=4, check=7 → BG7523169267
			// Invalid: mutate check digit 7→8
			"BG7523169267, true",
			"BG7523169268, false",

			//
			// CH — Switzerland (UID)
			// CH VAT number REQUIRES the VAT marker MWST/TVA/IVA/TPV (de/fr/it/rm) — source: stdnum/ch/vat.py.
			// Valid 9-digit body 100155212 (stdnum/ch/uid.py); the bare UID without a marker is NOT a VAT number.
			"CHE100155212MWST, true",
			"CHE100155212TVA, true",
			"CHE100155212IVA, true",
			"CHE100155212TPV, true",
			"CHE100155212, false",
			"CHE100155213MWST, false",

			//
			// CY — Cyprus
			// Source: stdnum/cy/vat.py — valid: CY10259033P  (compact of 'CY-10259033P')
			// Invalid: stdnum invalid example CY10259033Z
			"CY10259033P, true",
			"CY10259033Z, false",

			//
			// CZ — Czech Republic (8-digit legal entity)
			// Source: stdnum/cz/dic.py — valid: CZ25123891
			// Invalid: mutate last digit 1→2
			"CZ25123891, true",
			"CZ25123892, false",

			//
			// CZ — Czech Republic (9-digit special entity, first digit 6)
			// Source: stdnum/cz/dic.py — valid: CZ640903926  (listed in module doctest as '640903926')
			// Invalid: mutate last digit 6→7
			"CZ640903926, true",
			"CZ640903927, false",

			//
			// CZ — Czech Republic (9-digit pre-1954 birth number, first digit != 6)
			// Source: stdnum/cz/dic.py — no check digit; any well-formed 9-digit non-6-prefixed number is valid
			// Always returns true (no check-digit validation for this sub-path)
			"CZ520101111, true",

			//
			// CZ — Czech Republic (10-digit post-1954 birth number)
			// Source: stdnum/cz/dic.py — valid iff Long.parseLong(digits) % 11 == 0
			// Generated: 8101010060 % 11 = 0 (month=01 is valid)
			// Invalid: 8101010061 % 11 != 0
			"CZ8101010060, true",
			"CZ8101010061, false",

			//
			// DE — Germany
			// Source: stdnum/de/vat.py — valid: DE136695976  (compact of 'DE 136,695 976')
			// Invalid: stdnum invalid example 136695978 → DE136695978
			"DE136695976, true",
			"DE136695978, false",

			//
			// DK — Denmark
			// Source: stdnum/dk/cvr.py — valid: DK13585628
			// Invalid: stdnum invalid example DK13585627
			"DK13585628, true",
			"DK13585627, false",

			//
			// EE — Estonia
			// Source: stdnum/ee/kmkr.py — valid: EE100594102
			// Invalid: stdnum invalid example 100594103 → EE100594103
			"EE100594102, true",
			"EE100594103, false",

			//
			// EL — Greece
			// Source: stdnum/gr/vat.py — valid: EL094259216  (validate('EL 094259216 ') = '094259216')
			// Invalid: stdnum invalid example EL123456781
			"EL094259216, true",
			"EL123456781, false",

			//
			// ES — Spain (CIF organisation)
			// Source: stdnum/es/nif.py — valid: ESB58378431
			// Invalid: mutate last digit 1→2
			"ESB58378431, true",
			"ESB58378432, false",

			//
			// ES — Spain (DNI: 8 digits + check letter, first char is digit)
			// Source: stdnum/es/nif.py — valid: ES54362315K  (54362315 % 23 = 21 → 'K')
			// Invalid: mutate check letter K→L
			"ES54362315K, true",
			"ES54362315L, false",

			//
			// ES — Spain (K/L/M prefix — uses DNI check on 7 middle digits)
			// Source: stdnum/es/nif.py — valid: ESM1234567L  (1234567 % 23 = 19 → 'L')
			// Invalid: mutate check letter L→M
			"ESM1234567L, true",
			"ESM1234567M, false",

			//
			// ES — Spain (NIE: X/Y/Z prefix — replace X=0,Y=1,Z=2 then DNI check)
			// Source: stdnum/es/nif.py — valid: ESX2482300W  (X→0: 02482300=2482300, 2482300 % 23 = 2 → 'W')
			// Invalid: mutate check letter W→Q (Q is at index 16 ≠ 2)
			"ESX2482300W, true",
			"ESX2482300Q, false",

			//
			// FI — Finland
			// Source: stdnum/fi/alv.py — valid: FI20774740
			// Invalid: stdnum invalid example FI20774741
			"FI20774740, true",
			"FI20774741, false",

			//
			// FR — France (all-numeric key)
			// Source: stdnum/fr/tva.py — valid: FR40303265045
			// Invalid: mutate key 40→41 (41303265045 → (303265045+12) % 97 = 30303265057 % 97 ≠ 41)
			"FR40303265045, true",
			"FR41303265045, false",

			//
			// GB — United Kingdom (9-digit standard)
			// Source: stdnum/gb/vat.py — valid: GB980780684
			// Invalid: stdnum invalid example 802311781 → GB802311781
			"GB980780684, true",
			"GB802311781, false",

			//
			// GB — United Kingdom (12-digit: first 9 validated, last 3 ignored)
			// Source: stdnum/gb/vat.py — same check-digit logic as 9-digit, trailing 3 digits irrelevant
			// Valid: GB980780684123 (first 9 = GB980780684 which is valid; sum%97=0)
			// Invalid: prefix with invalid first-9 (GB802311781) + any 3 trailing digits
			"GB980780684123, true",
			"GB802311781456, false",

			//
			// HR — Croatia
			// Source: stdnum/hr/oib.py — valid: HR33392005961
			// Invalid: stdnum invalid example 33392005962 → HR33392005962
			"HR33392005961, true",
			"HR33392005962, false",

			//
			// HU — Hungary
			// Source: stdnum/hu/anum.py — valid: HU12892312
			// Invalid: stdnum invalid example HU12892313
			"HU12892312, true",
			"HU12892313, false",

			//
			// IE — Ireland (new 7-digit format + check letter)
			// Source: stdnum/ie/vat.py — valid: IE6433435F
			// Invalid: mutate check letter F→G
			"IE6433435F, true",
			"IE6433435G, false",

			//
			// IE — Ireland (old format: digit + letter + 5 digits + check letter)
			// Source: stdnum/ie/vat.py — valid: IE8D79739I  (listed in module doctest)
			// Invalid: mutate check letter I→J
			"IE8D79739I, true",
			"IE8D79739J, false",

			//
			// IE — Ireland (9-char new format: 7 digits + check letter + A or H suffix)
			// Source: stdnum/ie/vat.py — valid: IE6433435OA  (listed in module doctest as '6433435OA')
			// Invalid: mutate check letter O→P
			"IE6433435OA, true",
			"IE6433435PA, false",

			//
			// IT — Italy
			// Source: stdnum/it/iva.py — valid: IT00743110157
			// Invalid: stdnum invalid example 00743110158 → IT00743110158
			"IT00743110157, true",
			"IT00743110158, false",

			//
			// LT — Lithuania (9-digit)
			// Source: stdnum/lt/pvm.py — valid: LT119511515
			// Invalid: mutate last digit 5→6
			"LT119511515, true",
			"LT119511516, false",

			//
			// LT — Lithuania (12-digit temporarily registered — different checksum path)
			// Source: stdnum/lt/pvm.py — valid: LT100001919017  (doctest example)
			//   calc_check_digit(100001919001..1) = 7 (primary path: sum%11 < 10)
			// Source: stdnum/lt/pvm.py — valid: LT100004801610  (doctest: 'second step in check digit calculation')
			//   primary sum%11 = 10 triggers fallback; calc_check_digit(...) = 0
			// Invalid: stdnum invalid example LT100001919018
			"LT100001919017, true",
			"LT100004801610, true",
			"LT100001919018, false",

			//
			// LU — Luxembourg
			// Source: stdnum/lu/tva.py — valid: LU15027442
			// Invalid: stdnum invalid example 15027443 → LU15027443
			"LU15027442, true",
			"LU15027443, false",

			//
			// LV — Latvia (legal entity, first digit >= 4)
			// Source: stdnum/lv/pvn.py — valid: LV40003521600
			// Invalid: mutate last digit 0→1
			"LV40003521600, true",
			"LV40003521601, false",

			//
			// LV — Latvia (personal code, first digit 0-3)
			// Source: stdnum/lv/pvn.py — valid: LV16117519997  (compact of '161175-19997')
			// Invalid: mutate last digit 7→8
			"LV16117519997, true",
			"LV16117519998, false",

			//
			// MT — Malta
			// Source: stdnum/mt/vat.py — valid: MT11679112
			// Invalid: stdnum invalid example 11679113 → MT11679113
			"MT11679112, true",
			"MT11679113, false",

			//
			// NL — Netherlands
			// Source: stdnum/nl/btw.py — valid: NL004495445B01
			// Invalid: stdnum invalid example 123456789B90 → NL123456789B90
			"NL004495445B01, true",
			"NL123456789B90, false",

			//
			// NO — Norway
			// Source: stdnum/no/mva.py — valid: NO995525828MVA
			// Invalid: stdnum invalid example 995525829 → NO995525829MVA
			"NO995525828MVA, true",
			"NO995525829MVA, false",
			"NO995525828, false",

			//
			// PL — Poland
			// Source: stdnum/pl/nip.py — valid: PL8567346215
			// Invalid: mutate last digit 5→6
			"PL8567346215, true",
			"PL8567346216, false",

			//
			// PT — Portugal
			// Source: stdnum/pt/nif.py — valid: PT501964843
			// Invalid: mutate last digit 3→4
			"PT501964843, true",
			"PT501964844, false",

			//
			// RO — Romania
			// Source: stdnum/ro/cui.py — valid: RO18547290
			// Invalid: stdnum invalid example 18547291 → RO18547291
			"RO18547290, true",
			"RO18547291, false",

			//
			// SE — Sweden
			// Source: stdnum/se/vat.py — valid: SE123456789701
			// Invalid: mutate last 2 to "02" (must be "01")
			"SE123456789701, true",
			"SE123456789702, false",

			//
			// SI — Slovenia
			// Source: stdnum/si/ddv.py — valid: SI50223054
			// Invalid: stdnum invalid example 50223055 → SI50223055
			"SI50223054, true",
			"SI50223055, false",

			//
			// SK — Slovakia
			// Source: stdnum/sk/dph.py — valid: SK2022749619
			// Invalid: mutate last digit 9→8
			"SK2022749619, true",
			"SK2022749618, false",

			//
			// XI — Northern Ireland (uses GB algorithm)
			// Source: stdnum/gb/vat.py — use same valid GB number with XI prefix
			// XI980780684 with same 9-digit checksum as GB980780684
			"XI980780684, true",
			"XI802311781, false",

			//
			// GB — GD variant (government department 000-499)
			"GBGD001, true",
			"GBGD500, false",   // GD must be < 500

			//
			// GB — HA variant (health authority 500-999)
			"GBHA500, true",
			"GBHA499, false",   // HA must be >= 500
	})
	void perCountry_validAndInvalid(final String vatId, final boolean expectedValid)
	{
		assertThat(EUVatIdValidator.isValid(vatId.trim()))
				.as("vatId=%s", vatId.trim())
				.isEqualTo(expectedValid);
	}

	// ===================================================================
	// Structural format failures (prefix known, but wrong shape)
	// ===================================================================

	@ParameterizedTest
	@ValueSource(strings = {
			"AT12345678",      // missing U prefix
			"BE123456789",     // only 9 digit chars (needs 10 digits after BE prefix)
			"DE12345",         // too short
			"FRX12345678",     // only 8 SIREN digits (needs 9)
	})
	void wrongFormat_isInvalid(final String vatId)
	{
		assertThat(EUVatIdValidator.isValid(vatId)).isFalse();
	}

	// ===================================================================
	// Formatting tolerance with real valid numbers
	// ===================================================================

	@ParameterizedTest
	@ValueSource(strings = {
			"DE 136 695 976",    // spaces — DE valid number
			"DE136.695.976",     // dots
	})
	void spacesAndDots_normalised_thenValid(final String vatId)
	{
		assertThat(EUVatIdValidator.isValid(vatId)).isTrue();
	}
}
