package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

import static de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;

public class PickingJobCompleteCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
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
		public PickingJob execute() {return build().execute();}
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
			throw new AdempiereException("Picking shall be DONE on all steps in order to complete the job");
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

		initialPickingJob.getPickingSlotId()
				.ifPresent(pickingSlotId -> pickingSlotService.release(pickingSlotId, initialPickingJob.getId()));

		pickingJobHUReservationService.releaseAllReservations(pickingJob);

		pickingJobLockService.unlockShipmentSchedules(pickingJob);

		createShipmentIfNeeded(pickingJob);

		return pickingJob;
	}

	private void createShipmentIfNeeded(final PickingJob pickingJob)
	{
		if (createShipmentPolicy.isCreateShipment())
		{
			shipmentService.generateShipmentsForScheduleIds(GenerateShipmentsForSchedulesRequest.builder()
					.scheduleIds(pickingJob.getShipmentScheduleIds())
					.quantityTypeToUse(TYPE_PICKED_QTY)
					.onTheFlyPickToPackingInstructions(true)
					.isCompleteShipment(createShipmentPolicy.isCreateAndCompleteShipment())
					.isCloseShipmentSchedules(createShipmentPolicy.isCloseShipmentSchedules())
					// since we are not going to immediately create invoices, we want to move on and to wait for shipments
					.waitForShipments(false)
					.build());
		}
	}
}
