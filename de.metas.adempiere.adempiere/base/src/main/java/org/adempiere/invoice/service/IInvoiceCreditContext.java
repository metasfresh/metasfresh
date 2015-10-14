package org.adempiere.invoice.service;

/*
 * #%L
 * ADempiere ERP - Base
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


/**
 * Holds configuration parameters when crediting an invoice. See implementation.
 * 
 * @author al
 * 
 */
public interface IInvoiceCreditContext
{
	/**
	 * @return the DocType used to create the CreditMemo.
	 */
	int getC_DocType_ID();

	/**
	 * @return if <code>true</code>, then the credit memo is completed and the credit memo's <code>GrandTotal</code> is allocated against the given invoice, so that the given
	 *         invoice has <code>IsPaid='Y'</code> afterwards. If <code>false</code>, then the credit memo is only "prepared", so to that is can still be edited by a user.
	 */
	boolean completeAndAllocate();

	/**
	 * @return if <code>true</code>, then we copy <code>C_Invoice.C_Order_ID</code>, <code>C_Invoice.POReference</code> and <code>C_InvoiceLine.C_OrderLine_ID</code>s
	 */
	boolean isReferenceOriginalOrder();

	/**
	 * @return If <code>true</code>, then the credit memo and the invoice will reference each other via <code>Ref_CrediMemo_ID</code> (this is already the case) and if the credit memo
	 *         is completed later, it is automatically allocated against the invoice (also if it's completed right directly).<br>
	 *         Also, the credit memo will reference the invoice via <code>Ref_Invoice_ID</code>.
	 *         If <code>false</code>, then the original invoice is just used as a template, but not linked with the credit memo
	 */
	boolean isReferenceInvoice();

	/**
	 *
	 * @return if <code>true</code> then the credit memo is generated with <code>IsCreditedInvoiceReinvoicable='Y'</code>, which leads to the invoice candidates to be updated in a manner that allows
	 *         the credited quantities to be invoiced once more. Note that if the invoice's grand total is already partially allocated, this value will be override with <code>false</code>.
	 */
	boolean isCreditedInvoiceReinvoicable();
}
