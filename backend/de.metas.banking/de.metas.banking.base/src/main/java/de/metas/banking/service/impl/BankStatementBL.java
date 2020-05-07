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

package de.metas.banking.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_DocType;

import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;

public class BankStatementBL implements IBankStatementBL
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IBankStatementListenerService bankStatementListenersService = Services.get(IBankStatementListenerService.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);

	@Override
	public I_C_BankStatement getById(@NonNull final BankStatementId bankStatementId)
	{
		return bankStatementDAO.getById(bankStatementId);
	}

	@Override
	public I_C_BankStatementLine getLineById(@NonNull final BankStatementLineId bankStatementLineId)
	{
		return bankStatementDAO.getLineById(bankStatementLineId);
	}

	@Override
	public List<I_C_BankStatementLine> getLinesByBankStatementId(@NonNull final BankStatementId bankStatementId)
	{
		return bankStatementDAO.getLinesByBankStatementId(bankStatementId);
	}

	@Override
	public List<I_C_BankStatementLine> getLinesByIds(@NonNull final Set<BankStatementLineId> lineIds)
	{
		return bankStatementDAO.getLinesByIds(lineIds);
	}

	@Override
	public boolean isPaymentOnBankStatement(@NonNull final PaymentId paymentId)
	{
		return bankStatementDAO.isPaymentOnBankStatement(paymentId);
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

		factAcctDAO.deleteForDocumentModel(bankStatement);

		bankStatement.setPosted(false);
		InterfaceWrapperHelper.save(bankStatement);
	}

	@Override
	public boolean isReconciled(@NonNull final I_C_BankStatementLine line)
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
	public BigDecimal computeStmtAmtExcludingChargeAmt(final I_C_BankStatementLine line)
	{
		return line.getTrxAmt()
				.add(line.getInterestAmt())
				// .add(line.getChargeAmt())
				;
	}

	@Override
	public String getDocumentNo(@NonNull final BankStatementId bankStatementId)
	{
		final I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementId);
		return bankStatement.getDocumentNo();
	}

	@Override
	public void unlinkPaymentsAndDeleteReferences(@NonNull final List<I_C_BankStatementLine> bankStatementLines)
	{
		if (bankStatementLines.isEmpty())
		{
			return;
		}

		//
		// Unlink payment from line
		final ArrayList<BankStatementLineId> bankStatementLineIds = new ArrayList<>();
		final ArrayList<PaymentId> paymentIdsToUnReconcile = new ArrayList<>();
		for (final I_C_BankStatementLine bankStatementLine : bankStatementLines)
		{
			final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID());
			bankStatementLineIds.add(bankStatementLineId);

			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());
			if (paymentId != null)
			{
				paymentIdsToUnReconcile.add(paymentId);
			}

			bankStatementLine.setIsReconciled(false);
			bankStatementLine.setIsMultiplePaymentOrInvoice(false);
			bankStatementLine.setIsMultiplePayment(false);

			bankStatementLine.setC_Payment_ID(-1);

			bankStatementDAO.save(bankStatementLine);
		}

		//
		// Check references
		final BankStatementLineReferenceList lineRefs = bankStatementDAO.getLineReferences(bankStatementLineIds);
		paymentIdsToUnReconcile.addAll(lineRefs.getPaymentIds());

		deleteReferencesAndNotifyListeners(lineRefs);

		paymentBL.markNotReconciled(paymentIdsToUnReconcile);
	}

	@Override
	public void deleteReferences(@NonNull final BankStatementLineId bankStatementLineId)
	{
		final BankStatementLineReferenceList lineRefs = bankStatementDAO.getLineReferences(bankStatementLineId);
		deleteReferencesAndNotifyListeners(lineRefs);
	}

	private void deleteReferencesAndNotifyListeners(@NonNull final BankStatementLineReferenceList lineRefs)
	{
		if (lineRefs.isEmpty())
		{
			return;
		}

		bankStatementListenersService.firePaymentsUnlinkedFromBankStatementLineReferences(lineRefs);
		bankStatementDAO.deleteReferencesByIds(lineRefs.getBankStatementLineRefIds());
	}

	@Override
	public int computeNextLineNo(@NonNull final BankStatementId bankStatementId)
	{
		final int lastLineNo = bankStatementDAO.getLastLineNo(bankStatementId);
		return lastLineNo / 10 * 10 + 10;
	}

	@NonNull
	@Override
	public ImmutableSet<PaymentId> getLinesPaymentIds(@NonNull final BankStatementId bankStatementId)
	{
		return bankStatementDAO.getLinesPaymentIds(bankStatementId);
	}

	@Override
	public void updateLineFromInvoice(final @NonNull I_C_BankStatementLine bankStatementLine, @NonNull final InvoiceId invoiceId)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		bankStatementLine.setC_BPartner_ID(invoice.getC_BPartner_ID());
		bankStatementLine.setStmtAmt(invoice.getGrandTotal());
		bankStatementLine.setTrxAmt(invoice.getGrandTotal());
		bankStatementLine.setC_Currency_ID(invoice.getC_Currency_ID());
	}

}
