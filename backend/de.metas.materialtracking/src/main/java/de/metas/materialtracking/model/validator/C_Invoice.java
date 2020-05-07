package de.metas.materialtracking.model.validator;

/*
 * #%L
 * de.metas.materialtracking
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.adempiere.model.I_C_InvoiceLine;

@Interceptor(I_C_Invoice.class)
public class C_Invoice extends MaterialTrackableDocumentByASIInterceptor<I_C_Invoice, I_C_InvoiceLine>
{

	/**
	 * Returns {@code false} if the given invoice is a sales document or a reversal.
	 * <p>
	 * Note: Reversals are not eligible, because their original-invoice counterpart is also unlinked.
	 * <p>
	 * When changing this logic, please also take a look at {@link C_AllocationHdr#getMaterialTrackingFromDocumentLineASI(org.compiere.model.I_C_AllocationLine)}.
	 */
	@Override
	protected boolean isEligibleForMaterialTracking(final I_C_Invoice document)
	{
		// Reversals are not eligible, because their original-invoice counterpart is also unlinked.
		if (Services.get(IInvoiceBL.class).isReversal(document))
		{
			return false;
		}
		// Sales Invoices are not eligible
		if (document.isSOTrx())
		{
			return false;
		}

		return false;
	}

	@Override
	protected List<I_C_InvoiceLine> retrieveDocumentLines(final I_C_Invoice document)
	{
		final List<I_C_InvoiceLine> documentLines = Services.get(IInvoiceDAO.class).retrieveLines(document);
		return documentLines;
	}

	/**
	 * Gets order line's ASI (where the material tracking is set)
	 */
	@Override
	protected I_M_AttributeSetInstance getM_AttributeSetInstance(final I_C_InvoiceLine documentLine)
	{
		if (documentLine.getC_OrderLine_ID() <= 0)
		{
			return null;
		}
		final I_C_OrderLine orderLine = documentLine.getC_OrderLine();
		return orderLine.getM_AttributeSetInstance();
	}

}
