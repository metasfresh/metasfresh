package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.model.I_C_Invoice_Candidate;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;

@Interceptor(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	/**
	 * Set the IC's <code>QtyEnteredTU</code> from either the referenced object or order line (if available).
	 *
	 * @param ic
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateQtyEnteredTU(final I_C_Invoice_Candidate ic)
	{
		final Set<Integer> seenIOLs = new HashSet<>();

		final BigDecimal qtyEnteredTU;

		if (TableRecordCacheLocal.isChildModelType(ic, I_M_InOutLine.class)) // if it's 1-to-1 with shipment line, use the Record_ID
		{
			final I_M_InOutLine iol = TableRecordCacheLocal.getReferencedValue(ic, I_M_InOutLine.class);
			if (!Services.get(IDocumentBL.class).isDocumentCompletedOrClosed(iol.getM_InOut()))
			{
				qtyEnteredTU = BigDecimal.ZERO;
			}
			else if (iol.isPackagingMaterial())
			{
				qtyEnteredTU = iol.getQtyEntered(); // the bound line is the PM line
			}
			else
			{
				qtyEnteredTU = iol.getQtyEnteredTU();
			}
		}
		else if (ic.getC_OrderLine_ID() > 0)
		{
			qtyEnteredTU = getOrderLineQtyEnteredTU(ic, seenIOLs);
		}
		else
		{
			qtyEnteredTU = BigDecimal.ZERO;
		}

		ic.setQtyEnteredTU(qtyEnteredTU);
	}

	/**
	 * @param ic
	 * @param seenIOLs
	 * @return shipped TU qty or ordered (if nothing was shipped)
	 */
	private final BigDecimal getOrderLineQtyEnteredTU(final I_C_Invoice_Candidate ic, final Set<Integer> seenIOLs)
	{
		BigDecimal qtyEnteredTU = BigDecimal.ZERO;

		final List<I_M_InOutLine> iols = Services.get(IInvoiceCandDAO.class).retrieveInOutLinesForCandidate(ic, I_M_InOutLine.class);
		if (iols.isEmpty()) // if no IOLs, fall back to the order line's qty
		{
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(ic.getC_OrderLine(), I_C_OrderLine.class);
			final BigDecimal olQtyEnteredTU = ol.getQtyEnteredTU();
			if (olQtyEnteredTU != null) // safety
			{
				qtyEnteredTU = olQtyEnteredTU;
			}
		}

		for (final I_M_InOutLine iol : iols)
		{
			//
			// Just to be safe and prevent a possible bug, make sure the IOL IDs are unique
			final int iolId = iol.getM_InOutLine_ID();
			if (!seenIOLs.add(iolId))
			{
				continue;
			}
			if (!Services.get(IDocumentBL.class).isDocumentCompletedOrClosed(iol.getM_InOut()))
			{
				continue; // skip not-completed shipments
			}

			final BigDecimal iolQtyEnteredTU;
			if (iol.isPackagingMaterial())
			{
				iolQtyEnteredTU = iol.getQtyEntered(); // the bound line is the PM line
			}
			else
			{
				iolQtyEnteredTU = iol.getQtyEnteredTU();
			}

			if (iolQtyEnteredTU != null) // safety
			{
				qtyEnteredTU = qtyEnteredTU.add(iolQtyEnteredTU);
			}
		}
		return qtyEnteredTU;
	}
}
