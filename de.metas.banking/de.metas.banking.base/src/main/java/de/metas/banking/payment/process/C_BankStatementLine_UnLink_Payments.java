package de.metas.banking.payment.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;

import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.DocStatus;
import de.metas.payment.PaymentId;
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
	private final transient IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final transient IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
	private final IPaymentBL paymentService = Services.get(IPaymentBL.class);

	@Override
	protected String doIt()
	{
		final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(getRecord_ID());

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
			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());
			if (paymentId != null)
			{
				paymentService.markReconciled(paymentId);
				addLog("@C_Payment_ID@ " + paymentId);

				bankStatementLine.setC_Payment_ID(-1);
				bankStatementDAO.save(bankStatementLine);
			}
		}

		// Delete references
		for (final I_C_BankStatementLine_Ref lineRef : bankStatementDAO.retrieveLineReferences(bankStatementLine))
		{
			// make sure we are not changing the bank statement line amounts when we will delete the line reference
			IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.setValue(lineRef, true);

			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());
			if (paymentId != null)
			{
				paymentService.markNotReconciled(paymentId);
				addLog("@C_Payment_ID@ " + paymentId);
			}

			bankStatementDAO.delete(lineRef);
		}

		//
		// Unpost
		bankStatementBL.unpost(bankStatement);

		return MSG_OK;
	}

}
