package de.metas.banking.bankstatement.match.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import com.google.common.collect.ImmutableList;

import de.metas.banking.bankstatement.match.model.IBankStatementLine;
import de.metas.banking.bankstatement.match.model.IBankStatementPaymentMatching;
import de.metas.banking.bankstatement.match.model.IPayment;
import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.model.BankStatementAndLineAndRefId;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.service.BankStatementLineRefCreateRequest;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
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
 * Process {@link IBankStatementPaymentMatching}s and creates bank statement line references.
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
			final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());

			final BankStatementAndLineAndRefId bankStatementLineRefId = bankStatementDAO.createBankStatementLineRef(BankStatementLineRefCreateRequest.builder()
					.bankStatementId(BankStatementId.ofRepoId(bankStatementLinePO.getC_BankStatement_ID()))
					.bankStatementLineId(BankStatementLineId.ofRepoId(bankStatementLinePO.getC_BankStatementLine_ID()))
					//
					.orgId(OrgId.ofRepoId(bankStatementLinePO.getAD_Org_ID()))
					//
					.lineNo(nextReferenceLineNo)
					//
					.paymentId(paymentId)
					.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
					.invoiceId(InvoiceId.ofRepoIdOrNull(payment.getC_Invoice_ID()))
					//
					.trxAmt(extractPayAmt(payment))
					//
					.build());
			nextReferenceLineNo += 10;

			// Payment batch linking
			final IPaymentBatch paymentBatch = payment.getPaymentBatch();
			if (paymentBatch != null)
			{
				paymentBatch.linkBankStatementLine(bankStatementLineRefId, paymentId);
			}

			//
			// Update the payment
			final I_C_Payment paymentPO = paymentDAO.getById(paymentId);
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

	private static Money extractPayAmt(final IPayment payment)
	{
		return Money.of(payment.getPayAmt(), CurrencyId.ofRepoId(payment.getC_Currency_ID()));
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
