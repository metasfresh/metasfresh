package org.adempiere.impexp.bpartner;

import org.adempiere.bank.BankRepository;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

import de.metas.currency.ICurrencyBL;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ class BPartnerBankAccountImportHelper
{
	public static BPartnerBankAccountImportHelper newInstance()
	{
		return new BPartnerBankAccountImportHelper();
	}


	private BPartnerImportProcess process;

	private BPartnerBankAccountImportHelper()
	{
	}

	public BPartnerBankAccountImportHelper setProcess(final BPartnerImportProcess process)
	{
		this.process = process;
		return this;
	}

	public I_C_BP_BankAccount importRecord(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner = importRecord.getC_BPartner();

		I_C_BP_BankAccount bankAccount = importRecord.getC_BP_BankAccount();
		if (bankAccount != null)
		{
			bankAccount.setIBAN(importRecord.getIBAN());
			ModelValidationEngine.get().fireImportValidate(process, importRecord, bankAccount, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bankAccount);
		}
		else if (!Check.isEmpty(importRecord.getSwiftCode(), true) && !Check.isEmpty(importRecord.getIBAN(), true))
		{
			bankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, bpartner);
			bankAccount.setC_BPartner_ID(bpartner.getC_BPartner_ID());
			bankAccount.setIBAN(importRecord.getIBAN());
			bankAccount.setA_Name(importRecord.getSwiftCode());
			bankAccount.setC_Currency_ID(Services.get(ICurrencyBL.class).getBaseCurrency(process.getCtx()).getC_Currency_ID());
			final I_C_Bank bank = Adempiere.getBean(BankRepository.class).findBankBySwiftCode(importRecord.getSwiftCode());
			if (bank != null)
			{
				bankAccount.setC_Bank(bank);
			}

			ModelValidationEngine.get().fireImportValidate(process, importRecord, bankAccount, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bankAccount);

			importRecord.setC_BP_BankAccount(bankAccount);
		}

		return bankAccount;
	}

}
