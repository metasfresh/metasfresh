package  de.metas.adempiere.ait.event;

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


public enum EventType implements IITEventType
{
	/**
	 * Event is fired at the end of {@link AIntegrationTestDriver#setupAdempiere()}
	 */
	TESTDRIVER_STARTED,
	
	BPARTNER_CREATE_BEFORE,
	BPARTNER_CREATE_AFTER,
	
	/**
	 * A bank statement with at least one bank statement line has been completed
	 */
	BANKSTATEMENT_COMPLETE_AFTER,
	
	
	/**
	 * A C_Flatrate_Term has been created
	 */
	FLATRATE_TERM_CREATE_AFTER,
	
	/**
	 * A C_Flatrate_Term has been successfully completed
	 */
	FLATRATE_TERM_COMPLETE_AFTER,
	
	/**
	 * A standard order has been completed
	 */
	ORDER_STANDARD_COMPLETE_AFTER,
	
	/**
	 * A prepay order has been completed
	 */
	ORDER_PREPAY_COMPLETE_AFTER,
	
	/**
	 * A prepay order has been closed, after it has already been paid.
	 */
	ORDER_PREPAY_PAID_CLOSE_AFTER,
	
	ORDER_WITH_DIRECT_INVOICE_CREATE_BEFORE,
	ORDER_WITH_DIRECT_INVOICE_COMPLETE_AFTER,
	
	ORDER_POS_CREATE_BEFORE,
	ORDER_POS_COMPLETE_AFTER,
	
	ORDER_POS_REACTIVATE_BEFORE,
	ORDER_POS_REACTIVATE_AFTER,
	
	/**
	 * A shipment (MInout with ISSOTrx='Y') has been completed
	 */
	SHIPMENT_COMPLETE_AFTER,
	
	/**
	 * An invoice candidate has been created
	 */
	INVOICECAND_CREATE_AFTER,
	
	/**
	 * A sales invoice has been created
	 */
	INVOICE_SALES_CREATE_AFTER,
	
	/**
	 * A sales invoice line has been changed
	 */
	INVOICELINE_SALES_CHANGE_AFTER,
	
	INVOICE_EMPLOYEE_CREATE_BEFORE,
	INVOICE_EMPLOYEE_CREATE_AFTER,
	
	INVOICE_PAID_REVERSE_AFTER,
	
	INVOICE_UNPAID_REVERSE_AFTER,
	
	/**
	 * A cash payment has been completed
	 */
	PAYMENT_CASH_COMPLETE_AFTER,
	
	/**
	 * A cash payment has been voided
	 */
	PAYMENT_CASH_COMPLETED_VOID_AFTER,
	
	/**
	 * A payment that references an order has been completed
	 */
	PAYMENT_ORDER_COMPLETE_AFTER,
	
	/**
	 * A payment that references an invoice has been completed
	 */
	PAYMENT_INVOICE_CREATE_AFTER,
}