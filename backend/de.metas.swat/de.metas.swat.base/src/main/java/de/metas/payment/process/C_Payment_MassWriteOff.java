package de.metas.payment.process;

/*
 * #%L
 * de.metas.swat.base
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

import com.google.common.base.Stopwatch;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TrxRunnableAdapter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Mass write-off payments.
 * This process is meant to be used in AD_Scheduler.
 *
 * @implSpec task 09373
 */
public class C_Payment_MassWriteOff extends JavaProcess
{
	public static final AdMessageKey MSG_AllocationCreated = AdMessageKey.of("MSG_AllocationCreated");

	// services
	private final transient IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final transient IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	// parameters
	private static final String PARAM_OpenAmt = "OpenAmt";
	private BigDecimal p_OpenAmt = null;
	private static final String PARAM_PaymentDate = "PaymentDate";
	private Timestamp p_PaymentDateFrom = null;
	private Timestamp p_PaymentDateTo = null;
	private static final String PARAM_IsSOTrx = "IsSOTrx";
	private SOTrx p_SOTrx = null;
	//
	private static final String PARAM_AllocDateTrx = "DateTrx";
	private Instant p_AllocDateTrx = null;

	// status
	private final AtomicInteger counterSkipped = new AtomicInteger(0);
	private final AtomicInteger counterProcessed = new AtomicInteger(0);

	@Override
	protected void prepare()
	{
		final IRangeAwareParams params = getParameterAsIParams();
		p_OpenAmt = params.getParameterAsBigDecimal(PARAM_OpenAmt);
		p_PaymentDateFrom = params.getParameterAsTimestamp(PARAM_PaymentDate);
		p_PaymentDateTo = params.getParameter_ToAsTimestamp(PARAM_PaymentDate);
		p_SOTrx = SOTrx.ofNullableBoolean(params.getParameterAsBoolean(PARAM_IsSOTrx, null));
		p_AllocDateTrx = CoalesceUtil.coalesceSuppliers(
				() -> params.getParameterAsInstant(PARAM_AllocDateTrx),
				SystemTime::asInstant);
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		if (p_OpenAmt == null)
		{
			throw new FillMandatoryException(PARAM_OpenAmt);
		}

		final Iterator<I_C_Payment> iterator = retrievePayments();
		while (iterator.hasNext())
		{
			final I_C_Payment payment = iterator.next();
			paymentWriteOff(payment);
		}

		addLog("WriteOff " + counterProcessed.get() + " payments, Skipped " + counterSkipped.intValue() + " payments. Took " + stopwatch);
		return msgBL.getMsg(getCtx(), MSG_AllocationCreated, new Object[] { counterProcessed.intValue() });
	}

	private void paymentWriteOff(final I_C_Payment payment)
	{
		final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
		final Money paymentOpenAmt = Money.of(paymentDAO.getAvailableAmount(paymentId), CurrencyId.ofRepoId(payment.getC_Currency_ID()));

		// Skip this payment if the open amount is above given limit
		if (paymentOpenAmt.toBigDecimal().abs().compareTo(p_OpenAmt.abs()) > 0)
		{
			counterSkipped.incrementAndGet();
			return;
		}
		// also skip the payment if there is nothing allocated yet! We only want to complete *partial* allocations
		if (paymentDAO.getAllocatedAmt(payment).signum() == 0)
		{
			counterSkipped.incrementAndGet();
			return;
		}

		@SuppressWarnings("UnnecessaryLocalVariable")
		final Money amtToWriteOff = paymentOpenAmt;

		final String writeOffDescription = getProcessInfo().getTitle();

		trxManager.assertThreadInheritedTrxNotExists();
		trxManager.runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				final I_C_AllocationHdr allocationHdr = paymentBL.paymentWriteOff(payment, amtToWriteOff, p_AllocDateTrx, writeOffDescription);

				// Make sure the payment was fully allocated
				InterfaceWrapperHelper.refresh(payment, localTrxName);
				Check.errorIf(!payment.isAllocated(), "C_Payment {} still has isAllocated='N' after having created {} with paymentWriteOffAmt={}", payment, allocationHdr, amtToWriteOff);

				// Log the success and increase the counter
				addLog("@Processed@: @C_Payment_ID@ " + payment.getDocumentNo() + "; @PaymentWriteOffAmt@=" + amtToWriteOff);
				counterProcessed.incrementAndGet();
			}

			@Override
			public boolean doCatch(final Throwable e)
			{
				final String errmsg = "@Error@: @C_Payment_ID@ " + payment.getDocumentNo() + ": " + e.getLocalizedMessage();
				addLog(errmsg);
				log.error(errmsg, e);
				return true; // rollback
			}
		});
	}

	private Iterator<I_C_Payment> retrievePayments()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		//
		// Create the selection which we might need to update
		final IQueryBuilder<I_C_Payment> queryBuilder = queryBL
				.createQueryBuilder(I_C_Payment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Payment.COLUMNNAME_AD_Client_ID, getClientId())
				.addEqualsFilter(I_C_Payment.COLUMNNAME_IsAllocated, false);

		if (!getProcessInfo().isInvokedByScheduler())
		{
			// user selection..if any
			final IQueryFilter<I_C_Payment> userSelectionFilter = getProcessInfo().getQueryFilterOrElseFalse();
			queryBuilder.filter(userSelectionFilter);
		}

		if (p_SOTrx != null)
		{
			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_IsReceipt, p_SOTrx.toBoolean());
		}

		if (p_PaymentDateFrom != null)
		{
			queryBuilder.addCompareFilter(I_C_Payment.COLUMNNAME_DateTrx, Operator.GREATER_OR_EQUAL, p_PaymentDateFrom);
		}
		if (p_PaymentDateTo != null)
		{
			queryBuilder.addCompareFilter(I_C_Payment.COLUMNNAME_DateTrx, Operator.LESS_OR_EQUAL, p_PaymentDateTo);
		}

		final IQuery<I_C_Payment> query = queryBuilder
				.orderBy(I_C_Payment.COLUMNNAME_C_Payment_ID)
				.create();
		addLog("Using query: " + query);

		final int count = query.count();
		if (count > 0)
		{
			final Iterator<I_C_Payment> iterator = query.iterate(I_C_Payment.class);
			addLog("Found " + count + " payments to evaluate. Took " + stopwatch);
			return iterator;
		}
		else
		{
			addLog("No payments found. Took " + stopwatch);
			return Collections.emptyIterator();
		}
	}
}
