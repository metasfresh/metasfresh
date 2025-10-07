package de.metas.inventory.mobileui.launchers;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.i18n.TranslatableStrings;
import de.metas.inventory.InventoryQuery;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncher.WorkflowLauncherBuilder;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Stream;

import static de.metas.inventory.mobileui.InventoryMobileApplication.APPLICATION_ID;
import static de.metas.inventory.mobileui.mappers.InventoryWFProcessMapper.toWFProcessId;

@Service
@RequiredArgsConstructor
public class InventoryWorkflowLaunchersProvider
{
	@NonNull private final InventoryService inventoryService;
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final InventoryJobReferenceMapperService mapperService;

	public WorkflowLaunchersList provideLaunchers(final WorkflowLaunchersQuery query)
	{
		query.assertNoFilterByDocumentNo();
		query.assertNoFilterByQRCode();
		query.assertNoFacetIds();

		final UserId userId = query.getUserId();
		final Workplace workplace = workplaceService.getWorkplaceByUserId(userId).orElse(null);
		final WarehouseId warehouseId = workplace != null ? workplace.getWarehouseId() : null;
		final QueryLimit limit = query.getLimit().orElse(QueryLimit.NO_LIMIT);

		//
		// Already started jobs
		final ArrayList<InventoryJobReference> jobReferences = new ArrayList<>();
		streamReferences(InventoryQuery.builder()
				.limit(limit)
				.onlyDraftOrInProgress()
				//.onlyWarehouseIdOrAny(warehouseId)
				.onlyResponsibleId(userId)
				.build())
				.forEach(jobReferences::add);

		//
		// New jobs
		if (limit.isBelowLimit(jobReferences))
		{
			streamReferences(InventoryQuery.builder()
					.limit(limit.minusSizeOf(jobReferences))
					.onlyDraftOrInProgress()
					.onlyWarehouseIdOrAny(warehouseId)
					.noResponsibleId()
					.excludeInventoryIdsOf(jobReferences, InventoryJobReference::getInventoryId)
					.build())
					.forEach(jobReferences::add);
		}

		//
		return WorkflowLaunchersList.builder()
				.launchers(jobReferences.stream()
						.map(InventoryWorkflowLaunchersProvider::toWorkflowLauncher)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public Stream<InventoryJobReference> streamReferences(@NonNull final InventoryQuery query)
	{
		return mapperService.toInventoryJobReferencesStream(inventoryService.streamReferences(query));
	}

	private static WorkflowLauncher toWorkflowLauncher(final InventoryJobReference jobRef)
	{
		final WorkflowLauncherBuilder builder = WorkflowLauncher.builder()
				.applicationId(APPLICATION_ID)
				.caption(computeCaption(jobRef));

		if (jobRef.isJobStarted())
		{
			final WFProcessId wfProcessId = toWFProcessId(jobRef.getInventoryId());
			return builder.startedWFProcessId(wfProcessId).build();
		}
		else
		{
			return builder.wfParameters(InventoryWFProcessStartParams.of(jobRef).toParams()).build();
		}
	}

	private static @NonNull WorkflowLauncherCaption computeCaption(final InventoryJobReference jobRef)
	{
		return WorkflowLauncherCaption.of(
				TranslatableStrings.builder()
						.append(jobRef.getWarehouseName())
						.append(" | ")
						.appendDate(jobRef.getMovementDate())
						.append(" | ")
						.append(jobRef.getDocumentNo())
						.build()
		);
	}

}
