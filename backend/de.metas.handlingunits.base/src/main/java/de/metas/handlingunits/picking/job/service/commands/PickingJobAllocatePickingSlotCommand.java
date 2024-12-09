package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
<<<<<<< HEAD
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
=======
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Check;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
<<<<<<< HEAD
=======
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

public class PickingJobAllocatePickingSlotCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
<<<<<<< HEAD
	@NonNull final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobSlotService pickingSlotService;

	private final PickingJob initialPickingJob;
	private final PickingSlotQRCode pickingSlotQRCode;
=======
	@NonNull
	private final PickingJobRepository pickingJobRepository;
	@NonNull
	private final PickingJobSlotService pickingSlotService;

	private final PickingJob initialPickingJob;
	private final PickingSlotQRCode pickingSlotQRCode;
	private final PickingSlotId pickingSlotId;

	private final boolean failIfNotAllocated;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Builder
	private PickingJobAllocatePickingSlotCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobSlotService pickingSlotService,
			//
			@NonNull final PickingJob pickingJob,
<<<<<<< HEAD
			@NonNull final PickingSlotQRCode pickingSlotQRCode)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingSlotService = pickingSlotService;

		this.initialPickingJob = pickingJob;
		this.pickingSlotQRCode = pickingSlotQRCode;
=======
			@Nullable final PickingSlotQRCode pickingSlotQRCode,
			@Nullable final PickingSlotId pickingSlotId,
			@Nullable final Boolean failIfNotAllocated)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingSlotService = pickingSlotService;
		this.initialPickingJob = pickingJob;

		Check.errorIf(pickingSlotQRCode == null && pickingSlotId == null, "One of pickingSlotQRCode or pickingSlotId needs to be not-null");
		Check.errorIf(pickingSlotQRCode != null && pickingSlotId != null, "Only one of pickingSlotQRCode or pickingSlotId needs to be not-null");

		this.pickingSlotQRCode = pickingSlotQRCode;
		this.pickingSlotId = pickingSlotId;
		this.failIfNotAllocated = failIfNotAllocated != null ? failIfNotAllocated : true;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
<<<<<<< HEAD
		// Make sure that scanned picking slot exist in our system
		final PickingSlotIdAndCaption newPickingSlot = pickingSlotService.getPickingSlotIdAndCaption(pickingSlotQRCode);
=======
		// Make sure that provided picking slot exist in our system
		final PickingSlotIdAndCaption newPickingSlot = Optional.ofNullable(pickingSlotQRCode)
				.map(pickingSlotService::getPickingSlotIdAndCaption)
				.orElseGet(() -> pickingSlotService.getPickingSlotIdAndCaption(pickingSlotId));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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

<<<<<<< HEAD
		pickingSlotService.allocate(newPickingSlot, initialPickingJob.getDeliveryBPLocationId());

		final PickingJob pickingJob = initialPickingJob.withPickingSlot(newPickingSlot);
		pickingJobRepository.save(pickingJob);

		return pickingJob;
=======
		final BooleanWithReason allocated = pickingSlotService.allocate(newPickingSlot, initialPickingJob.getDeliveryBPLocationId());

		if (failIfNotAllocated && allocated.isFalse())
		{
			throw new AdempiereException(TranslatableStrings.builder()
												 .append("Failed allocating picking slot ").append(newPickingSlot.getCaption()).append(" because ")
												 .append(allocated.getReason())
												 .build());
		}
		else if (allocated.isTrue())
		{
			final PickingJob pickingJob = initialPickingJob.withPickingSlot(newPickingSlot);
			pickingJobRepository.save(pickingJob);

			return pickingJob;
		}
		else
		{
			return initialPickingJob;
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
