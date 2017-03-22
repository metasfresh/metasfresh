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

import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.spi.impl.listeners.PaymentAllocationLineMaterialTrackingListener;

@Interceptor(I_C_AllocationHdr.class)
public class C_AllocationHdr extends MaterialTrackableDocumentByASIInterceptor<I_C_AllocationHdr, I_C_AllocationLine>
{
	@Init
	public void init()
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);

		materialTrackingBL.addModelTrackingListener(
				PaymentAllocationLineMaterialTrackingListener.LISTENER_TableName,
				PaymentAllocationLineMaterialTrackingListener.instance);
	}

	@Override
	protected boolean isEligibleForMaterialTracking(final I_C_AllocationHdr document)
	{
		// NOTE: at this point we don't know if is eligible or not, so we consider everything as eligible
		// and we will decide it later, on Allocation Line

		if (Services.get(IAllocationBL.class).isReversal(document))
		{
			// .. at any rate, we don't link reversals, because the original allocation is also unlinked.
			return false;
		}

		return true;
	}

	@Override
	protected List<I_C_AllocationLine> retrieveDocumentLines(final I_C_AllocationHdr document)
	{
		return Services.get(IAllocationDAO.class).retrieveLines(document);
	}

	@Override
	protected I_M_AttributeSetInstance getM_AttributeSetInstance(final I_C_AllocationLine documentLine)
	{
		// shall not be called because we implement "getMaterialTrackingFromDocumentLineASI"
		throw new IllegalStateException("shall not be called");
	}

	/**
	 * Loads and returns the material tracking of the invoice referenced by the given {@code documentLine}, if there is any. If there is none, it returns {@code null}.
	 * Analog to {@link C_Invoice#isEligibleForMaterialTracking(I_C_Invoice)}, if the invoice is a sales invoice or if it is reversed, then we don't bother trying to get its material tracking and directly return {@code null}.
	 */
	@Override
	protected I_M_Material_Tracking getMaterialTrackingFromDocumentLineASI(final I_C_AllocationLine documentLine)
	{
		if (documentLine.getC_Invoice_ID() <= 0)
		{
			return null;
		}

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_C_Invoice invoice = documentLine.getC_Invoice();

		// please keep in sync with the isEligible method mentioned in the javadoc.
		if (Services.get(IInvoiceBL.class).isReversal(invoice))
		{
			return null;
		}
		if (invoice.isSOTrx())
		{
			return null;
		}

		final I_M_Material_Tracking materialTracking = materialTrackingDAO.retrieveMaterialTrackingForModel(invoice);
		return materialTracking;
	}
}
