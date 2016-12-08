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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.process.RunOutOfTrx;
import de.metas.process.JavaProcess;

/**
 * task 09373
 */
public class C_Payment_DiscountAllocation_Process extends JavaProcess
{
	public static final String MSG_AllocationCreated = "MSG_AllocationCreated";

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
	private Boolean p_isSOTrx = null;
	//
	private static final String PARAM_AllocDateTrx = "DateTrx";
	private Timestamp p_AllocDateTrx = null;

	// status
	private final AtomicInteger counterSkipped = new AtomicInteger(0);
	private final AtomicInteger counterProcessed = new AtomicInteger(0);

	@Override
	protected void prepare()
	{
		final IRangeAwareParams params = getParameterAsIParams();
		p_OpenAmt = params.getParameterAsBigDecimal(PARAM_OpenAmt);
		if(p_OpenAmt==null)
		{
			throw new FillMandatoryException(PARAM_OpenAmt);
		}

		p_PaymentDateFrom = params.getParameterAsTimestamp(PARAM_PaymentDate);
		p_PaymentDateTo = params.getParameter_ToAsTimestamp(PARAM_PaymentDate);
		p_isSOTrx = params.getParameterAsBool(PARAM_IsSOTrx);

		p_AllocDateTrx = params.getParameterAsTimestamp(PARAM_AllocDateTrx);
		if (p_AllocDateTrx == null)
		{
			p_AllocDateTrx = Env.getDate(getCtx());
		}
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final Iterator<I_C_Payment> iterator = createIterator();
		if (!iterator.hasNext())
		{
			addLog("@NoSelection@");
		}

		while (iterator.hasNext())
		{
			final I_C_Payment payment = iterator.next();
			paymentWriteOff(payment);
		}

		final String message = msgBL.getMsg(getCtx(), MSG_AllocationCreated, new Object[] { counterProcessed.intValue() });
		return message;
	}

	private final void paymentWriteOff(final I_C_Payment payment)
	{
		final BigDecimal paymentOpenAmt = paymentDAO.getAvailableAmount(payment);

		// Skip this payment if the open amount is above given limit
		if (paymentOpenAmt.abs().compareTo(p_OpenAmt.abs()) > 0)
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

		final BigDecimal amtToWriteOff = paymentOpenAmt;

		trxManager.run(new TrxRunnableAdapter()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				final I_C_AllocationHdr allocationHdr = paymentBL.paymentWriteOff(payment, amtToWriteOff, p_AllocDateTrx);

				// Make sure the payment was fully allocated
				InterfaceWrapperHelper.refresh(payment, localTrxName);
				Check.errorIf(!payment.isAllocated(), "C_Payment {} still has isAllocated='N' after having created {} with paymentWriteOffAmt={}", payment, allocationHdr, amtToWriteOff);

				// Log the success and increase the counter
				addLog("@Processed@: @C_Payment_ID@ " + payment.getDocumentNo() + "; @PaymentWriteOffAmt@=" + amtToWriteOff);
				counterProcessed.incrementAndGet();
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				final String errmsg = "@Error@: @C_Payment_ID@ " + payment.getDocumentNo() + ": " + e.getLocalizedMessage();
				addLog(errmsg);
				log.error(errmsg, e);
				return true; // rollback
			}
		});
	}

	private Iterator<I_C_Payment> createIterator()
	{
		// user selection..if any
		final IQueryFilter<I_C_Payment> userSelectionFilter = getProcessInfo().getQueryFilter();

		//
		// Create the selection which we might need to update
		final IQueryBuilder<I_C_Payment> queryBuilder = queryBL
				.createQueryBuilder(I_C_Payment.class, this)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Payment.COLUMNNAME_IsAllocated, false);

		queryBuilder.filter(userSelectionFilter);

		if (p_isSOTrx != null)
		{
			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_IsReceipt, p_isSOTrx);
		}
		if (p_PaymentDateFrom != null)
		{
			queryBuilder.addCompareFilter(I_C_Payment.COLUMNNAME_DateTrx, Operator.GREATER_OR_EQUAL, p_PaymentDateFrom);
		}
		if (p_PaymentDateTo != null)
		{
			queryBuilder.addCompareFilter(I_C_Payment.COLUMNNAME_DateTrx, Operator.LESS_OR_EQUAL, p_PaymentDateTo);
		}

		return queryBuilder
				.orderBy().addColumn(I_C_Payment.COLUMNNAME_C_Payment_ID)
				.endOrderBy()
				.create()
				.iterate(I_C_Payment.class);
	}
}
