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

package de.metas.banking.camt53.wrapper.v02;

import com.google.common.collect.ImmutableList;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.jaxb.camt053_001_02.AccountStatement2;
import de.metas.banking.camt53.jaxb.camt053_001_02.BankToCustomerStatementV02;
import de.metas.banking.camt53.jaxb.camt053_001_02.EntryTransaction2;
import de.metas.banking.camt53.jaxb.camt053_001_02.ReportEntry2;
import de.metas.banking.camt53.wrapper.IAccountStatementWrapper;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value(staticConstructor = "of")
public class BatchBankToCustomerStatementV02Wrapper
{
	private static final AdMessageKey MSG_BATCH_TRANSACTIONS_NOT_SUPPORTED = AdMessageKey.of("de.metas.banking.camt53.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported");

	@NonNull
	BankToCustomerStatementV02 bankToCustomerStatementV02;

	private BatchBankToCustomerStatementV02Wrapper(@NonNull final BankToCustomerStatementV02 bankToCustomerStatementV02)
	{
		bankToCustomerStatementV02
				.getStmt()
				.forEach(BatchBankToCustomerStatementV02Wrapper::validateAccountStatement2);

		this.bankToCustomerStatementV02 = bankToCustomerStatementV02;
	}

	@NonNull
	public ImmutableList<IAccountStatementWrapper> getAccountStatements(
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		return bankToCustomerStatementV02.getStmt()
				.stream()
				.map(stmt -> buildAccountStatementWrapper(stmt, bankAccountService, currencyRepository, msgBL))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static IAccountStatementWrapper buildAccountStatementWrapper(
			@NonNull final AccountStatement2 accountStatement2,
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		return AccountStatement2Wrapper.builder()
				.accountStatement2(accountStatement2)
				.bankAccountService(bankAccountService)
				.currencyRepository(currencyRepository)
				.msgBL(msgBL)
				.build();
	}

	/**
	 * Validates that the {@link AccountStatement2} contains only single transactions.
	 */
	private static void validateAccountStatement2(@NonNull final AccountStatement2 accountStatement2)
	{
		accountStatement2.getNtry().forEach(BatchBankToCustomerStatementV02Wrapper::validateNoBatchedTrxPresent);
	}

	private static void validateNoBatchedTrxPresent(@NonNull final ReportEntry2 reportEntry)
	{
		if (isBatchedTrxPresent(reportEntry))
		{
			throw new AdempiereException(MSG_BATCH_TRANSACTIONS_NOT_SUPPORTED)
					.markAsUserValidationError();
		}
	}

	/**
	 * Checks if there are any batched transactions within given {@link ReportEntry2};
	 * Notes:
	 * - there will be one {@link de.metas.banking.camt53.jaxb.camt053_001_02.EntryDetails1} for each batch of transactions included in the given {@link ReportEntry2}
	 * - there will be one {@link EntryTransaction2} for each transaction included in a batch i.e. in a EntryDetails1
	 */
	private static boolean isBatchedTrxPresent(@NonNull final ReportEntry2 reportEntry)
	{
		return reportEntry.getNtryDtls().size() > 1
				// dev-note: we consider batch with one trx non-batched as it doesn't make any difference
				|| reportEntry.getNtryDtls().size() == 1 && reportEntry.getNtryDtls().get(0).getTxDtls().size() > 1;
	}
}
