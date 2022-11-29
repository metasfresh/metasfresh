/**
 * 
 */
package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.sepa.api.IBBANStructureBL;
import de.metas.payment.sepa.api.IIBANValidationBL;
import de.metas.payment.sepa.wrapper.BBANStructure;
import de.metas.payment.sepa.wrapper.BBANStructureEntry;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

/**
 * @author cg
 *
 */
public class IBANValidationBL implements IIBANValidationBL
{
	private static final AdMessageKey MSG_IBAN_REFORMATTED = AdMessageKey.of("IBAN_Reformatted");

	/* package */ IMsgBL msgBL = Services.get(IMsgBL.class);

	public static final String DEFAULT_CHECK_DIGIT = "00";

	/* package */ static final int MOD = 97;
	/* package */ static final long MAX = 999999999;

	/* package */ static final int COUNTRY_CODE_INDEX = 0;
	/* package */ static final int COUNTRY_CODE_LENGTH = 2;
	/* package */ static final int CHECK_DIGIT_INDEX = COUNTRY_CODE_LENGTH;
	/* package */ static final int CHECK_DIGIT_LENGTH = 2;
	/* package */ static final int BBAN_INDEX = CHECK_DIGIT_INDEX + CHECK_DIGIT_LENGTH;

	@Override
	public void validate(final String iban)
	{
		validateEmpty(iban);
		validateCountryCode(iban);
		validateCheckDigitPresence(iban);

		final BBANStructure structure = getBBANCode(iban);

		validateBbanLength(iban, structure);
		validateBbanEntries(iban, structure);

		validateCheckDigit(iban);
	}
	
	@Override
	public String calculateCheckDigit(final String iban)
	{
		final String reformattedIban = replaceCheckDigit(iban, DEFAULT_CHECK_DIGIT);
		final int modResult = calculateMod(reformattedIban);
		final int checkDigitIntValue = (98 - modResult);
		final String checkDigit = Integer.toString(checkDigitIntValue);
		return checkDigitIntValue > 9 ? checkDigit : "0" + checkDigit;
	}

	@Override
	public void validateCheckDigit(final String iban)
	{
		final String checkDigit = getCheckDigit(iban);
		final String expectedCheckDigit = calculateCheckDigit(iban);
		Check.assume(checkDigit.equals(expectedCheckDigit), msgBL.getMsg(Env.getCtx(), "IBAN_Invalid_Check_Digit", new Object[] { checkDigit, expectedCheckDigit }));
		Check.assume(ISO7064Mod97_10(iban) == 1, msgBL.getMsg(Env.getCtx(), "IBAN_Invalid"));
	}
	
	/**
	 * Other implementation of modulo 97
	 */
	/* package */ int ISO7064Mod97_10(final String iban)
	{
		// Replace the two check digits by 00 and Move the four initial characters to the end of the string
		String reformattedIban = getBBAN(iban) + getCountryCodeAndCheckDigit(iban);
		// Replace the letters in the string with digits, expanding the string as necessary
		for (int i = 0; i <= 25; i++)
		{

			while (reformattedIban.indexOf(fromCharCode(i + 65)) != -1)
			{
				reformattedIban = reformattedIban.replace(fromCharCode(i + 65), String.valueOf(i + 10));
			}
		}

		// Calculate mod-97
		String remainer = String.valueOf(Integer.parseInt(reformattedIban.substring(0, 9)) % 97);
		reformattedIban = reformattedIban.substring(9);

		while (reformattedIban.length() >= 1)
		{
			if (reformattedIban.length() >= 7)
			{
				remainer = String.valueOf(Integer.parseInt(remainer + reformattedIban.substring(0, 7)) % 97);
				reformattedIban = reformattedIban.substring(7);
			}
			else
			{
				remainer = String.valueOf(Integer.parseInt(remainer + reformattedIban.substring(0)) % 97);
				break;
			}

		}
		return Integer.parseInt(remainer);
	}

	
	/**
	 * Calculates <a href="http://en.wikipedia.org/wiki/ISO_13616#Modulo_operation_on_IBAN">Iban Modulo</a>.
	 *
	 * @param iban String value
	 * @return modulo 97
	 */
	/* package */ int calculateMod(final String iban)
	{
		// Replace the two check digits by 00 and Move the four initial characters to the end of the string
		final String reformattedIban = getBBAN(iban) + getCountryCodeAndCheckDigit(iban);

		// Replace the letters in the string with digits, expanding the string as necessary
		// And Calculate mod-97
		long total = 0;
		for (int i = 0; i < reformattedIban.length(); i++)
		{
			final int numericValue = Character.getNumericValue(reformattedIban.charAt(i));
			if (numericValue < 0 || numericValue > 35)
			{
				throw new AdempiereException(MSG_IBAN_REFORMATTED, reformattedIban, reformattedIban.charAt(i));
			}
			total = (numericValue > 9 ? total * 100 : total * 10) + numericValue;

			if (total > MAX)
			{
				total = (total % MOD);
			}

		}
		return (int)(total % MOD);
	}	

	/**
	 * Returns iban's check digit.
	 *
	 * @param iban String
	 * @return checkDigit String
	 */
	/* package */ String getCheckDigit(final String iban)
	{
		return iban.substring(CHECK_DIGIT_INDEX,
				CHECK_DIGIT_INDEX + CHECK_DIGIT_LENGTH);
	}

	/**
	 * Returns iban's country code.
	 *
	 * @param iban String
	 * @return countryCode String
	 */
	/* package */ String getCountryCode(final String iban)
	{
		return iban.substring(COUNTRY_CODE_INDEX,
				COUNTRY_CODE_INDEX + COUNTRY_CODE_LENGTH);
	}

	/**
	 * Returns iban's country code and check digit.
	 *
	 * @param iban String
	 * @return countryCodeAndCheckDigit String
	 */
	/* package */ String getCountryCodeAndCheckDigit(final String iban)
	{
		return iban.substring(COUNTRY_CODE_INDEX,
				COUNTRY_CODE_INDEX + COUNTRY_CODE_LENGTH + CHECK_DIGIT_LENGTH);
	}

	/**
	 * Returns iban's bban (Basic Bank Account Number).
	 *
	 * @param iban String
	 * @return bban String
	 */
	/* package */ String getBBAN(final String iban)
	{
		return iban.substring(BBAN_INDEX);
	}

	/**
	 * Returns an iban with replaced check digit.
	 *
	 * @param iban The iban
	 * @return The iban without the check digit
	 */
	/* package */ String replaceCheckDigit(final String iban, final String checkDigit)
	{
		return getCountryCode(iban) + checkDigit + getBBAN(iban);
	}

	/**
	 * Validates IBAN against NULL or EMPTY
	 * 
	 * @param iban
	 */
	/* package */ void validateEmpty(final String iban)
	{
		Check.assumeNotNull(iban, "Null can't be a valid Iban.");

		Check.assume(iban.length() != 0, "Empty string can't be a valid Iban.");
	}

	/**
	 * Validates the country code<br>
	 * <il> if has the proper length<bR>
	 * <il> if the given IBAN contains a country code<bR>
	 * <il> if the country code is properly written<bR>
	 * <il> if the country code is supported by our implementation
	 * 
	 * @param iban
	 */
	/* package */ void validateCountryCode(final String iban)
	{
		// check if iban contains 2 char country code
		Check.assume(iban.length() > COUNTRY_CODE_LENGTH, msgBL.getMsg(Env.getCtx(), "IBAN_Country_Code_Length"));

		final String countryCode = getCountryCode(iban);
		Check.assumeNotNull(countryCode, msgBL.getMsg(Env.getCtx(), "IBAN_Non_Country_Code"));

		// check case sensitivity
		Check.assume(countryCode.equals(countryCode.toUpperCase()) ||
				Character.isLetter(countryCode.charAt(0)) ||
				Character.isLetter(countryCode.charAt(1)), msgBL.getMsg(Env.getCtx(), "IBAN_Upper_Letters"));

		// check if country is supported
		final BBANStructure code = Services.get(IBBANStructureBL.class).getBBANStructureForCountry(countryCode);
		Check.assumeNotNull(code, msgBL.getMsg(Env.getCtx(), "IBAN_Country_Code_Not_Supported"));
	}

	/* package */ void validateCheckDigitPresence(final String iban)
	{
		// check if iban contains 2 digit check digit
		Check.assume(iban.length() > COUNTRY_CODE_LENGTH + CHECK_DIGIT_LENGTH, msgBL.getMsg(Env.getCtx(), "IBAN_Check_Digit"));

		final String checkDigit = getCheckDigit(iban);

		// check digits
		Check.assume(Character.isDigit(checkDigit.charAt(0)) || Character.isDigit(checkDigit.charAt(1)), msgBL.getMsg(Env.getCtx(), "IBAN_Check_Digit_Only_Digits"));
	}

	/* package */ void validateBbanLength(final String iban,
			final BBANStructure structure)
	{
		final int expectedBbanLength = structure.getBbanLength();
		final String bban = getBBAN(iban);
		final int bbanLength = bban.length();

		Check.assume(expectedBbanLength == bbanLength, msgBL.getMsg(Env.getCtx(), "BBAN_Length", new Object[] { bbanLength, expectedBbanLength }));
	}

	/* package */ void validateBbanEntries(final String iban, final BBANStructure code)
	{
		final String bban = getBBAN(iban);
		int bbanEntryOffset = 0;
		for (final BBANStructureEntry entry : code.getEntries())
		{
			final int entryLength = entry.getLength();
			final String entryValue = bban.substring(bbanEntryOffset,
					bbanEntryOffset + entryLength);

			bbanEntryOffset = bbanEntryOffset + entryLength;

			// validate character type
			validateBbanEntryCharacterType(entry, entryValue);
		}
	}

	/* package */ void validateBbanEntryCharacterType(final BBANStructureEntry entry, final String entryValue)
	{
		switch (entry.getCharacterType())
		{
			case a:
				for (char ch : entryValue.toCharArray())
				{
					Check.assume(Character.isUpperCase(ch), msgBL.getMsg(Env.getCtx(), "BBAN_Upper_Letters", new Object[] { entryValue }));
				}
				break;
			case c:
				for (char ch : entryValue.toCharArray())
				{
					Check.assume(Character.isLetterOrDigit(ch), msgBL.getMsg(Env.getCtx(), "BBAN_Letters_Digits", new Object[] { entryValue }));
				}
				break;
			case n:
				for (char ch : entryValue.toCharArray())
				{
					Check.assume(Character.isDigit(ch), msgBL.getMsg(Env.getCtx(), "BBAN_Digits", new Object[] { entryValue }));
				}
				break;
		}
	}
	
	/* package */ BBANStructure getBBANCode(final String iban)
	{
		final String countryCode = getCountryCode(iban);
		return Services.get(IBBANStructureBL.class).getBBANStructureForCountry(countryCode);
	}
	
	private String fromCharCode(int... codePoints)
	{
		return new String(codePoints, 0, codePoints.length);
	}
}
