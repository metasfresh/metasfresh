package de.metas.banking.payment.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import de.metas.banking.model.BankStatementLineReference;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.payment.api.IPaymentBL;
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
public class C_BankStatementLine_UnLink_Payments extends JavaProcess
{
	// services
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
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
		final DocStatus docStatus = DocStatus.ofCode(bankStatement.getDocStatus());
		if (!docStatus.isDraftedOrInProgress() && !docStatus.isCompleted())
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
		
		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

		// Delete references
		for (final BankStatementLineReference lineRef : bankStatementDAO.retrieveLineReferences(bankStatementLine))
		{
			if (lineRef.getPaymentId() != null)
			{
				paymentBL.markNotReconciled(lineRef.getPaymentId());
				addLog("@C_Payment_ID@ " + lineRef.getPaymentId());
			}

			InterfaceWrapperHelper.delete(lineRef);
		}

		//
		// Unpost
		bankStatementBL.unpost(bankStatement);

		return MSG_OK;
	}

}
