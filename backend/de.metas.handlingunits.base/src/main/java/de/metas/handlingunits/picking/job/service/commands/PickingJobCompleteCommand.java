package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.job.shipment.PickingShipmentService;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderBL;

public class PickingJobCompleteCommand
{
	private final static AdMessageKey PICKING_ON_ALL_STEPS_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG");
	private final static AdMessageKey NOTHING_HAS_BEEN_PICKED = AdMessageKey.of("de.metas.handlingunits.picking.job.service.commands.NOTHING_HAS_BEEN_PICKED");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final MobileUIPickingUserProfileRepository configRepository;
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final PickingShipmentService shipmentService;

	@NonNull private final PickingJob initialPickingJob0;

	@Builder
	private PickingJobCompleteCommand(
			final @NonNull MobileUIPickingUserProfileRepository configRepository,
			final @NonNull PickingJobService pickingJobService,
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobLockService pickingJobLockService,
			final @NonNull PickingJobSlotService pickingSlotService,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			final @NonNull PickingShipmentService shipmentService,
			//
			final @NonNull PickingJob pickingJob)
	{
		this.configRepository = configRepository;
		this.pickingJobService = pickingJobService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingSlotService = pickingSlotService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.shipmentService = shipmentService;

		this.initialPickingJob0 = pickingJob;
	}

	public static class PickingJobCompleteCommandBuilder
	{
		public PickingJob execute() {return build().execute();}
	}

	public PickingJob execute()
	{
		validateJob();
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		// State
		PickingJob pickingJob = initialPickingJob0;

		ppOrderBL.closeOrdersByIds(pickingJob.getManufacturingOrderIds());

		pickingJob = pickingJob.withDocStatus(PickingJobDocStatus.Completed);
		pickingJobRepository.save(pickingJob);

		pickingJob = pickingJobService.closeAllLUPickingTargets(pickingJob);

		final PickingJobId pickingJobId = pickingJob.getId();
		pickingJob.getPickingSlotId()
				.ifPresent(pickingSlotId -> pickingSlotService.release(pickingSlotId, pickingJobId));

		pickingJobHUReservationService.releaseAllReservations(pickingJob);

		pickingJobLockService.unlockShipmentSchedules(pickingJob);

		shipmentService.createShipmentIfNeeded(pickingJob);

		return pickingJob;
	}

	private void validateJob()
	{
		initialPickingJob0.assertNotProcessed();
		if (initialPickingJob0.isNothingPicked())
		{
			throw new AdempiereException(NOTHING_HAS_BEEN_PICKED);
		}

		final PickingJobOptions options = configRepository.getPickingJobOptions(initialPickingJob0.getCustomerId());
		if (!options.isAllowCompletingPartialPickingJob() && !initialPickingJob0.getProgress().isDone())
		{
			throw new AdempiereException(PICKING_ON_ALL_STEPS_ERROR_MSG);
		}
	}
}
