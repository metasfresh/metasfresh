package de.metas.bpartner.impexp;

import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.invoice_gateway.spi.model.BPartnerId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

import javax.annotation.Nullable;
import javax.validation.constraints.Null;

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
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final BankRepository bankRepository;
	private BPartnerImportProcess process;

	@Builder
	private BPartnerBankAccountImportHelper(
			@NonNull final BankRepository bankRepository)
	{
		this.bankRepository = bankRepository;
	}

	public BPartnerBankAccountImportHelper setProcess(final BPartnerImportProcess process)
	{
		this.process = process;
		return this;
	}

	@Nullable
	public I_C_BP_BankAccount importRecord(final I_I_BPartner importRecord)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(importRecord.getC_BPartner_ID());

		I_C_BP_BankAccount bankAccount = BankAccountId.optionalOfRepoId(importRecord.getC_BP_BankAccount_ID())
				.map(bankAccountId -> InterfaceWrapperHelper.load(bankAccountId, I_C_BP_BankAccount.class))
				.orElse(null);
		if (bankAccount != null)
		{
			bankAccount.setIBAN(importRecord.getIBAN());
			ModelValidationEngine.get().fireImportValidate(process, importRecord, bankAccount, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bankAccount);
		}
		else if (!Check.isEmpty(importRecord.getSwiftCode(), true) && !Check.isEmpty(importRecord.getIBAN(), true))
		{
			bankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);
			bankAccount.setC_BPartner_ID(bpartnerId.getRepoId());
			bankAccount.setIBAN(importRecord.getIBAN());
			bankAccount.setA_Name(importRecord.getSwiftCode());
			bankAccount.setSwiftCode(importRecord.getSwiftCode());
			bankAccount.setC_Currency_ID(currencyBL.getBaseCurrency(process.getCtx()).getId().getRepoId());
			final BankId bankId = bankRepository.getBankIdBySwiftCode(importRecord.getSwiftCode()).orElse(null);
			if (bankId != null)
			{
				bankAccount.setC_Bank_ID(bankId.getRepoId());
			}

			ModelValidationEngine.get().fireImportValidate(process, importRecord, bankAccount, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bankAccount);

			importRecord.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
		}

		return bankAccount;
	}

}
