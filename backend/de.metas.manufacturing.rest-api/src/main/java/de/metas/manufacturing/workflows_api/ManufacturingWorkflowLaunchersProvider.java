package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;

import java.time.Duration;
import java.util.ArrayList;

public class ManufacturingWorkflowLaunchersProvider
{
	private final ManufacturingRestService manufacturingRestService;

	public ManufacturingWorkflowLaunchersProvider(
			@NonNull final ManufacturingRestService manufacturingRestService)
	{
		this.manufacturingRestService = manufacturingRestService;
	}

	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit,
			@NonNull final Duration maxStaleAccepted)
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
		final ImmutableList<PPOrderReference> existingPPOrders = manufacturingRestService.streamActiveReferencesAssignedTo(userId)
				.collect(ImmutableList.toImmutableList());
		existingPPOrders.stream()
				.map(this::toExistingWorkflowLauncher)
				.forEach(currentResult::add);

		//
		// New launchers
		if (!limit.isLimitHitOrExceeded(currentResult))
		{
			manufacturingRestService.streamActiveReferencesNotAssigned()
					.limit(limit.minusSizeOf(currentResult).toIntOr(Integer.MAX_VALUE))
					.map(this::toNewWorkflowLauncher)
					.forEach(currentResult::add);
		}

		return WorkflowLaunchersList.builder()
				.launchers(ImmutableList.copyOf(currentResult))
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private WorkflowLauncher toExistingWorkflowLauncher(final PPOrderReference ppOrderReference)
	{
		return WorkflowLauncher.builder()
				.applicationId(ManufacturingMobileApplication.HANDLER_ID)
				.caption(computeCaption(ppOrderReference))
				.startedWFProcessId(WFProcessId.ofIdPart(ManufacturingMobileApplication.HANDLER_ID, ppOrderReference.getPpOrderId()))
				.build();
	}

	private WorkflowLauncher toNewWorkflowLauncher(@NonNull final PPOrderReference ppOrderReference)
	{
		return WorkflowLauncher.builder()
				.applicationId(ManufacturingMobileApplication.HANDLER_ID)
				.caption(computeCaption(ppOrderReference))
				.wfParameters(ManufacturingWFProcessStartParams.builder()
						.ppOrderId(ppOrderReference.getPpOrderId())
						.build()
						.toParams())
				.build();
	}

	private ITranslatableString computeCaption(@NonNull final PPOrderReference ppOrderReference)
	{
		return TranslatableStrings.builder()
				.append(ppOrderReference.getDocumentNo())
				.append(" | ")
				.append(ppOrderReference.getProductName())
				.append(" | ")
				.appendQty(ppOrderReference.getQtyRequiredToProduce().toBigDecimal(), ppOrderReference.getQtyRequiredToProduce().getUOMSymbol())
				.append(" | ")
				.appendDateTime(ppOrderReference.getDatePromised())
				.build();
	}
}
