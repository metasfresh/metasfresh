package de.metas.banking.payment.paymentallocation.model;

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
import java.util.Date;

import org.compiere.swing.table.ColumnInfo;
import org.compiere.swing.table.TableInfo;

import de.metas.banking.payment.paymentallocation.service.PayableDocument;
import de.metas.currency.CurrencyCode;

/**
 * Invoice Row
 * 
 * NOTE:
 * <ul>
 * <li>NONE of the amounts are AP adjusted (so customer invoices and vendor invoices will have same sign)
 * <li>all amounts are credit memo adjusted
 * </ul>
 * 
 * @author tsa
 *
 */
@TableInfo(defaultHideAll = true)
public interface IInvoiceRow extends IAllocableDocRow
{
	//@formatter:off
	@ColumnInfo(columnName = PROPERTY_Selected, seqNo = 10, displayName=" ", translate = false, selectionColumn = true)
	@Override
	boolean isSelected();
	@Override
	void setSelected(boolean selected);
	
	@ColumnInfo(columnName = "C_DocType_ID", seqNo = 20)
	String getDocTypeName();

	@ColumnInfo(columnName = "DocumentNo", seqNo = 30)
	@Override
	String getDocumentNo();
	
	@ColumnInfo(columnName="POReference", seqNo=35)
	String getPOReference();

	@ColumnInfo(columnName = "C_BPartner_ID", seqNo = 40)
	String getBPartnerName();

	@ColumnInfo(columnName = "DocumentDate", seqNo = 50)
	@Override
	Date getDocumentDate();

	String PROPERTY_DocumentCurrency = "DocumentCurrency";
	@ColumnInfo(columnName = PROPERTY_DocumentCurrency, seqNo = 60)
	String getCurrencyISOCodeAsString();
	CurrencyCode getCurrencyISOCode();

	@ColumnInfo(columnName = "OriginalAmt", seqNo = 70)
	BigDecimal getGrandTotal();

	String PROPERTY_ConvertedAmt = "ConvertedAmt";
	@ColumnInfo(columnName = PROPERTY_ConvertedAmt, seqNo = 80)
	BigDecimal getGrandTotalConv();

	@ColumnInfo(columnName = "OpenAmt", seqNo = 90)
	@Override
	BigDecimal getOpenAmtConv();
	@Override
	BigDecimal getOpenAmtConv_APAdjusted();

	String PROPERTY_Discount = "Discount";
	@ColumnInfo(columnName = PROPERTY_Discount, seqNo = 100)
	BigDecimal getDiscount();
	BigDecimal getDiscount_APAdjusted();
	void setDiscount(BigDecimal discount);

	String PROPERTY_WriteOffAmt = "WriteOff";
	@ColumnInfo(columnName = PROPERTY_WriteOffAmt, seqNo = 110)
	BigDecimal getWriteOffAmt();
	BigDecimal getWriteOffAmt_APAdjusted();
	void setWriteOffAmt(BigDecimal writeOffAmt);
	
	String PROPERTY_OverUnderAmt = "OverUnderAmt";
	@ColumnInfo(columnName = PROPERTY_OverUnderAmt, seqNo = 120)
	BigDecimal getOverUnderAmt();
	BigDecimal getOverUnderAmt_APAdjusted();
	void setOverUnderAmt(BigDecimal overUnderAmt);

	@ColumnInfo(columnName = PROPERTY_AppliedAmt, seqNo = 130)
	@Override
	BigDecimal getAppliedAmt();
	@Override
	BigDecimal getAppliedAmt_APAdjusted();
	@Override
	void setAppliedAmt(BigDecimal appliedAmt);
	BigDecimal getNotAppliedAmt();


	String PROPERTY_PaymentRequestAmt = "ReadPaymentForm.totalPayments";
	@ColumnInfo(columnName=PROPERTY_PaymentRequestAmt, seqNo=140)
	BigDecimal getPaymentRequestAmt();
	//@formatter:on

	//@formatter:off
	/** Sets the write-off amount of given type */
	void setWriteOffAmtOfType(InvoiceWriteOffAmountType type, BigDecimal writeOffAmt);
	/** @return write-off amount of given type */
	BigDecimal getWriteOffAmtOfType(InvoiceWriteOffAmountType type);
	
	/**
	 * Reset all write-off amounts (i.e. set them to zero).
	 * 
	 * @see InvoiceWriteOffAmountType
	 */
	void resetAllWriteOffAmounts();

	//@formatter:on

	boolean isPrepayOrder();

	int getC_Order_ID();

	int getC_Invoice_ID();

	/**
	 * Sets the write-off amount of given type and then updates the AppliedAmt and the other write-off amounts.
	 * 
	 * @param context
	 * @param writeOffType
	 * @param writeOffAmt
	 */
	void setWriteOffManual(PaymentAllocationContext context, InvoiceWriteOffAmountType writeOffType, BigDecimal writeOffAmt);

	void recalculateWriteOffAmounts(PaymentAllocationContext context);

	/**
	 * Distributes the not applied amount (i.e. open amount - applied amount) to first write-off amount which is enabled.
	 * 
	 * If there are no allowed write-off amounts, this method will just set all the write-off amounts to zero.
	 * 
	 * @param context
	 */
	void distributeNotAppliedAmtToWriteOffs(PaymentAllocationContext context);

	/**
	 * Distributes the not applied amount (i.e. open amount - applied amount) to given write-off amount type.
	 * 
	 * @param type
	 */
	void distributeNotAppliedAmtToWriteOffs(InvoiceWriteOffAmountType type);
	
	PayableDocument copyAsPayableDocument();
}
