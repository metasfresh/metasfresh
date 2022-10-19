/*
 * #%L
 * camt53
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.banking.camt53.jaxb.camt053_001_02.AccountStatement2;
import de.metas.banking.camt53.jaxb.camt053_001_02.BankToCustomerStatementV02;
import de.metas.banking.camt53.jaxb.camt053_001_02.EntryTransaction2;
import de.metas.banking.camt53.jaxb.camt053_001_02.ReportEntry2;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@Value(staticConstructor = "of")
public class NoBatchBankToCustomerStatementV02Wrapper
{
	private static final AdMessageKey MSG_BATCH_TRANSACTIONS_NOT_SUPPORTED = AdMessageKey.of("de.metas.banking.camt53.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported");

	@NonNull
	BankToCustomerStatementV02 bankToCustomerStatementV02;

	private NoBatchBankToCustomerStatementV02Wrapper(@NonNull final BankToCustomerStatementV02 bankToCustomerStatementV02)
	{
		bankToCustomerStatementV02
				.getStmt()
				.forEach(NoBatchBankToCustomerStatementV02Wrapper::validateAccountStatement2);

		this.bankToCustomerStatementV02 = bankToCustomerStatementV02;
	}

	@NonNull
	public List<AccountStatement2> getAccountStatements()
	{
		return bankToCustomerStatementV02.getStmt();
	}

	/**
	 * Validates that the {@link AccountStatement2} contains only single transactions.
	 */
	private static void validateAccountStatement2(@NonNull final AccountStatement2 accountStatement2)
	{
		accountStatement2.getNtry().forEach(NoBatchBankToCustomerStatementV02Wrapper::validateNoBatchedTrxPresent);
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
