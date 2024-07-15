package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

import javax.annotation.Nullable;
import java.util.Optional;

public class PickingJobAllocatePickingSlotCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	final PickingJobRepository pickingJobRepository;
	@NonNull
	private final PickingJobSlotService pickingSlotService;

	private final PickingJob initialPickingJob;
	private final PickingSlotQRCode pickingSlotQRCode;
	private final PickingSlotId pickingSlotId;

	@Builder
	private PickingJobAllocatePickingSlotCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobSlotService pickingSlotService,
			//
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingSlotQRCode pickingSlotQRCode,
			@Nullable final PickingSlotId pickingSlotId)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingSlotService = pickingSlotService;
		this.initialPickingJob = pickingJob;
		
		Check.errorIf(pickingSlotQRCode == null && pickingSlotId == null, "One of pickingSlotQRCode or pickingSlotId needs to be not-null");
		Check.errorIf(pickingSlotQRCode != null && pickingSlotId != null, "Only one of pickingSlotQRCode or pickingSlotId needs to be not-null");

		this.pickingSlotQRCode = pickingSlotQRCode;
		this.pickingSlotId = pickingSlotId;
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		// Make sure that provided picking slot exist in our system
		final PickingSlotIdAndCaption newPickingSlot = Optional.ofNullable(pickingSlotQRCode)
				.map(pickingSlotService::getPickingSlotIdAndCaption)
				.orElse(pickingSlotService.getPickingSlotIdAndCaption(pickingSlotId));

		// No picking slot change
		final PickingSlotId oldPickingSlotId = initialPickingJob.getPickingSlotId().orElse(null);
		if (PickingSlotId.equals(newPickingSlot.getPickingSlotId(), oldPickingSlotId))
		{
			return initialPickingJob;
		}

		if (oldPickingSlotId != null)
		{
			pickingSlotService.release(oldPickingSlotId, initialPickingJob.getId());
		}

		pickingSlotService.allocate(newPickingSlot, initialPickingJob.getDeliveryBPLocationId());

		final PickingJob pickingJob = initialPickingJob.withPickingSlot(newPickingSlot);
		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}
}
