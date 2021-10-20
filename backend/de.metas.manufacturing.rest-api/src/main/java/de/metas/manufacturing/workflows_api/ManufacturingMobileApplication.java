package de.metas.manufacturing.workflows_api;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.service.MobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.springframework.stereotype.Component;

import java.time.Duration;
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

	private final ManufacturingWorkflowLaunchersProvider wfLaunchersProvider;

	public ManufacturingMobileApplication()
	{
		wfLaunchersProvider = new ManufacturingWorkflowLaunchersProvider();
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
		// final UserId invokerId = request.getInvokerId();
		// final ManufacturingWFProcessStartParams params = ManufacturingWFProcessStartParams.ofParams(request.getWfParameters());
		throw new UnsupportedOperationException(); // TODO
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
		throw new UnsupportedOperationException(); // TODO
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
