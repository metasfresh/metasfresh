package de.metas.banking.api;

import de.metas.banking.Bank;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
	private final CurrencyRepository currencyRepo;

	public BankAccountService(
			@NonNull final BankRepository bankRepo,
			@NonNull final CurrencyRepository currencyRepo)
	{
		this.bankRepo = bankRepo;
		this.currencyRepo = currencyRepo;
	}

	public static BankAccountService newInstanceForUnitTesting()
	{
		return new BankAccountService(
				new BankRepository(),
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

	public boolean isImportAsSingleSummaryLine(@NonNull final BankAccountId bankAccountId)
	{
		final BankId bankId = bankAccountDAO.getBankId(bankAccountId);
		return bankRepo.isImportAsSingleSummaryLine(bankId);
	}

	@NonNull
	public Optional<BankId> getBankIdBySwiftCode(@NonNull final String swiftCode)
	{
		return bankRepo.getBankIdBySwiftCode(swiftCode);
	}

	@NonNull
	public Optional<BankAccountId> getBankAccountId(
			@NonNull final BankId bankId,
			@NonNull final String accountNo)
	{
		return bankAccountDAO.getBankAccountId(bankId, accountNo);
	}

	@NonNull
	public Optional<BankAccountId> getBankAccountIdByIBAN(@NonNull final String iban)
	{
		return bankAccountDAO.getBankAccountIdByIBAN(iban);
	}

	@NonNull
	public BankAccount upsertBankAccount(@NonNull final UpsertBPBankAccountRequest request)
	{
		final BankAccount updatedExistingAcct = Optional.ofNullable(request.getIban())
				.flatMap(bankAccountDAO::getBankAccountByIBAN)
				.map(bpAccount -> bpAccount.toBuilder()
						.name(request.getName())
						.accountNo(request.getAccountNo())
						.routingNo(request.getRoutingNo())
						.IBAN(request.getIban())
						.bPartnerId(request.getBPartnerId())
						.currencyId(request.getCurrencyId())
						.orgId(request.getOrgId())
						.build())
				.orElse(null);

		if (updatedExistingAcct != null)
		{
			return bankAccountDAO.update(updatedExistingAcct);
		}

		final CreateBPBankAccountRequest createBPBankAccountRequest = CreateBPBankAccountRequest.builder()
				.name(request.getName())
				.accountNo(request.getAccountNo())
				.routingNo(request.getRoutingNo())
				.iban(request.getIban())
				.bPartnerId(request.getBPartnerId())
				.currencyId(request.getCurrencyId())
				.orgId(request.getOrgId())
				.build();

		return bankAccountDAO.create(createBPBankAccountRequest);
	}
}
