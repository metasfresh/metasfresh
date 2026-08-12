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

import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Format + check-digit validator for EU VAT identification numbers (EU-27, Northern Ireland/XI,
 * United Kingdom/GB, Switzerland/CH, Norway/NO).
 *
 * <p>For each supported prefix the value must pass <em>both</em> the structural regex
 * <em>and</em> the country-specific check-digit algorithm.  Either failure returns {@code false}.
 * For CH and NO the legally-mandatory VAT marker is required (CH: {@code MWST}/{@code TVA}/{@code IVA}/{@code TPV};
 * NO: {@code MVA}) — the bare UID / org-number is not a VAT number and is rejected.
 * Algorithms are ported from python-stdnum
 * (<a href="https://github.com/arthurdejong/python-stdnum">arthurdejong/python-stdnum</a>).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EUVatIdValidator
{
	private static final Logger logger = LogManager.getLogger(EUVatIdValidator.class);

	// ------------------------------------------------------------------ patterns

	/**
	 * VAT number structural formats (31 prefixes: EU-27 + XI + GB + CH + NO).
	 * Keys are the 2-letter country prefix (uppercase); patterns match the full normalised string.
	 * Normalised = trimmed, spaces/dots/hyphens removed, uppercased.
	 */
	private static final Map<String, Pattern> PATTERNS_BY_PREFIX = ImmutableMap.<String, Pattern>builder()
			.put("AT", Pattern.compile("ATU\\d{8}"))
			.put("BE", Pattern.compile("BE[01]\\d{9}"))
			.put("BG", Pattern.compile("BG\\d{9,10}"))
			.put("CH", Pattern.compile("CHE\\d{9}(MWST|TVA|IVA|TPV)"))       // Swiss VAT no.: CHE + 9 digits + required VAT marker (MWST/TVA/IVA/TPV); bare UID is not a VAT number
			.put("CY", Pattern.compile("CY\\d{8}[A-Z]"))
			.put("CZ", Pattern.compile("CZ\\d{8,10}"))
			.put("DE", Pattern.compile("DE\\d{9}"))
			.put("DK", Pattern.compile("DK\\d{8}"))
			.put("EE", Pattern.compile("EE\\d{9}"))
			.put("EL", Pattern.compile("EL\\d{9}"))
			.put("ES", Pattern.compile("ES[A-Z0-9]\\d{7}[A-Z0-9]"))
			.put("FI", Pattern.compile("FI\\d{8}"))
			.put("FR", Pattern.compile("FR[A-Z0-9]{2}\\d{9}"))
			.put("GB", Pattern.compile("GB(\\d{9}|\\d{12}|GD\\d{3}|HA\\d{3})"))
			.put("HR", Pattern.compile("HR\\d{11}"))
			.put("HU", Pattern.compile("HU\\d{8}"))
			.put("IE", Pattern.compile("IE(\\d{7}[A-W]|\\d[A-Z0-9+*]\\d{5}[A-W]|\\d{7}[A-W][AH])"))
			.put("IT", Pattern.compile("IT\\d{11}"))
			.put("LT", Pattern.compile("LT(\\d{9}|\\d{12})"))
			.put("LU", Pattern.compile("LU\\d{8}"))
			.put("LV", Pattern.compile("LV\\d{11}"))
			.put("MT", Pattern.compile("MT\\d{8}"))
			.put("NL", Pattern.compile("NL[A-Z0-9+*]{10}\\d{2}"))
			.put("NO", Pattern.compile("NO\\d{9}MVA"))
			.put("PL", Pattern.compile("PL\\d{10}"))
			.put("PT", Pattern.compile("PT\\d{9}"))
			.put("RO", Pattern.compile("RO\\d{2,10}"))
			.put("SE", Pattern.compile("SE\\d{12}"))
			.put("SI", Pattern.compile("SI\\d{8}"))
			.put("SK", Pattern.compile("SK\\d{10}"))
			.put("XI", Pattern.compile("XI(\\d{9}|\\d{12}|GD\\d{3}|HA\\d{3})"))
			.build();

	// ------------------------------------------------------------------ check-digit validators (per prefix)

	private static final Map<String, Predicate<String>> CHECK_DIGIT_VALIDATORS = ImmutableMap.<String, Predicate<String>>builder()
			.put("AT", EUVatIdValidator::checkAt)
			.put("BE", EUVatIdValidator::checkBe)
			.put("BG", EUVatIdValidator::checkBg)
			.put("CH", EUVatIdValidator::checkCh)
			.put("CY", EUVatIdValidator::checkCy)
			.put("CZ", EUVatIdValidator::checkCz)
			.put("DE", EUVatIdValidator::checkDe)
			.put("DK", EUVatIdValidator::checkDk)
			.put("EE", EUVatIdValidator::checkEe)
			.put("EL", EUVatIdValidator::checkEl)
			.put("ES", EUVatIdValidator::checkEs)
			.put("FI", EUVatIdValidator::checkFi)
			.put("FR", EUVatIdValidator::checkFr)
			.put("GB", EUVatIdValidator::checkGb)
			.put("HR", EUVatIdValidator::checkHr)
			.put("HU", EUVatIdValidator::checkHu)
			.put("IE", EUVatIdValidator::checkIe)
			.put("IT", EUVatIdValidator::checkIt)
			.put("LT", EUVatIdValidator::checkLt)
			.put("LU", EUVatIdValidator::checkLu)
			.put("LV", EUVatIdValidator::checkLv)
			.put("MT", EUVatIdValidator::checkMt)
			.put("NL", EUVatIdValidator::checkNl)
			.put("NO", EUVatIdValidator::checkNo)
			.put("PL", EUVatIdValidator::checkPl)
			.put("PT", EUVatIdValidator::checkPt)
			.put("RO", EUVatIdValidator::checkRo)
			.put("SE", EUVatIdValidator::checkSe)
			.put("SI", EUVatIdValidator::checkSi)
			.put("SK", EUVatIdValidator::checkSk)
			.put("XI", EUVatIdValidator::checkGb)   // XI uses the GB algorithm (Northern Ireland)
			.build();

	// ------------------------------------------------------------------ public API

	/**
	 * Returns {@code true} if the given VAT-ID value is acceptable to store, {@code false} if it must be rejected.
	 *
	 * <ul>
	 *   <li>Null, empty, blank, or any value that normalises to fewer than two characters is always
	 *       accepted ({@code true}).</li>
	 *   <li>Any other value whose first two normalised characters are not one of the supported country
	 *       prefixes is <em>rejected</em> ({@code false}).</li>
	 *   <li>Values with a recognised prefix must pass both the structural regex and the
	 *       country-specific check-digit algorithm.</li>
	 * </ul>
	 *
	 * <p>Normalisation applied before checking (does not mutate the input):
	 * trim → remove all spaces, dots, and hyphens → uppercase.
	 */
	public static boolean isValid(@Nullable final String vatId)
	{
		if (vatId == null || vatId.trim().isEmpty())
		{
			return true;
		}

		final String normalised = normalise(vatId);
		if (normalised.length() < 2)
		{
			return true;
		}

		final String prefix = normalised.substring(0, 2);
		final Pattern pattern = PATTERNS_BY_PREFIX.get(prefix);
		if (pattern == null)
		{
			return false;
		}

		if (!pattern.matcher(normalised).matches())
		{
			return false;
		}

		final Predicate<String> checkDigitValidator = CHECK_DIGIT_VALIDATORS.get(prefix);
		if (checkDigitValidator == null)
		{
			// Format passed, no check-digit function registered — accept
			return true;
		}

		try
		{
			return checkDigitValidator.test(normalised);
		}
		catch (final RuntimeException e)
		{
			// A check-digit function should never throw on regex-gated input; if one does it is a bug
			// in that algorithm (e.g. an off-by-one substring index). Reject the value but log so the
			// defect is visible rather than silently swallowed.
			logger.warn("Unexpected exception validating VAT-ID for prefix {} — treating as invalid", prefix, e);
			return false;
		}
	}

	// ------------------------------------------------------------------ normalisation

	/**
	 * Normalises a VAT-ID value: trims, removes spaces/dots/hyphens, uppercases.
	 */
	private static String normalise(final String vatId)
	{
		return vatId.trim()
				.replace(" ", "")
				.replace(".", "")
				.replace("-", "")
				.toUpperCase();
	}

	// ================================================================== CHECK-DIGIT ALGORITHMS
	// All algorithms ported from python-stdnum (https://github.com/arthurdejong/python-stdnum)
	// Input to each method is the full normalised string (including the country prefix).

	// ------------------------------------------------------------------ AT (Austria)
	// Source: stdnum/at/uid.py — check = (6 - luhn_checksum(U_digit_1..7)) % 10
	// Valid example: ATU13585627

	private static boolean checkAt(final String n)
	{
		// n = "ATU" + 8 digits; last digit is the check digit
		// compact form (no AT prefix) = "U" + 8 digits (7 body + 1 check digit = 9 chars total);
		// calc_check_digit receives only the body ("U" + 7 digits = 8 chars) and calls luhn.checksum(number[1:])
		// i.e. luhn.checksum("U1358562"[1:]) = luhn.checksum("1358562") — 7 digits
		final String digits = n.substring(2); // "U" + 8 digits
		final String body = digits.substring(1, 8); // 7 digits: positions [1..7] of the "U########" string
		final int check = Math.floorMod(6 - luhnChecksum(body), 10);
		return check == Character.getNumericValue(digits.charAt(8));
	}

	// ------------------------------------------------------------------ BE (Belgium)
	// Source: stdnum/be/vat.py — (int(number[:-2]) + int(number[-2:])) % 97 == 0
	// Valid example: BE0428759497

	private static boolean checkBe(final String n)
	{
		// n = "BE" + 10 digits
		final String digits = n.substring(2); // 10 digits
		final long main = Long.parseLong(digits.substring(0, 8));
		final long check = Long.parseLong(digits.substring(8));
		return (main + check) % 97 == 0;
	}

	// ------------------------------------------------------------------ BG (Bulgaria)
	// Source: stdnum/bg/vat.py
	// Legal entities (9 digits): weights (1..8) mod 11; if 10 retry with shifted weights (+2)
	// 10-digit: weights (4,3,2,7,6,5,4,3,2); check = (11 - sum%11) % 11
	// Valid example (9-digit legal): BG175074752

	private static boolean checkBg(final String n)
	{
		final String digits = n.substring(2); // 9 or 10 digits
		if (digits.length() == 9)
		{
			return checkBgLegal(digits);
		}
		else
		{
			return checkBgPersonal(digits);
		}
	}

	private static boolean checkBgLegal(final String digits)
	{
		// weights = (1,2,3,4,5,6,7,8) for positions 0-7
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			sum += (i + 1) * Character.getNumericValue(digits.charAt(i));
		}
		int check = sum % 11;
		if (check == 10)
		{
			sum = 0;
			for (int i = 0; i < 8; i++)
			{
				sum += (i + 3) * Character.getNumericValue(digits.charAt(i));
			}
			check = sum % 11 % 10;
		}
		return check == Character.getNumericValue(digits.charAt(8));
	}

	private static boolean checkBgPersonal(final String digits)
	{
		// weights = (4,3,2,7,6,5,4,3,2) for first 9 digits
		final int[] weights = { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
		int sum = 0;
		for (int i = 0; i < 9; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		final int check = (11 - sum % 11) % 11;
		return check == Character.getNumericValue(digits.charAt(9));
	}

	// ------------------------------------------------------------------ CH (Switzerland)
	// Source: stdnum/ch/uid.py — weights = (5,4,3,2,7,6,5,4); check = (11 - sum%11) % 11
	// Valid example: CHE100155212

	private static boolean checkCh(final String n)
	{
		// n = "CHE" + 9 digits (+ optional MWST/TVA/IVA suffix) → take exactly the 9 digits
		final String digits = n.substring(3, 12);
		final int[] weights = { 5, 4, 3, 2, 7, 6, 5, 4 };
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		// double-mod: when sum%11==0, (11-0)%11==0 gives check digit 0 correctly
		final int check = (11 - sum % 11) % 11;
		return check == Character.getNumericValue(digits.charAt(8));
	}

	// ------------------------------------------------------------------ CY (Cyprus)
	// Source: stdnum/cy/vat.py
	// Odd positions (0-indexed even): translation table; even positions (0-indexed odd): face value
	// result = sum % 26 → maps to letter A-Z; first two digits must not be "12"
	// Valid example: CY10259033P

	private static final int[] CY_ODD_TRANS = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21 };

	private static boolean checkCy(final String n)
	{
		// n = "CY" + 8 digits + 1 letter
		final String body = n.substring(2); // 8 digits + letter
		if (body.startsWith("12"))
		{
			return false;
		}
		final String digits = body.substring(0, 8);
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			final int d = Character.getNumericValue(digits.charAt(i));
			if (i % 2 == 0)
			{
				sum += CY_ODD_TRANS[d];
			}
			else
			{
				sum += d;
			}
		}
		final char expected = (char) ('A' + (sum % 26));
		return expected == body.charAt(8);
	}

	// ------------------------------------------------------------------ CZ (Czech Republic)
	// Source: stdnum/cz/dic.py — legal (8 digits), special (9 starting with 6), individual birth number (9-10)
	// Valid example: CZ25123891 (8-digit legal entity)

	private static boolean checkCz(final String n)
	{
		final String digits = n.substring(2);
		final int len = digits.length();
		if (len == 8)
		{
			return checkCzLegal(digits);
		}
		else if (len == 9 && digits.charAt(0) == '6')
		{
			return checkCzSpecial(digits);
		}
		else if (len == 9)
		{
			// Pre-1954 birth number — no check digit
			return true;
		}
		else if (len == 10)
		{
			// Post-1954 birth number: entire 10-digit number % 11 == 0
			final long num = Long.parseLong(digits);
			return num % 11 == 0;
		}
		return false;
	}

	private static boolean checkCzLegal(final String digits)
	{
		// (11 - sum((8-i)*d for i in 0..6) % 11) % 10 == digits[7]
		int sum = 0;
		for (int i = 0; i < 7; i++)
		{
			sum += (8 - i) * Character.getNumericValue(digits.charAt(i));
		}
		final int check = (11 - sum % 11) % 10;
		return check == Character.getNumericValue(digits.charAt(7));
	}

	private static boolean checkCzSpecial(final String digits)
	{
		// 9 digits starting with '6': check on middle 7 digits (positions 1-7)
		final String middle = digits.substring(1, 8);
		int sum = 0;
		for (int i = 0; i < 7; i++)
		{
			sum += (8 - i) * Character.getNumericValue(middle.charAt(i));
		}
		final int rem = sum % 11;
		// Python: (8 - (10 - rem) % 11) % 10  — use Math.floorMod to match Python's floor-division % semantics
		// rem = sum % 11 ∈ [0..10], so (10 - rem) ∈ [0..10] — always non-negative.
		// However when rem ∈ {0, 1}: inner = (10-rem) % 11 ∈ {9, 10}, making (8 - inner) ∈ {-2, -1} (negative).
		// Math.floorMod handles those negative values correctly (e.g. floorMod(-2, 10) = 8).
		final int check = Math.floorMod(8 - (10 - rem) % 11, 10);
		return check == Character.getNumericValue(digits.charAt(8));
	}

	// ------------------------------------------------------------------ DE (Germany)
	// Source: stdnum/de/vat.py — ISO 7064 Mod 11,10 on 9 digits; first digit != 0
	// Valid example: DE136695976

	private static boolean checkDe(final String n)
	{
		// n = "DE" + 9 digits
		final String digits = n.substring(2);
		if (digits.charAt(0) == '0')
		{
			return false;
		}
		return mod1110IsValid(digits);
	}

	// ------------------------------------------------------------------ DK (Denmark)
	// Source: stdnum/dk/cvr.py — weights = (2,7,6,5,4,3,2,1); sum % 11 == 0; first digit != 0
	// Valid example: DK13585628

	private static boolean checkDk(final String n)
	{
		final String digits = n.substring(2); // 8 digits
		if (digits.charAt(0) == '0')
		{
			return false;
		}
		final int[] weights = { 2, 7, 6, 5, 4, 3, 2, 1 };
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		return sum % 11 == 0;
	}

	// ------------------------------------------------------------------ EE (Estonia)
	// Source: stdnum/ee/kmkr.py — weights = (3,7,1,3,7,1,3,7,1); sum % 10 == 0
	// Valid example: EE100594102

	private static boolean checkEe(final String n)
	{
		final String digits = n.substring(2); // 9 digits
		final int[] weights = { 3, 7, 1, 3, 7, 1, 3, 7, 1 };
		int sum = 0;
		for (int i = 0; i < 9; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		return sum % 10 == 0;
	}

	// ------------------------------------------------------------------ EL (Greece)
	// Source: stdnum/gr/vat.py — running doubling checksum on first 8 digits
	// checksum = 0; for each of first 8: checksum = checksum*2 + d; result = checksum*2 % 11 % 10
	// Valid example: EL094259216

	private static boolean checkEl(final String n)
	{
		// n = "EL" + 9 digits
		final String digits = n.substring(2);
		int checksum = 0;
		for (int i = 0; i < 8; i++)
		{
			checksum = checksum * 2 + Character.getNumericValue(digits.charAt(i));
		}
		final int check = checksum * 2 % 11 % 10;
		return check == Character.getNumericValue(digits.charAt(8));
	}

	// ------------------------------------------------------------------ ES (Spain)
	// Source: stdnum/es/nif.py — delegates to cif/dni/nie depending on first character
	// Valid examples: ESB58378431 (CIF), ES54362315K (DNI), ESX2482300W (NIE)

	private static boolean checkEs(final String n)
	{
		// n = "ES" + 9 chars
		final String body = n.substring(2);
		final char first = body.charAt(0);
		if (Character.isLetter(first))
		{
			if (first == 'X' || first == 'Y' || first == 'Z')
			{
				return checkEsNie(body);
			}
			if (first == 'K' || first == 'L' || first == 'M')
			{
				return checkEsDniLike(body);
			}
			return checkEsCif(body);
		}
		else
		{
			return checkEsDni(body);
		}
	}

	// DNI: 8 digits + check letter (mod 23 → "TRWAGMYFPDXBNJZSQVHLCKE")
	private static final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

	private static boolean checkEsDni(final String body)
	{
		if (!Character.isLetter(body.charAt(8)))
		{
			return false;
		}
		final int num = Integer.parseInt(body.substring(0, 8));
		return DNI_LETTERS.charAt(num % 23) == body.charAt(8);
	}

	private static boolean checkEsDniLike(final String body)
	{
		// K/L/M prefix: check letter is computed on the 7 middle digits only
		if (!Character.isLetter(body.charAt(8)))
		{
			return false;
		}
		try
		{
			final int num = Integer.parseInt(body.substring(1, 8));
			return DNI_LETTERS.charAt(num % 23) == body.charAt(8);
		}
		catch (final NumberFormatException e)
		{
			return false;
		}
	}

	// NIE: X/Y/Z + 7 digits + check letter (replace X=0,Y=1,Z=2 then DNI check)
	private static boolean checkEsNie(final String body)
	{
		final char first = body.charAt(0);
		final int replaced = "XYZ".indexOf(first);
		final String asDigits = replaced + body.substring(1, 8);
		try
		{
			final int num = Integer.parseInt(asDigits);
			return DNI_LETTERS.charAt(num % 23) == body.charAt(8);
		}
		catch (final NumberFormatException e)
		{
			return false;
		}
	}

	// CIF: letter + 7 digits + check digit or letter; Luhn on positions 1-7, map → "JABCDEFGHI"
	private static boolean checkEsCif(final String body)
	{
		final String middle = body.substring(1, 8);
		try
		{
			Integer.parseInt(middle);
		}
		catch (final NumberFormatException e)
		{
			return false;
		}
		final int luhnDigit = luhnCalcCheckDigit(middle);
		final char luhnLetter = "JABCDEFGHI".charAt(luhnDigit);
		final char last = body.charAt(8);
		return last == (char) ('0' + luhnDigit) || last == luhnLetter;
	}

	// ------------------------------------------------------------------ FI (Finland)
	// Source: stdnum/fi/alv.py — weights = (7,9,10,5,8,4,2,1); sum % 11 == 0
	// Valid example: FI20774740

	private static boolean checkFi(final String n)
	{
		final String digits = n.substring(2); // 8 digits
		final int[] weights = { 7, 9, 10, 5, 8, 4, 2, 1 };
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		return sum % 11 == 0;
	}

	// ------------------------------------------------------------------ FR (France)
	// Source: stdnum/fr/tva.py — all-numeric or alphanumeric key check
	// Valid examples: FR40303265045, FRK7399859412, FR4Z123456782

	private static final String FR_ALPHABET = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";

	private static boolean checkFr(final String n)
	{
		// n = "FR" + 2-char key + 9-digit SIREN
		final String body = n.substring(2); // 11 chars
		final char c0 = body.charAt(0);
		final char c1 = body.charAt(1);
		final String siren = body.substring(2); // 9 digits

		final boolean c0IsDigit = Character.isDigit(c0);
		final boolean c1IsDigit = Character.isDigit(c1);

		if (c0IsDigit && c1IsDigit)
		{
			// All-numeric: int(c0c1) == (int(siren + "12") % 97)
			final int key = Integer.parseInt(body.substring(0, 2));
			final long base = Long.parseLong(siren + "12");
			return key == (base % 97);
		}

		if (!FR_ALPHABET.contains(String.valueOf(c0)) || !FR_ALPHABET.contains(String.valueOf(c1)))
		{
			return false;
		}
		final int i0 = FR_ALPHABET.indexOf(c0);
		final int i1 = FR_ALPHABET.indexOf(c1);

		final int check;
		if (c0IsDigit)
		{
			check = i0 * 24 + i1 - 10;
		}
		else
		{
			check = i0 * 34 + i1 - 100;
		}

		try
		{
			final long sirenVal = Long.parseLong(siren);
			return (sirenVal + 1 + check / 11) % 11 == (check % 11);
		}
		catch (final NumberFormatException e)
		{
			return false;
		}
	}

	// ------------------------------------------------------------------ GB (United Kingdom)
	// Source: stdnum/gb/vat.py
	// Standard 9/12-digit: weights = (8,7,6,5,4,3,2,10,1); sum % 97 in {0, 42, 55} (or just 0 if first3 < 100)
	// GD000-GD499, HA500-HA999: range check only
	// Valid example: GB980780684

	private static boolean checkGb(final String n)
	{
		final String body = n.substring(2); // after "GB" or "XI"

		if (body.startsWith("GD") || body.startsWith("HA"))
		{
			final int num = Integer.parseInt(body.substring(2));
			if (body.startsWith("GD"))
			{
				return num < 500;
			}
			else
			{
				return num >= 500;
			}
		}

		// 9 or 12 digits — validate the first 9
		final String nineDigits = body.substring(0, 9);
		final int[] weights = { 8, 7, 6, 5, 4, 3, 2, 10, 1 };
		int sum = 0;
		for (int i = 0; i < 9; i++)
		{
			sum += weights[i] * Character.getNumericValue(nineDigits.charAt(i));
		}
		final int rem = sum % 97;
		final int first3 = Integer.parseInt(nineDigits.substring(0, 3));
		if (first3 < 100)
		{
			return rem == 0;
		}
		return rem == 0 || rem == 42 || rem == 55;
	}

	// ------------------------------------------------------------------ HR (Croatia)
	// Source: stdnum/hr/oib.py — ISO 7064 Mod 11, 10 on 11 digits
	// Valid example: HR33392005961

	private static boolean checkHr(final String n)
	{
		final String digits = n.substring(2); // 11 digits
		return mod1110IsValid(digits);
	}

	// ------------------------------------------------------------------ HU (Hungary)
	// Source: stdnum/hu/anum.py — weights = (9,7,3,1,9,7,3,1); sum % 10 == 0
	// Valid example: HU12892312

	private static boolean checkHu(final String n)
	{
		final String digits = n.substring(2); // 8 digits
		final int[] weights = { 9, 7, 3, 1, 9, 7, 3, 1 };
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		return sum % 10 == 0;
	}

	// ------------------------------------------------------------------ IE (Ireland)
	// Source: stdnum/ie/vat.py — mod23; alphabet = "WABCDEFGHIJKLMNOPQRSTUV"
	// Valid examples: IE6433435F, IE6433435OA, IE8D79739I

	private static final String IE_ALPHABET = "WABCDEFGHIJKLMNOPQRSTUV";

	private static boolean checkIe(final String n)
	{
		final String body = n.substring(2); // 8 or 9 chars
		final boolean isOldFormat = !Character.isDigit(body.charAt(1));

		// Build the 7-digit computation string and the position of the check letter.
		// Old format (e.g. "8D79739I"): char[1] is a letter.
		//   python-stdnum zfill(7)-pads "0" + body[2:7] + body[0] to get the 7 computation digits.
		//   The check letter stays at body[7].
		// New format 8 chars (e.g. "6433435F"): the 7 computation digits are body[0:7], check = body[7].
		// New format 9 chars (e.g. "6433435OA"): computation digits are body[0:7], extra weight body[8], check = body[7].
		final String sevenDigits;
		if (isOldFormat)
		{
			// "0" + body[2..6] (5 chars) + body[0] = 7 chars; check letter is body[7]
			sevenDigits = "0" + body.substring(2, 7) + body.charAt(0);
		}
		else
		{
			sevenDigits = body.substring(0, 7);
		}

		// Compute check via mod23
		int sum = 0;
		for (int i = 0; i < 7; i++)
		{
			sum += (8 - i) * Character.getNumericValue(sevenDigits.charAt(i));
		}
		// 9-char new format: extra trailing letter carries a 9× weight
		if (!isOldFormat && body.length() == 9)
		{
			final char extra = body.charAt(8);
			sum += 9 * IE_ALPHABET.indexOf(extra);
		}
		final char expected = IE_ALPHABET.charAt(sum % 23);
		// Check letter is always at body[7] regardless of format
		return expected == body.charAt(7);
	}

	// ------------------------------------------------------------------ IT (Italy)
	// Source: stdnum/it/iva.py — Luhn on 11 digits; province code check
	// Valid example: IT00743110157

	private static boolean checkIt(final String n)
	{
		// n = "IT" + 11 digits
		final String digits = n.substring(2);
		// First 7 digits must not all be zero
		if (digits.startsWith("0000000"))
		{
			return false;
		}
		// Province code (digits 8-10, 0-indexed 7-9): must be 001-100, 120, 121, 888, or 999
		final int prov = Integer.parseInt(digits.substring(7, 10));
		if (!((prov >= 1 && prov <= 100) || prov == 120 || prov == 121 || prov == 888 || prov == 999))
		{
			return false;
		}
		return luhnIsValid(digits);
	}

	// ------------------------------------------------------------------ LT (Lithuania)
	// Source: stdnum/lt/pvm.py — 9-digit (pos 8 must be '1') or 12-digit (pos 11 must be '1')
	// primary weights (1 + i%9), fallback if check==10
	// Valid examples: LT119511515, LT100001919017

	private static boolean checkLt(final String n)
	{
		final String digits = n.substring(2); // 9 or 12 digits
		if (digits.length() == 9)
		{
			if (digits.charAt(7) != '1')
			{
				return false;
			}
			return checkLtChecksum(digits, 8);
		}
		else
		{
			if (digits.charAt(10) != '1')
			{
				return false;
			}
			return checkLtChecksum(digits, 11);
		}
	}

	private static boolean checkLtChecksum(final String digits, final int checkPos)
	{
		int sum = 0;
		for (int i = 0; i < checkPos; i++)
		{
			sum += (1 + i % 9) * Character.getNumericValue(digits.charAt(i));
		}
		int check = sum % 11;
		if (check == 10)
		{
			sum = 0;
			for (int i = 0; i < checkPos; i++)
			{
				sum += (1 + (i + 2) % 9) * Character.getNumericValue(digits.charAt(i));
			}
			check = sum % 11 % 10;
		}
		return check == Character.getNumericValue(digits.charAt(checkPos));
	}

	// ------------------------------------------------------------------ LU (Luxembourg)
	// Source: stdnum/lu/tva.py — check = int(first_6) % 89; last 2 digits
	// Valid example: LU15027442

	private static boolean checkLu(final String n)
	{
		final String digits = n.substring(2); // 8 digits
		final int base = Integer.parseInt(digits.substring(0, 6));
		final int check = base % 89;
		final int lastTwo = Integer.parseInt(digits.substring(6));
		return check == lastTwo;
	}

	// ------------------------------------------------------------------ LV (Latvia)
	// Source: stdnum/lv/pvn.py
	// Legal entity (first digit >= 4): weights = (9,1,4,8,3,10,2,5,7,6,1); sum % 11 == 3
	// Personal (first digit 0-3): weights = (10,5,8,4,2,1,6,3,7,9); check = (1 + sum) % 11 % 10
	// Valid example: LV40003521600 (legal entity)

	private static boolean checkLv(final String n)
	{
		final String digits = n.substring(2); // 11 digits
		final int firstDigit = Character.getNumericValue(digits.charAt(0));
		if (firstDigit >= 4)
		{
			return checkLvLegal(digits);
		}
		else
		{
			return checkLvPersonal(digits);
		}
	}

	private static boolean checkLvLegal(final String digits)
	{
		final int[] weights = { 9, 1, 4, 8, 3, 10, 2, 5, 7, 6, 1 };
		int sum = 0;
		for (int i = 0; i < 11; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		return sum % 11 == 3;
	}

	private static boolean checkLvPersonal(final String digits)
	{
		final int[] weights = { 10, 5, 8, 4, 2, 1, 6, 3, 7, 9 };
		int sum = 1;
		for (int i = 0; i < 10; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		final int check = sum % 11 % 10;
		return check == Character.getNumericValue(digits.charAt(10));
	}

	// ------------------------------------------------------------------ MT (Malta)
	// Source: stdnum/mt/vat.py — weights = (3,4,6,7,8,9,10,1); sum % 37 == 0; first digit != 0
	// Valid example: MT11679112

	private static boolean checkMt(final String n)
	{
		final String digits = n.substring(2); // 8 digits
		if (digits.charAt(0) == '0')
		{
			return false;
		}
		final int[] weights = { 3, 4, 6, 7, 8, 9, 10, 1 };
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		return sum % 37 == 0;
	}

	// ------------------------------------------------------------------ NL (Netherlands)
	// Source: stdnum/nl/btw.py — BSN (mod-11) on first 9 digits, or MOD 97-10 on full "NL"+12 chars
	// Valid example: NL004495445B01

	private static boolean checkNl(final String n)
	{
		// n = "NL" + 10 chars (9 alnum + "B" ... see regex) + 2 digits
		// Check: BSN on first 9 if all digits, else MOD 97-10
		final String body = n.substring(2); // 12 chars
		final String first9 = body.substring(0, 9);
		if (first9.chars().allMatch(Character::isDigit))
		{
			if (bsnIsValid(first9))
			{
				return true;
			}
		}
		// Fallback: MOD 97-10 on "NL" + body (convert letters to numeric per ISO 7064 standard)
		return nlMod9710IsValid(n);
	}

	// BSN: (sum((9-i)*d for i in 0..7) - last_digit) % 11 == 0 and > 0
	private static boolean bsnIsValid(final String digits)
	{
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			sum += (9 - i) * Character.getNumericValue(digits.charAt(i));
		}
		sum -= Character.getNumericValue(digits.charAt(8));
		return sum % 11 == 0 && sum > 0;
	}

	// NL MOD 97-10: convert each char (digit = face, letter = 10 + position A=0..Z=25), compute mod 97 == 1
	private static boolean nlMod9710IsValid(final String n)
	{
		final StringBuilder sb = new StringBuilder();
		for (final char c : n.toCharArray())
		{
			if (Character.isDigit(c))
			{
				sb.append(c);
			}
			else if (Character.isLetter(c))
			{
				sb.append(10 + (c - 'A'));
			}
		}
		try
		{
			return new BigInteger(sb.toString()).mod(BigInteger.valueOf(97)).intValue() == 1;
		}
		catch (final NumberFormatException e)
		{
			return false;
		}
	}

	// ------------------------------------------------------------------ NO (Norway)
	// Source: stdnum/no/mva.py → stdnum/no/orgnr.py — weights = (3,2,7,6,5,4,3,2,1); sum % 11 == 0
	// Valid example: NO995525828MVA

	private static boolean checkNo(final String n)
	{
		// n = "NO" + 9 digits + "MVA"
		final String digits = n.substring(2, 11); // 9 digits
		final int[] weights = { 3, 2, 7, 6, 5, 4, 3, 2, 1 };
		int sum = 0;
		for (int i = 0; i < 9; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		return sum % 11 == 0;
	}

	// ------------------------------------------------------------------ PL (Poland)
	// Source: stdnum/pl/nip.py — weights = (6,5,7,2,3,4,5,6,7,-1); sum % 11 == 0
	// Valid example: PL8567346215

	private static boolean checkPl(final String n)
	{
		final String digits = n.substring(2); // 10 digits
		final int[] weights = { 6, 5, 7, 2, 3, 4, 5, 6, 7, -1 };
		int sum = 0;
		for (int i = 0; i < 10; i++)
		{
			sum += weights[i] * Character.getNumericValue(digits.charAt(i));
		}
		return sum % 11 == 0;
	}

	// ------------------------------------------------------------------ PT (Portugal)
	// Source: stdnum/pt/nif.py — weights descending 9..1; check = (11 - sum%11) % 11 % 10; first != 0
	// Valid example: PT501964843

	private static boolean checkPt(final String n)
	{
		final String digits = n.substring(2); // 9 digits
		if (digits.charAt(0) == '0')
		{
			return false;
		}
		int sum = 0;
		for (int i = 0; i < 8; i++)
		{
			sum += (9 - i) * Character.getNumericValue(digits.charAt(i));
		}
		final int check = (11 - sum % 11) % 11 % 10;
		return check == Character.getNumericValue(digits.charAt(8));
	}

	// ------------------------------------------------------------------ RO (Romania)
	// Source: stdnum/ro/cui.py — pad to 10, weights = (7,5,3,2,1,7,5,3,2); check = (10*sum%11)%10; first != 0
	// Valid example: RO18547290

	private static boolean checkRo(final String n)
	{
		final String digits = n.substring(2); // 2-10 digits
		if (digits.charAt(0) == '0')
		{
			return false;
		}
		// Pad to length 10 on the left with zeros
		final String padded = String.format("%010d", Long.parseLong(digits));
		final int[] weights = { 7, 5, 3, 2, 1, 7, 5, 3, 2 };
		int sum = 0;
		for (int i = 0; i < 9; i++)
		{
			sum += weights[i] * Character.getNumericValue(padded.charAt(i));
		}
		final int check = (10 * sum % 11) % 10;
		return check == Character.getNumericValue(padded.charAt(9));
	}

	// ------------------------------------------------------------------ SE (Sweden)
	// Source: stdnum/se/vat.py → stdnum/se/orgnr.py → Luhn on first 10 digits; last 2 must be "01"
	// Valid example: SE123456789701

	private static boolean checkSe(final String n)
	{
		// n = "SE" + 12 digits; last 2 must be "01"
		final String digits = n.substring(2); // 12 digits
		if (!digits.endsWith("01"))
		{
			return false;
		}
		return luhnIsValid(digits.substring(0, 10));
	}

	// ------------------------------------------------------------------ SI (Slovenia)
	// Source: stdnum/si/ddv.py — check = (11 - sum((8-i)*d)) % 11; must not start with 0; result != 10
	// Valid example: SI50223054

	private static boolean checkSi(final String n)
	{
		final String digits = n.substring(2); // 8 digits
		if (digits.charAt(0) == '0')
		{
			return false;
		}
		int sum = 0;
		for (int i = 0; i < 7; i++)
		{
			sum += (8 - i) * Character.getNumericValue(digits.charAt(i));
		}
		final int check = (11 - sum % 11) % 11;
		if (check == 10)
		{
			return false;
		}
		return check == Character.getNumericValue(digits.charAt(7));
	}

	// ------------------------------------------------------------------ SK (Slovakia)
	// Source: stdnum/sk/dph.py — int(number) % 11 == 0; first != 0; 3rd digit in {2,3,4,7,8,9}
	// Valid example: SK2022749619

	private static boolean checkSk(final String n)
	{
		final String digits = n.substring(2); // 10 digits
		if (digits.charAt(0) == '0')
		{
			return false;
		}
		final char third = digits.charAt(2);
		if ("234789".indexOf(third) < 0)
		{
			return false;
		}
		return Long.parseLong(digits) % 11 == 0;
	}

	// ================================================================== SHARED UTILITIES

	// ------------------------------------------------------------------ ISO 7064 Mod 11, 10
	// Source: stdnum/iso7064/mod_11_10.py
	// checksum(): check = 5; for each digit: check = ((check==0?10:check)*2 % 11 + d) % 10; valid if check == 1

	private static boolean mod1110IsValid(final String digits)
	{
		int check = 5;
		for (int i = 0; i < digits.length(); i++)
		{
			check = ((check == 0 ? 10 : check) * 2 % 11 + Character.getNumericValue(digits.charAt(i))) % 10;
		}
		return check == 1;
	}

	// ------------------------------------------------------------------ Luhn

	/**
	 * Returns true if the given all-digit string passes the Luhn check.
	 */
	private static boolean luhnIsValid(final String digits)
	{
		int total = 0;
		final int len = digits.length();
		for (int i = 0; i < len; i++)
		{
			int d = Character.getNumericValue(digits.charAt(len - 1 - i));
			if (i % 2 == 1)
			{
				d *= 2;
				if (d > 9)
				{
					d -= 9;
				}
			}
			total += d;
		}
		return total % 10 == 0;
	}

	/**
	 * Computes the Luhn check digit for the given digit string (without check digit).
	 * Used for ES/CIF and AT.
	 */
	private static int luhnCalcCheckDigit(final String digits)
	{
		int total = 0;
		final int len = digits.length();
		for (int i = 0; i < len; i++)
		{
			int d = Character.getNumericValue(digits.charAt(len - 1 - i));
			if (i % 2 == 0)
			{
				d *= 2;
				if (d > 9)
				{
					d -= 9;
				}
			}
			total += d;
		}
		return (10 - total % 10) % 10;
	}

	/**
	 * Luhn checksum of the given digits (result mod 10; 0 means valid for a full number with check digit).
	 * Used by AT: compute checksum on the body digits, then check = (6 - result) % 10.
	 */
	private static int luhnChecksum(final String digits)
	{
		int total = 0;
		final int len = digits.length();
		for (int i = 0; i < len; i++)
		{
			int d = Character.getNumericValue(digits.charAt(len - 1 - i));
			if (i % 2 == 1)
			{
				d *= 2;
				if (d > 9)
				{
					d -= 9;
				}
			}
			total += d;
		}
		return total % 10;
	}
}
