/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.payment.esr.api.impl;

import static de.metas.payment.esr.api.impl.ESRBPBankAccountDAO.createMatchingESRAccountNumbers;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_PostFinanceUserNumber;
import de.metas.util.Services;

class ESRBPBankAccountDAOTest
{
	@Nested
	public class retrieveESRBPBankAccounts
	{
		private ESRBPBankAccountDAO esrBPBankAccountDAO;

		@BeforeEach
		public void init()
		{
			AdempiereTestHelper.get().init();
			esrBPBankAccountDAO = (ESRBPBankAccountDAO)Services.get(IESRBPBankAccountDAO.class);
		}

		@Test
		public void retrieveESRBPBankAccounts_OneAccount_FitPostalAndAcctNumber()
		{
			final String esrPostal = "1234567";
			final String accountNo = "notImportant";
			final I_C_BP_BankAccount account1 = createAccount(true, esrPostal, accountNo);

			final String esrAccountNo1 = "12-345-67";
			createESRPostFinanceUserNumber(account1, esrAccountNo1);

			final List<I_C_BP_BankAccount> esrBPBankAccounts = esrBPBankAccountDAO.retrieveESRBPBankAccounts(esrAccountNo1, accountNo);

			assertThat(esrBPBankAccounts).contains(account1);

		}

		@Test
		public void retrieveESRBPBankAccounts_OneAccount_FitPostalButNoAcctNumber()
		{
			final String esrPostal1 = "1234567";

			final String rightAccountNo = "7777777";
			final String wrongAccountNo = "8888888";
			final I_C_BP_BankAccount account1 = createAccount(true, esrPostal1, rightAccountNo);

			final String esrAccountNo1 = "12-345-67";
			createESRPostFinanceUserNumber(account1, esrAccountNo1);

			final List<I_C_BP_BankAccount> esrBPBankAccounts = esrBPBankAccountDAO.retrieveESRBPBankAccounts(esrAccountNo1, wrongAccountNo);

			assertThat(esrBPBankAccounts).isEmpty();

		}

		@Test
		public void retrieveESRBPBankAccounts_OneAccount_FitAcctNumberButNoPostal()
		{
			final String esrAcctNo = "1234567";

			final String accountNo = "notImportant";
			final I_C_BP_BankAccount account1 = createAccount(true, esrAcctNo, accountNo);

			final String esrAccountNo1 = "12-345-67";
			final String esrAccountNo2 = "76-543-21";
			createESRPostFinanceUserNumber(account1, esrAccountNo1);

			final List<I_C_BP_BankAccount> esrBPBankAccounts = esrBPBankAccountDAO.retrieveESRBPBankAccounts(esrAccountNo2, accountNo);

			assertThat(esrBPBankAccounts).isEmpty();

		}

		@Test
		public void retrieveESRBPBankAccounts_TwoAccounts_FitPostalAcctNumber0000000()
		{
			final String esrAcctNo1 = "1234567";
			final String esrAcctNo2 = "7654321";

			final String unimportantAccountNo = "0000000";

			final I_C_BP_BankAccount account1 = createAccount(true, esrAcctNo1, unimportantAccountNo);
			final I_C_BP_BankAccount account2 = createAccount(true, esrAcctNo2, unimportantAccountNo);

			final String esrAccountNo1 = "12-345-67";
			createESRPostFinanceUserNumber(account1, esrAccountNo1);
			createESRPostFinanceUserNumber(account2, esrAccountNo1);

			final List<I_C_BP_BankAccount> esrBPBankAccounts = esrBPBankAccountDAO.retrieveESRBPBankAccounts(esrAccountNo1, unimportantAccountNo);

			assertThat(esrBPBankAccounts).contains(account1, account2);

		}

		@Test
		public void retrieveESRBPBankAccounts_OneAccount_NumberFitsBPBankAccount()
		{
			final String esrAcctNo = "1234567";

			final String accountNo = "8888888";
			final I_C_BP_BankAccount account1 = createAccount(true, esrAcctNo, accountNo);
			final String esrAccountNo1 = "12-345-67";
			createESRPostFinanceUserNumber(account1, esrAccountNo1);

			final List<I_C_BP_BankAccount> esrBPBankAccounts = esrBPBankAccountDAO.retrieveESRBPBankAccounts(esrAccountNo1, accountNo);

			assertThat(esrBPBankAccounts).contains(account1);

		}

		@Test
		public void retrieveESRBPBankAccounts_OneAccount_NumberFitsBPBankAccountButNoESR()
		{
			final String esrAcctNo = "1234567";

			final String accountNo = "8888888";
			final I_C_BP_BankAccount account1 = createAccount(true, esrAcctNo, accountNo);
			final String esrAccountNo1 = "12-345-67";
			createESRPostFinanceUserNumber(account1, esrAccountNo1);

			final List<I_C_BP_BankAccount> esrBPBankAccounts = esrBPBankAccountDAO.retrieveESRBPBankAccounts(esrAccountNo1, accountNo);

			assertThat(esrBPBankAccounts).contains(account1);

		}

		private I_C_BP_BankAccount createAccount(final boolean isEsrAccount, final String esrRenderedAcctNo, final String accountNo)
		{
			final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);
			account.setIsEsrAccount(isEsrAccount);
			account.setAccountNo(accountNo);
			account.setESR_RenderedAccountNo(esrRenderedAcctNo);

			save(account);

			return account;
		}

		private I_ESR_PostFinanceUserNumber createESRPostFinanceUserNumber(final I_C_BP_BankAccount account, final String esrAccountNo)
		{
			final I_ESR_PostFinanceUserNumber postFinanceUserNumber = newInstance(I_ESR_PostFinanceUserNumber.class);
			postFinanceUserNumber.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
			postFinanceUserNumber.setESR_RenderedAccountNo(esrAccountNo);
			save(postFinanceUserNumber);

			return postFinanceUserNumber;
		}
	}

	@Nested
	public class createMatchingESRAccountNumbers
	{
		@Test
		void esrHas9Digits()
		{
			final ImmutableSet<String> esrs = createMatchingESRAccountNumbers("01-1067-4");
			esrs.forEach(s -> assertEquals(9, s.length()));
		}

		@Test
		void fromEsrWithDash()
		{
			final ImmutableSet<String> esrs = createMatchingESRAccountNumbers("01-1067-4");
			assertEquals(2, esrs.size());

			Assertions.assertThat(esrs).containsAll(Arrays.asList("01-1067-4", "010010674"));
		}

		@Test
		void fromEsrWithoutDash()
		{
			final ImmutableSet<String> esrs = createMatchingESRAccountNumbers("010010674");
			assertEquals(2, esrs.size());

			Assertions.assertThat(esrs).containsAll(Arrays.asList("01-1067-4", "010010674"));
		}
	}
}
