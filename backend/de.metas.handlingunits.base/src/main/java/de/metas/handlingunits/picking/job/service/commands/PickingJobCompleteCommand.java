package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderBL;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

import static de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;

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
	@NonNull private final IShipmentService shipmentService;

	@NonNull private final PickingJob initialPickingJob0;

	// State
	private PickingJob pickingJob = null;

	@Builder
	private PickingJobCompleteCommand(
			final @NonNull MobileUIPickingUserProfileRepository configRepository,
			final @NonNull PickingJobService pickingJobService,
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobLockService pickingJobLockService,
			final @NonNull PickingJobSlotService pickingSlotService,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			final @NonNull IShipmentService shipmentService,
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
		pickingJob = initialPickingJob0;

		ppOrderBL.closeOrdersByIds(pickingJob.getManufacturingOrderIds());

		pickingJob = pickingJob.withDocStatus(PickingJobDocStatus.Completed);
		pickingJobRepository.save(pickingJob);

		pickingJob = pickingJobService.closeAllLUPickingTargets(pickingJob);

		final PickingJobId pickingJobId = pickingJob.getId();
		pickingJob.getPickingSlotId()
				.ifPresent(pickingSlotId -> pickingSlotService.release(pickingSlotId, pickingJobId));

		pickingJobHUReservationService.releaseAllReservations(pickingJob);

		pickingJobLockService.unlockShipmentSchedules(pickingJob);

		createShipmentIfNeeded();

		return pickingJob;
	}

	private void createShipmentIfNeeded()
	{
		prepareShipmentCandidates(pickingJob).forEach(this::processShipmentCandidate);
	}

	private static Collection<ShipmentCandidate> prepareShipmentCandidates(final PickingJob pickingJob)
	{
		final LinkedHashMap<ShipmentCandidateKey, ShipmentCandidate> shipmentCandidates = new LinkedHashMap<>();
		for (final PickingJobLine line : pickingJob.getLines())
		{
			shipmentCandidates.computeIfAbsent(ShipmentCandidateKey.of(line), ShipmentCandidate::new)
					.addLine(line);
		}

		return shipmentCandidates.values();
	}

	private void processShipmentCandidate(final ShipmentCandidate shipmentCandidate)
	{
		final PickingJobOptions pickingJobOptions = configRepository.getPickingJobOptions(shipmentCandidate.getCustomerId());
		final CreateShipmentPolicy createShipmentPolicy = pickingJobOptions.getCreateShipmentPolicy();

		if (createShipmentPolicy.isCreateShipment())
		{
			shipmentService.generateShipmentsForScheduleIds(GenerateShipmentsForSchedulesRequest.builder()
					.scheduleIds(shipmentCandidate.getShipmentScheduleIds())
					.quantityTypeToUse(TYPE_PICKED_QTY)
					.onTheFlyPickToPackingInstructions(true)
					.isCompleteShipment(createShipmentPolicy.isCreateAndCompleteShipment())
					.isCloseShipmentSchedules(createShipmentPolicy.isCloseShipmentSchedules())
					// since we are not going to immediately create invoices, we want to move on and to not wait for shipments
					.waitForShipments(false)
					.build());
		}
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

	//
	//
	//
	//
	//

	@Value
	private static class ShipmentCandidateKey
	{
		@NonNull BPartnerId customerId;

		public static ShipmentCandidateKey of(final PickingJobLine line)
		{
			return new ShipmentCandidateKey(line.getCustomerId());
		}
	}

	//
	//
	//
	//
	//

	@RequiredArgsConstructor
	private static class ShipmentCandidate
	{
		@Delegate
		@NonNull private final ShipmentCandidateKey key;
		@NonNull private final HashSet<ShipmentScheduleId> shipmentScheduleIds = new HashSet<>();

		public void addLine(@NonNull final PickingJobLine line)
		{
			shipmentScheduleIds.add(line.getShipmentScheduleId());
		}

		public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
		{
			return ImmutableSet.copyOf(shipmentScheduleIds);
		}
	}

}
