package de.metas.distribution.workflows_api;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.distribution.workflows_api.activity_handlers.CompleteDistributionWFActivityHandler;
import de.metas.distribution.workflows_api.activity_handlers.MoveWFActivityHandler;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.service.WorkflowBasedMobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Component
public class DistributionMobileApplication implements WorkflowBasedMobileApplication
{
	@VisibleForTesting
	public static final MobileApplicationId HANDLER_ID = MobileApplicationId.ofString("distribution");

	private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.distribution.appName");
	private static final MobileApplicationInfo APPLICATION_INFO = MobileApplicationInfo.builder()
			.id(HANDLER_ID)
			.caption(TranslatableStrings.adMessage(MSG_Caption))
			.build();

	private final DistributionRestService distributionRestService;
	private final DistributionWorkflowLaunchersProvider wfLaunchersProvider;

	public DistributionMobileApplication(
			@NonNull final DistributionRestService distributionRestService)
	{
		this.distributionRestService = distributionRestService;
		this.wfLaunchersProvider = new DistributionWorkflowLaunchersProvider(distributionRestService);

	}

	@Override
	@NonNull
	public MobileApplicationInfo getApplicationInfo() {return APPLICATION_INFO;}

	@Override
	public WorkflowLaunchersList provideLaunchers(final @NonNull UserId userId, final @NonNull QueryLimit suggestedLimit, final @NonNull Duration maxStaleAccepted)
	{
		return wfLaunchersProvider.provideLaunchers(userId, suggestedLimit);
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

	private static WFProcess toWFProcess(final DistributionJob job)
	{
		return WFProcess.builder()
				.id(WFProcessId.ofIdPart(HANDLER_ID, job.getDdOrderId()))
				.invokerId(Objects.requireNonNull(job.getResponsibleId()))
				.caption(TranslatableStrings.anyLanguage("" + job.getDdOrderId().getRepoId()))
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
		distributionRestService.abort(wfProcess.getDocumentAs(DistributionJob.class));
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		throw new UnsupportedOperationException(); // TODO impl
	}

	@Override
	public WFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final DDOrderId ddOrderId = wfProcessId.getRepoId(DDOrderId::ofRepoId);
		final DistributionJob job = distributionRestService.getJobById(ddOrderId);
		return toWFProcess(job);
	}

	@Override
	public WFProcess changeWFProcessById(final WFProcessId wfProcessId, final UnaryOperator<WFProcess> remappingFunction)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		return remappingFunction.apply(wfProcess);
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(final @NonNull WFProcess wfProcess)
	{
		final DistributionJob job = wfProcess.getDocumentAs(DistributionJob.class);
		return WFProcessHeaderProperties.builder()
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
						.build())
				.build();
	}

	public void processEvent(final JsonDistributionEvent event, final UserId callerId)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(event.getWfProcessId());
		changeWFProcessById(
				wfProcessId,
				wfProcess -> {
					wfProcess.assertHasAccess(callerId);
					//assertPickingActivityType(jsonEvents, wfProcess);

					return wfProcess.<DistributionJob>mapDocument(job -> distributionRestService.processEvent(job, event));
				});
	}
}
