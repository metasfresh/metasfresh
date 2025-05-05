package de.metas.payment.esr.api;

/*
 * #%L
 * de.metas.payment.esr
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;

import de.metas.banking.BankAccountId;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_PostFinanceUserNumber;
import de.metas.util.ISingletonService;

public interface IESRBPBankAccountDAO extends ISingletonService
{
	/**
	 * Search for the ESR bank account(s) of the given Organization's linked partner.
	 * 
	 * @return account(s) if exist, throw exception otherwise. If there is more than one ESR account, then the one with IsDefaultESR='Y' is returned first.
	 * @return account(s) if exists, throw exception otherwise. If there is more than one ESR account, then the one with IsDefaultESR='Y' is returned first.
	 */
	List<I_C_BP_BankAccount> fetchOrgEsrAccounts(I_AD_Org org);

	/**
	 * Retrieve matching ESR bank accounts. Note that the given {@code postAccountNo} and {@code innerAccountNo} are <b>not</b> guaranteed to be unique.<br>
	 * A simple example of non-unique parameters would be two {@link I_C_BPartner}s that are tightly coupled in the real world and have the same bank account.
	 *
	 * @return {@link I_C_BP_BankAccount}s corresponding to the ESR accountNo and inner-bank accountNo or an empty list if none was found.
	 */
	List<I_C_BP_BankAccount> retrieveESRBPBankAccounts(String postAccountNo, String innerAccountNo);

	/**
	 * @return All the ESR_PostFinanceUserNumber entries for the bank account
	 */
	List<I_ESR_PostFinanceUserNumber> retrieveESRPostFinanceUserNumbers(BankAccountId bankAcctId);

	List<I_C_BP_BankAccount> retrieveQRBPBankAccounts(String IBAN);

	boolean isESRBankAccount(int bpBankAccountId);
}
