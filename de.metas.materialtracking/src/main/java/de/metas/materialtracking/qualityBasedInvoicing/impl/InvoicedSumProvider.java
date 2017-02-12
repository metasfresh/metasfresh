package de.metas.materialtracking.qualityBasedInvoicing.impl;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.process.DocAction;

import de.metas.document.engine.IDocActionBL;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IInvoicedSumProvider;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * This is the default implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class InvoicedSumProvider implements IInvoicedSumProvider
{
	/**
	 * @return the sum of <code>C_Invoice.TotalLines</code> of all invoices that
	 *         <ul>
	 *         <li>are either completed or closed and
	 *         <li>that reference the given <code>materialtracking</code> and
	 *         <li>have a "quality inspection" docType
	 *         </ul>
	 */
	@Override
	public BigDecimal getAlreadyInvoicedNetSum(final I_M_Material_Tracking materialTracking)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		final List<I_C_Invoice> invoices = materialTrackingDAO.retrieveReferences(materialTracking, I_C_Invoice.class);

		BigDecimal result = BigDecimal.ZERO;
		for (final I_C_Invoice invoice : invoices)
		{
			if (!docActionBL.isStatusOneOf(invoice, DocAction.STATUS_Completed, DocAction.STATUS_Closed))
			{
				continue;
			}

			// note: completed/closed implies that a C_DocType is set.
			final I_C_DocType docType = invoice.getC_DocType();
			final String docSubType = docType.getDocSubType();

			if (!IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_DownPayment.equals(docSubType)
					&& !IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_FinalSettlement.equals(docSubType))
			{
				continue;
			}
			result = result.add(invoice.getTotalLines());
		}
		return result;
	}

}
