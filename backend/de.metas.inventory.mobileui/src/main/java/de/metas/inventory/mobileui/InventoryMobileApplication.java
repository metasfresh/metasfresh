package de.metas.inventory.mobileui;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.document.engine.IDocument;
import de.metas.i18n.TranslatableStrings;
import de.metas.inventory.InventoryId;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobId;
import de.metas.inventory.mobileui.job.InventoryJobService;
import de.metas.inventory.mobileui.launchers.InventoryWFProcessStartParams;
import de.metas.inventory.mobileui.launchers.InventoryWorkflowLaunchersProvider;
import de.metas.inventory.mobileui.workflows_api.activity_handlers.CompleteWFActivityHandler;
import de.metas.inventory.mobileui.workflows_api.activity_handlers.InventoryJobWFActivityHandler;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.service.WorkflowBasedMobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.UnaryOperator;

@Component
@RequiredArgsConstructor
public class InventoryMobileApplication implements WorkflowBasedMobileApplication
{
	@VisibleForTesting
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("inventory");

	@NonNull private final InventoryWorkflowLaunchersProvider launchersProvider;
	@NonNull private final InventoryJobService jobService;

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}

	@Override
	public WorkflowLaunchersList provideLaunchers(final WorkflowLaunchersQuery query) {return launchersProvider.provideLaunchers(query);}

	@Override
	public WFProcess startWorkflow(final WorkflowStartRequest request)
	{
		final UserId invokerId = request.getInvokerId();
		final InventoryId inventoryId = InventoryWFProcessStartParams.ofParams(request.getWfParameters()).getInventoryId();

		final InventoryJob job = jobService.startJob(inventoryId, invokerId);
		return toWFProcess(job);

	}

	@Override
	public WFProcess continueWorkflow(final WFProcessId wfProcessId, final UserId callerId)
	{
		final InventoryJobId jobId = InventoryJobId.ofWFProcessId(wfProcessId);
		final InventoryJob job = jobService.assignJob(jobId, callerId);
		return toWFProcess(job);
	}

	@Override
	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		final InventoryJobId jobId = InventoryJobId.ofWFProcessId(wfProcessId);
		jobService.abort(jobId, callerId);
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		jobService.abortAll(callerId);
	}

	@Override
	public WFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final InventoryJobId jobId = InventoryJobId.ofWFProcessId(wfProcessId);
		final InventoryJob job = jobService.getJobById(jobId);
		return toWFProcess(job);
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(final @NonNull WFProcess wfProcess)
	{
		return WFProcessHeaderProperties.EMPTY;
	}

	private static WFProcess toWFProcess(final InventoryJob job)
	{
		return WFProcess.builder()
				.id(job.getId().toWFProcessId())
				.responsibleId(job.getResponsibleId())
				.document(job)
				.activities(ImmutableList.of(
						WFActivity.builder()
								.id(WFActivityId.ofString("A1"))
								.caption(TranslatableStrings.empty())
								.wfActivityType(InventoryJobWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.status(InventoryJobWFActivityHandler.computeActivityState(job))
								.build(),
						WFActivity.builder()
								.id(WFActivityId.ofString("A2"))
								.caption(TranslatableStrings.adRefList(IDocument.ACTION_AD_Reference_ID, IDocument.ACTION_Complete))
								.wfActivityType(CompleteWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.status(CompleteWFActivityHandler.computeActivityState(job))
								.build()
				))
				.build();
	}

	@NonNull
	public static InventoryJob getInventoryJob(final @NonNull WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(InventoryJob.class);
	}

	public static WFProcess mapJob(@NonNull final WFProcess wfProcess, @NonNull final UnaryOperator<InventoryJob> mapper)
	{
		final InventoryJob job = getInventoryJob(wfProcess);
		final InventoryJob jobChanged = mapper.apply(job);
		return !Objects.equals(job, jobChanged) ? toWFProcess(jobChanged) : wfProcess;
	}

}
