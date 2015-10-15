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

import org.adempiere.util.lang.ITableRecordReference;

public interface IPayableDocument
{
	public enum PayableDocumentType
	{
		Invoice,
		PrepaidOrder,
	}

	PayableDocumentType getType();

	String getDocumentNo();
	
	boolean isCustomerDocument();

	boolean isVendorDocument();

	boolean isCreditMemo();

	int getC_BPartner_ID();

	ITableRecordReference getReference();

	/**
	 * @return true if everything that was requested to be allocated, was allocated
	 */
	boolean isFullyAllocated();

	BigDecimal getAmountToAllocateInitial();

	BigDecimal getAmountToAllocate();

	void addAllocatedAmounts(BigDecimal allocatedAmtToAdd, BigDecimal discountAmtToAdd, BigDecimal writeOffAmtToAdd);

	BigDecimal getDiscountAmtToAllocate();

	BigDecimal getWriteOffAmtToAllocate();

	/**
	 * @return how much would be Over/Under amount if we are considering given amounts to be allocated.
	 */
	BigDecimal calculateProjectedOverUnderAmt(BigDecimal amountToAllocate, BigDecimal discountAmtToAllocate, BigDecimal writeOffAmtToAllocate);
}
