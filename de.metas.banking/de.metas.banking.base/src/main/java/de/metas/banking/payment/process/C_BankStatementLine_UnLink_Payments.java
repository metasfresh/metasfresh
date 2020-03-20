package de.metas.banking.payment.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;

import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.DocStatus;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

/**
 * Process used to unlink all payments of selected {@link I_C_BankStatementLine}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_BankStatementLine_UnLink_Payments extends JavaProcess
{
	// services
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

	@Override
	protected String doIt()
	{
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(getRecord_ID());
		unreconcile(bankStatementLineId);

		return MSG_OK;
	}

	private void unreconcile(final BankStatementLineId bankStatementLineId)
	{
		final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(bankStatementLineId);

		//
		// Make sure we are allowed to modify this line
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());
		final I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementId);
		final DocStatus docStatus = DocStatus.ofCode(bankStatement.getDocStatus());
		if (!docStatus.isDraftedInProgressOrCompleted())
		{
			throw new AdempiereException("@Invalid@ @DocStatus@: " + docStatus);
		}

		bankStatementBL.unlinkPaymentsAndDeleteReferences(bankStatementLine);
		bankStatementBL.unpost(bankStatement);
	}

}
