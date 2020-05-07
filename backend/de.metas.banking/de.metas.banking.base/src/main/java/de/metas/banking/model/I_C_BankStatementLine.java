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


import org.compiere.model.I_C_Order;

public interface I_C_BankStatementLine extends org.compiere.model.I_C_BankStatementLine
, IBankStatementLineOrRef
{

	public static final String COLUMNNAME_IsMultiplePaymentOrInvoice = "IsMultiplePaymentOrInvoice";

	@Override
	boolean isMultiplePaymentOrInvoice();

	@Override
	void setIsMultiplePaymentOrInvoice(boolean multiplePaymentOrInvoice);

	public static final String COLUMNNAME_IsMultiplePayment = "IsMultiplePayment";

	@Override
	boolean isMultiplePayment();

	@Override
	void setIsMultiplePayment(boolean multiplePayment);
	
	public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";
	@Override
	public void setC_Order_ID(int C_Order_ID);
	@Override
	public int getC_Order_ID();
	@Override
	public I_C_Order getC_Order();

	public static final String COLUMNNAME_IsOverUnderPayment = "IsOverUnderPayment";
	@Override
	public void setIsOverUnderPayment(boolean IsOverUnderPayment);
	@Override
	public boolean isOverUnderPayment();
}
