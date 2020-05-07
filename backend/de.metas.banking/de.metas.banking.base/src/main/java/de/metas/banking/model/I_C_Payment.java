package de.metas.banking.model;

/*
 * #%L
 * de.metas.banking.base
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


public interface I_C_Payment extends org.compiere.model.I_C_Payment {

	public static final String CCPAYMENTSTATE_Captured = "CA";
	public static final String CCPAYMENTSTATE_Error = "ER";
	public static final String CCPAYMENTSTATE_None = "NO";
	public static final String CCPAYMENTSTATE_Purchased = "PU";
	public static final String CCPAYMENTSTATE_Refunded = "RF";
	public static final String CCPAYMENTSTATE_Reserved = "RE";
	public static final String CCPAYMENTSTATE_Reversed = "RV";
	public static final String CCPAYMENTSTATE_Credit = "CR";

	public static final String TRXTYPE_RefundTotalOrPart = "R";
	
	public String getCCPaymentState();

	public void setCCPaymentState(String ccPaymentState);
	
	// 04193
	public static final String COLUMNNAME_IsAutoAllocateAvailableAmt = "isAutoAllocateAvailableAmt";

	// 04193
	public void setIsAutoAllocateAvailableAmt(boolean IsAutoAllocateAvailableAmt);

	// 04193
	public boolean isAutoAllocateAvailableAmt();
	
}
