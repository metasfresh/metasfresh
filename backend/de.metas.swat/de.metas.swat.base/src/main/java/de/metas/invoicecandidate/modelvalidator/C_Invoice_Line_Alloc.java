package de.metas.invoicecandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.util.Services;

/**
 * This validator makes sure that a <code>C_Invoice_Line_Alloc</code>'s candidate is invalidated on every creation,
 * change and deletion
 */
@Validator(I_C_Invoice_Line_Alloc.class)
public class C_Invoice_Line_Alloc
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void invalidateCandidate(final I_C_Invoice_Line_Alloc ila)
	{
		Services.get(IInvoiceCandDAO.class).invalidateCand(ila.getC_Invoice_Candidate());
	}

	/**
	 * Making sure that the invoiced qty is not above invoice candidate's the invoiceable qty, i.e. <code>QtyToInvoice + QtyInvoiced</code>.
	 * In the absence of any bugs, this would not be the case, but this method makes sure.
	 *
	 * @param ila
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void checkConsistency(final I_C_Invoice_Line_Alloc ila)
	{
//		Commented out for now, interferes with 05420
//
//		final I_C_Invoice_Candidate ic = ila.getC_Invoice_Candidate();
//
//		if (ic.isToRecompute() || ic.isToClear())
//		{
//			return;
//		}
//
//		final BigDecimal invoicedQty =
//				new Query(InterfaceWrapperHelper.getCtx(ila), I_C_Invoice_Line_Alloc.Table_Name, I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID + "=?",
//						InterfaceWrapperHelper.getTrxName(ila))
//						.setParameters(ila.getC_Invoice_Candidate_ID())
//						.setOnlyActiveRecords(true)
//						.setClient_ID()
//						.sum(I_C_Invoice_Line_Alloc.COLUMNNAME_QtyInvoiced);
//
//		final BigDecimal maxQty = ic.getQtyToInvoice().add(ic.getQtyInvoiced());
//
//		Check.assume(invoicedQty.compareTo(maxQty) <= 0,
//				ic + " has QtyToInvoice + QtyInvoiced = " + maxQty + " but the QtyInvoiced of all C_Invoice_Line_Allocs sums up to " + invoicedQty);

	}
}
