/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.bankstatement.importer.wrapper;

import de.metas.banking.bankstatement.jaxb.camt053_001_02.AccountStatement2;
import de.metas.banking.bankstatement.jaxb.camt053_001_02.BankToCustomerStatementV02;
import de.metas.banking.bankstatement.jaxb.camt053_001_02.ReportEntry2;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@Value(staticConstructor = "of")
public class NoBatchBankToCustomerStatementV02Wrapper
{
	private static final AdMessageKey MSG_BATCH_TRANSACTIONS_NOT_SUPPORTED = AdMessageKey.of("de.metas.banking.bankstatement.importer.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported");

	@NonNull
	BankToCustomerStatementV02 bankToCustomerStatementV02;

	@NonNull
	IMsgBL msgBL;

	private NoBatchBankToCustomerStatementV02Wrapper(
			@NonNull final BankToCustomerStatementV02 bankToCustomerStatementV02,
			@NonNull final IMsgBL msgBL)
	{
		this.msgBL = msgBL;

		bankToCustomerStatementV02
				.getStmt()
				.forEach(this::validateAccountStatement2);

		this.bankToCustomerStatementV02 = bankToCustomerStatementV02;
	}

	@NonNull
	public List<AccountStatement2> getAccountStatements()
	{
		return bankToCustomerStatementV02.getStmt();
	}

	private void validateAccountStatement2(@NonNull final AccountStatement2 accountStatement2)
	{
		accountStatement2.getNtry().forEach(this::validateNoBatchedTrxPresent);
	}

	private void validateNoBatchedTrxPresent(@NonNull final ReportEntry2 reportEntry)
	{
		if (isBatchedTrxPresent(reportEntry))
		{
			final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG_BATCH_TRANSACTIONS_NOT_SUPPORTED);
			throw new AdempiereException(msg)
					.markAsUserValidationError();
		}
	}

	private static boolean isBatchedTrxPresent(@NonNull final ReportEntry2 reportEntry)
	{
		return reportEntry.getNtryDtls().size() > 1;
	}
}
