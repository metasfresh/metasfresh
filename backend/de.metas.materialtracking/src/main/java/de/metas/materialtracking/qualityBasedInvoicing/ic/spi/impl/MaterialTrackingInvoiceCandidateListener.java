package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MTLinkRequest.IfModelAlreadyLinked;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Assign created invoice to the same material tracking as the invoice candidates were assigned.
 *
 * @author tsa
 *
 */
public class MaterialTrackingInvoiceCandidateListener implements IInvoiceCandidateListener
{
	public static final MaterialTrackingInvoiceCandidateListener instance = new MaterialTrackingInvoiceCandidateListener();

	private MaterialTrackingInvoiceCandidateListener()
	{
	}

	@Override
	public void onBeforeInvoiceLineCreated(final I_C_InvoiceLine invoiceLine, final IInvoiceLineRW fromInvoiceLine, final List<I_C_Invoice_Candidate> fromCandidates)
	{
		//
		// Get material tracking of those invoice candidates
		final I_M_Material_Tracking materialTracking = retrieveMaterialTracking(fromCandidates);
		if (materialTracking == null)
		{
			return;
		}

		//
		// Assign the invoice to material tracking
		final I_C_Invoice invoice = invoiceLine.getC_Invoice();
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.linkModelToMaterialTracking(
				MTLinkRequest.builder()
						.model(invoice)
						.materialTrackingRecord(materialTracking)
						.ifModelAlreadyLinked(IfModelAlreadyLinked.UNLINK_FROM_PREVIOUS)
						.build()
				);
	}

	private I_M_Material_Tracking retrieveMaterialTracking(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		// no candidates => nothing to do (shall not happen)
		if (invoiceCandidates == null || invoiceCandidates.isEmpty())
		{
			return null;
		}

		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.create(invoiceCandidates.get(0), de.metas.materialtracking.model.I_C_Invoice_Candidate.class).getM_Material_Tracking();
		for (final I_C_Invoice_Candidate fromInvoiceCandidate : invoiceCandidates)
		{
			final de.metas.materialtracking.model.I_C_Invoice_Candidate invoicecandExt = InterfaceWrapperHelper.create(fromInvoiceCandidate,
					de.metas.materialtracking.model.I_C_Invoice_Candidate.class);
			final int materialTrackingID = materialTracking == null ? 0 : materialTracking.getM_Material_Tracking_ID();
			Check.assume(materialTrackingID == invoicecandExt.getM_Material_Tracking_ID(),
					"All I_C_InvoiceCandidates from the list have the same M_Material_Tracking_ID: {}", invoiceCandidates);
		}

		return materialTracking;
	}
}
