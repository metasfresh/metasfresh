package de.metas.banking.payment.paymentallocation.service;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;

import de.metas.allocation.api.IAllocationBL;
import de.metas.banking.payment.paymentallocation.model.IPaymentRow;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;

/**
 * Creates outbound payments to match the given inbound payments.
 * 
 * @author tsa
 *
 */
public class Inbound2OutboundPaymentAllocationBuilder
{
	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient IAllocationBL allocationBL = Services.get(IAllocationBL.class);

	// Parameters
	private Date _date = null;
	private Properties _ctx;
	private final List<PaymentToAllocate> paymentsToAllocate = new ArrayList<>();

	// Statistics
	private int countPaymentsProcessed = 0;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * 
	 * @return true if at least one payment was created
	 */
	public boolean build()
	{
		if (paymentsToAllocate.isEmpty())
		{
			throw new AdempiereException("@Select@ @C_Payment_ID@");
		}

		trxManager.run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				buildInTrx();
			}
		});

		return countPaymentsProcessed > 0;
	}

	private final void buildInTrx()
	{
		final Properties ctx = getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		for (final PaymentToAllocate paymentToAllocate : paymentsToAllocate)
		{
			final I_C_Payment paymentIn = InterfaceWrapperHelper.create(ctx, paymentToAllocate.getPaymentId(), I_C_Payment.class, trxName);
			createOutboundPayment(paymentIn, paymentToAllocate.getOpenAmt());
		}
	}

	public Inbound2OutboundPaymentAllocationBuilder setCtx(final Properties ctx)
	{
		this._ctx = ctx;
		return this;
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return this._ctx;
	}

	public Inbound2OutboundPaymentAllocationBuilder setDate(final Date date)
	{
		this._date = date;
		return this;
	}

	private final Date getDate()
	{
		// null is allowed
		return _date;
	}

	public Inbound2OutboundPaymentAllocationBuilder addInboundPayment(final IPaymentRow row)
	{
		Check.assumeNotNull(row, "row not null");

		final int paymentId = row.getC_Payment_ID();
		Check.assume(paymentId > 0, "paymentId > 0: {}", row);

		// Get inbound payment's open amount.
		// NOTE: the amount shall NOT be AP adjusted because the BL depends on it.
		final BigDecimal openAmt = row.getOpenAmtConv();

		final PaymentToAllocate paymentToAllocate = new PaymentToAllocate(paymentId, openAmt);
		paymentsToAllocate.add(paymentToAllocate);
		return this;
	}

	public Inbound2OutboundPaymentAllocationBuilder addInboundPayments(final Collection<IPaymentRow> rows)
	{
		for (final IPaymentRow row : rows)
		{
			addInboundPayment(row);
		}

		return this;
	}

	private I_C_Payment createOutboundPayment(final I_C_Payment paymentIn, final BigDecimal openAmt)
	{
		// Skip payments which are not inbound (i.e. not receipts)
		if (!paymentIn.isReceipt())
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(paymentIn);
		final String trxName = InterfaceWrapperHelper.getTrxName(paymentIn);

		final Timestamp date = TimeUtil.asTimestamp(getDate());

		//
		// Create outbound payment
		final I_C_Payment paymentOut = InterfaceWrapperHelper.create(ctx, I_C_Payment.class, trxName);
		InterfaceWrapperHelper.copyValues(paymentIn, paymentOut);
		paymentOut.setDocumentNo(""); // reset documentNo
		paymentOut.setDateTrx(date);
		paymentOut.setDateAcct(date);
		paymentOut.setC_DocType(null); // to be updated
		paymentOut.setIsReceipt(false);
		paymentOut.setPayAmt(openAmt);
		paymentOut.setDiscountAmt(Env.ZERO);
		paymentOut.setWriteOffAmt(Env.ZERO);
		paymentOut.setOverUnderAmt(Env.ZERO);
		paymentOut.setC_Invoice_ID(-1);
		paymentOut.setC_Order_ID(-1);
		paymentOut.setDocStatus(IDocument.STATUS_Drafted);
		paymentOut.setDocAction(IDocument.ACTION_Complete);
		InterfaceWrapperHelper.save(paymentOut);
		Check.assume(!paymentOut.isReceipt(), "payment is not receipt: {}", paymentOut);
		docActionBL.processEx(paymentOut, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		
		
		final Timestamp dateAcct = TimeUtil.max(paymentIn.getDateAcct(), paymentOut.getDateAcct());
		final Timestamp dateTrx = TimeUtil.max(paymentIn.getDateTrx(), paymentOut.getDateTrx());

		//
		// Allocate the inbound payment against outbound payment
		allocationBL.newBuilder(new PlainContextAware(ctx, trxName))
				.setAD_Org_ID(paymentOut.getAD_Org_ID())
				.setC_Currency_ID(paymentOut.getC_Currency_ID())
				.setDateAcct(dateAcct)
				.setDateTrx(dateTrx)
				.setManual(false) // not manual
				//
				// Allocate: Inbound receipt
				.addLine()
				.setC_BPartner_ID(paymentIn.getC_BPartner_ID())
				.setC_Payment_ID(paymentIn.getC_Payment_ID())
				.setAmount(paymentIn.getPayAmt())
				.lineDone()
				//
				// Allocate: Outbound payment
				.addLine()
				.setC_BPartner_ID(paymentOut.getC_BPartner_ID())
				.setC_Payment_ID(paymentOut.getC_Payment_ID())
				.setAmount(paymentOut.getPayAmt().negate())
				.lineDone()
				//
				// Create and process
				.createAndComplete();

		countPaymentsProcessed++;

		return paymentOut;
	}

	private static final class PaymentToAllocate
	{
		private final int paymentId;
		private final BigDecimal openAmt;

		public PaymentToAllocate(final int paymentId, final BigDecimal openAmt)
		{
			super();
			this.paymentId = paymentId;
			this.openAmt = openAmt;
		}

		public int getPaymentId()
		{
			return paymentId;
		}

		public BigDecimal getOpenAmt()
		{
			return openAmt;
		}
	}

}
