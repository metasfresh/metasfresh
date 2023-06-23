package de.metas.bpartner.impexp;

import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.bpartner.service.BankAccountQuery;
import de.metas.bpartner.service.IBPBankAccountDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.invoice_gateway.spi.model.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

import java.util.Optional;
import java.util.Set;

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
	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
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

	public void importRecord(final I_I_BPartner importRecord)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(importRecord.getC_BPartner_ID());
		BankId bankId = null;

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
			bankId = bankRepository.getBankIdBySwiftCode(importRecord.getSwiftCode()).orElse(null);
			if (bankId != null)
			{
				bankAccount.setC_Bank_ID(bankId.getRepoId());
			}

			ModelValidationEngine.get().fireImportValidate(process, importRecord, bankAccount, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bankAccount);

			importRecord.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
		}
		else if (Check.isNotBlank(importRecord.getBankDetails()))
		{
			bankId = resolveBankIdFromBankDetails(importRecord.getBankDetails()).orElse(null);
		}

		if (Check.isNotBlank(importRecord.getIBAN()))
		{
			if (bankAccount == null)
			{
				final BankAccountQuery queryWithIban = BankAccountQuery.builder().iban(importRecord.getIBAN()).build();
				bankAccount = bankAccountDAO.getSingleBPBankAccount(queryWithIban)
						.orElseGet(bankAccountDAO::initializeBPBankAccount);
			}

			bankAccount.setIBAN(importRecord.getIBAN());
		}

		if (Check.isNotBlank(importRecord.getQR_IBAN()))
		{
			if (bankAccount == null)
			{
				final BankAccountQuery queryWithQRIban = BankAccountQuery.builder().qrIban(importRecord.getQR_IBAN()).build();
				bankAccount = bankAccountDAO.getSingleBPBankAccount(queryWithQRIban)
						.orElseGet(bankAccountDAO::initializeBPBankAccount);
			}

			bankAccount.setQR_IBAN(importRecord.getQR_IBAN());
		}

		if (bankAccount == null)
		{
			return;
		}

		importCurrencyCode(bankAccount, importRecord);
		importBankDetails(bankAccount, importRecord);

		bankAccount.setC_Bank_ID(BankId.toRepoId(bankId));
		bankAccount.setC_BPartner_ID(bpartnerId.getRepoId());
		bankAccount.setAD_Org_ID(importRecord.getAD_Org_ID());

		ModelValidationEngine.get().fireImportValidate(process, importRecord, bankAccount, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(bankAccount);

		importRecord.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
	}

	@NonNull
	private Optional<BankId> resolveBankIdFromBankDetails(@NonNull final String bankDetails)
	{
		final Set<BankId> bankIdCollection = bankRepository.retrieveBankIdsByName(bankDetails);

		return Optional.ofNullable(CollectionUtils.singleElementOrNull(bankIdCollection));
	}

	private void importCurrencyCode(@NonNull final I_C_BP_BankAccount bpBankAccount, @NonNull final I_I_BPartner importRecord)
	{
		if (bpBankAccount.getC_Currency_ID() <= 0 && Check.isBlank(importRecord.getISO_Code()))
		{
			throw new AdempiereException("Missing mandatory value for currency on business partner bank account!");
		}

		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(importRecord.getISO_Code());
		final CurrencyId currencyId = currencyBL.getByCurrencyCode(currencyCode).getId();
		bpBankAccount.setC_Currency_ID(currencyId.getRepoId());
	}

	private void importBankDetails(@NonNull final I_C_BP_BankAccount bpBankAccount, @NonNull final I_I_BPartner importRecord)
	{
		if (Check.isBlank(importRecord.getBankDetails()))
		{
			return;
		}

		if (Check.isBlank(bpBankAccount.getName()))
		{
			bpBankAccount.setName(importRecord.getBankDetails());
		}

		if (Check.isBlank(bpBankAccount.getDescription()))
		{
			bpBankAccount.setDescription(importRecord.getBankDetails());
		}
	}
}
