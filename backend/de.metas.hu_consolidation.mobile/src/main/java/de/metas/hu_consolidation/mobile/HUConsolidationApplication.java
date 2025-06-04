package de.metas.hu_consolidation.mobile;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.IDocument;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.hu_consolidation.mobile.job.commands.consolidate.ConsolidateRequest;
import de.metas.hu_consolidation.mobile.launchers.HUConsolidationWorkflowLaunchersProvider;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonConsolidateRequest;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJobAvailableTargets;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationTarget;
import de.metas.hu_consolidation.mobile.workflows_api.activity_handlers.CompleteWFActivityHandler;
import de.metas.hu_consolidation.mobile.workflows_api.activity_handlers.HUConsolidateWFActivityHandler;
import de.metas.i18n.TranslatableStrings;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.service.WorkflowBasedMobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Component
@RequiredArgsConstructor
public class HUConsolidationApplication implements WorkflowBasedMobileApplication
{
	@VisibleForTesting
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("huConsolidation");

	@NonNull private final HUConsolidationWorkflowLaunchersProvider launchersProvider;
	@NonNull private final HUConsolidationJobService jobService;
	@NonNull private final IDocumentLocationBL documentLocationBL;

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}

	@Override
	public WorkflowLaunchersList provideLaunchers(final WorkflowLaunchersQuery query) {return launchersProvider.provideLaunchers(query);}

	@Override
	public WFProcess startWorkflow(final WorkflowStartRequest request)
	{
		final UserId invokerId = request.getInvokerId();
		final HUConsolidationJobReference reference = HUConsolidationJobReference.ofParams(request.getWfParameters());

		final HUConsolidationJob job = jobService.startJob(reference, invokerId);
		return toWFProcess(job);
	}

	@Override
	public WFProcess continueWorkflow(final WFProcessId wfProcessId, final UserId callerId)
	{
		final HUConsolidationJobId jobId = HUConsolidationJobId.ofWFProcessId(wfProcessId);
		final HUConsolidationJob job = jobService.assignJob(jobId, callerId);
		return toWFProcess(job);
	}

	@Override
	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		final HUConsolidationJobId jobId = HUConsolidationJobId.ofWFProcessId(wfProcessId);
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
		final HUConsolidationJobId jobId = HUConsolidationJobId.ofWFProcessId(wfProcessId);
		final HUConsolidationJob job = jobService.getJobById(jobId);
		return toWFProcess(job);
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(final @NonNull WFProcess wfProcess)
	{
		final HUConsolidationJob job = wfProcess.getDocumentAs(HUConsolidationJob.class);
		final BPartnerLocationId shipToBPLocationId = job.getShipToBPLocationId();
		@NonNull final String shipToAddress = documentLocationBL.newRenderedAddressProvider().getAddress(shipToBPLocationId);

		return WFProcessHeaderProperties.builder()
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("BPartnerAddress"))
						.value(shipToAddress)
						.build())
				.build();
	}

	private static WFProcess toWFProcess(final HUConsolidationJob job)
	{
		return WFProcess.builder()
				.id(job.getId().toWFProcessId())
				.responsibleId(job.getResponsibleId())
				.document(job)
				.activities(ImmutableList.of(
						WFActivity.builder()
								.id(WFActivityId.ofString("A1"))
								.caption(TranslatableStrings.anyLanguage("Consolidate"))
								.wfActivityType(HUConsolidateWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.status(HUConsolidateWFActivityHandler.computeActivityState(job))
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
	public static HUConsolidationJob getHUConsolidationJob(final @NonNull WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(HUConsolidationJob.class);
	}

	public static WFProcess mapJob(@NonNull final WFProcess wfProcess, @NonNull final UnaryOperator<HUConsolidationJob> mapper)
	{
		final HUConsolidationJob job = getHUConsolidationJob(wfProcess);
		final HUConsolidationJob jobChanged = mapper.apply(job);
		return !Objects.equals(job, jobChanged)
				? toWFProcess(jobChanged)
				: wfProcess;
	}

	public JsonHUConsolidationJobAvailableTargets getAvailableTargets(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final UserId callerId)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		wfProcess.assertHasAccess(callerId);

		final HUConsolidationJob job = getHUConsolidationJob(wfProcess);

		return JsonHUConsolidationJobAvailableTargets.builder()
				.targets(jobService.getAvailableTargets(job)
						.stream()
						.map(JsonHUConsolidationTarget::of)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public WFProcess setTarget(
			@NonNull final WFProcessId wfProcessId,
			@Nullable final HUConsolidationTarget target,
			@NonNull final UserId callerId)
	{
		final HUConsolidationJob job = jobService.setTarget(HUConsolidationJobId.ofWFProcessId(wfProcessId), target, callerId);
		return toWFProcess(job);
	}

	public WFProcess closeTarget(
			@NonNull final WFProcessId wfProcessId,
			final @NotNull UserId callerId)
	{
		final HUConsolidationJob job = jobService.closeTarget(HUConsolidationJobId.ofWFProcessId(wfProcessId), callerId);
		return toWFProcess(job);
	}

	public WFProcess consolidate(@NonNull final JsonConsolidateRequest request, @NonNull final UserId callerId)
	{
		final HUConsolidationJob job = jobService.consolidate(ConsolidateRequest.builder()
				.callerId(callerId)
				.jobId(HUConsolidationJobId.ofWFProcessId(request.getWfProcessIdNotNull()))
				.fromPickingSlotId(request.getFromPickingSlotId())
				.build());
		return toWFProcess(job);
	}
}
