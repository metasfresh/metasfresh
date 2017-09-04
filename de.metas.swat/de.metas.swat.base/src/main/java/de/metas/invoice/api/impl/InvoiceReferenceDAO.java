package de.metas.invoice.api.impl;

import java.util.Collections;
import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice_Reference;
import org.compiere.model.X_C_Invoice_Reference;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoice.api.IInvoiceReferenceDAO;

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

public class InvoiceReferenceDAO implements IInvoiceReferenceDAO
{

	@Override
	public I_C_Invoice_Reference createReferencedInvoice(final I_C_Invoice origin, final I_C_Invoice referenced, final String type)
	{
		final I_C_Invoice_Reference invoiceReference = InterfaceWrapperHelper.newInstance(I_C_Invoice_Reference.class);

		invoiceReference.setC_Invoice(origin);

		invoiceReference.setC_Invoice_Linked(referenced);

		invoiceReference.setC_Invoice_Reference_Type(type);

		InterfaceWrapperHelper.save(invoiceReference);

		return invoiceReference;
	}

	@Override
	public Iterator<I_C_Invoice> retrieveParentInvoiceForAdjustmentCharge(final I_C_Invoice adjustmentCharge)
	{
		// services
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		if (!invoiceBL.isAdjustmentCharge(adjustmentCharge))
		{
			// nothing to do
			return Collections.emptyIterator();
		}

		return queryBL.createQueryBuilder(I_C_Invoice_Reference.class, adjustmentCharge)
				.filterByClientId()
				.addEqualsFilter(I_C_Invoice_Reference.COLUMNNAME_C_Invoice_Linked_ID, adjustmentCharge.getC_Invoice_ID())
				.addEqualsFilter(I_C_Invoice_Reference.COLUMN_C_Invoice_Reference_Type, X_C_Invoice_Reference.C_INVOICE_REFERENCE_TYPE_AdjustmentCharge)
				.andCollect(I_C_Invoice_Reference.COLUMN_C_Invoice_ID, I_C_Invoice.class)
				.create()
				.iterate(I_C_Invoice.class);
	}

	@Override
	public Iterator<I_C_Invoice> retrieveParentInvoiceForCreditMemo(final I_C_Invoice creditMemo)
	{
		// services
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		if (!invoiceBL.isCreditMemo(creditMemo))
		{
			// nothing to do
			return Collections.emptyIterator();
		}

		return queryBL.createQueryBuilder(I_C_Invoice_Reference.class, creditMemo)
				.filterByClientId()
				.addEqualsFilter(I_C_Invoice_Reference.COLUMNNAME_C_Invoice_Linked_ID, creditMemo.getC_Invoice_ID())
				.addEqualsFilter(I_C_Invoice_Reference.COLUMN_C_Invoice_Reference_Type, X_C_Invoice_Reference.C_INVOICE_REFERENCE_TYPE_CreditMemo)
				.andCollect(I_C_Invoice_Reference.COLUMN_C_Invoice_ID, I_C_Invoice.class)
				.create()
				.iterate(I_C_Invoice.class);
	}

	@Override
	public void deleteInvoiceReferences(final I_C_Invoice parentInvoice, final I_C_Invoice referencedInvoice)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_C_Invoice_Reference.class, parentInvoice)
				.filterByClientId()
				.addEqualsFilter(I_C_Invoice_Reference.COLUMN_C_Invoice_ID, parentInvoice.getC_Invoice_ID())
				.addEqualsFilter(I_C_Invoice_Reference.COLUMN_C_Invoice_Linked_ID, referencedInvoice.getC_Invoice_ID())
				.create()
				.delete();

	}

	@Override
	public Iterator<I_C_Invoice> retrieveCreditMemosForInvoice(final I_C_Invoice invoice)
	{
		return retrieveReferencesForInvoice(invoice, X_C_Invoice_Reference.C_INVOICE_REFERENCE_TYPE_CreditMemo);
	}

	@Override
	public Iterator<I_C_Invoice> retrieveAdjustmentChargesForInvoice(final I_C_Invoice invoice)
	{
		return retrieveReferencesForInvoice(invoice, X_C_Invoice_Reference.C_INVOICE_REFERENCE_TYPE_AdjustmentCharge);
	}

	private Iterator<I_C_Invoice> retrieveReferencesForInvoice(final I_C_Invoice invoice, final String referenceType)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_Invoice_Reference.class, invoice)
				.filterByClientId()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Reference.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
				.addEqualsFilter(I_C_Invoice_Reference.COLUMN_C_Invoice_Reference_Type, referenceType)
				.andCollect(I_C_Invoice_Reference.COLUMN_C_Invoice_ID, I_C_Invoice.class)
				.create()
				.iterate(I_C_Invoice.class);
	}

}
