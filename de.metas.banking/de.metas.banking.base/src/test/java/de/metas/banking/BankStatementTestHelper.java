/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.banking;

import de.metas.banking.api.BankAccountId;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@UtilityClass
public class BankStatementTestHelper
{

	public static I_C_BankStatement createBankStatement(final BankAccountId bankAccountId, final String name, final Timestamp statementDate)
	{
		final I_C_BankStatement bankStatement = newInstanceOutOfTrx(I_C_BankStatement.class);
		bankStatement.setC_BP_BankAccount_ID(bankAccountId.getRepoId());
		bankStatement.setName(name);
		bankStatement.setStatementDate(statementDate);
		save(bankStatement);

		return bankStatement;
	}

	public static I_C_BankStatementLine createBankStatementLine(final BankStatementId bankStatementId,
			final BPartnerId bPartnerId,
			final int lineNumber,
			final Timestamp statementLineDate,
			final Timestamp valutaDate,
			final BigDecimal stmtAmt,
			final CurrencyId currencyId)
	{
		final I_C_BankStatementLine bsl = newInstanceOutOfTrx(I_C_BankStatementLine.class);
		bsl.setC_BankStatement_ID(bankStatementId.getRepoId());
		bsl.setC_BPartner_ID(bPartnerId.getRepoId());
		bsl.setLine(lineNumber);
		bsl.setStatementLineDate(statementLineDate);
		bsl.setValutaDate(valutaDate);
		bsl.setC_Currency_ID(currencyId.getRepoId());
		bsl.setStmtAmt(stmtAmt);
		save(bsl);

		return bsl;
	}
}
