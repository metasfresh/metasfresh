package de.metas.materialtracking.spi.impl.listeners;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;

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
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery.Aggregate;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;

import org.eevolution.api.PPOrderId;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingId;
import de.metas.materialtracking.MaterialTrackingListenerAdapter;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.util.Services;
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
		if (!ppCostCollectorBL.isAnyComponentIssue(ppCostCollector))
		{
			return;
		}

		final I_M_Material_Tracking materialTrackingRecord = request.getMaterialTrackingRecord();

		// sum up only for the same product
		if (materialTrackingRecord.getM_Product_ID() != ppCostCollector.getM_Product_ID())
		{
			return;
		}
		final MaterialTrackingId materialTrackingId = MaterialTrackingId.ofRepoId(materialTrackingRecord.getM_Material_Tracking_ID());
		resetQtyIssuedForMaterialTracking(materialTrackingId);
		resetQtyIssuedForPPOrder(materialTrackingId, ppCostCollector);
	}

	@Override
	public void afterModelUnlinked(
			@NonNull final Object model,
			@NonNull final I_M_Material_Tracking materialTrackingOld)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		final I_PP_Cost_Collector ppCostCollector = InterfaceWrapperHelper.create(model, I_PP_Cost_Collector.class);

		// Applies only on issue collectors
		if (!ppCostCollectorBL.isAnyComponentIssue(ppCostCollector))
		{
			return;
		}

		final MaterialTrackingId materialTrackingOldId = MaterialTrackingId.ofRepoId(materialTrackingOld.getM_Material_Tracking_ID());

		// sum up only for the same product
		if (materialTrackingOld.getM_Product_ID() == ppCostCollector.getM_Product_ID())
		{
			resetQtyIssuedForMaterialTracking(materialTrackingOldId);
			resetQtyIssuedForPPOrder(materialTrackingOldId, ppCostCollector);
		}
	}

	@Override
	public void afterQtyIssuedChanged(
			@NonNull final I_M_Material_Tracking_Ref materialTrackingRef,
			@NonNull final BigDecimal oldValue)
	{
		final MaterialTrackingId materialTrackingId = MaterialTrackingId.ofRepoId(materialTrackingRef.getM_Material_Tracking_ID());

		final I_PP_Cost_Collector ppCostCollector = TableRecordReference
				.ofReferenced(materialTrackingRef)
				.getModel(I_PP_Cost_Collector.class);

		resetQtyIssuedForMaterialTracking(materialTrackingId);
		resetQtyIssuedForPPOrder(materialTrackingId, ppCostCollector);
	}

	/**
	 * Compute total qty issued for *all* manufacturing orders and set the sum in material tracking
	 */
	private void resetQtyIssuedForMaterialTracking(@NonNull final MaterialTrackingId materialTrackingId)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final int costCollectorTableId = adTableDAO.retrieveTableId(I_PP_Cost_Collector.Table_Name);

		final BigDecimal qtyIssuedNew = Services.get(IQueryBL.class).createQueryBuilder(I_M_Material_Tracking_Ref.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_M_Material_Tracking_ID, materialTrackingId)

				// PP_Order's M_Material_Tracking_Refs also have their QtyIssued set, but that's just an aggregation of the cost collector's ref's qty.
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMNNAME_AD_Table_ID, costCollectorTableId)
				.create()
				.aggregate(I_M_Material_Tracking_Ref.COLUMNNAME_QtyIssued, Aggregate.SUM, BigDecimal.class);

		final I_M_Material_Tracking materialTrackingRecord = load(materialTrackingId, I_M_Material_Tracking.class);
		materialTrackingRecord.setQtyIssued(qtyIssuedNew);
		InterfaceWrapperHelper.saveRecord(materialTrackingRecord);
	}

	/**
	 * Compute total qty issued for a specific manufacturing order and a given material tracking and set the sum in the order's material tracking ref.
	 */
	private void resetQtyIssuedForPPOrder(
			@NonNull final MaterialTrackingId materialTrackingId,
			@NonNull final I_PP_Cost_Collector ppCostCollector)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);

		BigDecimal qtyIssuedNew = ZERO;

		final List<I_PP_Cost_Collector> costCollectors = ppCostCollectorDAO.getCompletedOrClosedByOrderId(PPOrderId.ofRepoId(ppCostCollector.getPP_Order_ID()));
		for (final I_PP_Cost_Collector currentCostCollectorRecord : costCollectors)
		{
			// TODO select the refs for all cost collectors at once
			final I_M_Material_Tracking_Ref ref = materialTrackingDAO.retrieveMaterialTrackingRefFor(currentCostCollectorRecord, materialTrackingId);
			if (ref != null)
			{
				qtyIssuedNew = qtyIssuedNew.add(ref.getQtyIssued());
			}
		}

		final I_M_Material_Tracking_Ref refToUpdate = materialTrackingDAO.retrieveMaterialTrackingRefFor(ppCostCollector.getPP_Order(), materialTrackingId);

		refToUpdate.setQtyIssued(qtyIssuedNew);
		InterfaceWrapperHelper.save(refToUpdate);
	}
}
