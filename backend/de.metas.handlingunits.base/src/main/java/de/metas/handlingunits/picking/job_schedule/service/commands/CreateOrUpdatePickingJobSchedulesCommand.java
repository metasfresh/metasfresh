package de.metas.handlingunits.picking.job_schedule.service.commands;

import com.google.common.collect.Sets;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ShipmentScheduleLoadingCache;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.repository.PickingJobScheduleCreateRepoRequest;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
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

public class CreateOrUpdatePickingJobSchedulesCommand
{
	private static final String SYSCONFIG_CARRIER_PRODUCT_REQUIRED = "de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet";
	private static final AdMessageKey ERROR_CARRIER_PRODUCT_NOT_SET = AdMessageKey.of("de.metas.handlingunits.picking.job_schedule.CarrierProductNotSet");

	// Services
	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;
	@NonNull private final IHUShipmentScheduleBL shipmentScheduleBL;

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	// Params
	@NonNull private final CreateOrUpdatePickingJobSchedulesRequest request;

	// State
	@NonNull private final ShipmentScheduleLoadingCache<I_M_ShipmentSchedule> shipmentSchedules;

	@Builder
	private CreateOrUpdatePickingJobSchedulesCommand(
			@NonNull final PickingJobScheduleRepository pickingJobScheduleRepository,
			@NonNull final IHUShipmentScheduleBL shipmentScheduleBL,
			@NonNull final CreateOrUpdatePickingJobSchedulesRequest request)
	{
		this.pickingJobScheduleRepository = pickingJobScheduleRepository;
		this.shipmentScheduleBL = shipmentScheduleBL;
		this.request = request;

		this.shipmentSchedules = shipmentScheduleBL.newLoadingCache();
	}

	public void execute()
	{
		final WorkplaceId workplaceId = request.getWorkplaceId();
		final Set<ShipmentScheduleId> shipmentScheduleIds = request.getShipmentScheduleAndJobScheduleIds().getShipmentScheduleIds();
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}
		shipmentSchedules.warmUpByIds(shipmentScheduleIds);

		if(sysConfigBL.getBooleanValue(SYSCONFIG_CARRIER_PRODUCT_REQUIRED, false))
		{
			shipmentSchedules.getByIds(shipmentScheduleIds).forEach(CreateOrUpdatePickingJobSchedulesCommand::assumeCarrierProductSet);
		}

		final ShipmentScheduleAndJobScheduleIdSet existingJobScheduleIds = pickingJobScheduleRepository.getIdsByShipmentScheduleIdsAndWorkplaceId(shipmentScheduleIds, workplaceId);
		final Set<ShipmentScheduleId> shipmentScheduleIds_woJobSchedule = Sets.difference(shipmentScheduleIds, existingJobScheduleIds.getShipmentScheduleIds());

		//
		// Update existing job schedules
		pickingJobScheduleRepository.updateByIds(existingJobScheduleIds.getJobScheduleIds(), jobSchedule -> jobSchedule.toBuilder()
				.workplaceId(workplaceId)
				.qtyToPick(computeQtyToPick(jobSchedule))
				.build());

		//
		// Create missing schedules
		shipmentSchedules.getByIds(shipmentScheduleIds_woJobSchedule)
				.forEach(shipmentSchedule -> {
					final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
					pickingJobScheduleRepository.create(PickingJobScheduleCreateRepoRequest.builder()
							.shipmentScheduleId(shipmentScheduleId)
							.workplaceId(workplaceId)
							.qtyToPick(computeQtyToPick(shipmentSchedule))
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

	private @NonNull Quantity computeQtyToPick(final PickingJobSchedule jobSchedule)
	{
		final I_C_UOM uom = jobSchedule.getQtyToPick().getUOM();
		final BigDecimal qtyToPickBD = request.getQtyToPickBD();
		if (qtyToPickBD != null)
		{
			return Quantity.of(qtyToPickBD, uom);
		}
		else
		{
			return jobSchedule.getQtyToPick();
		}
	}

	private Quantity computeQtyToPick(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final Quantity qtyRemainingToScheduleForPicking = shipmentScheduleBL.getQtyRemainingToScheduleForPicking(shipmentSchedule);

		final BigDecimal qtyToPickBD = request.getQtyToPickBD();
		if (qtyToPickBD != null)
		{
			return Quantity.of(qtyToPickBD, qtyRemainingToScheduleForPicking.getUOM());
		}
		else
		{
			return qtyRemainingToScheduleForPicking;
		}
	}
}
