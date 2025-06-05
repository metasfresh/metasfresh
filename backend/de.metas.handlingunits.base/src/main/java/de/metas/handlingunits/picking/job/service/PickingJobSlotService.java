package de.metas.handlingunits.picking.job.service;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.slot.PickingSlotAllocateRequest;
import de.metas.handlingunits.picking.slot.PickingSlotListener;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.i18n.BooleanWithReason;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickingJobSlotService implements PickingSlotListener
{
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final PickingJobRepository pickingJobRepository;

	public static PickingJobSlotService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new PickingJobSlotService(
				PickingSlotService.newInstanceForUnitTesting(),
				new PickingJobRepository()
		);
	}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotQRCode pickingSlotQRCode)
	{
		return pickingSlotService.getPickingSlotIdAndCaption(pickingSlotQRCode.getPickingSlotId());
	}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotService.getPickingSlotIdAndCaption(pickingSlotId);
	}

	@NonNull
	public BooleanWithReason allocate(
			@NonNull final PickingSlotIdAndCaption pickingSlot,
			@NonNull final BPartnerLocationId deliveryBPLocationId)
	{
		return pickingSlotService.allocatePickingSlotIfPossible(
				PickingSlotAllocateRequest.builder()
						.pickingSlotId(pickingSlot.getPickingSlotId())
						.bpartnerAndLocationId(deliveryBPLocationId)
						.build());
	}

	public void release(
			@NonNull final PickingSlotId pickingSlotId,
			@NonNull final PickingJobId pickingJobId)
	{
		if (!pickingJobRepository.hasDraftJobsUsingPickingSlot(pickingSlotId, pickingJobId))
		{
			pickingSlotService.releasePickingSlotIfPossible(pickingSlotId);
		}
	}

	@Override
	public boolean hasAllocationsForSlot(@NonNull final PickingSlotId slotId)
	{
		return pickingJobRepository.hasDraftJobsUsingPickingSlot(slotId, null);
	}

	public void addToPickingSlotQueue(@NonNull final PickingSlotId pickingSlotId, @NonNull final Set<HuId> huIds)
	{
		pickingSlotService.addToPickingSlotQueue(pickingSlotId, huIds);
	}

}
