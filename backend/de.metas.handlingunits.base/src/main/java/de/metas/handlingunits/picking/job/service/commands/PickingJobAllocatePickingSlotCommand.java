package de.metas.handlingunits.picking.job.service.commands;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

public class PickingJobAllocatePickingSlotCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	private final PickingJobRepository pickingJobRepository;
	@NonNull
	private final PickingJobSlotService pickingSlotService;

	private final PickingJob initialPickingJob;
	private final PickingSlotQRCode pickingSlotQRCode;
	private final PickingSlotId pickingSlotId;

	private final boolean failIfNotAllocated;

	@Builder
	private PickingJobAllocatePickingSlotCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobSlotService pickingSlotService,
			//
			@NonNull final PickingJob pickingJob,
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
				.orElseGet(() -> pickingSlotService.getPickingSlotIdAndCaption(pickingSlotId));

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

		final BPartnerLocationId deliveryBPLocationId = initialPickingJob.getDeliveryBPLocationId();
		final BooleanWithReason allocated = deliveryBPLocationId != null
				? pickingSlotService.allocate(newPickingSlot, deliveryBPLocationId)
				: BooleanWithReason.TRUE; // TODO implement a way to reserve a picking slot for a picking job

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
	}
}
