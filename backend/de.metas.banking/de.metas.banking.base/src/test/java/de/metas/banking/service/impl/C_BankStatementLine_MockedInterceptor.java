package de.metas.banking.service.impl;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_BankStatementLine;

import de.metas.banking.BankStatementId;
import de.metas.banking.model.validator.C_BankStatementLine;
import de.metas.banking.service.IBankStatementBL;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
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

@Interceptor(I_C_BankStatementLine.class)
public class C_BankStatementLine_MockedInterceptor extends C_BankStatementLine
{
	public C_BankStatementLine_MockedInterceptor(@NonNull final IBankStatementBL bankStatementBL)
	{
		super(bankStatementBL);
	}

	@Override
	protected void updateStatementDifferenceAndEndingBalance(final BankStatementId bankStatementId)
	{
		// avoid JDBC
		System.out.println("in JUnit mode, updateStatementDifferenceAndEndingBalance(" + bankStatementId + ") does nothing");
	}

	@Override
	protected void updateBankStatementIsReconciledFlag(final BankStatementId bankStatementId)
	{
		// avoid JDBC
		System.out.println("in JUnit mode, updateBankStatementIsReconciledFlag(" + bankStatementId + ") does nothing");
	}
}
