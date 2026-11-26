package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.picking.job.service.HUWithPickOnTheFlyStatus;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.ReopenPickingJobRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

/**
 * Gives the picks back that a reversed shipment had taken: reopens the picking jobs of the shipment's rows and, where that
 * restored nothing, re-creates the picked rows from the reversed ones.
 */
@Service
@RequiredArgsConstructor
public class ShipmentReversalPickRestoreService
{
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

	@NonNull private final PickingJobService pickingJobService;

	/**
	 * @param reversedRows the rows that were assigned to the reversed shipment
	 */
	public void restorePickedQtys(@NonNull final List<I_M_ShipmentSchedule_QtyPicked> reversedRows)
	{
		final ReversedPickedRows rows = new ReversedPickedRows(
				reversedRows,
				pickingJobService.getPickedHuIdsByShipmentScheduleIds(ReversedPickedRows.extractScheduleIds(reversedRows)));
		pickingJobService.reopenPickingJobs(rows.toReopenRequest());

		// Safety net: the reopen above only restores picked qty for a *Completed* picking job. When the shipment
		// was recreated via "Generate Shipments" the job is left Drafted, so a subsequent reverse restores nothing
		// and the shipment can no longer be recreated. Re-create the picked rows directly from the
		// just-reversed rows for any (schedule, CU) the reopen did not restore.
		//
		// Gate this to rows covered by a picking job: a QtyToDeliver / on-the-fly shipment has
		// NO picking job and its reverse must instead clear the HU's BPartner + return it to Active stock (so it
		// can be shipped to a different customer): that behaviour must stay untouched.
		final ImmutableSet<HuId> huIdsCoveredByPickingJobs = pickingJobService.getHuIdsCoveredByPickingJobs(rows.getScheduleIds());
		if (!huIdsCoveredByPickingJobs.isEmpty())
		{
			huShipmentScheduleBL.restoreUnshippedQtyPickedIfMissing(rows.toRowsToRestore(huIdsCoveredByPickingJobs));
		}
	}
}
