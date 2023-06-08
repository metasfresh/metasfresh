/*
 * #%L
 * de.metas.banking.camt53
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.banking.camt53.wrapper;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.BankAccountService;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.Optional;

@NonFinal
@Value
public abstract class AccountStatementWrapper implements IAccountStatementWrapper
{
	private static final AdMessageKey MSG_MISSING_MF_BANK_ACCT_WITH_IBAN = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithIBAN");
	private static final AdMessageKey MSG_MISSING_BANK_STMT_SWIFT_CODE = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementSwiftCode");
	private static final AdMessageKey MSG_MISSING_MF_BANK_ACCT_WITH_SWIFT_CODE = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithSwiftCode");
	private static final AdMessageKey MSG_MISSING_MF_BANK_ACCT_WITH_ACCOUNT_NO = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithAccountNo");
	private static final AdMessageKey MSG_MISSING_BANK_STMT_ACCOUNT_NO = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementAccountNo");

	@NonNull BankAccountService bankAccountService;

	@NonNull CurrencyRepository currencyRepository;

	@NonNull IMsgBL msgBL;

	protected AccountStatementWrapper(
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		this.bankAccountService = bankAccountService;
		this.currencyRepository = currencyRepository;
		this.msgBL = msgBL;
	}

	@Override
	@NonNull
	public ExplainedOptional<BankAccountId> getBPartnerBankAccountId()
	{
		final Optional<String> accountIBANOpt = getAccountIBAN();
		if (accountIBANOpt.isPresent())
		{
			final String iban = accountIBANOpt.get();
			final Optional<BankAccountId> bankAccountIdByIBAN = bankAccountService.getBankAccountIdByIBAN(iban);
			return bankAccountIdByIBAN
					.map(ExplainedOptional::of)
					.orElseGet(() -> {
						final String msg = getMsg(MSG_MISSING_MF_BANK_ACCT_WITH_IBAN, getId(), iban);
						return ExplainedOptional.emptyBecause(msg);
					});
		}

		final ExplainedOptional<BankId> bankIdOpt = getBankId();
		if (!bankIdOpt.isPresent())
		{
			return ExplainedOptional.emptyBecause(bankIdOpt.getExplanation());
		}

		final Optional<String> accountNoOpt = getAccountNo();
		return accountNoOpt.map(accountNoCandidate -> bankAccountService.getBankAccountId(bankIdOpt.get(), accountNoCandidate)
						.map(ExplainedOptional::of)
						.orElseGet(() -> {
							final String msg = getMsg(MSG_MISSING_MF_BANK_ACCT_WITH_ACCOUNT_NO, getId(), bankIdOpt.get().getRepoId(), accountNoCandidate);
							return ExplainedOptional.emptyBecause(msg);
						}))
				.orElseGet(() -> {
					final String msg = getMsg(MSG_MISSING_BANK_STMT_ACCOUNT_NO, getId());
					return ExplainedOptional.emptyBecause(msg);
				});
	}

	@NonNull
	private ExplainedOptional<BankId> getBankId()
	{
		if (getSwiftCode().isEmpty())
		{
			final String msg = getMsg(MSG_MISSING_BANK_STMT_SWIFT_CODE, getId());
			return ExplainedOptional.emptyBecause(msg);
		}
		final String swiftCode = getSwiftCode().get();

		return bankAccountService.getBankIdBySwiftCode(swiftCode)
				.map(ExplainedOptional::of)
				.orElseGet(() -> {
					final String msg = getMsg(MSG_MISSING_MF_BANK_ACCT_WITH_SWIFT_CODE, getId(), swiftCode);
					return ExplainedOptional.emptyBecause(msg);
				});
	}

	@NonNull
	private String getMsg(@NonNull final AdMessageKey adMessageKey, final Object... params)
	{
		return msgBL.getMsg(adMessageKey, ImmutableList.copyOf(params));
	}

	protected abstract Optional<String> getAccountIBAN();

	protected abstract Optional<String> getSwiftCode();

	protected abstract Optional<String> getAccountNo();
}
