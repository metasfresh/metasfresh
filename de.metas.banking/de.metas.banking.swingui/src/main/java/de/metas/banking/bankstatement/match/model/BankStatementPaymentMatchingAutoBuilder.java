package de.metas.banking.bankstatement.match.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableList;

import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.bankstatement.match.spi.PaymentBatch;

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
 * Helper class to automatically match a list of {@link IPayment}s to a list of {@link IBankStatementLine}s.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BankStatementPaymentMatchingAutoBuilder
{
	public static final BankStatementPaymentMatchingAutoBuilder newBuilder()
	{
		return new BankStatementPaymentMatchingAutoBuilder();
	}

	// services
	private final transient Logger logger = LogManager.getLogger(getClass());

	// Parameters
	private List<IBankStatementLine> bankStatementLines = ImmutableList.of();
	private List<IPayment> payments = ImmutableList.of();
	
	private static final String SYSCONFIG_DateToleranceDays = "de.metas.banking.bankstatement.match.model.BankStatementPaymentMatchingAutoBuilder.DateToleranceDays";
	private static final int DEFAULT_DateToleranceDays = 3;
	private int dateToleranceDays = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_DateToleranceDays, DEFAULT_DateToleranceDays);

	private BankStatementPaymentMatchingAutoBuilder()
	{
		super();
	}

	public List<IBankStatementPaymentMatching> build()
	{
		final List<IBankStatementPaymentMatching> matchingsAll = new ArrayList<>();

		final List<IBankStatementLine> bankStatementLinesToMatch = new ArrayList<>(bankStatementLines);
		final List<BatchWithPayments> paymentBatchesToMatch = createBatches(payments);

		//
		// Iterate all bank statements
		for (final Iterator<IBankStatementLine> bankStatementLineIt = bankStatementLinesToMatch.iterator(); bankStatementLineIt.hasNext();)
		{
			final IBankStatementLine bankStatementLine = bankStatementLineIt.next();

			//
			// Iterate all batches
			for (final Iterator<BatchWithPayments> batchesIt = paymentBatchesToMatch.iterator(); batchesIt.hasNext();)
			{
				final BatchWithPayments batch = batchesIt.next();

				// Create the matching, if possible)
				final IBankStatementPaymentMatching matching = createMatching(bankStatementLine, batch);
				if (matching == null)
				{
					// no matching was created => try on next batch
					continue;
				}
				
				// Remove the bank statement and batch from current list because they were matched
				bankStatementLineIt.remove();
				batchesIt.remove();
				// Collect the created matching
				matchingsAll.add(matching);

				// Stop here and go to next bank statement
				break;
			}
		}

		return matchingsAll;
	}

	private IBankStatementPaymentMatching createMatching(final IBankStatementLine bankStatementLine, final BatchWithPayments batch)
	{
		logger.info("Bank statement line: {}", bankStatementLine);
		logger.info("Batch with payments: {}", batch);

		// Match Bank Account
		if (!Check.equals(bankStatementLine.getBankAccount(), batch.getBankAccount()))
		{
			logger.info("Bank account not matching");
			return null;
		}

		// Match amount
		if (bankStatementLine.getTrxAmt().compareTo(batch.getPayAmt()) != 0)
		{
			logger.info("Amount not matching");
			return null;
		}

		if (batch.getDate() != null)
		{
			final int days = Math.abs(TimeUtil.getDaysBetween(bankStatementLine.getStatementDate(), batch.getDate()));
			if (days > dateToleranceDays)
			{
				logger.info("Date not matching");
				return null;
			}
		}

		final IBankStatementPaymentMatching matching = BankStatementPaymentMatching.builder()
				.setBankStatementLine(bankStatementLine)
				.setPayments(batch.getPayments())
				.setFailIfNotValid(false)
				.build();
		logger.info("Matched: {}", matching);
		return matching;
	}

	public BankStatementPaymentMatchingAutoBuilder setBankStatementLines(final List<IBankStatementLine> bankStatementLines)
	{
		this.bankStatementLines = ImmutableList.copyOf(bankStatementLines);
		return this;
	}

	public BankStatementPaymentMatchingAutoBuilder setPayments(final List<IPayment> payments)
	{
		Check.assumeNotNull(payments, "payments not null");
		this.payments = ImmutableList.copyOf(payments);

		return this;
	}

	public BankStatementPaymentMatchingAutoBuilder setDateToleranceDays(final int dateToleranceDays)
	{
		Check.assume(dateToleranceDays >= 0, "dateToleranceDays >= 0");
		this.dateToleranceDays = dateToleranceDays;
		return this;
	}

	private static final List<BatchWithPayments> createBatches(final List<IPayment> payments)
	{
		final List<BatchWithPayments> list = new ArrayList<>();
		final List<BatchWithPayments> noBatchesList = new ArrayList<>();

		final Map<ArrayKey, BatchWithPayments> key2batch = new LinkedHashMap<>();
		for (final IPayment payment : payments)
		{
			final BankAccount bankAccount = payment.getBankAccount();
			if (payment.getPaymentBatch() == null)
			{
				final IPaymentBatch batch = PaymentBatch.builder()
						.setId("C_Payment#"+payment.getC_Payment_ID())
						.setDate(payment.getDateTrx())
						.setName(payment.toString())
						.build();
				
				final BatchWithPayments batchWithPayments = new BatchWithPayments(batch, bankAccount);
				batchWithPayments.addPayment(payment);
				noBatchesList.add(batchWithPayments);
			}
			else
			{
				final IPaymentBatch batch = payment.getPaymentBatch();
				final ArrayKey key = Util.mkKey(batch, bankAccount);
				BatchWithPayments batchWithPayments = key2batch.get(key);
				if (batchWithPayments == null)
				{
					batchWithPayments = new BatchWithPayments(batch, bankAccount);
				}
				batchWithPayments.addPayment(payment);
				list.add(batchWithPayments);
				key2batch.put(key, batchWithPayments);
			}
		}

		// add the payments without batches last
		list.addAll(noBatchesList);

		return list;
	}

	private static final class BatchWithPayments
	{
		private final BankAccount bankAccount;
		private final Date date;

		private final List<IPayment> payments = new ArrayList<>();
		private BigDecimal payAmt = BigDecimal.ZERO;

		public BatchWithPayments(final IPaymentBatch paymentBatch, final BankAccount bankAccount)
		{
			super();
			date = paymentBatch == null ? null : paymentBatch.getDate();
			this.bankAccount = bankAccount;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public Date getDate()
		{
			return date;
		}

		public BankAccount getBankAccount()
		{
			return bankAccount;
		}

		public BigDecimal getPayAmt()
		{
			return payAmt;
		}

		public void addPayment(final IPayment payment)
		{
			Check.assumeNotNull(payment, "payment not null");
			payments.add(payment);

			payAmt = payAmt.add(payment.getPayAmt());
		}

		public List<IPayment> getPayments()
		{
			return payments;
		}

	}
}
