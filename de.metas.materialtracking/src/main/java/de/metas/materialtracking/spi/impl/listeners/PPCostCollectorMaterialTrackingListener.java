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
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingListenerAdapter;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.util.Services;

public final class PPCostCollectorMaterialTrackingListener extends MaterialTrackingListenerAdapter
{
	public static final transient PPCostCollectorMaterialTrackingListener instance = new PPCostCollectorMaterialTrackingListener();

	final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

	private PPCostCollectorMaterialTrackingListener()
	{
		super();
	}

	@Override
	public void afterModelLinked(final MTLinkRequest request)
	{
		final I_PP_Cost_Collector ppCostCollector = InterfaceWrapperHelper.create(request.getModel(), I_PP_Cost_Collector.class);

		// Applies only on issue collectors
		if (!ppCostCollectorBL.isMaterialIssue(ppCostCollector, false))
		{
			return;
		}
		
		final I_M_Material_Tracking materialTracking = request.getMaterialTracking();
		
		// sum up only for the same product
		if (materialTracking.getM_Product_ID() == ppCostCollector.getM_Product_ID())
		{
			computeQtyIssuedSum(ppCostCollector, materialTracking);
			computeQtyIssuedPerManufacturingOrder(ppCostCollector);
		}
	}

	@Override
	public void afterModelUnlinked(final Object model, final I_M_Material_Tracking materialTrackingOld)
	{
		final I_PP_Cost_Collector ppCostCollector = InterfaceWrapperHelper.create(model, I_PP_Cost_Collector.class);

		// Applies only on issue collectors
		if (!ppCostCollectorBL.isMaterialIssue(ppCostCollector, false))
		{
			return;
		}

		// sum up only for the same product
		if (materialTrackingOld.getM_Product_ID() == ppCostCollector.getM_Product_ID())
		{
			computeQtyIssuedToRemoveSum(ppCostCollector, materialTrackingOld);
			computeQtyIssuedToRemovePerManufacturingOrder(ppCostCollector);
		}
	}

	/**
	 * Compute total qty issued for all manufacturing orders and set the sum in material tracking
	 * 
	 * @param ppCostCollector
	 * @param materialTracking
	 */
	void computeQtyIssuedSum(final I_PP_Cost_Collector ppCostCollector, final I_M_Material_Tracking materialTracking)
	{
		final BigDecimal qtyIssuedToAdd = ppCostCollector.getMovementQty();
		final BigDecimal qtyIssued = materialTracking.getQtyIssued();
		final BigDecimal qtyIssuedNew = qtyIssued.add(qtyIssuedToAdd);

		materialTracking.setQtyIssued(qtyIssuedNew);
		InterfaceWrapperHelper.save(materialTracking);
	}
	
	/**
	 * Compute total qty issued for a specific manufacturing order and set the sum in material tracking ref
	 * 
	 * @param ppCostCollector
	 */
	void computeQtyIssuedPerManufacturingOrder(final I_PP_Cost_Collector ppCostCollector)
	{
		final I_M_Material_Tracking_Ref refExisting = materialTrackingDAO.retrieveMaterialTrackingRefForModel(ppCostCollector.getPP_Order());
		
		final BigDecimal qtyIssuedToAdd = ppCostCollector.getMovementQty();
		final BigDecimal qtyIssued = refExisting.getQtyIssued();
		final BigDecimal qtyIssuedNew = qtyIssued.add(qtyIssuedToAdd);

		refExisting.setQtyIssued(qtyIssuedNew);
		InterfaceWrapperHelper.save(refExisting);
	}
	
	
	/**
	 * Compute total qty issued for all manufacturing orders and set the sum in material tracking, when cost collector is voided
	 * 
	 * @param ppCostCollector
	 * @param materialTrackingOld
	 */
	void computeQtyIssuedToRemoveSum(final I_PP_Cost_Collector ppCostCollector, final I_M_Material_Tracking materialTrackingOld)
	{
		final BigDecimal qtyIssuedToRemove = ppCostCollector.getMovementQty();
		final BigDecimal qtyIssued = materialTrackingOld.getQtyIssued();
		final BigDecimal qtyIssuedNew = qtyIssued.subtract(qtyIssuedToRemove);

		materialTrackingOld.setQtyIssued(qtyIssuedNew);
		InterfaceWrapperHelper.save(materialTrackingOld);
	}
	
	/**
	 * Compute total qty issued for a specific manufacturing order and set the sum in material tracking ref, when cost collector is voided
	 * 
	 * @param ppCostCollector
	 */
	void computeQtyIssuedToRemovePerManufacturingOrder(final I_PP_Cost_Collector ppCostCollector)
	{
		final I_M_Material_Tracking_Ref refExisting = materialTrackingDAO.retrieveMaterialTrackingRefForModel(ppCostCollector.getPP_Order());
		
		final BigDecimal qtyIssuedToRemove = ppCostCollector.getMovementQty();
		final BigDecimal qtyIssued = refExisting.getQtyIssued();
		final BigDecimal qtyIssuedNew = qtyIssued.subtract(qtyIssuedToRemove);

		refExisting.setQtyIssued(qtyIssuedNew);
		InterfaceWrapperHelper.save(refExisting);
	}
}
