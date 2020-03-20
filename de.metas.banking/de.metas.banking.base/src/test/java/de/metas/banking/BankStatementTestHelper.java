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

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.time.LocalDate;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.util.TimeUtil;

import de.metas.banking.api.BankAccountId;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BankStatementTestHelper
{

	public static I_C_BankStatement createBankStatement(final BankAccountId bankAccountId, final String name, final LocalDate statementDate)
	{
		final I_C_BankStatement bankStatement = newInstanceOutOfTrx(I_C_BankStatement.class);
		bankStatement.setC_BP_BankAccount_ID(bankAccountId.getRepoId());
		bankStatement.setName(name);
		bankStatement.setStatementDate(TimeUtil.asTimestamp(statementDate));
		save(bankStatement);

		return bankStatement;
	}

	public static I_C_BankStatementLine createBankStatementLine(final BankStatementId bankStatementId,
			final BPartnerId bpartnerId,
			final int lineNumber,
			final LocalDate statementLineDate,
			final LocalDate valutaDate,
			final Money stmtAmt)
	{
		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

		final BankStatementLineId bankStatementLineId = bankStatementDAO.createBankStatementLine(BankStatementLineCreateRequest.builder()
				.bankStatementId(bankStatementId)
				.orgId(OrgId.ANY)
				.bpartnerId(bpartnerId)
				.lineNo(lineNumber)
				.statementLineDate(statementLineDate)
				.valutaDate(valutaDate)
				.statementAmt(stmtAmt)
				.trxAmt(stmtAmt)
				.build());

		return bankStatementDAO.getLineById(bankStatementLineId);
	}
}
