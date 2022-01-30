package de.metas.banking.api;

import de.metas.acct.api.AcctSchemaId;
import de.metas.banking.Bank;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountAcct;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Service
public class BankAccountService
{
	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final BankRepository bankRepo;
	private final BankAccountAcctRepository bankAccountAcctRepo;
	private final CurrencyRepository currencyRepo;

	public BankAccountService(
			@NonNull final BankRepository bankRepo,
			@NonNull final BankAccountAcctRepository bankAccountAcctRepo,
			@NonNull final CurrencyRepository currencyRepo)
	{
		this.bankRepo = bankRepo;
		this.bankAccountAcctRepo = bankAccountAcctRepo;
		this.currencyRepo = currencyRepo;
	}

	public static BankAccountService newInstanceForUnitTesting()
	{
		return new BankAccountService(
				new BankRepository(),
				new BankAccountAcctRepository(),
				new CurrencyRepository());
	}

	public boolean isCashBank(@NonNull final BankAccountId bankAccountId)
	{
		final BankId bankId = bankAccountDAO.getBankId(bankAccountId);
		return bankId != null && bankRepo.isCashBank(bankId);
	}

	public BankAccount getById(@NonNull final BankAccountId bankAccountId)
	{
		return bankAccountDAO.getById(bankAccountId);
	}

	public BankAccountAcct getBankAccountAcct(
			@NonNull final BankAccountId bankAccountId,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		return bankAccountAcctRepo.getByBankAccountIdAndAcctSchemaId(bankAccountId, acctSchemaId);
	}

	public String createBankAccountName(@NonNull final BankAccountId bankAccountId)
	{
		final BankAccount bankAccount = getById(bankAccountId);

		final CurrencyCode currencyCode = currencyRepo.getCurrencyCodeById(bankAccount.getCurrencyId());

		final BankId bankId = bankAccount.getBankId();
		if (bankId != null)
		{
			final Bank bank = bankRepo.getById(bankId);
			return bank.getBankName() + "_" + currencyCode.toThreeLetterCode();
		}
		return currencyCode.toThreeLetterCode();
	}

	public DataImportConfigId getDataImportConfigIdForBankAccount(@NonNull final BankAccountId bankAccountId)
	{
		final BankId bankId = bankAccountDAO.getBankId(bankAccountId);

		return bankRepo.retrieveDataImportConfigIdForBank(bankId);
	}
}
