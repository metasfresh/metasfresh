package de.metas.banking.bankstatement.match.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import com.google.common.collect.ImmutableList;

import de.metas.banking.bankstatement.match.model.IBankStatementLine;
import de.metas.banking.bankstatement.match.model.IBankStatementPaymentMatching;
import de.metas.banking.bankstatement.match.model.IPayment;
import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.banking.swingui
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
 * Process {@link IBankStatementPaymentMatching}s and creates {@link I_C_BankStatementLine_Ref}s.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BankStatementPaymentMatchingProcessor
{
	// services
	private final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	// Parameters
	private List<IBankStatementPaymentMatching> matchings = ImmutableList.of();

	// Status
	private boolean processed = false;
	private final Map<Integer, I_C_BankStatement> bankStatementsToUnpost = new HashMap<>();

	public static final BankStatementPaymentMatchingProcessor newInstance()
	{
		return new BankStatementPaymentMatchingProcessor();
	}

	private BankStatementPaymentMatchingProcessor()
	{
		super();
	}

	public void process()
	{
		markAsProcessed();

		trxManager.runInThreadInheritedTrx(this::processInTrx);
	}

	private final void processInTrx()
	{
		//
		// Create bank statement line references
		for (final IBankStatementPaymentMatching matching : matchings)
		{
			process(matching);
		}

		//
		// Un-post bank statements
		for (final I_C_BankStatement bankStatement : bankStatementsToUnpost.values())
		{
			bankStatementBL.unpost(bankStatement);
		}
	}

	private void process(final IBankStatementPaymentMatching matching)
	{
		final IBankStatementLine bankStatementLine = matching.getBankStatementLine();
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID());
		final I_C_BankStatementLine bankStatementLinePO = bankStatementDAO.getLineById(bankStatementLineId);

		//
		// Create bank statement line refs
		final List<IPayment> payments = matching.getPayments();
		Check.assumeNotEmpty(payments, "payments not empty");
		int nextReferenceLineNo = 10;
		for (final IPayment payment : payments)
		{
			final I_C_BankStatementLine_Ref bankStatementLineRef = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class);
			IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.setValue(bankStatementLineRef, true); // disable recalculation because we shall NOT change the bank statement
																															 // line

			bankStatementLineRef.setC_BankStatement_ID(bankStatementLinePO.getC_BankStatement_ID());
			bankStatementLineRef.setC_BankStatementLine_ID(bankStatementLinePO.getC_BankStatementLine_ID());
			bankStatementLineRef.setLine(nextReferenceLineNo);

			bankStatementLineRef.setAD_Org_ID(bankStatementLinePO.getAD_Org_ID());

			bankStatementLineRef.setC_BPartner_ID(payment.getC_BPartner_ID());
			bankStatementLineRef.setC_Payment_ID(payment.getC_Payment_ID());
			bankStatementLineRef.setC_Invoice_ID(payment.getC_Invoice_ID());
			bankStatementLineRef.setC_Currency_ID(payment.getC_Currency_ID());

			bankStatementLineRef.setTrxAmt(payment.getPayAmt());
			bankStatementLineRef.setDiscountAmt(BigDecimal.ZERO);
			bankStatementLineRef.setOverUnderAmt(BigDecimal.ZERO);
			bankStatementLineRef.setIsOverUnderPayment(false);

			//
			// Save the bank statement line reference
			// bankStatementLineRef.setProcessed(true); // virtual column
			bankStatementDAO.save(bankStatementLineRef);
			nextReferenceLineNo += 10;

			// Payment batch linking
			final IPaymentBatch paymentBatch = payment.getPaymentBatch();
			if (paymentBatch != null)
			{
				paymentBatch.linkBankStatementLine(bankStatementLineRef);
			}

			//
			// Update the payment
			final I_C_Payment paymentPO = bankStatementLineRef.getC_Payment();
			paymentPO.setIsReconciled(true);
			paymentDAO.save(paymentPO);
		}

		//
		// Update bank statement line
		bankStatementLinePO.setIsMultiplePaymentOrInvoice(true);
		bankStatementLinePO.setIsMultiplePayment(true);
		bankStatementDAO.save(bankStatementLinePO);

		//
		// Un-post the bank statement
		final int bankStatementId = bankStatementLinePO.getC_BankStatement_ID();
		if (!bankStatementsToUnpost.containsKey(bankStatementId))
		{
			bankStatementsToUnpost.put(bankStatementId, bankStatementLinePO.getC_BankStatement());
		}
	}

	private final void assertNotProcessed()
	{
		Check.assume(!processed, "not processed");
	}

	private final void markAsProcessed()
	{
		assertNotProcessed();
		processed = true;
	}

	public BankStatementPaymentMatchingProcessor setBankStatementPaymentMatchings(final List<IBankStatementPaymentMatching> matchings)
	{
		assertNotProcessed();

		if (matchings == null)
		{
			this.matchings = ImmutableList.of();
		}
		else
		{
			this.matchings = ImmutableList.copyOf(matchings);
		}
		return this;
	}
}
