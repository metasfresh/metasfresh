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

package de.metas.banking.camt53.wrapper.v04;

import com.google.common.collect.ImmutableList;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.jaxb.camt053_001_04.AccountStatement4;
import de.metas.banking.camt53.jaxb.camt053_001_04.BankToCustomerStatementV04;
import de.metas.banking.camt53.wrapper.IAccountStatementWrapper;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.IMsgBL;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class BatchBankToCustomerStatementV04Wrapper
{
	@NonNull
	BankToCustomerStatementV04 bankToCustomerStatementV04;

	private BatchBankToCustomerStatementV04Wrapper(@NonNull final BankToCustomerStatementV04 bankToCustomerStatementV04)
	{
		bankToCustomerStatementV04
				.getStmt();

		this.bankToCustomerStatementV04 = bankToCustomerStatementV04;
	}

	@NonNull
	public ImmutableList<IAccountStatementWrapper> getAccountStatementWrappers(
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		return bankToCustomerStatementV04.getStmt()
				.stream()
				.map(stmt -> buildAccountStatementWrapper(stmt, bankAccountService, currencyRepository, msgBL))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static IAccountStatementWrapper buildAccountStatementWrapper(
			@NonNull final AccountStatement4 accountStatement4,
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		return AccountStatement4Wrapper.builder()
				.accountStatement4(accountStatement4)
				.bankAccountService(bankAccountService)
				.currencyRepository(currencyRepository)
				.msgBL(msgBL)
				.build();
	}
}
