package de.metas.banking.payment.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.process.DocAction;

import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.process.SvrProcess;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Process used to unlink all payments of selected {@link I_C_BankStatementLine}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_BankStatementLine_UnLink_Payments extends SvrProcess
{
	// services
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final transient IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_BankStatementLine bankStatementLine = getRecord(I_C_BankStatementLine.class);

		//
		// Make sure we are allowed to modify this line
		final I_C_BankStatement bankStatement = bankStatementLine.getC_BankStatement();
		final String docStatus = bankStatement.getDocStatus();
		if (!docActionBL.isStatusOneOf(docStatus, DocAction.STATUS_Drafted, DocAction.STATUS_Completed, DocAction.STATUS_InProgress))
		{
			throw new AdempiereException("@Invalid@ @DocStatus@: " + docStatus);
		}

		//
		// Unlink payment from line
		{
			final I_C_Payment payment = bankStatementLine.getC_Payment();
			if (payment != null)
			{
				payment.setIsReconciled(false);
				InterfaceWrapperHelper.save(payment);
				addLog("@C_Payment_ID@ " + payment.getDocumentNo());
			}
			bankStatementLine.setC_Payment(null);
			InterfaceWrapperHelper.save(bankStatementLine);
		}

		// Delete references
		for (final I_C_BankStatementLine_Ref lineRef : bankStatementDAO.retrieveLineReferences(bankStatementLine))
		{
			// make sure we are not changing the bank statement line amounts when we will delete the line reference
			IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.setValue(lineRef, true);

			final I_C_Payment payment = lineRef.getC_Payment();
			if (payment != null)
			{
				payment.setIsReconciled(false);
				InterfaceWrapperHelper.save(payment);
				addLog("@C_Payment_ID@ " + payment.getDocumentNo());
			}

			InterfaceWrapperHelper.delete(lineRef);
		}

		//
		// Unpost
		bankStatementBL.unpost(bankStatement);

		return MSG_OK;
	}

}
