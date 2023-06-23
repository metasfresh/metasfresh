package de.metas.bpartner.impexp;

import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.currency.Currency;
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

		I_C_BP_BankAccount bankAccount = BankAccountId.optionalOfRepoId(importRecord.getC_BP_BankAccount_ID())
				.map(bankAccountId -> InterfaceWrapperHelper.load(bankAccountId, I_C_BP_BankAccount.class))
				.orElse(null);
		if (bankAccount != null)
		{
			bankAccount.setIBAN(importRecord.getIBAN());

			ModelValidationEngine.get().fireImportValidate(process, importRecord, bankAccount, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bankAccount);
		}
		else if (Check.isNotBlank(importRecord.getSwiftCode())
				|| Check.isNotBlank(importRecord.getIBAN())
				|| Check.isNotBlank(importRecord.getQR_IBAN()))
		{
			bankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);
			bankAccount.setC_BPartner_ID(bpartnerId.getRepoId());
			bankAccount.setIBAN(importRecord.getIBAN());
			bankAccount.setQR_IBAN(importRecord.getQR_IBAN());
			bankAccount.setC_Bank_ID(BankId.optionalToRepoId(resolveBankId(importRecord)));

			final CurrencyId currencyId = resolveCurrencyId(importRecord)
					.orElseGet(() -> currencyBL.getBaseCurrency(process.getCtx()).getId());
			bankAccount.setC_Currency_ID(CurrencyId.toRepoId(currencyId));

			importSwiftCode(bankAccount, importRecord);
			importBankDetails(bankAccount, importRecord);

			ModelValidationEngine.get().fireImportValidate(process, importRecord, bankAccount, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bankAccount);

			importRecord.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
		}
	}

	@NonNull
	private Optional<BankId> resolveBankIdFromBankDetails(@NonNull final String bankDetails)
	{
		final Set<BankId> bankIdCollection = bankRepository.retrieveBankIdsByName(bankDetails);

		return Optional.ofNullable(CollectionUtils.singleElementOrNull(bankIdCollection));
	}

	private void importBankDetails(@NonNull final I_C_BP_BankAccount bpBankAccount, @NonNull final I_I_BPartner importRecord)
	{
		if (Check.isBlank(importRecord.getBankDetails()))
		{
			return;
		}

		bpBankAccount.setName(importRecord.getBankDetails());
		bpBankAccount.setDescription(importRecord.getBankDetails());
	}

	private void importSwiftCode(@NonNull final I_C_BP_BankAccount bpBankAccount, @NonNull final I_I_BPartner importRecord)
	{
		if (Check.isBlank(importRecord.getSwiftCode()))
		{
			return;
		}

		bpBankAccount.setA_Name(importRecord.getSwiftCode());
		bpBankAccount.setSwiftCode(importRecord.getSwiftCode());
	}

	@NonNull
	private Optional<BankId> resolveBankId(@NonNull final I_I_BPartner importRecord)
	{
		if (Check.isNotBlank(importRecord.getSwiftCode()))
		{
			return bankRepository.getBankIdBySwiftCode(importRecord.getSwiftCode());
		}

		if (Check.isNotBlank(importRecord.getBankDetails()))
		{
			return resolveBankIdFromBankDetails(importRecord.getBankDetails());
		}

		return Optional.empty();
	}

	@NonNull
	private Optional<CurrencyId> resolveCurrencyId(@NonNull final I_I_BPartner importRecord)
	{
		return Optional.ofNullable(importRecord.getISO_Code())
				.map(CurrencyCode::ofThreeLetterCode)
				.map(currencyBL::getByCurrencyCode)
				.map(Currency::getId);
	}
}
