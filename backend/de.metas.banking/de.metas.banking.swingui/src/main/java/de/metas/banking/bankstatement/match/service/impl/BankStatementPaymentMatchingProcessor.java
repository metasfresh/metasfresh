package de.metas.banking.bankstatement.match.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TrxRunnable;

import com.google.common.collect.ImmutableList;

import de.metas.banking.bankstatement.match.model.IBankStatementLine;
import de.metas.banking.bankstatement.match.model.IBankStatementPaymentMatching;
import de.metas.banking.bankstatement.match.model.IPayment;
import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.IBankStatementBL;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
	private final transient IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	// Parameters
	private Properties ctx;
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
		
		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnable()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				processInTrx();
			}
		});
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
		final I_C_BankStatementLine bankStatementLinePO = InterfaceWrapperHelper.create(getCtx(), bankStatementLine.getC_BankStatementLine_ID(), I_C_BankStatementLine.class, ITrx.TRXNAME_ThreadInherited);

		//
		// Create bank statement line refs
		final List<IPayment> payments = matching.getPayments();
		Check.assumeNotEmpty(payments, "payments not empty");
		int nextReferenceLineNo = 10;
		for (final IPayment payment : payments)
		{
			final I_C_BankStatementLine_Ref bankStatementLineRef = InterfaceWrapperHelper.create(getCtx(), I_C_BankStatementLine_Ref.class, ITrx.TRXNAME_ThreadInherited);
			IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.setValue(bankStatementLineRef, true); // disable recalculation because we shall NOT change the bank statement
																																// line

			bankStatementLineRef.setC_BankStatementLine(bankStatementLinePO);
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
			InterfaceWrapperHelper.save(bankStatementLineRef);
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
			InterfaceWrapperHelper.save(paymentPO);
		}

		//
		// Update bank statement line
		bankStatementLinePO.setIsMultiplePaymentOrInvoice(true);
		bankStatementLinePO.setIsMultiplePayment(true);
		InterfaceWrapperHelper.save(bankStatementLinePO);

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

	public BankStatementPaymentMatchingProcessor setContext(final Properties ctx)
	{
		assertNotProcessed();
		this.ctx = ctx;
		return this;
	}

	private Properties getCtx()
	{
		Check.assumeNotNull(ctx, "ctx not null");
		return ctx;
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
