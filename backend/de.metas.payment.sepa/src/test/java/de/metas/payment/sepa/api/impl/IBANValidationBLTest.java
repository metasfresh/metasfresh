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


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.payment.sepa.interfaces.I_C_Country;

/**
 * @author cg
 *
 */
public class IBANValidationBLTest
{

	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();
		private Properties ctx;

	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@Before
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		// Setup context
		ctx = Env.getCtx();
		ctx.clear();
		Env.setContext(ctx, "#AD_Client_ID", 1);
		Env.setContext(ctx, "#AD_Org_ID", 1);
		Env.setContext(ctx, "#AD_Role_ID", 1);
		Env.setContext(ctx, "#AD_User_ID", 1);

		// prepare countries: DE, CH, FRA

		prepareCountry("DE", "n", "8", "10", null, null, null, "n", "10", "20", null, null, null, null, null, null);

		prepareCountry("CH", "n", "5", "10", null, null, null, "n", "12", "20", null, null, null, null, null, null);

		prepareCountry("FR", "n", "5", "10", "n", "5", "20", "n", "12", "30", null, null, null, "c", "11", "40");

	}

	/**
	 * create country
	 * 
	 * @param countryCode
	 * @param bankCodeCharType
	 * @param bankCodeLength
	 * @param bankCodeSeqNo
	 * @param branchCodeCharType
	 * @param branchCodeLength
	 * @param branchCodeSeqNo
	 * @param accountNumberCharType
	 * @param accountNumberLength
	 * @param accountNumberSeqNo
	 * @param accountTypeCharTyoe
	 * @param accountTypeLength
	 * @param accountTypeSeqNo
	 * @param nationalCheckDigitCharType
	 * @param nationalCheckDigitLength
	 * @param nationalCheckDigiSeqNo
	 */
	private void prepareCountry(final String countryCode,
			String bankCodeCharType, String bankCodeLength, String bankCodeSeqNo,
			String branchCodeCharType, String branchCodeLength, String branchCodeSeqNo,
			String accountNumberCharType, String accountNumberLength, String accountNumberSeqNo,
			String accountTypeCharTyoe, String accountTypeLength, String accountTypeSeqNo,
			String nationalCheckDigitCharType, String nationalCheckDigitLength, String nationalCheckDigiSeqNo
			)
	{
		final I_C_Country country = InterfaceWrapperHelper.create(ctx, I_C_Country.class, ITrx.TRXNAME_None);
		country.setCountryCode(countryCode);

		country.setBankCodeCharType(bankCodeCharType);
		country.setBankCodeLength(bankCodeLength);
		country.setBankCodeSeqNo(bankCodeSeqNo);

		country.setBranchCodeCharType(branchCodeCharType);
		country.setBranchCodeLength(branchCodeLength);
		country.setBranchCodeSeqNo(branchCodeSeqNo);

		country.setAccountNumberCharType(accountNumberCharType);
		country.setAccountNumberLength(accountNumberLength);
		country.setAccountNumberSeqNo(accountNumberSeqNo);

		country.setAccountTypeCharType(accountTypeCharTyoe);
		country.setAccountTypeLength(accountTypeLength);
		country.setAccountTypeSeqNo(accountTypeSeqNo);

		country.setNationalCheckDigitCharType(nationalCheckDigitCharType);
		country.setNationalCheckDigitLength(nationalCheckDigitLength);
		country.setNationalCheckDigitSeqNo(nationalCheckDigiSeqNo);

		InterfaceWrapperHelper.save(country);
	}

	@Test
	public void test_CheckDigit_GoodIBAN() 
	{
		final IBANValidationBL ibanValidationBL = new IBANValidationBL();
		final String ibanDE = "DE45500105170041262312";
		final int checkDigitDE = ibanValidationBL.ISO7064Mod97_10(ibanDE);
		
		final String ibanCH = "CH9300762011623852957";
		final int checkDigitCH = ibanValidationBL.ISO7064Mod97_10(ibanCH);
		
		final String ibanFR = "FR1420041010050500013M02606";
		final int checkDigitFR = ibanValidationBL.ISO7064Mod97_10(ibanFR);
		
		assertThat(" Check digit should be 1 , but is " + checkDigitDE, 1, is(checkDigitDE));
		assertThat(" Check digit should be 1 , but is " + checkDigitCH, 1, is(checkDigitCH));
		assertThat(" Check digit should be 1 , but is " + checkDigitFR, 1, is(checkDigitFR));
	}
	

	@Test
	public void test_CheckDigit_WrongIBAN() 
	{
		final IBANValidationBL ibanValidationBL = new IBANValidationBL();
		final String ibanDE = "DE45500105170041262311";
		final int checkDigitDE = ibanValidationBL.ISO7064Mod97_10(ibanDE);
		
		final String ibanCH = "CH9300762011623852956";
		final int checkDigitCH = ibanValidationBL.ISO7064Mod97_10(ibanCH);
		
		final String ibanFR = "FR1420041010050500013M02605";
		final int checkDigitFR = ibanValidationBL.ISO7064Mod97_10(ibanFR);
		
		assertThat(" Check digit should be different from 1! " , 1, not(checkDigitDE));
		assertThat(" Check digit should be different from 1! " , 1, not(checkDigitCH));
		assertThat(" Check digit should be different from 1! " , 1, not(checkDigitFR));
	}
	
}
