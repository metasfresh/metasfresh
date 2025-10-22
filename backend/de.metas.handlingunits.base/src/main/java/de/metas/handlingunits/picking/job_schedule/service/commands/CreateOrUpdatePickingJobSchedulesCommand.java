package de.metas.handlingunits.picking.job_schedule.service.commands;

import com.google.common.collect.Sets;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.job_schedule.repository.PickingJobScheduleCreateRepoRequest;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public class CreateOrUpdatePickingJobSchedulesCommand
{
	private static final String SYS_CFG_CARRIER_PRODUCT_REQUIRED = "de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet";
	private static final AdMessageKey ERROR_CARRIER_PRODUCT_NOT_SET = AdMessageKey.of("de.metas.handlingunits.picking.job_schedule.CarrierProductNotSet");

	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;
	@NonNull private final IHUShipmentScheduleBL shipmentScheduleBL;

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

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

		if(sysConfigBL.getBooleanValue(SYS_CFG_CARRIER_PRODUCT_REQUIRED, false))
		{
			shipmentScheduleBL.getByIds(shipmentScheduleIds).values().forEach(CreateOrUpdatePickingJobSchedulesCommand::assumeCarrierProductSet);
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
		
		shipmentScheduleBL.flagForRecompute(shipmentScheduleIds);
	}

	private static void assumeCarrierProductSet(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		if(CarrierProductId.ofRepoIdOrNull(shipmentSchedule.getCarrier_Product_ID()) == null)
		{
			throw new AdempiereException(ERROR_CARRIER_PRODUCT_NOT_SET, shipmentSchedule.getM_ShipmentSchedule_ID());
		}
	}
}
