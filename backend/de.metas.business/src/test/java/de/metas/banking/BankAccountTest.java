/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.banking;

import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

public class BankAccountTest
{
	final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void accountNoMatching_WithSlash()
	{
		final String qr_iban = "CH763570011875432512";
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount bankAccountRecord = createBankAccount(qr_iban, currencyEUR);
		final BankAccountId bankAccountId = BankAccountId.ofRepoId(bankAccountRecord.getC_BP_BankAccount_ID());
		final BankAccount bankAccount = bpBankAccountDAO.getById(bankAccountId);

		final String accountNo = "CH763570011875432512/942";
		final boolean accountNoMatching = bankAccount.isAccountNoMatching(accountNo);

		assertThat(accountNoMatching).isTrue();
	}



	@Test
	public void accountNoMatching_WithoutSlash()
	{
		final String qr_iban = "CH763570011875432512";
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount bankAccountRecord = createBankAccount(qr_iban, currencyEUR);
		final BankAccountId bankAccountId = BankAccountId.ofRepoId(bankAccountRecord.getC_BP_BankAccount_ID());
		final BankAccount bankAccount = bpBankAccountDAO.getById(bankAccountId);

		final String accountNo = "CH763570011875432512";
		final boolean accountNoMatching = bankAccount.isAccountNoMatching(accountNo);

		assertThat(accountNoMatching).isTrue();
	}



	@Test
	public void accountNoDoesntMatch_WithSlash()
	{
		final String qr_iban = "CH763570011875432512";
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount bankAccountRecord = createBankAccount(qr_iban, currencyEUR);
		final BankAccountId bankAccountId = BankAccountId.ofRepoId(bankAccountRecord.getC_BP_BankAccount_ID());
		final BankAccount bankAccount = bpBankAccountDAO.getById(bankAccountId);

		final String accountNo = "CH763570011875132512/942";
		final boolean accountNoMatching = bankAccount.isAccountNoMatching(accountNo);

		assertThat(accountNoMatching).isFalse();
	}



	@Test
	public void accountNoDoesntMatch_WithoutSlash()
	{
		final String qr_iban = "CH763570011875432512";
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount bankAccountRecord = createBankAccount(qr_iban, currencyEUR);
		final BankAccountId bankAccountId = BankAccountId.ofRepoId(bankAccountRecord.getC_BP_BankAccount_ID());
		final BankAccount bankAccount = bpBankAccountDAO.getById(bankAccountId);

		final String accountNo = "CH763570011375432512";
		final boolean accountNoMatching = bankAccount.isAccountNoMatching(accountNo);

		assertThat(accountNoMatching).isFalse();
	}
	private I_C_BP_BankAccount createBankAccount(final String qr_iban, final CurrencyId currencyId)
	{
		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);

		bankAccount.setQR_IBAN(qr_iban);
		bankAccount.setC_Currency_ID(currencyId.getRepoId());
		bankAccount.setC_BPartner_ID(10);

		save(bankAccount);

		return bankAccount;
	}

}
