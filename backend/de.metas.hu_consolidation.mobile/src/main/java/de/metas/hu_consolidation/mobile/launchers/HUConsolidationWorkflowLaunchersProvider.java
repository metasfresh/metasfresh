package de.metas.hu_consolidation.mobile.launchers;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.handlingunits.picking.slot.PickingSlotReservation;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.hu_consolidation.mobile.HUConsolidationApplication;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference.HUConsolidationJobReferenceBuilder;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HUConsolidationWorkflowLaunchersProvider
{
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final HUConsolidationLauncherCaptionProviderFactory captionProviderFactory;

	public WorkflowLaunchersList provideLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		final HUConsolidationLauncherCaptionProvider captionProvider = captionProviderFactory.newCaptionProvider();

		final ImmutableList<WorkflowLauncher> launchers = streamNewLaunchers(query)
				.map(reference -> toWorkflowLauncher(reference, captionProvider))
				.collect(ImmutableList.toImmutableList());

		return WorkflowLaunchersList.builder()
				.launchers(launchers)
				.build();
	}

	private Stream<HUConsolidationJobReference> streamNewLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		query.assertNoFilterByDocumentNo();
		query.assertNoFilterByQRCode();
		query.assertNoFacetIds();
		
		final PickingSlotQueues pickingSlotQueues = pickingSlotService.getNotEmptyQueues();
		final List<PickingSlotReservation> pickingSlots = pickingSlotService.getPickingSlotReservations(pickingSlotQueues.getPickingSlotIds());

		final HashMap<HUConsolidationJobReferenceKey, HUConsolidationJobReferenceBuilder> builders = new HashMap<>();
		pickingSlots.forEach(pickingSlot -> {
			final HUConsolidationJobReferenceKey key = extractReferenceKey(pickingSlot);
			if (key == null)
			{
				return;
			}

			final PickingSlotQueue queue = pickingSlotQueues.getQueue(pickingSlot.getPickingSlotId());

			builders.computeIfAbsent(key, HUConsolidationWorkflowLaunchersProvider::newReference)
					.pickingSlotId(pickingSlot.getPickingSlotId())
					.addToCountHUs(queue.getCountHUs());
		});

		return builders.values().stream()
				.map(HUConsolidationJobReferenceBuilder::build);
	}

	@Nullable
	private static HUConsolidationJobReferenceKey extractReferenceKey(final PickingSlotReservation pickingSlot)
	{
		final BPartnerLocationId bpartnerLocationId = pickingSlot.getBpartnerLocationId();
		if (bpartnerLocationId == null)
		{
			return null;
		}

		return HUConsolidationJobReferenceKey.builder()
				.bpartnerLocationId(bpartnerLocationId)
				.build();
	}

	private static HUConsolidationJobReferenceBuilder newReference(final HUConsolidationJobReferenceKey key)
	{
		return HUConsolidationJobReference.builder()
				.bpartnerLocationId(key.getBpartnerLocationId());
	}

	private WorkflowLauncher toWorkflowLauncher(
			@NonNull final HUConsolidationJobReference reference,
			@NonNull final HUConsolidationLauncherCaptionProvider captionProvider)
	{
		return WorkflowLauncher.builder()
				.applicationId(HUConsolidationApplication.APPLICATION_ID)
				.caption(captionProvider.computeCaption(reference))
				.wfParameters(reference.toParams())
				.build();
	}

	//
	//
	//

	@Value
	@Builder
	private static class HUConsolidationJobReferenceKey
	{
		@NonNull BPartnerLocationId bpartnerLocationId;
	}
}
