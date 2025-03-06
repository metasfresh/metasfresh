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
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

import static de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;

public class PickingJobCompleteCommand
{
	private final static AdMessageKey PICKING_ON_ALL_STEPS_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final MobileUIPickingUserProfileRepository configRepository;
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IShipmentService shipmentService;

	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final CreateShipmentPolicy createShipmentPolicy;
	private final boolean approveIfReadyToReview;

	private static final AdMessageKey MSG_NotApproved = AdMessageKey.of("NotApproved");

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
			final @NonNull PickingJob pickingJob,
			final boolean approveIfReadyToReview,
			final @Nullable CreateShipmentPolicy createShipmentPolicy)
	{
		this.configRepository = configRepository;
		this.pickingJobService = pickingJobService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingSlotService = pickingSlotService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.shipmentService = shipmentService;

		this.initialPickingJob = pickingJob;
		this.approveIfReadyToReview = approveIfReadyToReview;
		this.createShipmentPolicy = createShipmentPolicy != null ? createShipmentPolicy : CreateShipmentPolicy.DO_NOT_CREATE;
	}

	public static class PickingJobCompleteCommandBuilder
	{
		public PickingJob execute()
		{
			return build().execute();
		}
	}

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		initialPickingJob.assertNotProcessed();
		if (!initialPickingJob.getProgress().isDone())
		{
			throw new AdempiereException(PICKING_ON_ALL_STEPS_ERROR_MSG);
		}

		PickingJob pickingJob = initialPickingJob;
		if (approveIfReadyToReview
				&& !pickingJob.isApproved()
				&& pickingJob.isReadyToReview())
		{
			pickingJob = pickingJob.withApproved();
		}

		if (pickingJob.isPickingReviewRequired() && !pickingJob.isApproved())
		{
			final ITranslatableString msg_notapproved = msgBL.getTranslatableMsgText(MSG_NotApproved, pickingJob.getPickingSlot(), pickingJob.getCustomerName());
			throw new AdempiereException(msg_notapproved);
		}

		pickingJob = pickingJob.withDocStatus(PickingJobDocStatus.Completed);
		pickingJobRepository.save(pickingJob);

		pickingJob = pickingJobService.closeAllLUPickingTargets(pickingJob);

		final PickingJobId pickingJobId = pickingJob.getId();
		pickingJob.getPickingSlotId()
				.ifPresent(pickingSlotId -> pickingSlotService.release(pickingSlotId, pickingJobId));

		pickingJobHUReservationService.releaseAllReservations(pickingJob);

		pickingJobLockService.unlockShipmentSchedules(pickingJob);

		createShipmentIfNeeded(pickingJob);

		return pickingJob;
	}

	private void createShipmentIfNeeded(final PickingJob pickingJob)
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
