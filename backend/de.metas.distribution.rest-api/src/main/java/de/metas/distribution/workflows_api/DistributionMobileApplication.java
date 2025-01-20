package de.metas.distribution.workflows_api;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.distribution.workflows_api.activity_handlers.CompleteDistributionWFActivityHandler;
import de.metas.distribution.workflows_api.activity_handlers.MoveWFActivityHandler;
import de.metas.document.engine.IDocument;
import de.metas.i18n.TranslatableStrings;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import de.metas.workflow.rest_api.service.WorkflowBasedMobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

@Component
public class DistributionMobileApplication implements WorkflowBasedMobileApplication
{
	@VisibleForTesting
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("distribution");

	private final DistributionRestService distributionRestService;
	private final DistributionWorkflowLaunchersProvider wfLaunchersProvider;

	public DistributionMobileApplication(
			@NonNull final DistributionRestService distributionRestService)
	{
		this.distributionRestService = distributionRestService;
		this.wfLaunchersProvider = new DistributionWorkflowLaunchersProvider(distributionRestService);

	}

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}

	@Override
	@NonNull
	public MobileApplicationInfo customizeApplicationInfo(@NonNull final MobileApplicationInfo applicationInfo, @NonNull final UserId loggedUserId)
	{
		return applicationInfo.toBuilder()
				.showFilters(true)
				.build();
	}

	@Override
	public WorkflowLaunchersList provideLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		return wfLaunchersProvider.provideLaunchers(query);
	}

	@Override
	public WorkflowLaunchersFacetGroupList getFacets(final WorkflowLaunchersFacetQuery query)
	{
		return wfLaunchersProvider.getFacets(query);
	}

	@Override
	public WFProcess startWorkflow(final WorkflowStartRequest request)
	{
		final UserId invokerId = request.getInvokerId();
		final DistributionWFProcessStartParams params = DistributionWFProcessStartParams.ofParams(request.getWfParameters());
		final DDOrderId ddOrderId = params.getDdOrderId();

		final DistributionJob job = distributionRestService.createJob(ddOrderId, invokerId);
		return toWFProcess(job);
	}

	@Override
	public WFProcess continueWorkflow(@NonNull final WFProcessId wfProcessId, @NonNull final UserId callerId)
	{
		final DDOrderId ddOrderId = toDDOrderId(wfProcessId);
		final DistributionJob job = distributionRestService.assignJob(ddOrderId, callerId);
		return toWFProcess(job);
	}

	private static WFProcess toWFProcess(final DistributionJob job)
	{
		return WFProcess.builder()
				.id(WFProcessId.ofIdPart(APPLICATION_ID, job.getDdOrderId()))
				.responsibleId(job.getResponsibleId())
				.document(job)
				.activities(ImmutableList.of(
						WFActivity.builder()
								.id(WFActivityId.ofString("A1"))
								.caption(TranslatableStrings.anyLanguage("Move"))
								.wfActivityType(MoveWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.status(job.getStatus())
								.build(),
						WFActivity.builder()
								.id(WFActivityId.ofString("A2"))
								.caption(TranslatableStrings.adRefList(IDocument.ACTION_AD_Reference_ID, IDocument.ACTION_Complete))
								.wfActivityType(CompleteDistributionWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.status(CompleteDistributionWFActivityHandler.computeActivityState(job))
								.build()
				))
				.build();
	}

	@Override
	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		distributionRestService.abort(getDistributionJob(wfProcess));
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		distributionRestService.abortAll(callerId);
	}

	@Override
	public WFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final DDOrderId ddOrderId = toDDOrderId(wfProcessId);
		final DistributionJob job = distributionRestService.getJobById(ddOrderId);
		return toWFProcess(job);
	}

	@NonNull
	private static DDOrderId toDDOrderId(final WFProcessId wfProcessId)
	{
		return wfProcessId.getRepoId(DDOrderId::ofRepoId);
	}

	@Override
	public WFProcess changeWFProcessById(final WFProcessId wfProcessId, final UnaryOperator<WFProcess> remappingFunction)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		return remappingFunction.apply(wfProcess);
	}

	private WFProcess changeWFProcessById(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final BiFunction<WFProcess, DistributionJob, DistributionJob> remappingFunction)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		return mapDocument(wfProcess, job -> remappingFunction.apply(wfProcess, job));
	}

	public static WFProcess mapDocument(@NonNull final WFProcess wfProcess, @NonNull final UnaryOperator<DistributionJob> mapper)
	{
		final DistributionJob job = getDistributionJob(wfProcess);
		final DistributionJob jobChanged = mapper.apply(job);
		return !Objects.equals(job, jobChanged)
				? toWFProcess(jobChanged)
				: wfProcess;
	}

	@NonNull
	public static DistributionJob getDistributionJob(final @NonNull WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(DistributionJob.class);
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(final @NonNull WFProcess wfProcess)
	{
		final DistributionJob job = getDistributionJob(wfProcess);
		final WFProcessHeaderProperties.WFProcessHeaderPropertiesBuilder builder = WFProcessHeaderProperties.builder()
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("DocumentNo"))
						.value(job.getDocumentNo())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("DateRequired"))
						.value(job.getDateRequired())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("M_Warehouse_From_ID"))
						.value(job.getPickFromWarehouse().getCaption())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("M_Warehouse_To_ID"))
						.value(job.getDropToWarehouse().getCaption())
						.build());

		if (Check.isNotBlank(job.getSalesOrderDocumentNo()))
		{
			builder.entry(WFProcessHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage("C_Order_DocumentNo"))
					.value(job.getSalesOrderDocumentNo())
					.build());
		}

		if (Check.isNotBlank(job.getPpOrderDocumentNo()))
		{
			builder.entry(WFProcessHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage("PP_Order_DocumentNo"))
					.value(job.getPpOrderDocumentNo())
					.build());
		}

		return builder.build();
	}

	public void processEvent(final JsonDistributionEvent event, final UserId callerId)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(event.getWfProcessId());
		changeWFProcessById(
				wfProcessId,
				(wfProcess, job) -> {
					wfProcess.assertHasAccess(callerId);
					//assertPickingActivityType(jsonEvents, wfProcess);
					return distributionRestService.processEvent(job, event);
				});
	}

	@Override
	public void logout(final @NonNull UserId userId)
	{
		abortAll(userId);
	}
}
