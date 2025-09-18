package de.metas.handlingunits.picking.job_schedule.service.commands;

import com.google.common.collect.Sets;
import de.metas.handlingunits.picking.job_schedule.repository.PickingJobScheduleCreateRepoRequest;
import de.metas.handlingunits.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.quantity.Quantity;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public class CreateOrUpdatePickingJobSchedulesCommand
{
	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;
	@NonNull private final IHUShipmentScheduleBL shipmentScheduleBL;

	@NonNull private final CreateOrUpdatePickingJobSchedulesRequest request;

	public void execute()
	{
		final WorkplaceId workplaceId = request.getWorkplaceId();
		final BigDecimal qtyToPickBD = request.getQtyToPickBD();
		final Set<ShipmentScheduleId> shipmentScheduleIds = request.getShipmentScheduleAndJobScheduleIds().getShipmentScheduleIds();
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}

		final ShipmentScheduleAndJobScheduleIdSet existingJobScheduleIds = pickingJobScheduleRepository.getIdsByShipmentScheduleIdsAndWorkplaceId(shipmentScheduleIds, workplaceId);
		final Set<ShipmentScheduleId> shipmentScheduleIds_woJobSchedule = Sets.difference(shipmentScheduleIds, existingJobScheduleIds.getShipmentScheduleIds());

		//
		// Update existing job schedules
		pickingJobScheduleRepository.updateByIds(existingJobScheduleIds.getJobScheduleIds(), jobSchedule -> jobSchedule.toBuilder()
				.workplaceId(workplaceId)
				.qtyToPick(Quantity.of(qtyToPickBD, jobSchedule.getQtyToPick().getUOM()))
				.build());

		//
		// Create missing schedules
		shipmentScheduleBL.getByIds(shipmentScheduleIds_woJobSchedule)
				.values()
				.forEach(shipmentSchedule -> {
					final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
					final I_C_UOM uom = shipmentScheduleBL.getQtyToDeliver(shipmentSchedule).getUOM();
					pickingJobScheduleRepository.create(PickingJobScheduleCreateRepoRequest.builder()
							.shipmentScheduleId(shipmentScheduleId)
							.workplaceId(workplaceId)
							.qtyToPick(Quantity.of(qtyToPickBD, uom))
							.build());
				});

	}
}
