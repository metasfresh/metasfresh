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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Bank;

import de.metas.payment.sepa.api.ISEPABankAccountBL;
import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.util.Check;

public class SEPABankAccountBL implements ISEPABankAccountBL
{
	@Override
	public String getSwiftCode(final org.compiere.model.I_C_BP_BankAccount bankAccount)
	{
		final String swiftCode = getSwiftCodeOrNull(bankAccount);
		if (Check.isEmpty(swiftCode, true))
		{
			final I_C_BP_BankAccount sepaBankAccount = InterfaceWrapperHelper.create(bankAccount, I_C_BP_BankAccount.class);
			throw new AdempiereException("@NotFound@ @SwiftCode@ (@C_BP_BankAccount_ID@: " + sepaBankAccount.getIBAN() + ")");
		}

		return swiftCode;
	}

	@Override
	public String getSwiftCodeOrNull(final org.compiere.model.I_C_BP_BankAccount bankAccount)
	{
		Check.assumeNotNull(bankAccount, "bankAccount not null");
		final I_C_BP_BankAccount sepaBankAccount = InterfaceWrapperHelper.create(bankAccount, I_C_BP_BankAccount.class);

		//
		// Check if we have SwiftCode/BIC on Bank Account level
		final String bankAccountSwiftCode = sepaBankAccount.getSwiftCode();
		if (!Check.isEmpty(bankAccountSwiftCode, true))
		{
			return bankAccountSwiftCode;
		}

		//
		// Check if we have SwiftCode/BIC on Bank level
		final I_C_Bank bank = sepaBankAccount.getC_Bank();
		if (bank != null && bank.getC_Bank_ID() > 0)
		{
			final String bankSwiftCode = bank.getSwiftCode();
			return bankSwiftCode;
		}

		// No SwiftCode/BIC found
		return null;
	}

	@Override
	public void setSwiftCode(final org.compiere.model.I_C_BP_BankAccount bankAccount, final String swiftCode)
	{
		Check.assumeNotNull(bankAccount, "bankAccount not null");
		final I_C_BP_BankAccount sepaBankAccount = InterfaceWrapperHelper.create(bankAccount, I_C_BP_BankAccount.class);

		sepaBankAccount.setSwiftCode(swiftCode);
	}
}
