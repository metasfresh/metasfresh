package de.metas.inventory.mobileui.launchers;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import de.metas.inventory.InventoryQuery;
import de.metas.inventory.mobileui.job.InventoryJobId;
import de.metas.inventory.mobileui.job.InventoryJobService;
import de.metas.user.UserId;
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

import static de.metas.inventory.mobileui.InventoryMobileApplication.APPLICATION_ID;

@Service
@RequiredArgsConstructor
public class InventoryWorkflowLaunchersProvider
{
	@NonNull private final InventoryJobService jobService;
	@NonNull private final WorkplaceService workplaceService;

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
		jobService.streamReferences(InventoryQuery.builder()
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
			jobService.streamReferences(InventoryQuery.builder()
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

	private static WorkflowLauncher toWorkflowLauncher(final InventoryJobReference jobRef)
	{
		final WorkflowLauncherBuilder builder = WorkflowLauncher.builder()
				.applicationId(APPLICATION_ID)
				.caption(computeCaption(jobRef));

		if (jobRef.isJobStarted())
		{
			return builder.startedWFProcessId(InventoryJobId.ofInventoryId(jobRef.getInventoryId()).toWFProcessId()).build();
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
