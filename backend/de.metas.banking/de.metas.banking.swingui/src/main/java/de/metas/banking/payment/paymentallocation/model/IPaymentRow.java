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

import de.metas.banking.payment.paymentallocation.service.IPaymentDocument;

@TableInfo(defaultHideAll = true)
public interface IPaymentRow extends IAllocableDocRow
{
	//@formatter:off
	@ColumnInfo(columnName = PROPERTY_Selected, seqNo = 10, displayName=" ", translate = false, selectionColumn = true)
	@Override
	boolean isSelected();
	@Override
	void setSelected(boolean selected);

	@ColumnInfo(columnName = "C_DocType_ID", seqNo = 20)
	String getDocTypeName();

	@Override
	@ColumnInfo(columnName = "DocumentNo", seqNo = 30)
	String getDocumentNo();

	@ColumnInfo(columnName = "C_BPartner_ID", seqNo = 40)
	String getBPartnerName();

	@ColumnInfo(columnName = "DocumentDate", seqNo = 50)
	@Override
	Date getDocumentDate();

	String PROPERTY_DocumentCurrency = "DocumentCurrency";
	@ColumnInfo(columnName = PROPERTY_DocumentCurrency, seqNo = 60)
	String getCurrencyISOCodeAsString();
	
	@ColumnInfo(columnName = "OriginalAmt", seqNo = 70)
	BigDecimal getPayAmt();

	String PROPERTY_ConvertedAmt = "ConvertedAmt";
	@ColumnInfo(columnName = PROPERTY_ConvertedAmt, seqNo = 80)
	BigDecimal getPayAmtConv();

	@ColumnInfo(columnName = "OpenAmt", seqNo = 90)
	@Override
	BigDecimal getOpenAmtConv();
	
	String PROPERTY_DiscountAmt = "DiscountAmt";
	@ColumnInfo(columnName = PROPERTY_DiscountAmt, seqNo = 100)
	BigDecimal getDiscountAmt();
	void setDiscountAmt(BigDecimal discountAmt);

	@ColumnInfo(columnName = "AppliedAmt", seqNo = 130)
	@Override
	BigDecimal getAppliedAmt();
	@Override
	void setAppliedAmt(BigDecimal appliedAmt);
	
	//@formatter:on

	int getC_Payment_ID();

	IPaymentDocument copyAsPaymentDocument();

	/**
	 * Reset discount amount
	 */
	void resetDiscountAmount();

	/**
	 * Sets the discount amount and then updates the AppliedAmt
	 * 
	 * @param context
	 * @param discountAmt
	 */
	void setDiscountManual(PaymentAllocationContext context, BigDecimal discountAmt);

	/**
	 * <ul>
	 * <li>Set DiscountAmt=0 if it does not apply anymore
	 * <li>update AppliedAmt=Open-Discount if discount applies.
	 * </ul>
	 * 
	 * @param context
	 */
	void recalculateDiscountAmount(PaymentAllocationContext context);
}
