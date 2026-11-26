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

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inout.QtyPickedRecords;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Gives the picks back that a reversed shipment had taken: reopens the picking jobs of the shipment's rows and, where that
 * restored nothing, re-creates the picked rows from the reversed ones.
 */
@Service
@RequiredArgsConstructor
public class ShipmentReversalPickRestoreService
{
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobRepository pickingJobRepository;

	/**
	 * @param reversedRows the rows that were assigned to the reversed shipment
	 */
	public void restorePickedQtys(@NonNull final QtyPickedRecords reversedRows)
	{
		final ReversedPickedRows rows = new ReversedPickedRows(
				reversedRows,
				pickingJobRepository.getPickedHuIdsByShipmentScheduleIds(reversedRows.extractScheduleIds()));
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
			huShipmentScheduleBL.restoreUnshippedQtyPickedIfMissing(rows.toRowsToRestore(huIdsCoveredByPickingJobs).toList());
		}
	}
}
