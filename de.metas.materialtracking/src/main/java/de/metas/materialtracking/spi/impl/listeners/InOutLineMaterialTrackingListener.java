package de.metas.materialtracking.spi.impl.listeners;

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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingListenerAdapter;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingDAO;
import de.metas.materialtracking.qualityBasedInvoicing.impl.PPOrderQualityCalculator;
import de.metas.util.Services;

public class InOutLineMaterialTrackingListener extends MaterialTrackingListenerAdapter
{
	public static final transient InOutLineMaterialTrackingListener instance = new InOutLineMaterialTrackingListener();
	public static final String LISTENER_TableName = I_M_InOutLine.Table_Name;

	/**
	 * Increase {@link I_M_Material_Tracking#COLUMNNAME_QtyReceived}
	 */
	@Override
	public void afterModelLinked(final MTLinkRequest request)
	{
		final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(request.getModel(), I_M_InOutLine.class);
		final I_M_Material_Tracking materialTracking = request.getMaterialTrackingRecord();

		if (!isEligible(receiptLine, materialTracking))
		{
			return;
		}

		final BigDecimal qtyReceivedToAdd = receiptLine.getMovementQty();
		final BigDecimal qtyReceived = materialTracking.getQtyReceived();
		final BigDecimal qtyReceivedNew = qtyReceived.add(qtyReceivedToAdd);

		materialTracking.setQtyReceived(qtyReceivedNew);
		InterfaceWrapperHelper.save(materialTracking);

		// task 08021
		final IQualityBasedInvoicingDAO qualityBasedInvoicingDAO = Services.get(IQualityBasedInvoicingDAO.class);
		final IMaterialTrackingDocuments materialTrackingDocuments = qualityBasedInvoicingDAO.retrieveMaterialTrackingDocuments(materialTracking);

		final PPOrderQualityCalculator calculator = new PPOrderQualityCalculator();
		calculator.update(materialTrackingDocuments);
		// end task 08021
	}

	/**
	 * Decrease {@link I_M_Material_Tracking#COLUMNNAME_QtyReceived}
	 */
	@Override
	public void afterModelUnlinked(final Object model, final I_M_Material_Tracking materialTrackingOld)
	{
		final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
		if (!isEligible(receiptLine, materialTrackingOld))
		{
			return;
		}

		final BigDecimal qtyReceivedToRemove = receiptLine.getMovementQty();
		final BigDecimal qtyReceived = materialTrackingOld.getQtyReceived();
		final BigDecimal qtyReceivedNew = qtyReceived.subtract(qtyReceivedToRemove);

		materialTrackingOld.setQtyReceived(qtyReceivedNew);
		InterfaceWrapperHelper.save(materialTrackingOld);
	}

	private final boolean isEligible(final I_M_InOutLine inoutLine, final I_M_Material_Tracking materialTracking)
	{
		// check if the inoutLine's product is our tracked product.
		if (materialTracking.getM_Product_ID() != inoutLine.getM_Product_ID())
		{
			return false;
		}

		final I_M_InOut inout = inoutLine.getM_InOut();

		// Shipments are not eligible (just in case)
		if (inout.isSOTrx())
		{
			return false;
		}
		return true;
	}
}
