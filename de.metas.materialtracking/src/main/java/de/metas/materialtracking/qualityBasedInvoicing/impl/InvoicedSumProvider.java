package de.metas.materialtracking.qualityBasedInvoicing.impl;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.document.service.IDocActionBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.process.DocAction;

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
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class InvoicedSumProvider implements IInvoicedSumProvider
{
	/**
	 * @return the sum of <code>C_Invoice.TotalLines</code> of all invoices that are either completed or closed and that reference the given <code>materialtracking</code>.
	 */
	@Override
	public BigDecimal getAlreadyInvoicedNetSum(final I_M_Material_Tracking materialTracking)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		final List<I_C_Invoice> invoices = materialTrackingDAO.retrieveReferences(materialTracking, I_C_Invoice.class);

		BigDecimal result = BigDecimal.ZERO;
		for (final I_C_Invoice invoice : invoices)
		{
			if (Services.get(IDocActionBL.class).isStatusOneOf(invoice, DocAction.STATUS_Completed, DocAction.STATUS_Closed))
			{
				result = result.add(invoice.getTotalLines());
			}
			else
			{
				// for other docstates, we ignore the invoice
			}
		}
		return result;
	}

}
