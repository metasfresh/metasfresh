/**
 * 
 */
package de.metas.payment.api.impl;

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


import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_Payment;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.allocation.api.IAllocationBL;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.interfaces.I_C_BP_Group;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * @author cg
 * 
 */
public class PaymentBL implements IPaymentBL
{

	Logger log = LogManager.getLogger(getClass());
	
	private final transient IAllocationBL allocationBL = Services.get(IAllocationBL.class);

	/**
	 * Get Invoice Currency
	 * 
	 * @param payment
	 * @return
	 */
	private int fetchC_Currency_Invoice_ID(final I_C_Payment payment)
	{
		final int C_Invoice_ID = payment.getC_Invoice_ID();
		final int C_Order_ID = payment.getC_Order_ID();

		int C_Currency_Invoice_ID = 0;

		if (C_Invoice_ID > 0)
		{
			C_Currency_Invoice_ID = payment.getC_Invoice().getC_Currency_ID();
		} // get Invoice Info
		else if (C_Order_ID > 0)
		{
			C_Currency_Invoice_ID = payment.getC_Order().getC_Currency_ID();
		}
		log.debug("C_Currency_Invoice_ID = " + C_Currency_Invoice_ID + ", C_Invoice_ID=" + C_Invoice_ID);

		return C_Currency_Invoice_ID;
	}

	/**
	 * Get Open Amount invoice
	 * 
	 * @param payment
	 * @param creditMemoAdjusted True if we want to get absolute values for Credit Memos
	 * @return
	 */
	private BigDecimal fetchOpenAmount(final I_C_Payment payment, final boolean creditMemoAdjusted)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);

		BigDecimal InvoiceOpenAmt = Env.ZERO;

		final int C_Invoice_ID = payment.getC_Invoice_ID();
		final int C_Order_ID = payment.getC_Order_ID();

		if (C_Invoice_ID > 0)
		{
			InvoiceOpenAmt = Services.get(IPaymentDAO.class).getInvoiceOpenAmount(payment, creditMemoAdjusted);
		}
		else if (C_Order_ID > 0)
		{
			final BigDecimal grandTotal = payment.getC_Order().getGrandTotal();
			final BigDecimal allocated = Services.get(IPrepayOrderBL.class).retrieveAllocatedAmt(ctx, C_Order_ID, ITrx.TRXNAME_None);
			InvoiceOpenAmt = grandTotal.subtract(allocated);
		}

		log.debug("Open=" + InvoiceOpenAmt + ", C_Invoice_ID=" + C_Invoice_ID);

		final int C_Currency_ID = payment.getC_Currency_ID();
		final I_C_Currency currency = payment.getC_Currency();
		final int C_Currency_Invoice_ID = fetchC_Currency_Invoice_ID(payment);
		final Timestamp ConvDate = payment.getDateTrx();
		final int C_ConversionType_ID = payment.getC_ConversionType_ID();
		final int AD_Client_ID = payment.getAD_Client_ID();
		final int AD_Org_ID = payment.getAD_Org_ID();

		// Get Currency Rate
		BigDecimal CurrencyRate = Env.ONE;
		if ((C_Currency_ID > 0 && C_Currency_Invoice_ID > 0 && C_Currency_ID != C_Currency_Invoice_ID))
		{
			log.debug("InvCurrency=" + C_Currency_Invoice_ID + ", PayCurrency="
					+ C_Currency_ID + ", Date=" + ConvDate + ", Type="
					+ C_ConversionType_ID);

			CurrencyRate = Services.get(ICurrencyBL.class).getRate(C_Currency_Invoice_ID, C_Currency_ID, ConvDate, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
			if (CurrencyRate == null || CurrencyRate.compareTo(Env.ZERO) == 0)
			{
				if (C_Currency_Invoice_ID == 0)
				{
					return InvoiceOpenAmt;
				}

				throw new AdempiereException("NoCurrencyConversion");
			}

			//
			InvoiceOpenAmt = InvoiceOpenAmt.multiply(CurrencyRate).setScale(
					currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);

			log.debug("Rate=" + CurrencyRate + ", InvoiceOpenAmt="
					+ InvoiceOpenAmt);
		}

		return InvoiceOpenAmt;
	}

	@Override
	public void updateAmounts(final I_C_Payment payment, final String colName, boolean creditMemoAdjusted)
	{
		final int C_Invoice_ID = payment.getC_Invoice_ID();
		final int C_Order_ID = payment.getC_Order_ID();

		// New Payment
		if (payment.getC_Payment_ID() <= 0 && payment.getC_BPartner_ID() <= 0 && C_Invoice_ID <= 0)
		{
			return;
		}

		// Changed Column IsOverUnderPayment
		if (I_C_Payment.COLUMNNAME_IsOverUnderPayment.equals(colName))
		{
			onIsOverUnderPaymentChange(payment, creditMemoAdjusted);
		}
		// set all amount on zero if no order nor invoice is set
		else if (C_Invoice_ID <= 0 && C_Order_ID <= 0)
		{
			if (payment.getDiscountAmt().signum() != 0)
			{
				payment.setDiscountAmt(Env.ZERO);
			}
			if (payment.getWriteOffAmt().signum() != 0)
			{
				payment.setWriteOffAmt(Env.ZERO);
			}
			if (payment.getOverUnderAmt().signum() != 0)
			{
				payment.setOverUnderAmt(Env.ZERO);
			}
		}
		// Changed Column C_Currency_ID or C_ConversionType_ID
		else if (I_C_Payment.COLUMNNAME_C_Currency_ID.equals(colName) || I_C_Payment.COLUMNNAME_C_ConversionType_ID.equals(colName))
		{
			onCurrencyChange(payment);
		}
		// Changed Column PayAmt
		else if (I_C_Payment.COLUMNNAME_PayAmt.equals(colName))
		{
			onPayAmtChange(payment, true);
		}
		// calculate PayAmt
		else if (X_C_Payment.DOCSTATUS_Drafted.equals(payment.getDocStatus()))
		{
			final BigDecimal InvoiceOpenAmt = fetchOpenAmount(payment, creditMemoAdjusted);
			final BigDecimal DiscountAmt = payment.getDiscountAmt();
			final BigDecimal WriteOffAmt = payment.getWriteOffAmt();
			final BigDecimal OverUnderAmt = payment.getOverUnderAmt();

			final BigDecimal PayAmt = InvoiceOpenAmt.subtract(DiscountAmt).subtract(WriteOffAmt).subtract(OverUnderAmt);
			payment.setPayAmt(PayAmt);
		}

	}

	@Override
	public void onIsOverUnderPaymentChange(final I_C_Payment payment, boolean creditMemoAdjusted)
	{
		payment.setOverUnderAmt(Env.ZERO);

		if (X_C_Payment.DOCSTATUS_Drafted.equals(payment.getDocStatus()))
		{
			final BigDecimal InvoiceOpenAmt = fetchOpenAmount(payment, creditMemoAdjusted);
			final BigDecimal DiscountAmt = payment.getDiscountAmt();
			final BigDecimal PayAmt = payment.getPayAmt();

			if (payment.isOverUnderPayment())
			{
				final BigDecimal OverUnderAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt);
				payment.setWriteOffAmt(Env.ZERO);
				payment.setOverUnderAmt(OverUnderAmt);
			}
			else
			{
				final BigDecimal WriteOffAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt);
				payment.setWriteOffAmt(WriteOffAmt);
				payment.setOverUnderAmt(Env.ZERO);
			}
		}
	}

	@Override
	public void onCurrencyChange(final I_C_Payment payment)
	{
		final int C_Invoice_ID = payment.getC_Invoice_ID();
		final int C_Order_ID = payment.getC_Order_ID();
		// Get Currency Info
		final int C_Currency_ID = payment.getC_Currency_ID();
		final I_C_Currency currency = payment.getC_Currency();
		final int C_Currency_Invoice_ID = fetchC_Currency_Invoice_ID(payment);
		final Timestamp ConvDate = payment.getDateTrx();
		final int C_ConversionType_ID = payment.getC_ConversionType_ID();
		final int AD_Client_ID = payment.getAD_Client_ID();
		final int AD_Org_ID = payment.getAD_Org_ID();

		// Get Currency Rate
		BigDecimal CurrencyRate = Env.ONE;
		if ((C_Currency_ID > 0 && C_Currency_Invoice_ID > 0 && C_Currency_ID != C_Currency_Invoice_ID))
		{
			log.debug("InvCurrency={}, PayCurrency={}, Date={}, Type={}"
					, new Object[] { C_Currency_Invoice_ID, C_Currency_ID, C_Currency_ID, ConvDate, C_ConversionType_ID });

			final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
			CurrencyRate = currencyBL.getRate(C_Currency_Invoice_ID, C_Currency_ID, ConvDate, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
			if (Check.isEmpty(CurrencyRate))
			{
				if (C_Currency_Invoice_ID <= 0)
				{
					return; // no error message when no invoice is selected
				}

				final ICurrencyConversionContext conversionCtx = currencyBL.createCurrencyConversionContext(ConvDate, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
				throw new NoCurrencyRateFoundException(conversionCtx, C_Currency_Invoice_ID, C_Currency_ID);
			}

		}

		BigDecimal PayAmt = payment.getPayAmt();
		BigDecimal DiscountAmt = payment.getDiscountAmt();
		BigDecimal WriteOffAmt = payment.getWriteOffAmt();
		BigDecimal OverUnderAmt = payment.getOverUnderAmt();

		PayAmt = PayAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		payment.setPayAmt(PayAmt);

		DiscountAmt = DiscountAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		payment.setDiscountAmt(DiscountAmt);

		WriteOffAmt = WriteOffAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		payment.setWriteOffAmt(WriteOffAmt);

		OverUnderAmt = OverUnderAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		payment.setOverUnderAmt(OverUnderAmt);

		// No Invoice or Order - Set Discount, Witeoff, Under/Over to 0
		if (C_Invoice_ID <= 0 && C_Order_ID <= 0)
		{
			if (Env.ZERO.compareTo(DiscountAmt) != 0)
			{
				payment.setDiscountAmt(Env.ZERO);
			}

			if (Env.ZERO.compareTo(WriteOffAmt) != 0)
			{
				payment.setWriteOffAmt(Env.ZERO);
			}
			if (Env.ZERO.compareTo(OverUnderAmt) != 0)
			{
				payment.setOverUnderAmt(Env.ZERO);
			}
		}

	}

	@Override
	public void onPayAmtChange(final I_C_Payment payment, boolean creditMemoAdjusted)
	{
		if (X_C_Payment.DOCSTATUS_Drafted.equals(payment.getDocStatus()))
		{
			final BigDecimal DiscountAmt = payment.getDiscountAmt();
			final BigDecimal PayAmt = payment.getPayAmt();
			final BigDecimal InvoiceOpenAmt = fetchOpenAmount(payment, creditMemoAdjusted);
			BigDecimal WriteOffAmt = payment.getWriteOffAmt();
			BigDecimal OverUnderAmt = payment.getOverUnderAmt();

			if (payment.isOverUnderPayment())
			{
				OverUnderAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt).subtract(WriteOffAmt);
				payment.setOverUnderAmt(OverUnderAmt);
			}

			WriteOffAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt).subtract(OverUnderAmt);
			payment.setWriteOffAmt(WriteOffAmt);

		}

	}

	@Override
	public <T extends DefaultPaymentBuilder> T newBuilder(Object ctxProvider, Class<T> implClazz)
	{
		try
		{
			final Constructor<T> c = implClazz.getConstructor(Object.class);
			final T builder = c.newInstance(ctxProvider);
			return builder;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Unable to create new IAllocationBuilder with class " + implClazz, e);
		}
	}

	@Override
	public DefaultPaymentBuilder newBuilder(Object ctxProvider)
	{
		return newBuilder(ctxProvider, DefaultPaymentBuilder.class);
	}

	@Override
	public String getPaymentRuleForBPartner(final I_C_BPartner bPartner)
	{
		Check.assumeNotNull(bPartner, "BPartner is not null");

		if (!Check.isEmpty(bPartner.getPaymentRule()))
		{
			return bPartner.getPaymentRule();
		}
		//
		// No payment rule in BP. Fallback to group.
		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.create(bPartner.getC_BP_Group(), I_C_BP_Group.class);

		if (null != bpGroup)
		{
			return bpGroup.getPaymentRule();
		}

		return null;
	}

	@Override
	public boolean isMatchInvoice(final I_C_Payment payment, final I_C_Invoice invoice)
	{
		final List<I_C_AllocationLine> allocations = Services.get(IPaymentDAO.class).retrieveAllocationLines(payment);
		final List<I_C_Invoice> invoices = new ArrayList<I_C_Invoice>();
		for (final I_C_AllocationLine alloc : allocations)
		{
			invoices.add(alloc.getC_Invoice());
		}

		for (final I_C_Invoice inv : invoices)
		{
			if (inv.getC_Invoice_ID() == invoice.getC_Invoice_ID())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean testAllocation(final I_C_Payment payment)
	{
		//
		BigDecimal alloc = Services.get(IPaymentDAO.class).getAllocatedAmt(payment);
		final boolean hasAllocations = alloc != null; // metas: tsa: 01955
		if (alloc == null)
		{
			alloc = Env.ZERO;
		}

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		BigDecimal total = payment.getPayAmt();

		// metas: tsa: begin: 01955:
		// If is an zero payment and it has no allocations and the AutoPayZeroAmt flag is not set
		// then don't touch the payment
		if (total.signum() == 0 && !hasAllocations
				&& !sysConfigBL.getBooleanValue("org.compiere.model.MInvoice.AutoPayZeroAmt", true, payment.getAD_Client_ID()))
		{
			// don't touch the IsAllocated flag, return not changed
			return false;
		}
		// metas: tsa: end: 01955
		if (!payment.isReceipt())
			total = total.negate();
		boolean test = total.compareTo(alloc) == 0;
		boolean change = test != payment.isAllocated();
		if (change)
		{
			payment.setIsAllocated(test);
		}

		log.debug("Allocated=" + test
				+ " (" + alloc + "=" + total + ")");
		return change;
	}	// testAllocation
	@Override
	public boolean isCashTrx(I_C_Payment payment)
	{
		return X_C_Payment.TENDERTYPE_Cash.equals(payment.getTenderType());
	}

	@Override
	public I_C_AllocationHdr paymentWriteOff(final I_C_Payment payment, final BigDecimal writeOffAmt, final Date date)
	{
		Check.assumeNotNull(payment, "payment not null");
		Check.assume(writeOffAmt != null && writeOffAmt.signum() != 0, "WriteOffAmt != 0 but it was {}", writeOffAmt);
		Check.assumeNotNull(date, "date not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final Timestamp dateTS = TimeUtil.asTimestamp(date);

		final Mutable<I_C_AllocationHdr> allocHdrRef = new Mutable<>();
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				final I_C_AllocationHdr allocHdr = allocationBL.newBuilder(PlainContextAware.newWithThreadInheritedTrx(ctx))
						.setAD_Org_ID(payment.getAD_Org_ID())
						.setC_Currency_ID(payment.getC_Currency_ID())
						.setDateAcct(dateTS)
						.setDateTrx(dateTS)
						//
						.addLine()
						.setAD_Org_ID(payment.getAD_Org_ID())
						.setC_BPartner_ID(payment.getC_BPartner_ID())
						.setC_Payment_ID(payment.getC_Payment_ID())
						.setPaymentWriteOffAmt(writeOffAmt)
						.lineDone()
						//
						.createAndComplete();
				allocHdrRef.setValue(allocHdr);
			}
		});

		return allocHdrRef.getValue();
	}
}
