package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.util.Env;
import org.junit.Assert;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_Order;
import de.metas.currency.ICurrencyDAO;
import de.metas.interfaces.I_C_BPartner;

public class PaymentHelper
{

	private final IHelper helper;

	private I_C_Invoice invoice;

	private I_C_Order order;

	private int orgId = -1;
	private boolean isReceipt = true;
	private I_C_BPartner bpartner = null;
	private String tenderType;
	private boolean expectInvoicePaid = false;
	private MPayment payment;
	private List<PaymentAllocateVO> paymentAllocates = new ArrayList<PaymentHelper.PaymentAllocateVO>();
	private int currencyId;
	private BigDecimal payAmt;
	private BigDecimal discountAmt;
	private BigDecimal writeOffAmt;
	private BigDecimal overUnderAmt;
	private Timestamp date;
	private boolean completePayment = true;

	private static class PaymentAllocateVO
	{
		private int invoiceId;
		private BigDecimal invoiceAmt;
		private BigDecimal amount;
		private BigDecimal discountAmt;
		private BigDecimal overUnderAmt;
		private BigDecimal writeOffAmt;
	}

	public PaymentHelper(IHelper helper)
	{
		this.helper = helper;
		this.currencyId = Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(helper.getCtx(), helper.getCurrencyCode()).getC_Currency_ID();
	}

	public I_C_Payment quickCreatePayment(I_C_Invoice invoice)
	{
		return quickCreatePayment(invoice, true);
	}

	public I_C_Payment quickCreatePayment(I_C_Invoice invoice, boolean completePayment)
	{
		return this.setC_Invoice(invoice)
				.setExpectInvoicePaid(true)
				.setCompletePayment(completePayment)
				.createPayment();
	}

	public PaymentHelper setC_Invoice(I_C_Invoice invoice)
	{
		Assert.assertNull(payment);

		this.invoice = invoice;
		this.orgId = invoice.getAD_Org_ID();
		this.isReceipt = invoice.isSOTrx();
		this.bpartner = InterfaceWrapperHelper.create(invoice.getC_BPartner(), I_C_BPartner.class);
		this.currencyId = invoice.getC_Currency_ID();
		this.payAmt = invoice.getGrandTotal();
		setExpectInvoicePaid(true);
		return this;
	}

	/**
	 * Set the order, plus orgId, isReceipts, bpartner, currencyId and payamt.
	 * 
	 * @param order
	 * @return
	 */
	public PaymentHelper setC_Order(I_C_Order order)
	{
		Assert.assertNull(payment);

		this.order = order;
		this.orgId = order.getAD_Org_ID();
		this.isReceipt = order.isSOTrx();
		this.bpartner = InterfaceWrapperHelper.create(order.getBill_BPartner(), I_C_BPartner.class);
		this.currencyId = order.getC_Currency_ID();
		this.payAmt = order.getGrandTotal();

		return this;
	}

	public PaymentHelper setC_BPartner(I_C_BPartner bpartner)
	{
		Assert.assertNull(payment);

		this.bpartner = bpartner;
		return this;
	}

	public PaymentHelper setIsReceipt(boolean isReceipt)
	{
		Assert.assertNull(payment);

		this.isReceipt = isReceipt;
		return this;
	}

	public PaymentHelper setC_Currency_ID(int currenyId)
	{
		Assert.assertNull(payment);

		this.currencyId = currenyId;
		return this;
	}

	public PaymentHelper setAD_Org_ID(int AD_Org_ID)
	{
		Assert.assertNull(payment);

		this.orgId = AD_Org_ID;
		return this;
	}

	public PaymentHelper setTenderType(String tenderType)
	{
		Assert.assertNull(payment);

		this.tenderType = tenderType;
		return this;
	}

	public PaymentHelper setExpectInvoicePaid(boolean expectInvoicePaid)
	{
		this.expectInvoicePaid = expectInvoicePaid;
		return this;
	}

	public PaymentHelper setPayAmt(BigDecimal payAmt)
	{
		this.payAmt = payAmt;
		return this;
	}

	public PaymentHelper setDiscountAmt(BigDecimal discountAmt)
	{
		this.discountAmt = discountAmt;
		return this;
	}

	public PaymentHelper setWriteOffAmt(BigDecimal writeOffAmt)
	{
		this.writeOffAmt = writeOffAmt;
		return this;
	}

	public PaymentHelper setOverUnderAmt(BigDecimal overUnderAmt)
	{
		this.overUnderAmt = overUnderAmt;
		return this;
	}

	public PaymentHelper setDate(Timestamp date)
	{
		this.date = date;
		return this;
	}

	public PaymentHelper setCompletePayment(boolean completePayment)
	{
		this.completePayment = completePayment;
		return this;
	}

	public PaymentHelper addPaymentAllocate(I_C_Invoice invoice)
	{
		Assert.assertNull(payment);

		final PaymentAllocateVO pa = new PaymentAllocateVO();
		pa.invoiceId = invoice.getC_Invoice_ID();
		pa.invoiceAmt = invoice.getGrandTotal();
		pa.amount = invoice.getGrandTotal(); // Pay amount
		pa.discountAmt = Env.ZERO;
		pa.overUnderAmt = Env.ZERO;
		pa.writeOffAmt = Env.ZERO;
		paymentAllocates.add(pa);

		return this;
	}

	public I_C_Payment createPayment()
	{
		Assert.assertNull(payment);

		if (bpartner == null)
			bpartner = helper.mkBPartnerHelper().getC_BPartner(helper.getConfig());
		// BPOpenBalanceValidator.get().recordState(bpartner);

		final Properties ctx = helper.getCtx();
		final String trxName = helper.getTrxName();

		final MPayment payment = new MPayment(ctx, 0, trxName);
		payment.setAD_Org_ID(orgId);
		payment.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		payment.setIsReceipt(isReceipt);

		if (tenderType != null)
			payment.setTenderType(tenderType);

		if (invoice != null)
			payment.setC_Invoice_ID(invoice.getC_Invoice_ID());

		if (order != null)
			payment.setC_Order_ID(order.getC_Order_ID());

		payment.setAmount(currencyId, payAmt);
		if (discountAmt != null)
			payment.setDiscountAmt(discountAmt);

		if (writeOffAmt != null)
			payment.setWriteOffAmt(writeOffAmt);

		if (overUnderAmt != null)
		{
			payment.setOverUnderAmt(overUnderAmt);
			payment.setIsOverUnderPayment(true);
		}

		if (date != null)
		{
			payment.setDateAcct(date);
			payment.setDateTrx(date);
		}

		InterfaceWrapperHelper.save(payment);

		BigDecimal paAmount = Env.ZERO;
		for (final PaymentAllocateVO vo : paymentAllocates)
		{
			final MPaymentAllocate pa = new MPaymentAllocate(ctx, 0, trxName);
			pa.setAD_Org_ID(payment.getAD_Org_ID());
			pa.setC_Payment_ID(payment.getC_Payment_ID());
			pa.setC_Invoice_ID(vo.invoiceId);
			pa.setInvoiceAmt(vo.invoiceAmt);
			pa.setAmount(vo.amount);
			pa.setDiscountAmt(vo.discountAmt);
			pa.setOverUnderAmt(vo.overUnderAmt);
			pa.setWriteOffAmt(vo.writeOffAmt);
			InterfaceWrapperHelper.save(pa);
			paAmount = paAmount.add(vo.amount);
		}

		InterfaceWrapperHelper.refresh(payment);

		if (!paymentAllocates.isEmpty())
		{
			assertThat("Payment Amount not match", paAmount, comparesEqualTo(payment.getPayAmt()));
		}

		this.payment = payment;
		if (completePayment)
		{
			helper.processComplete(payment);
			// BPOpenBalanceValidator.get().afterPaymentComplete(payment);
			afterComplete_validateInvoice();
		}
		
		return InterfaceWrapperHelper.create(payment, I_C_Payment.class);
	}

	private void afterComplete_validateInvoice()
	{
		if (invoice == null)
			return;
		//
		// Check Payment
		Assert.assertTrue("Payment should be allocated - " + payment, payment.isAllocated());

		//
		// Check Allocation
		final MAllocationHdr[] allocations = MAllocationHdr.getOfPayment(payment.getCtx(), payment.getC_Payment_ID(), payment.get_TrxName());
		Assert.assertNotNull("No allocations were created", allocations);
		Assert.assertEquals("Only one allocation should be created", 1, allocations.length);

		//
		// Check invoice paid
		if (expectInvoicePaid)
		{
			InterfaceWrapperHelper.refresh(invoice);
			assertThat("Open amount is not zero", Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice), comparesEqualTo(Env.ZERO));
			Assert.assertTrue("Invoice should be paid - " + invoice + "(id=" + invoice.getC_Invoice_ID() + ")", invoice.isPaid());
		}
	}
}
