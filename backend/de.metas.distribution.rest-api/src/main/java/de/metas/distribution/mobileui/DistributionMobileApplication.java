package de.metas.distribution.mobileui;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig;
import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobId;
import de.metas.distribution.mobileui.job.service.DistributionRestService;
import de.metas.distribution.mobileui.launchers.DistributionWFProcessStartParams;
import de.metas.distribution.mobileui.launchers.DistributionWorkflowLaunchersProvider;
import de.metas.distribution.mobileui.rest_api.json.JsonDistributionEvent;
import de.metas.distribution.mobileui.rest_api.json.JsonDropAllRequest;
import de.metas.distribution.mobileui.rest_api.json.JsonGetNextEligiblePickFromLineRequest;
import de.metas.distribution.mobileui.rest_api.json.JsonGetNextEligiblePickFromLineResponse;
import de.metas.distribution.mobileui.workflows_api.activity_handlers.CompleteDistributionWFActivityHandler;
import de.metas.distribution.mobileui.workflows_api.activity_handlers.MoveWFActivityHandler;
import de.metas.document.engine.IDocument;
import de.metas.i18n.TranslatableStrings;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroupList;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetQuery;
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
import de.metas.workflow.rest_api.service.WorkflowBasedMobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.UnaryOperator;

@Component
@RequiredArgsConstructor
public class DistributionMobileApplication implements WorkflowBasedMobileApplication
{
	// NOTE to dev: keep in sync with misc/services/mobile-webui/mobile-webui-frontend/src/apps/distribution/constants.js  
	@VisibleForTesting
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("distribution");
	private static final WFActivityId ACTIVITY_ID_MoveLines = WFActivityId.ofString("MoveLines");
	private static final WFActivityId ACTIVITY_ID_Complete = WFActivityId.ofString("Complete");

	@NonNull private final DistributionRestService distributionRestService;
	@NonNull private final DistributionWorkflowLaunchersProvider wfLaunchersProvider;

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}

	@Override
	@NonNull
	public MobileApplicationInfo customizeApplicationInfo(@NonNull final MobileApplicationInfo applicationInfo, @NonNull final UserId loggedUserId)
	{
		final MobileUIDistributionConfig config = distributionRestService.getConfig();

		return applicationInfo.toBuilder()
				.requiresTrolley(config.isRequireTrolley())
				.showFilters(true)
				.maxStartedLaunchers(config.getMaxStartedLaunchers())
				.isAllowStartNextJobOnly(config.isAllowStartNextJobOnly())
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
		final DistributionJobId jobId = DistributionJobId.ofWFProcessId(wfProcessId);
		final DistributionJob job = distributionRestService.assignJob(jobId, callerId);
		return toWFProcess(job);
	}

	private static WFProcess toWFProcess(final DistributionJob job)
	{
		return WFProcess.builder()
				.id(job.getId().toWFProcessId())
				.responsibleId(job.getResponsibleId())
				.document(job)
				.activities(ImmutableList.of(
						WFActivity.builder()
								.id(ACTIVITY_ID_MoveLines)
								.caption(TranslatableStrings.anyLanguage("Move"))
								.wfActivityType(MoveWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.status(job.getStatus())
								.build(),
						WFActivity.builder()
								.id(ACTIVITY_ID_Complete)
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
		final DistributionJobId jobId = DistributionJobId.ofWFProcessId(wfProcessId);
		final DistributionJob job = distributionRestService.getJobById(jobId);
		return toWFProcess(job);
	}

	@Override
	public WFProcess changeWFProcessById(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final UnaryOperator<WFProcess> remappingFunction)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		return remappingFunction.apply(wfProcess);
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
						.caption(TranslatableStrings.adElementOrMessage("PickDate"))
						.value(job.getPickDate())
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
						.caption(TranslatableStrings.adElementOrMessage("M_LocatorFrom_ID"))
						.value(job.getSinglePickFromLocator().map(LocatorInfo::getCaption).orElse(null))
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("M_Warehouse_To_ID"))
						.value(job.getDropToWarehouse().getCaption())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("M_LocatorTo_ID"))
						.value(job.getSingleDropToLocator().map(LocatorInfo::getCaption).orElse(null))
						.build());

		final String salesOrderDocumentNo = job.getSalesOrderRef() != null ? job.getSalesOrderRef().getDocumentNo() : null;
		if (Check.isNotBlank(salesOrderDocumentNo))
		{
			builder.entry(WFProcessHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage("C_Order_DocumentNo"))
					.value(salesOrderDocumentNo)
					.build());
		}

		final String manufacturingOrderDocumentNo = job.getManufacturingOrderRef() != null ? job.getManufacturingOrderRef().getDocumentNo() : null;
		if (Check.isNotBlank(manufacturingOrderDocumentNo))
		{
			builder.entry(WFProcessHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage("PP_Order_DocumentNo"))
					.value(manufacturingOrderDocumentNo)
					.build());
		}

		if (Check.isNotBlank(job.getPlantName()))
		{
			builder.entry(WFProcessHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage("PP_Plant_ID"))
					.value(job.getPlantName())
					.build());
		}

		return builder.build();
	}

	public JsonGetNextEligiblePickFromLineResponse getNextEligiblePickFromLine(@NonNull final JsonGetNextEligiblePickFromLineRequest request, @NonNull final UserId callerId)
	{
		return distributionRestService.getNextEligiblePickFromLine(request, callerId);
	}

	public WFProcess processEvent(final JsonDistributionEvent event, final UserId callerId)
	{
		final DistributionJob job = distributionRestService.processEvent(event, callerId);
		return toWFProcess(job);
	}

	public void dropAll(final JsonDropAllRequest request, final UserId callerId)
	{
		distributionRestService.dropAll(request, callerId);
	}

	@Override
	public void logout(final @NonNull UserId userId)
	{
		abortAll(userId);
	}

	public WFProcess complete(@NonNull final WFProcessId wfProcessId, @NonNull final UserId callerId)
	{
		final DistributionJob job = distributionRestService.complete(DistributionJobId.ofWFProcessId(wfProcessId), callerId);
		return toWFProcess(job);
	}

	public void printMaterialInTransitReport(@NonNull final UserId userId, @NonNull final String adLanguage)
	{
		distributionRestService.printMaterialInTransitReport(userId, adLanguage);
	}
}
