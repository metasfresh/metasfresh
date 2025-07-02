package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.location.DocumentLocation;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingSlotSuggestion;
import de.metas.handlingunits.picking.job.model.PickingSlotSuggestions;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.slot.PickingSlotAllocateRequest;
import de.metas.handlingunits.picking.slot.PickingSlotListener;
import de.metas.handlingunits.picking.slot.PickingSlotQueueQuery;
import de.metas.handlingunits.picking.slot.PickingSlotQueuesSummary;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.i18n.BooleanWithReason;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.picking.qrcode.PickingSlotQRCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import java.util.List;
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

	public PickingSlotSuggestions getPickingSlotsSuggestions(@NonNull final Set<DocumentLocation> deliveryLocations)
	{
		if (deliveryLocations.isEmpty())
		{
			return PickingSlotSuggestions.EMPTY;
		}

		final ImmutableMap<BPartnerLocationId, DocumentLocation> deliveryLocationsById = Maps.uniqueIndex(deliveryLocations, DocumentLocation::getBpartnerLocationId);

		final List<I_M_PickingSlot> pickingSlots = pickingSlotService.list(PickingSlotQuery.builder().assignedToBPartnerLocationIds(deliveryLocationsById.keySet()).build());
		if (pickingSlots.isEmpty())
		{
			return PickingSlotSuggestions.EMPTY;
		}

		final PickingSlotQueuesSummary queues = pickingSlotService.getNotEmptyQueuesSummary(PickingSlotQueueQuery.onlyPickingSlotIds(extractPickingSlotIds(pickingSlots)));

		return pickingSlots.stream()
				.map(pickingSlotIdAndCaption -> toPickingSlotSuggestion(pickingSlotIdAndCaption, deliveryLocationsById, queues))
				.collect(PickingSlotSuggestions.collect());
	}

	private static PickingSlotSuggestion toPickingSlotSuggestion(
			@NonNull final I_M_PickingSlot pickingSlot,
			@NonNull final ImmutableMap<BPartnerLocationId, DocumentLocation> deliveryLocationsById,
			@NonNull final PickingSlotQueuesSummary queues)
	{
		final PickingSlotIdAndCaption pickingSlotIdAndCaption = toPickingSlotIdAndCaption(pickingSlot);

		final BPartnerLocationId deliveryLocationId = BPartnerLocationId.ofRepoIdOrNull(pickingSlot.getC_BPartner_ID(), pickingSlot.getC_BPartner_Location_ID());
		final DocumentLocation deliveryLocation = deliveryLocationsById.get(deliveryLocationId);

		return PickingSlotSuggestion.builder()
				.pickingSlotIdAndCaption(pickingSlotIdAndCaption)
				.deliveryLocation(deliveryLocation)
				.countHUs(queues.getCountHUs(pickingSlotIdAndCaption.getPickingSlotId()).orElse(0))
				.build();
	}

	private static ImmutableSet<PickingSlotId> extractPickingSlotIds(final List<I_M_PickingSlot> pickingSlots)
	{
		return pickingSlots.stream()
				.map(pickingSlot -> PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static PickingSlotIdAndCaption toPickingSlotIdAndCaption(@NonNull final I_M_PickingSlot record)
	{
		return PickingSlotIdAndCaption.of(PickingSlotId.ofRepoId(record.getM_PickingSlot_ID()), record.getPickingSlot());
	}

}
