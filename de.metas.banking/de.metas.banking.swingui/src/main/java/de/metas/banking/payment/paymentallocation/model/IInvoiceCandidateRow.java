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

@TableInfo(defaultHideAll = true)
public interface IInvoiceCandidateRow
{
	//@formatter:off
	@ColumnInfo(columnName = "Selected", seqNo = 10, displayName=" ", translate = false, selectionColumn = true)
	boolean isSelected();
	void setSelected(boolean selected);
	
	// @ColumnInfo(columnName = "C_Invoice_Candidate_ID", seqNo = 20) // hidden
	int getC_Invoice_Candidate_ID();
	
	@ColumnInfo(columnName = "C_BPartner_ID", seqNo = 30)
	String getBPartnerName();

	@ColumnInfo(columnName = "DocumentDate", seqNo = 40)
	Date getDocumentDate();
	
	@ColumnInfo(columnName = "DateToInvoice", seqNo = 50)
	Date getDateToInvoice();

	@ColumnInfo(columnName = "DateInvoiced", seqNo = 60)
	Date getDateInvoiced();
	
	@ColumnInfo(columnName = "NetAmtToInvoice", seqNo = 70)
	BigDecimal getNetAmtToInvoice();
	
	@ColumnInfo(columnName = "NetAmtInvoiced", seqNo = 80)
	BigDecimal getNetAmtInvoiced();
	
	@ColumnInfo(columnName = "Discount", seqNo = 90)
	BigDecimal getDiscount();
	
	@ColumnInfo(columnName = "DocumentCurrency", seqNo = 100)
	String getCurrencyISOCode();
	
	@ColumnInfo(columnName = "InvoiceRule", seqNo = 110)
	String getInvoiceRuleName();
	
	@ColumnInfo(columnName = "HeaderAggregationKey", seqNo = 120)
	String getHeaderAggregationKey();
	
	/**
	 * task 09643: separate transaction date from accounting date
	 * note: placing this last to not break something in the existing functionality
	 * @return accounting date
	 */
	@ColumnInfo(columnName = "DateAcct", seqNo = 130)
	Date getDateAcct();
	//@formatter:on
}
