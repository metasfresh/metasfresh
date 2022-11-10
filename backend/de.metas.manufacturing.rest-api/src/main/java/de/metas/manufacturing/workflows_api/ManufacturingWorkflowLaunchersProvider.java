package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.job.model.ManufacturingJobReference;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;

public class ManufacturingWorkflowLaunchersProvider
{
	private final ManufacturingRestService manufacturingRestService;

	public ManufacturingWorkflowLaunchersProvider(
			@NonNull final ManufacturingRestService manufacturingRestService)
	{
		this.manufacturingRestService = manufacturingRestService;
	}

	public WorkflowLaunchersList provideLaunchers(@NonNull final UserId userId, @NonNull final QueryLimit suggestedLimit) {return computeLaunchers(userId, suggestedLimit);}

	private WorkflowLaunchersList computeLaunchers(
			final @NonNull UserId userId,
			final @NonNull QueryLimit limit)
	{
		final ImmutableList<WorkflowLauncher> launchers = manufacturingRestService.streamJobReferencesForUser(userId, limit)
				.map(this::toWorkflowLauncher)
				.collect(ImmutableList.toImmutableList());

		return WorkflowLaunchersList.builder()
				.launchers(launchers)
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private WorkflowLauncher toWorkflowLauncher(final ManufacturingJobReference manufacturingJobReference)
	{
		if (manufacturingJobReference.isJobStarted())
		{
			return WorkflowLauncher.builder()
					.applicationId(ManufacturingMobileApplication.HANDLER_ID)
					.caption(computeCaption(manufacturingJobReference))
					.startedWFProcessId(WFProcessId.ofIdPart(ManufacturingMobileApplication.HANDLER_ID, manufacturingJobReference.getPpOrderId()))
					.build();
		}
		else
		{
			return WorkflowLauncher.builder()
					.applicationId(ManufacturingMobileApplication.HANDLER_ID)
					.caption(computeCaption(manufacturingJobReference))
					.wfParameters(ManufacturingWFProcessStartParams.builder()
							.ppOrderId(manufacturingJobReference.getPpOrderId())
							.build()
							.toParams())
					.build();
		}
	}

	private ITranslatableString computeCaption(@NonNull final ManufacturingJobReference manufacturingJobReference)
	{
		return TranslatableStrings.builder()
				.append(manufacturingJobReference.getDocumentNo())
				.append(" | ")
				.append(manufacturingJobReference.getProductName())
				.append(" | ")
				.appendQty(manufacturingJobReference.getQtyRequiredToProduce().toBigDecimal(), manufacturingJobReference.getQtyRequiredToProduce().getUOMSymbol())
				.append(" | ")
				.appendDateTime(manufacturingJobReference.getDatePromised())
				.build();
	}
}
