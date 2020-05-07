package de.metas.materialtracking.spi.impl.listeners;

import static java.math.BigDecimal.ZERO;

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

import java.math.BigDecimal;
import java.util.List;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery.Aggregate;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingListenerAdapter;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import lombok.NonNull;

public final class PPCostCollectorMaterialTrackingListener extends MaterialTrackingListenerAdapter
{
	public static final transient PPCostCollectorMaterialTrackingListener instance = new PPCostCollectorMaterialTrackingListener();

	private PPCostCollectorMaterialTrackingListener()
	{
	}

	@Override
	public void afterModelLinked(@NonNull final MTLinkRequest request)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		final I_PP_Cost_Collector ppCostCollector = InterfaceWrapperHelper.create(request.getModel(), I_PP_Cost_Collector.class);

		// Applies only on issue collectors
		if (!ppCostCollectorBL.isMaterialIssue(ppCostCollector, false))
		{
			return;
		}

		final I_M_Material_Tracking materialTracking = request.getMaterialTracking();

		// sum up only for the same product
		if (materialTracking.getM_Product_ID() != ppCostCollector.getM_Product_ID())
		{
			return;
		}

		resetQtyIssuedForMaterialTracking(materialTracking);
		resetQtyIssuedForPPOrder(materialTracking, ppCostCollector);
	}

	@Override
	public void afterModelUnlinked(
			@NonNull final Object model,
			@NonNull final I_M_Material_Tracking materialTrackingOld)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		final I_PP_Cost_Collector ppCostCollector = InterfaceWrapperHelper.create(model, I_PP_Cost_Collector.class);

		// Applies only on issue collectors
		if (!ppCostCollectorBL.isMaterialIssue(ppCostCollector, false))
		{
			return;
		}

		// sum up only for the same product
		if (materialTrackingOld.getM_Product_ID() == ppCostCollector.getM_Product_ID())
		{
			resetQtyIssuedForMaterialTracking(materialTrackingOld);
			resetQtyIssuedForPPOrder(materialTrackingOld, ppCostCollector);
		}
	}

	@Override
	public void afterQtyIssuedChanged(
			@NonNull final I_M_Material_Tracking_Ref materialTrackingRef,
			@NonNull final BigDecimal oldValue)
	{
		final I_M_Material_Tracking materialTracking = materialTrackingRef.getM_Material_Tracking();

		final I_PP_Cost_Collector ppCostCollector = TableRecordReference
				.ofReferenced(materialTrackingRef)
				.getModel(I_PP_Cost_Collector.class);

		resetQtyIssuedForMaterialTracking(materialTracking);
		resetQtyIssuedForPPOrder(materialTracking, ppCostCollector);
	}

	/**
	 * Compute total qty issued for *all* manufacturing orders and set the sum in material tracking
	 */
	private void resetQtyIssuedForMaterialTracking(@NonNull final I_M_Material_Tracking materialTracking)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final int costCollectorTableId = adTableDAO.retrieveTableId(I_PP_Cost_Collector.Table_Name);

		final BigDecimal qtyIssuedNew = Services.get(IQueryBL.class).createQueryBuilder(I_M_Material_Tracking_Ref.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_M_Material_Tracking_ID, materialTracking.getM_Material_Tracking_ID())

				// PP_Order's M_Material_Tracking_Refs also have their QtyIssued set, but that's just an aggregation of the cost collector's ref's qty.
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_AD_Table_ID, costCollectorTableId)
				.create()
				.aggregate(I_M_Material_Tracking_Ref.COLUMNNAME_QtyIssued, Aggregate.SUM, BigDecimal.class);

		materialTracking.setQtyIssued(qtyIssuedNew);
		InterfaceWrapperHelper.save(materialTracking);
	}

	/**
	 * Compute total qty issued for a specific manufacturing order and a given material tracking and set the sum in the order's material tracking ref.
	 */
	private void resetQtyIssuedForPPOrder(
			@NonNull final I_M_Material_Tracking materialTracking,
			@NonNull final I_PP_Cost_Collector ppCostCollector)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		BigDecimal qtyIssuedNew = ZERO;

		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
		final List<I_PP_Cost_Collector> costCollectors = ppCostCollectorDAO.retrieveNotReversedForOrder(ppCostCollector.getPP_Order());
		for (final I_PP_Cost_Collector currentCostCollectorRecord : costCollectors)
		{
			// TODO select the refs for all cost collectors at once
			final I_M_Material_Tracking_Ref ref = materialTrackingDAO.retrieveMaterialTrackingRefFor(currentCostCollectorRecord, materialTracking);
			if (ref != null)
			{
				qtyIssuedNew = qtyIssuedNew.add(ref.getQtyIssued());
			}
		}

		final I_M_Material_Tracking_Ref refToUpdate = materialTrackingDAO.retrieveMaterialTrackingRefFor(ppCostCollector.getPP_Order(), materialTracking);

		refToUpdate.setQtyIssued(qtyIssuedNew);
		InterfaceWrapperHelper.save(refToUpdate);
	}
}
