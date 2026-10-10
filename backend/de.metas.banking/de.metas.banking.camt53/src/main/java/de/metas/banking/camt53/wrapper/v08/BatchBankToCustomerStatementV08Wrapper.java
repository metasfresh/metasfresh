/*
 * #%L
 * de.metas.banking.camt53
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.banking.camt53.wrapper.v08;

import com.google.common.collect.ImmutableList;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.jaxb.camt053_001_08.AccountStatement9;
import de.metas.banking.camt53.jaxb.camt053_001_08.BankToCustomerStatementV08;
import de.metas.banking.camt53.wrapper.IAccountStatementWrapper;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.IMsgBL;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class BatchBankToCustomerStatementV08Wrapper
{
	@NonNull
	BankToCustomerStatementV08 bankToCustomerStatementV08;

	private BatchBankToCustomerStatementV08Wrapper(@NonNull final BankToCustomerStatementV08 bankToCustomerStatementV08)
	{
		bankToCustomerStatementV08
				.getStmt();

		this.bankToCustomerStatementV08 = bankToCustomerStatementV08;
	}

	@NonNull
	public ImmutableList<IAccountStatementWrapper> getAccountStatementWrappers(
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		return bankToCustomerStatementV08.getStmt()
				.stream()
				.map(stmt -> buildAccountStatementWrapper(stmt, bankAccountService, currencyRepository, msgBL))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static IAccountStatementWrapper buildAccountStatementWrapper(
			@NonNull final AccountStatement9 accountStatement9,
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		return AccountStatement9Wrapper.builder()
				.accountStatement9(accountStatement9)
				.bankAccountService(bankAccountService)
				.currencyRepository(currencyRepository)
				.msgBL(msgBL)
				.build();
	}
}
