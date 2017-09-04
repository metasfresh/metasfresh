package de.metas.invoice.api;

import java.util.Iterator;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Invoice_Reference;

import de.metas.adempiere.model.I_C_Invoice;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface IInvoiceReferenceDAO extends ISingletonService
{

	/**
	 * Create a reference between the origin invoice and the linked Credit Memo/ Adjustment Charge
	 * 
	 * @param origin
	 * @param referenced
	 * @param type
	 * @return
	 */
	I_C_Invoice_Reference createReferencedInvoice(I_C_Invoice origin, I_C_Invoice referenced, String type);

	/**
	 * Retrieves the invoice that was the orgin invoice of the adjustmentCharge as Ref_AdjustmentCharge_ID.<br>
	 * It will be taken from the table <code>org.compiere.model.I_C_Invoice_Reference</code>
	 * Never returns <code>null</code>.
	 * This method retrieves an iterator of invoices that have this Ref_AdjustmentCharge_ID.<br>
	 * Similar with <code>org.adempiere.invoice.service.IInvoiceDAO.retrieveReversalLine(I_C_InvoiceLine, int)</code>
	 * 
	 * @param adjustmentCharge
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveParentInvoiceForAdjustmentCharge(I_C_Invoice adjustmentCharge);

	/**
	 * Retrieves the invoice that has crediMemo's ID as Ref_CreditMemo.<br>
	 * Never returns <code>null</code>.
	 * This method retrieves an iterator of invoices that have this ref_creditMemo.<br>
	 * At the moment, there shouldn't be more than one, but in the future, the possibility of allocating several invoices against the same credit memo is not out of question.
	 * 
	 * @param creditMemo
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveParentInvoiceForCreditMemo(I_C_Invoice creditMemo);

	/**
	 * Retrieve all the C_Invoice entries that are found in C_Invoice_reference for the given invoice and are linked with a credit memo
	 * 
	 * @param invoice
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveCreditMemosForInvoice(I_C_Invoice invoice);

	/**
	 * Retrieve all the C_Invoice entries that are found in C_Invoice_reference for the given invoice and are linked with an Adjustment Charge
	 * 
	 * @param invoice
	 * @return
	 */
	Iterator<I_C_Invoice> retrieveAdjustmentChargesForInvoice(I_C_Invoice invoice);

	/**
	 * Delete all the references between the parent invoice and the linked credit memo
	 * 
	 * @param parentInvoice
	 * @param creditMemo
	 */
	void deleteInvoiceReferences(I_C_Invoice parentInvoice, I_C_Invoice creditMemo);

}
