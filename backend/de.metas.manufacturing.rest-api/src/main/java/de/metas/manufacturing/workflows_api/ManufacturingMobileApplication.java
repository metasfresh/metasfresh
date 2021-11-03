package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.job.ManufacturingJob;
import de.metas.manufacturing.job.ManufacturingJobActivity;
import de.metas.manufacturing.workflows_api.activity_handlers.ConfirmationActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.MaterialReceiptActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.RawMaterialsIssueActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.WorkReportActivityHandler;
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
import org.adempiere.exceptions.AdempiereException;
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
		return wfLaunchersProvider.provideLaunchers(userId, suggestedLimit);
	}

	@Override
	public WFProcess startWorkflow(final WorkflowStartRequest request)
	{
		final UserId invokerId = request.getInvokerId();
		final PPOrderId ppOrderId = ManufacturingWFProcessStartParams.ofParams(request.getWfParameters()).getPpOrderId();

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
						.map(ManufacturingMobileApplication::toWFActivity)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static WFActivity toWFActivity(final ManufacturingJobActivity jobActivity)
	{
		final WFActivity.WFActivityBuilder builder = WFActivity.builder()
				.id(WFActivityId.ofId(jobActivity.getId()))
				.caption(TranslatableStrings.anyLanguage(jobActivity.getCode().getAsString()));

		switch (jobActivity.getType())
		{
			case RawMaterialsIssue:
				return builder.wfActivityType(RawMaterialsIssueActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case MaterialReceipt:
				return builder.wfActivityType(MaterialReceiptActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case WorkReport:
				return builder.wfActivityType(WorkReportActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case ActivityConfirmation:
				return builder.wfActivityType(ConfirmationActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			default:
				throw new AdempiereException("Unknown type: " + jobActivity);
		}
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
