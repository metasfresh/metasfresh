package de.metas.hu_consolidation.mobile.launchers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.slot.PickingSlotQuery;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.handlingunits.picking.slot.PickingSlotQueuesSummary;
import de.metas.handlingunits.picking.slot.PickingSlotReservation;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.hu_consolidation.mobile.HUConsolidationApplication;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference.HUConsolidationJobReferenceBuilder;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobService;
import de.metas.picking.api.PickingSlotId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HUConsolidationWorkflowLaunchersProvider
{
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final HUConsolidationJobService jobService;
	@NonNull private final HUConsolidationLauncherCaptionProviderFactory captionProviderFactory;

	public WorkflowLaunchersList provideLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		query.assertNoFilterByDocumentNo();
		query.assertNoFilterByQRCode();
		query.assertNoFacetIds();

		final ArrayList<HUConsolidationJobReference> jobReferences = new ArrayList<>();

		//
		// Already started jobs
		jobService.getByNotProcessedAndResponsibleId(query.getUserId())
				.stream()
				.map(HUConsolidationJobReference::ofJob)
				.forEach(jobReferences::add);

		final ImmutableSet<PickingSlotId> pickingSlotIdsAlreadyInUse = jobService.getInUsePickingSlotIds();

		//
		// New possible jobs
		streamNewJobReferences(PickingSlotQuery.builder()
				.excludePickingSlotIds(pickingSlotIdsAlreadyInUse)
				.build())
				.forEach(jobReferences::add);

		return toWorkflowLaunchersList(jobReferences);
	}

	private Stream<HUConsolidationJobReference> streamNewJobReferences(@NonNull final PickingSlotQuery query)
	{
		final PickingSlotQueues pickingSlotQueues = pickingSlotService.getNotEmptyQueues(query);
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

	private WorkflowLaunchersList toWorkflowLaunchersList(final List<HUConsolidationJobReference> jobReferences)
	{
		final PickingSlotQueuesSummary stats = getMissingStats(jobReferences);
		final HUConsolidationLauncherCaptionProvider captionProvider = captionProviderFactory.newCaptionProvider();

		return WorkflowLaunchersList.builder()
				.launchers(jobReferences.stream()
						.map(jobReference -> jobReference.withUpdatedStats(stats))
						.map(jobReference -> WorkflowLauncher.builder()
								.applicationId(HUConsolidationApplication.APPLICATION_ID)
								.caption(captionProvider.computeCaption(jobReference))
								.wfParameters(jobReference.toParams())
								.startedWFProcessId(jobReference.getStartedJobId() != null
										? jobReference.getStartedJobId().toWFProcessId()
										: null)
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private PickingSlotQueuesSummary getMissingStats(final List<HUConsolidationJobReference> jobReferences)
	{
		final ImmutableSet<PickingSlotId> pickingSlotIdsWithoutStats = jobReferences.stream()
				.filter(HUConsolidationJobReference::isStatsMissing)
				.flatMap(jobReference -> jobReference.getPickingSlotIds().stream())
				.collect(ImmutableSet.toImmutableSet());
		if (pickingSlotIdsWithoutStats.isEmpty())
		{
			return PickingSlotQueuesSummary.EMPTY;
		}

		return pickingSlotService.getNotEmptyQueuesSummary(PickingSlotQuery.onlyPickingSlotIds(pickingSlotIdsWithoutStats));
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
