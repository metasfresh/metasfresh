package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.service.MobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Component
public class ManufacturingMobileApplication implements MobileApplication
{
	static final MobileApplicationId HANDLER_ID = MobileApplicationId.ofString("mfg");

	private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.manufacturing.appName");
	private static final MobileApplicationInfo APPLICATION_INFO = MobileApplicationInfo.builder()
			.id(HANDLER_ID)
			.caption(TranslatableStrings.adMessage(MSG_Caption))
			.build();

	private final ManufacturingRestService manufacturingRestService;
	private final ManufacturingWorkflowLaunchersProvider wfLaunchersProvider;

	public ManufacturingMobileApplication(
			@NonNull final ManufacturingRestService manufacturingRestService)
	{
		this.manufacturingRestService = manufacturingRestService;
		this.wfLaunchersProvider = new ManufacturingWorkflowLaunchersProvider(manufacturingRestService);
	}

	@Override
	@NonNull
	public MobileApplicationInfo getApplicationInfo() {return APPLICATION_INFO;}

	@Override
	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit,
			@NonNull final Duration maxStaleAccepted)
	{
		return wfLaunchersProvider.provideLaunchers(userId, suggestedLimit, maxStaleAccepted);
	}

	@Override
	public WFProcess startWorkflow(final WorkflowStartRequest request)
	{
		final UserId invokerId = request.getInvokerId();
		final ManufacturingWFProcessStartParams params = ManufacturingWFProcessStartParams.ofParams(request.getWfParameters());
		final PPOrderId ppOrderId = params.getPpOrderId();

		final ManufacturingJob job = manufacturingRestService.createJob(ppOrderId, invokerId);
		return toWFProcess(job);
	}

	private static WFProcess toWFProcess(final ManufacturingJob job)
	{
		return WFProcess.builder()
				.id(WFProcessId.ofIdPart(HANDLER_ID, job.getPpOrderId()))
				.invokerId(Objects.requireNonNull(job.getResponsibleId()))
				.caption(TranslatableStrings.anyLanguage("" + job.getPpOrderId().getRepoId())) // TODO
				.document(job)
				.activities(job.getActivities()
						.stream()
						.map(jobActivity -> WFActivity.builder()
								.id(WFActivityId.ofString(String.valueOf(jobActivity.getPpOrderRoutingActivityId().getRepoId())))
								.caption(TranslatableStrings.anyLanguage(jobActivity.getCode().getAsString()))
								.wfActivityType(ManufacturingActivityHandler.HANDLED_ACTIVITY_TYPE)
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Override
	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@Override
	public WFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final PPOrderId ppOrderId = wfProcessId.getRepoId(PPOrderId::ofRepoId);
		final ManufacturingJob job = manufacturingRestService.getJobById(ppOrderId);
		return toWFProcess(job);
	}

	@Override
	public WFProcess changeWFProcessById(final WFProcessId wfProcessId, final UnaryOperator<WFProcess> remappingFunction)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(final @NonNull WFProcess wfProcess)
	{
		return WFProcessHeaderProperties.EMPTY; // TODO
	}
}
