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

import com.google.common.base.Predicate;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/**
 * Allocable document (i.e. Invoice or Payment)
 * 
 * @author tsa
 *
 */
public interface IAllocableDocRow
{
	/**
	 * Predicate: Only those which are selected
	 * 
	 * @see #isSelected()
	 */
	Predicate<IAllocableDocRow> PREDICATE_Selected = new Predicate<IAllocableDocRow>()
	{
		@Override
		public boolean apply(IAllocableDocRow row)
		{
			return row.isSelected();
		}
	};

	/**
	 * Predicate: Only those which are NOT taboo
	 * 
	 * @see #isTaboo()
	 */
	Predicate<IAllocableDocRow> PREDICATE_NoTaboo = new Predicate<IAllocableDocRow>()
	{
		@Override
		public boolean apply(IAllocableDocRow row)
		{
			return !row.isTaboo();
		}
	};

	Ordering<IAllocableDocRow> ORDERING_DocumentDate_DocumentNo = new Ordering<IAllocableDocRow>()
	{
		@Override
		public int compare(IAllocableDocRow row1, IAllocableDocRow row2)
		{
			return ComparisonChain.start()
					.compare(row1.getDocumentDate(), row2.getDocumentDate())
					.compare(row1.getDocumentNo(), row2.getDocumentNo())
					.result();
		}
	};

	//@formatter:off
	String PROPERTY_Selected = "Selected";
	boolean isSelected();
	void setSelected(boolean selected);
	//@formatter:on

	String getDocumentNo();

	//@formatter:off
	BigDecimal getOpenAmtConv();
	BigDecimal getOpenAmtConv_APAdjusted();
	//@formatter:on

	//@formatter:off
	String PROPERTY_AppliedAmt = "AppliedAmt";
	BigDecimal getAppliedAmt();
	BigDecimal getAppliedAmt_APAdjusted();
	void setAppliedAmt(BigDecimal appliedAmt);
	/** Sets applied amount and update the other write-off amounts, if needed. */
	void setAppliedAmtAndUpdate(PaymentAllocationContext context, BigDecimal appliedAmt);
	//@formatter:on

	/** @return true if automatic calculations and updating are allowed on this row; false if user manually set the amounts and we don't want to change them for him/her */
	boolean isTaboo();

	/**
	 * @param taboo <ul>
	 *            <li>true if automatic calculations shall be disallowed on this row
	 *            <li>false if automatic calculations are allowed on this row
	 *            </ul>
	 */
	void setTaboo(boolean taboo);

	/** @return document's date */
	Date getDocumentDate();
	
	// task 09643: separate the accounting date from the transaction date
	Date getDateAcct();

	int getC_BPartner_ID();

	/** @return AP Multiplier, i.e. Vendor=-1, Customer=+1 */
	BigDecimal getMultiplierAP();

	boolean isVendorDocument();

	boolean isCustomerDocument();

	boolean isCreditMemo();
}
