package de.metas.payment.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.compiere.util.Util;
import org.springframework.stereotype.Component;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.payment.api.IPaymentBL;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component("de.metas.payment.api.impl.C_Payment")
@Callout(I_C_Payment.class)
@Interceptor(I_C_Payment.class)
public class C_Payment
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	@CalloutMethod(columnNames = { I_C_Payment.COLUMNNAME_C_Invoice_ID })
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_C_Payment.COLUMNNAME_C_Invoice_ID)
	public void onInvoiceChange(final I_C_Payment payment)
	{
		if (payment.getC_Invoice_ID() <= 0)
		{
			// nothing to do
			return;
		}
		final I_C_Invoice invoice = payment.getC_Invoice();

		payment.setC_Order(null);
		payment.setC_Charge(null);
		payment.setIsPrepayment(false);
		//
		payment.setDiscountAmt(BigDecimal.ZERO);
		payment.setWriteOffAmt(BigDecimal.ZERO);
		payment.setIsOverUnderPayment(false);
		payment.setOverUnderAmt(BigDecimal.ZERO);
		// TODO slve this
		// int C_InvoicePaySchedule_ID = 0;
		// if (calloutField.getTabInfoContextAsInt("C_Invoice_ID") == invoice.getC_Invoice_ID()
		// && calloutField.getTabInfoContextAsInt("C_InvoicePaySchedule_ID") > 0)
		// {
		// C_InvoicePaySchedule_ID = calloutField.getTabInfoContextAsInt("C_InvoicePaySchedule_ID");
		// }
		// Payment Date
		Timestamp ts = payment.getDateTrx();
		if (ts == null)
		{
			ts = new Timestamp(System.currentTimeMillis());
		}

		final int bPartnerId = invoice.getC_BPartner_ID();
		final int currencyId = invoice.getC_Currency_ID();

		payment.setC_BPartner_ID(bPartnerId);
		payment.setC_Currency_ID(currencyId);

		validateInvoiceAndDoctypeSOTrx(payment);

		// TODO solve this
		// + " invoiceOpen(C_Invoice_ID, ?)," // 3 #1
		// //
		// BigDecimal InvoiceOpen = rs.getBigDecimal(3); // Set Invoice
		// // OPen Amount
		// if (InvoiceOpen == null)
		// {
		// InvoiceOpen = BigDecimal.ZERO;
		// }

		// TODO solve this

		// + " invoiceDiscount(C_Invoice_ID,?,?)
		// BigDecimal DiscountAmt = rs.getBigDecimal(4); // Set Discount
		// // Amt
		// if (DiscountAmt == null)
		// {
		// DiscountAmt = BigDecimal.ZERO;
		// }

		// 07564
		// In case of a credit memo invoice, if the amount is negative, we have to make is positive

		// TODO
		// Solve this too
		// BigDecimal payAmt = InvoiceOpen.subtract(DiscountAmt);
		// final I_C_DocType invoiceDocType = invoice.getC_DocType();
		// if (X_C_DocType.DOCBASETYPE_APCreditMemo.equals(invoiceDocType.getDocBaseType())
		// || X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(invoiceDocType.getDocBaseType()))
		// {
		// if (payAmt.signum() < 0)
		// {
		// payAmt = payAmt.abs();
		// }
		// }
		//
		//
		// payment.setPayAmt(payAmt);
		// payment.setDiscountAmt(DiscountAmt);
	}

	@CalloutMethod(columnNames = { I_C_Payment.COLUMNNAME_C_DocType_ID })
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_C_Payment.COLUMNNAME_C_DocType_ID)
	public void onDoctypeChange(final I_C_Payment payment)
	{
		if (payment.getC_DocType_ID() <= 0)
		{
			// nothing to do
			return;
		}
		final I_C_DocType docType = payment.getC_DocType();

		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docType)
				.setOldDocumentNo(payment.getDocumentNo())
				.setDocumentModel(payment)
				.buildOrNull();

		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			payment.setDocumentNo(documentNoInfo.getDocumentNo());
		}

		validateInvoiceAndDoctypeSOTrx(payment);
		validateOrderAndDoctypeSOTrx(payment);

	}

	@CalloutMethod(columnNames = { I_C_Payment.COLUMNNAME_C_Order_ID })
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_C_Payment.COLUMNNAME_C_Order_ID)
	public void onOrderChange(final I_C_Payment payment)
	{

		if (payment.getC_Order_ID() <= 0)
		{
			// nothing to do
			return;
		}

		final I_C_Order order = payment.getC_Order();

		payment.setC_Invoice(null);
		payment.setC_Charge(null);
		payment.setIsPrepayment(true);
		//
		payment.setDiscountAmt(BigDecimal.ZERO);
		payment.setWriteOffAmt(BigDecimal.ZERO);
		payment.setIsOverUnderPayment(false);
		payment.setOverUnderAmt(BigDecimal.ZERO);

		final int bpartnerId = order.getBill_BPartner_ID() >= 0 ? order.getBill_BPartner_ID() : order.getC_BPartner_ID();
		payment.setC_BPartner_ID(bpartnerId);

		final int C_Currency_ID = order.getC_Currency_ID();
		payment.setC_Currency_ID(C_Currency_ID);

		final BigDecimal grandTotal = Util.coalesce(order.getGrandTotal(), BigDecimal.ZERO);
		payment.setPayAmt(grandTotal);

		validateOrderAndDoctypeSOTrx(payment);
	}

	@CalloutMethod(columnNames = { I_C_Payment.COLUMNNAME_C_Charge_ID })
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_C_Payment.COLUMNNAME_C_Charge_ID)
	public void onChargeChange(final I_C_Payment payment)
	{
		if (payment.getC_Charge_ID() <= 0)
		{
			// nothing to do
			return;
		}

		payment.setC_Invoice(null);
		payment.setC_Order(null);
		payment.setIsPrepayment(false);
		payment.setDiscountAmt(BigDecimal.ZERO);
		payment.setWriteOffAmt(BigDecimal.ZERO);
		payment.setIsOverUnderPayment(Boolean.FALSE);
		payment.setOverUnderAmt(BigDecimal.ZERO);
	}

	private void validateInvoiceAndDoctypeSOTrx(final I_C_Payment payment)
	{
		if (payment.getC_Invoice_ID() <= 0 || payment.getC_DocType_ID() <= 0)
		{
			// nothing to do
			return;
		}

		final I_C_Invoice invoice = payment.getC_Invoice();
		final I_C_DocType docType = payment.getC_DocType();
		if (invoice.isSOTrx() != docType.isSOTrx())
		{
			// task: 07564 the SOtrx flags don't match, but that's OK *if* the invoice i a credit memo (either for the vendor or customer side)
			if (!invoiceBL.isCreditMemo(invoice))
			{
				throw new AdempiereException("@PaymentDocTypeInvoiceInconsistent@");
			}
		}
	}

	private void validateOrderAndDoctypeSOTrx(final I_C_Payment payment)
	{
		if (payment.getC_Order_ID() <= 0 || payment.getC_DocType_ID() <= 0)
		{
			// nothing to do
			return;
		}
		final I_C_DocType docType = payment.getC_DocType();
		final I_C_Order order = payment.getC_Order();

		if (order.isSOTrx() != docType.isSOTrx())
		{
			throw new AdempiereException("@PaymentDocTypeInvoiceInconsistent@");
		}

	}

	@CalloutMethod(columnNames = I_C_Payment.COLUMNNAME_C_Currency_ID)
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },//
			ifColumnsChanged = I_C_Payment.COLUMNNAME_C_Currency_ID)
	public void onCurrencyChange(final I_C_Payment payment)
	{
		final String columnName = I_C_Payment.COLUMNNAME_C_Currency_ID;
		paymentBL.updateAmounts(payment, columnName, true);

	}

	@CalloutMethod(columnNames = I_C_Payment.COLUMNNAME_C_ConversionType_ID)
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },//
			ifColumnsChanged = I_C_Payment.COLUMNNAME_C_ConversionType_ID)
	public void onConversionTypeChange(final I_C_Payment payment)
	{
		final String columnName = I_C_Payment.COLUMNNAME_C_ConversionType_ID;
		paymentBL.updateAmounts(payment, columnName, true);
	}

	@CalloutMethod(columnNames = I_C_Payment.COLUMNNAME_DiscountAmt)
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },//
			ifColumnsChanged = I_C_Payment.COLUMNNAME_DiscountAmt)
	public void onDiscountAmtChange(final I_C_Payment payment)
	{
		final String columnName = I_C_Payment.COLUMNNAME_DiscountAmt;
		paymentBL.updateAmounts(payment, columnName, true);
	}

	@CalloutMethod(columnNames = I_C_Payment.COLUMNNAME_IsOverUnderPayment)
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },//
			ifColumnsChanged = I_C_Payment.COLUMNNAME_IsOverUnderPayment)
	public void onIsOverUnderPaymentChange(final I_C_Payment payment)
	{
		final String columnName = I_C_Payment.COLUMNNAME_IsOverUnderPayment;
		paymentBL.updateAmounts(payment, columnName, true);
	}

	@CalloutMethod(columnNames = I_C_Payment.COLUMNNAME_OverUnderAmt)
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },//
			ifColumnsChanged = I_C_Payment.COLUMNNAME_OverUnderAmt)
	public void onOverUnderAmtChange(final I_C_Payment payment)
	{
		final String columnName = I_C_Payment.COLUMNNAME_OverUnderAmt;
		paymentBL.updateAmounts(payment, columnName, true);
	}

	@CalloutMethod(columnNames = I_C_Payment.COLUMNNAME_PayAmt)
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },//
			ifColumnsChanged = I_C_Payment.COLUMNNAME_PayAmt)
	public void onPayAmtcuChange(final I_C_Payment payment)
	{
		final String columnName = I_C_Payment.COLUMNNAME_PayAmt;
		paymentBL.updateAmounts(payment, columnName, true);
	}

	@CalloutMethod(columnNames = I_C_Payment.COLUMNNAME_WriteOffAmt)
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },//
			ifColumnsChanged = I_C_Payment.COLUMNNAME_WriteOffAmt)
	public void onWriteOffAmtChange(final I_C_Payment payment)
	{
		final String columnName = I_C_Payment.COLUMNNAME_WriteOffAmt;
		paymentBL.updateAmounts(payment, columnName, true);
	}

}
