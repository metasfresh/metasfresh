package de.metas.banking.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_DocType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

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

import de.metas.acct.api.IFactAcctDAO;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.model.BankStatementLineReference;
import de.metas.banking.model.BankStatementLineReferenceList;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;

public class BankStatementBL implements IBankStatementBL
{
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	@Override
	public void handleAfterPrepare(final I_C_BankStatement bankStatement)
	{
		for (final I_C_BankStatementLine line : bankStatementDAO.retrieveLines(bankStatement))
		{
			if (line.isMultiplePaymentOrInvoice() && line.isMultiplePayment())
			{
				// Payment in C_BankStatementLine_Ref are mandatory
				final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(line.getC_BankStatementLine_ID());
				for (final BankStatementLineReference refLine : bankStatementDAO.retrieveLineReferences(bankStatementLineId))
				{
					if (refLine.getPaymentId() == null)
					{
						// TODO -> AD_Message
						throw new AdempiereException("Missing payment in reference line "
								+ refLine.getLineNo() + " of line "
								+ line.getLine());
					}
				}
			}
		}
	}

	@Override
	public void handleAfterComplete(final I_C_BankStatement bankStatement)
	{
		final IBankStatmentPaymentBL bankStatmentPaymentBL = Services.get(IBankStatmentPaymentBL.class);

		for (final I_C_BankStatementLine line : bankStatementDAO.retrieveLines(bankStatement))
		{
			bankStatmentPaymentBL.findOrCreateUnreconciledPaymentsAndLinkToBankStatementLine(bankStatement, line);
			reconcilePaymentsFromBankStatementLine_Ref(bankStatementDAO, line);
		}
	}

	private void reconcilePaymentsFromBankStatementLine_Ref(final IBankStatementDAO bankStatementDAO, final I_C_BankStatementLine line)
	{
		if (line.isMultiplePaymentOrInvoice() && line.isMultiplePayment())
		{
			final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(line.getC_BankStatementLine_ID());
			final ImmutableSet<PaymentId> paymentIds = bankStatementDAO
					.retrieveLineReferences(bankStatementLineId)
					.getPaymentIds();

			paymentBL.markReconciled(paymentIds);
		}
	}

	@Override
	public void handleBeforeVoid(final I_C_BankStatement bankStatement)
	{
		final List<I_C_BankStatementLine> lines = bankStatementDAO.retrieveLines(bankStatement);
		unlinkPaymentsAndDeleteReferences(lines);
	}

	@Override
	public void updateEndingBalance(final I_C_BankStatement bankStatement)
	{
		final BigDecimal endingBalance = bankStatement
				.getBeginningBalance()
				.add(bankStatement.getStatementDifference());

		bankStatement.setEndingBalance(endingBalance);
	}

	@Override
	public void unpost(final I_C_BankStatement bankStatement)
	{
		// Make sure the period is open
		final Properties ctx = InterfaceWrapperHelper.getCtx(bankStatement);
		MPeriod.testPeriodOpen(ctx, bankStatement.getStatementDate(), X_C_DocType.DOCBASETYPE_BankStatement, bankStatement.getAD_Org_ID());

		Services.get(IFactAcctDAO.class).deleteForDocumentModel(bankStatement);

		bankStatement.setPosted(false);
		InterfaceWrapperHelper.save(bankStatement);
	}

	@Override
	public boolean isReconciled(@NonNull final org.compiere.model.I_C_BankStatementLine line)
	{
		if (line.isMultiplePaymentOrInvoice())
		{
			if (line.isMultiplePayment())
			{
				// NOTE: for performance reasons we are not checking if we have C_BankStatementLine_Ref records which have payments.
				// If this flag is set we assume that we already have them
				return true;
			}
			else
			{
				final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
				return paymentId != null;
			}
		}
		else
		{
			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
			return paymentId != null;
		}
	}

	@Override
	public BigDecimal computeStmtAmtExcludingChargeAmt(final org.compiere.model.I_C_BankStatementLine line)
	{
		return line.getTrxAmt()
				.add(line.getInterestAmt())
		// .add(line.getChargeAmt())
		;
	}

	@Override
	public String getDocumentNo(@NonNull final BankStatementId bankStatementId)
	{
		final I_C_BankStatement bankStatement = Services.get(IBankStatementDAO.class).getById(bankStatementId);
		return bankStatement.getDocumentNo();
	}

	@Override
	public void unlinkPaymentsAndDeleteReferences(@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		unlinkPaymentsAndDeleteReferences(ImmutableList.of(bankStatementLine));
	}

	public void unlinkPaymentsAndDeleteReferences(@NonNull final List<I_C_BankStatementLine> bankStatementLines)
	{
		if (bankStatementLines.isEmpty())
		{
			return;
		}

		//
		// Unlink payment from line
		final ArrayList<BankStatementLineId> bankStatementLineIds = new ArrayList<>();
		for (final I_C_BankStatementLine bankStatementLine : bankStatementLines)
		{
			final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID());
			bankStatementLineIds.add(bankStatementLineId);

			final PaymentId paymentId = PaymentId.ofRepoId(bankStatementLine.getC_Payment_ID());
			if (paymentId != null)
			{
				paymentBL.markNotReconciled(paymentId);

				bankStatementLine.setC_Payment_ID(-1);
				bankStatementDAO.save(bankStatementLine);
			}
		}

		// Delete references
		final BankStatementLineReferenceList lineRefs = bankStatementDAO.retrieveLineReferences(bankStatementLineIds);
		paymentBL.markNotReconciled(lineRefs.getPaymentIds());
		bankStatementDAO.deleteReferences(lineRefs.toList());
	}

	@Override
	public void deleteReferencesAndUnReconcilePayments(@NonNull final BankStatementLineId bankStatementLineId)
	{
		final BankStatementLineReferenceList lineRefs = bankStatementDAO.retrieveLineReferences(bankStatementLineId);

		Services.get(IBankStatementListenerService.class)
				.getListeners()
				.onBankStatementLineVoiding(bankStatementLineId, lineRefs.toList());

		paymentBL.markNotReconciled(lineRefs.getPaymentIds());
		bankStatementDAO.deleteReferences(lineRefs.toList());
	}
}
