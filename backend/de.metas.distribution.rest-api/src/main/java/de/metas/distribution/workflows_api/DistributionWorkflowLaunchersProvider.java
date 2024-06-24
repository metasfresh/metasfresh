package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.IOrgDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.api.IWarehouseBL;

import java.util.ArrayList;

class DistributionWorkflowLaunchersProvider
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final DistributionRestService distributionRestService;

	public DistributionWorkflowLaunchersProvider(
			final @NonNull DistributionRestService distributionRestService)
	{
		this.distributionRestService = distributionRestService;
	}

	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit)
	{
		return computeLaunchers(userId, suggestedLimit);
	}

	private WorkflowLaunchersList computeLaunchers(
			final @NonNull UserId userId,
			final @NonNull QueryLimit limit)
	{
		final ArrayList<WorkflowLauncher> currentResult = new ArrayList<>();

		//
		// Already started launchers
		final ImmutableList<DDOrderReference> existingDDOrders = distributionRestService.streamActiveReferencesAssignedTo(userId)
				.collect(ImmutableList.toImmutableList());
		existingDDOrders.stream()
				.map(this::toExistingWorkflowLauncher)
				.forEach(currentResult::add);

		//
		// New launchers
		if (!limit.isLimitHitOrExceeded(currentResult))
		{
			distributionRestService.streamActiveReferencesNotAssigned()
					.limit(limit.minusSizeOf(currentResult).toIntOr(Integer.MAX_VALUE))
					.map(this::toNewWorkflowLauncher)
					.forEach(currentResult::add);
		}

		return WorkflowLaunchersList.builder()
				.launchers(ImmutableList.copyOf(currentResult))
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private WorkflowLauncher toExistingWorkflowLauncher(@NonNull final DDOrderReference ddOrderReference)
	{
		return WorkflowLauncher.builder()
				.applicationId(DistributionMobileApplication.APPLICATION_ID)
				.caption(computeCaption(ddOrderReference))
				.startedWFProcessId(WFProcessId.ofIdPart(DistributionMobileApplication.APPLICATION_ID, ddOrderReference.getDdOrderId()))
				.build();
	}

	private WorkflowLauncher toNewWorkflowLauncher(@NonNull final DDOrderReference ddOrderReference)
	{
		return WorkflowLauncher.builder()
				.applicationId(DistributionMobileApplication.APPLICATION_ID)
				.caption(computeCaption(ddOrderReference))
				.wfParameters(DistributionWFProcessStartParams.builder()
						.ddOrderId(ddOrderReference.getDdOrderId())
						.build()
						.toParams())
				.build();
	}

	private WorkflowLauncherCaption computeCaption(@NonNull final DDOrderReference ddOrderReference)
	{
		return WorkflowLauncherCaption.of(
				TranslatableStrings.builder()
						.append(warehouseBL.getWarehouseName(ddOrderReference.getFromWarehouseId()))
						.append(" > ")
						.append(warehouseBL.getWarehouseName(ddOrderReference.getToWarehouseId()))
						.append(" | ")
						.appendDateTime(ddOrderReference.getDatePromised().toZonedDateTime(orgDAO::getTimeZone))
						.build()
		);
	}

}
