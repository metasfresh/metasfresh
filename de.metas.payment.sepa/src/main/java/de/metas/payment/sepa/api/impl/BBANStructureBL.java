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


import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;

import de.metas.location.ICountryDAO;
import de.metas.payment.sepa.api.IBBANStructureBL;
import de.metas.payment.sepa.api.IBBANStructureBuilder;
import de.metas.payment.sepa.wrapper.BBANStructure;
import de.metas.payment.sepa.wrapper.BBANStructureEntry.BBANCodeEntryType;
import de.metas.payment.sepa.wrapper.BBANStructureEntry.EntryCharacterType;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author cg
 *
 */
public class BBANStructureBL implements IBBANStructureBL
{
	@Override
	public BBANStructure getBBANStructureForCountry(final String countryCode)
	{
		if(countryCode == null)
		{
			return null;
		}
		
		final I_C_Country countryPO = Services.get(ICountryDAO.class).retrieveCountryByCountryCode(countryCode);

		final de.metas.payment.sepa.interfaces.I_C_Country country = InterfaceWrapperHelper.create(countryPO, de.metas.payment.sepa.interfaces.I_C_Country.class);

		final String bank_code_length = country.getBankCodeLength();
		final String bank_code_char_char_type = country.getBankCodeCharType();
		final String bank_code_char_seqNo = country.getBankCodeSeqNo();

		final String branch_code_length = country.getBranchCodeLength();
		final String branch_code_char_type = country.getBranchCodeCharType();
		final String branch_code_seq_no = country.getBranchCodeSeqNo();

		final String account_number_length = country.getAccountNumberLength();
		final String account_number_char_type = country.getAccountNumberCharType();
		final String account_number_seq_no = country.getAccountNumberSeqNo();

		final String national_check_digit_length = country.getNationalCheckDigitLength();
		final String national_check_digit_char_type = country.getNationalCheckDigitCharType();
		final String national_check_digit_seq_no = country.getNationalCheckDigitSeqNo();

		final String account_type_length = country.getAccountTypeLength();
		final String account_type_char_type = country.getAccountTypeCharType();
		final String account_type_seq_no = country.getAccountTypeSeqNo();

		// create BBANStructure builder
		IBBANStructureBuilder builder = newBuilder();

		// Bank Code
		if (!Check.isEmpty(bank_code_length, true))
		{
			int length = Integer.valueOf(bank_code_length);
			Check.assume(bank_code_char_char_type.length() == 1, " Bank Code char type must have 1 letter!");
			char charType = bank_code_char_char_type.toCharArray()[0];

			// build BBANStructure Entry
			builder.addBBANStructureEntry()
					.setCodeType(BBANCodeEntryType.bank_code)
					.setLength(length)
					.setCharacterType(EntryCharacterType.valueOf(String.valueOf(charType)))
					.setSeqNo(bank_code_char_seqNo)
					.create();
		}

		// Branch Code
		if (!Check.isEmpty(branch_code_length, true))
		{
			int length = Integer.valueOf(branch_code_length);
			Check.assume(branch_code_char_type.length() == 1, " Branch Code char type must have 1 letter!");
			char charType = branch_code_char_type.toCharArray()[0];

			// build BBANStructure Entry
			builder.addBBANStructureEntry()
					.setCodeType(BBANCodeEntryType.branch_code)
					.setLength(length)
					.setCharacterType(EntryCharacterType.valueOf(String.valueOf(charType)))
					.setSeqNo(branch_code_seq_no)
					.create();

		}

		// Account Number
		if (!Check.isEmpty(account_number_length, true))
		{
			int length = Integer.valueOf(account_number_length);
			Check.assume(account_number_char_type.length() == 1, " Account Number char type must have 1 letter!");
			char charType = account_number_char_type.toCharArray()[0];

			// build BBANStructure Entry
			builder.addBBANStructureEntry()
					.setCodeType(BBANCodeEntryType.account_number)
					.setLength(length)
					.setCharacterType(EntryCharacterType.valueOf(String.valueOf(charType)))
					.setSeqNo(account_number_seq_no)
					.create();

		}

		// Account Type
		if (!Check.isEmpty(account_type_length, true))
		{
			int length = Integer.valueOf(account_type_length);
			Check.assume(account_type_char_type.length() == 1, " Account Type char type must have 1 letter!");
			char charType = account_type_char_type.toCharArray()[0];

			// build BBANStructure Entry
			builder.addBBANStructureEntry()
					.setCodeType(BBANCodeEntryType.account_type)
					.setLength(length)
					.setCharacterType(EntryCharacterType.valueOf(String.valueOf(charType)))
					.setSeqNo(account_type_seq_no)
					.create();

		}

		// National Check Digit
		if (!Check.isEmpty(national_check_digit_length, true))
		{
			int length = Integer.valueOf(national_check_digit_length);
			Check.assume(national_check_digit_char_type.length() == 1, " National Check digit char type must have 1 letter!");
			char charType = national_check_digit_char_type.toCharArray()[0];

			// build BBANStructure Entry
			builder.addBBANStructureEntry()
					.setCodeType(BBANCodeEntryType.national_check_digit)
					.setLength(length)
					.setCharacterType(EntryCharacterType.valueOf(String.valueOf(charType)))
					.setSeqNo(national_check_digit_seq_no)
					.create();

		}

		return builder.create();
	}

	@Override
	public IBBANStructureBuilder newBuilder()
	{
		return new BBANStructureBuilder();
	}
}
